package com.genentech.testcases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.genentech.baseclasses.BasePage;
import com.genentech.util.TestCaseBase;

public class RedirectsTest extends TestCaseBase {
	BasePage bp;
	@Test
	public void redirectsURL() throws Exception, IOException {
		bp = new BasePage(pageManager, excelReader);

		String projectPath = System.getProperty("user.dir");
		String path = "/testdata/redirects_input_urls.xlsx";
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		String date = df.format(new Date());
		Boolean loggedIn = false;

		// Create new workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet linkSheet;
		Row linkRow;

		try {
			// Existing workbook
			FileInputStream fileInput = new FileInputStream(projectPath + path);
			XSSFWorkbook inputWorkbook = new XSSFWorkbook(fileInput);
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
			linkSheet = workbook.createSheet("DestinationUrl");
			linkRow = linkSheet.createRow(0);
			linkRow.createCell(0).setCellValue("Source Url");
			linkRow.createCell(1).setCellValue("Destination URL");
			linkRow.createCell(2).setCellValue("Actual URL");
			linkRow.createCell(3).setCellValue("Result");
			linkRow.createCell(4).setCellValue("Status");

			for (int i = 0; i < 4; i++) {
				linkRow.getCell(i).setCellStyle(style);
			}

			for (int j = 1; j < totalRows; j++) {

				// Values from the input files
				String source_url = inputSheet.getRow(j).getCell(0).getStringCellValue();
				source_url = source_url.trim();
				String destination_Url = inputSheet.getRow(j).getCell(1).getStringCellValue();
				destination_Url = destination_Url.trim();

				if (source_url.contains("https://") || source_url.contains("http://")) {
					pageManager.navigate(source_url);
				} else {
					source_url = "https://" + source_url;
					pageManager.navigate(source_url);
				}
				
				if (source_url.contains("uat") && loggedIn == false) {
					bp.login("uat");
					loggedIn = true;
				} else if (source_url.contains("preview") && loggedIn == false) {
					bp.login("preview");
					loggedIn = true;
				}

				System.out.print("url:" + source_url);
				System.out.println("");
				System.out.print("Destination URL:" + destination_Url);
				System.out.println("");

				// Get the Actual URL from the site
				String actual_Url = pageManager.getDriver().getCurrentUrl();
				 HttpURLConnection status = (HttpURLConnection)new URL(actual_Url).openConnection();
				 // set the HEAD request with setRequestMethod
				 status.setRequestMethod("HEAD");
			     // connection started and get response code
				 status.connect();
			     int response_code = status.getResponseCode();
				actual_Url = actual_Url.trim();
				System.out.print("Actual url:" + actual_Url);
				System.out.println("");

				// Write in output file

				int rowNum = linkSheet.getLastRowNum();
				linkRow = linkSheet.createRow(rowNum + 1);
				linkRow.createCell(0).setCellValue(source_url);
				linkRow.createCell(1).setCellValue(destination_Url);
				linkRow.createCell(2).setCellValue(actual_Url);
				linkRow.createCell(4).setCellValue(response_code);

				//Comparing Actual and Destination URLs 
				if (destination_Url.equals(actual_Url) || actual_Url.equals(destination_Url+"/")) {
					linkRow.createCell(3).setCellValue("Pass");
				} else {
					linkRow.createCell(3).setCellValue("Fail");
				}				
			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(
					projectPath + "/test-output/redirects/RedirectsTest_" + date + ".xlsx");
			workbook.write(out);
			workbook.close();
			out.close();
			System.out.println("Excel Created!");
		}
	}
} 