package com.genentech.testcases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.genentech.baseclasses.BasePage;
import com.genentech.util.TestCaseBase;

public class TitleMeta_Test extends TestCaseBase {
	BasePage bp;

	@Test
	public void titleMetaTest() throws IOException {
		bp = new BasePage(pageManager, excelReader);

		String projectPath = System.getProperty("user.dir");
		String path = "/testdata/title_meta_input.xlsx";
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		String date = df.format(new Date());
		Boolean loggedIn = false;
		String actual_meta_description;
		WebElement bs; // for bootstrap
		String title;
		String meta_description;

		// Create new workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet linkSheet_title, linkSheet_meta;
		Row linkRow;

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
			linkSheet_title = workbook.createSheet("Title Output");
			linkRow = linkSheet_title.createRow(0);
			linkRow.createCell(0).setCellValue("Url");
			linkRow.createCell(1).setCellValue("Expected Title");
			linkRow.createCell(2).setCellValue("Actual Title");
			linkRow.createCell(3).setCellValue("Status");

			linkSheet_meta = workbook.createSheet("Meta Output");
			linkRow = linkSheet_meta.createRow(0);
			linkRow.createCell(0).setCellValue("Url");
			linkRow.createCell(1).setCellValue("Expected Meta");
			linkRow.createCell(2).setCellValue("Actual Meta");
			linkRow.createCell(3).setCellValue("Status");
			linkRow.createCell(4).setCellValue("Bootstrap");
			linkRow.createCell(5).setCellValue("HTML5");

//			for (int i = 0; i < 7; i++) {
//				linkRow.getCell(i).setCellStyle(style);
//			}

			for (int j = 1; j < totalRows; j++) {

				// Values from the input files
				String url = inputSheet.getRow(j).getCell(0).getStringCellValue();
				url = url.trim();

				cell = inputSheet.getRow(j).getCell(1, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					title = cell.getStringCellValue();
					title = title.trim();
				} else {
					title = "";
				}

				cell = inputSheet.getRow(j).getCell(2, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					meta_description = cell.getStringCellValue();
					meta_description = meta_description.trim();
				} else {
					meta_description = "";
				}

				pageManager.navigate(url);

				// Environment Login
				if (url.contains("uat") && loggedIn == false) {
					bp.login("uat");
					loggedIn = true;
				} else if (url.contains("preview") && loggedIn == false) {
					bp.login("preview");
					loggedIn = true;
				}

				System.out.println(url);

				// Get the Actual Title from the site
				String actual_title = pageManager.getDriver().getTitle();
				actual_title = actual_title.trim();

				// Get the Meta description Title from the site
				try {
					actual_meta_description = pageManager.getDriver()
							.findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
					System.out.println(actual_meta_description);

				} catch (NoSuchElementException e) {
					actual_meta_description = "";
				}
				// Write in output file
				int rowNum_title = linkSheet_title.getLastRowNum();
				linkRow = linkSheet_title.createRow(rowNum_title + 1);
				linkRow.createCell(0).setCellValue(url);
				linkRow.createCell(1).setCellValue(title);
				linkRow.createCell(2).setCellValue(actual_title);
				// Comparing Actual and Destination URLs
				if (title.equals(actual_title)) {
					linkRow.createCell(3).setCellValue("Pass");
				} else {
					linkRow.createCell(3).setCellValue("Fail");
				}

				// Write in output file
				int rowNum_meta = linkSheet_meta.getLastRowNum();
				linkRow = linkSheet_meta.createRow(rowNum_meta + 1);
				linkRow.createCell(0).setCellValue(url);
				linkRow.createCell(1).setCellValue(meta_description);
				linkRow.createCell(2).setCellValue(actual_meta_description);

				// Comparing Actual and Destination URLs
				if (meta_description.equals(actual_meta_description)) {
					linkRow.createCell(3).setCellValue("Pass");
				} else {
					linkRow.createCell(3).setCellValue("Fail");
				}

				/*****************************************
				 * BOOTSTRAP
				 **********************************************/

				try {
					if (url.contains("uat")) {
						bs = pageManager.getDriver()
								.findElement(By.xpath("//script[@src='//nexus.ensighten.com/gene/dev/Bootstrap.js']"));
						String x = bs.getAttribute("src");
						String y[] = x.split("/");
						System.out.println("BootStrap:" + y[4]);
						linkRow.createCell(4).setCellValue(y[4]);

					} else if (url.contains("preview")) {
						bs = pageManager.getDriver().findElement(
								By.xpath("//script[@src='//nexus.ensighten.com/gene/stage/Bootstrap.js']"));
						String x = bs.getAttribute("src");
						String y[] = x.split("/");
						System.out.println("BootStrap:" + y[4]);
						linkRow.createCell(4).setCellValue(y[4]);
					} else {
						bs = pageManager.getDriver()
								.findElement(By.xpath("//script[@src='//nexus.ensighten.com/gene/prod/Bootstrap.js']"));
						String x = bs.getAttribute("src");
						String y[] = x.split("/");
						System.out.println("BootStrap:" + y[4]);
						linkRow.createCell(4).setCellValue(y[4]);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				/***********************************
				 * xsdidataLayer
				 ******************************/
				try {
					bs = pageManager.getDriver()
							.findElement(By.xpath("//script[@data-adobe-client-data-layers = 'xsdidatalayer']"));
					String dataLayer = bs.getAttribute("data-adobe-client-data-layers");
					linkRow.createCell(5).setCellValue(dataLayer);
				} catch (Exception e) {
					linkRow.createCell(5).setCellValue("Not found");
				}
			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(
					projectPath + "/test-output/titlemeta/TitleMetaTest_" + date + ".xlsx");
			workbook.write(out);
			workbook.close();
			out.close();
			System.out.println("Excel Created!");
		}
	}
}
