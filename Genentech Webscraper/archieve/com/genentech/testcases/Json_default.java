package com.genentech.testcases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.genentech.baseclasses.BasePage;
import com.genentech.util.TestCaseBase;


public class Json_default extends TestCaseBase {
	BasePage bp;

	@Test
	public void jsonValuesTest() throws Exception {

		bp = new BasePage(pageManager, excelReader);

		String projectPath = System.getProperty("user.dir");
		String path = "/testdata/json_inputs.xlsx";
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		String date = df.format(new Date());
		Boolean loggedIn = false;

		// Create new workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet linkSheet;
		Row linkRow;
		String sitename;
		String indication;
		String siteaudience;
		String topic;

		try {
			// Existing workbook
			FileInputStream fileInput = new FileInputStream(projectPath + path);
			XSSFWorkbook inputWorkbook = new XSSFWorkbook(fileInput);
			inputWorkbook.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Cell cell;
			XSSFSheet inputSheet = inputWorkbook.getSheetAt(0);
			// String result;

			int totalRows = inputSheet.getLastRowNum();
			if ((totalRows > 0) || (inputSheet.getPhysicalNumberOfRows() > 0)) {
				totalRows++;
			}

			System.out.println("Rows count: " + totalRows);

			// Create a new font and alter it.
			XSSFFont font = workbook.createFont();
			font.setBold(true);

			// Fonts are set into a style so create a new one to use.
			CellStyle style = workbook.createCellStyle();
			style.setFont(font);

			// Set column names for output file
			linkSheet = workbook.createSheet("JSON output");
			linkRow = linkSheet.createRow(0);
			linkRow.createCell(0).setCellValue("URLs");
			linkRow.createCell(1).setCellValue("Expected SiteName");
			linkRow.createCell(2).setCellValue("Actual SiteName");
			linkRow.createCell(3).setCellValue("Status SiteName");
			
			linkRow.createCell(4).setCellValue("Expected siteAudience");
			linkRow.createCell(5).setCellValue("Actual siteAudience");
			linkRow.createCell(6).setCellValue("Status siteAudience");
			
			linkRow.createCell(7).setCellValue("Expected indication");
			linkRow.createCell(8).setCellValue("Actual indication");
			linkRow.createCell(9).setCellValue("Status indication");
			
			linkRow.createCell(10).setCellValue("Expected Topic");
			linkRow.createCell(11).setCellValue("Actual Topic");
			linkRow.createCell(12).setCellValue("Status Topic");

			for (int j = 0; j < 13; j++) {
				linkRow.getCell(j).setCellStyle(style);
			}

			for (int i = 1; i < totalRows; i++) {

				String url = inputSheet.getRow(i).getCell(0).getStringCellValue();
				url = url.trim();
				
				
				cell = inputSheet.getRow(i).getCell(1, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					sitename = cell.getStringCellValue();					
				} else {
					sitename = "";
				}
				
				cell = inputSheet.getRow(i).getCell(2, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					indication = cell.getStringCellValue();					
				} else {
					indication = "";
				}
				
				cell = inputSheet.getRow(i).getCell(3, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					siteaudience = cell.getStringCellValue();					
				} else {
					siteaudience = "";
				}
				
				cell = inputSheet.getRow(i).getCell(4, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					topic = cell.getStringCellValue();					
				} else {
					topic = "";
				}

				pageManager.navigate(url);
				pageManager.waitForSeconds(5000);
				// Environment Login
				if (url.contains("uat") && loggedIn == false) {
					bp.login("uat");
					loggedIn = true;
				} else if (url.contains("preview") && loggedIn == false) {
					bp.login("preview");
					loggedIn = true;
				}
				pageManager.waitForSeconds(5000);

				try {
					ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
					JavascriptExecutor j = (JavascriptExecutor) getDriver();
					Object t = j.executeScript("return dataLayer");
					String json = ow.writeValueAsString(t);
					int j1 = json.indexOf("{");
					json = json.substring(j1);
					System.out.println(json);
					// result = result.substring(j1);
					JSONObject jsonobject = new JSONObject(json);
					String s1 = (jsonobject.getString("siteName"));
					String s2 = (jsonobject.getString("siteAudience"));
					String s3 = (jsonobject.getString("indication"));
					String s4 = (jsonobject.getString("topic"));
					int rowNum = linkSheet.getLastRowNum();
					linkRow = linkSheet.createRow(rowNum + 1);
					linkRow.createCell(0).setCellValue(url);
					//SiteName
					linkRow.createCell(1).setCellValue(sitename);
					linkRow.createCell(2).setCellValue(s1);
					if (sitename.equals(s1)) {
						linkRow.createCell(3).setCellValue("Pass");
					} else {
						linkRow.createCell(3).setCellValue("Fail");
					}
					
					//SiteAudience
					linkRow.createCell(4).setCellValue(siteaudience);
					linkRow.createCell(5).setCellValue(s2);
					if (siteaudience.equals(s2)) {
						linkRow.createCell(6).setCellValue("Pass");
					} else {
						linkRow.createCell(6).setCellValue("Fail");
					}
					
					//Indication
					linkRow.createCell(7).setCellValue(indication);
					linkRow.createCell(8).setCellValue(s3);
					if (indication.equals(s3)) {
						linkRow.createCell(9).setCellValue("Pass");
					} else {
						linkRow.createCell(9).setCellValue("Fail");
					}
					
					//Topic
					linkRow.createCell(10).setCellValue(topic);
					linkRow.createCell(11).setCellValue(s4);
					if (topic.equals(s4)) {
						linkRow.createCell(12).setCellValue("Pass");
					} else {
						linkRow.createCell(12).setCellValue("Fail");
					}
				} catch (JavascriptException e) {
					int rowNum = linkSheet.getLastRowNum();
					linkRow = linkSheet.createRow(rowNum + 1);
					linkRow.createCell(0).setCellValue(url);
					
					linkRow.createCell(1).setCellValue(sitename);
					linkRow.createCell(2).setCellValue("No dataLayer present");
					linkRow.createCell(3).setCellValue("Fail");
					
					linkRow.createCell(4).setCellValue(siteaudience);
					linkRow.createCell(5).setCellValue("No dataLayer present");
					linkRow.createCell(6).setCellValue("Fail");
					
					linkRow.createCell(7).setCellValue(indication);
					linkRow.createCell(8).setCellValue("No dataLayer present");
					linkRow.createCell(9).setCellValue("Fail");
					
					linkRow.createCell(10).setCellValue(topic);
					linkRow.createCell(11).setCellValue("No dataLayer present");
					linkRow.createCell(12).setCellValue("Fail");
					
					System.err.println(e.getMessage());
				}

			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(
					projectPath + "/test-output/jsonvalues/JSONValueTest_" + date + ".xlsx");
			workbook.write(out);
			workbook.close();
			out.close();
			System.out.println("Excel Created!");
		}

	}

}
