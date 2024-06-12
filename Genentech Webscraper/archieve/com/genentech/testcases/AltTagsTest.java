package com.genentech.testcases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.genentech.baseclasses.BasePage;
import com.genentech.util.TestCaseBase;

public class AltTagsTest extends TestCaseBase {

	BasePage bp;

	@Test
	public void altTagTest() throws Exception, IOException {

		bp = new BasePage(pageManager, excelReader);

		String projectPath = System.getProperty("user.dir");
		String path = "/testdata/altTags_input_urls.xlsx";
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		String date = df.format(new Date());
		List<WebElement> allImg;
		String actual_imageName = null;
		String actual_altTag = null;
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
			linkSheet = workbook.createSheet("Alt Tags");
			linkRow = linkSheet.createRow(0);
			linkRow.createCell(0).setCellValue("URL");
			linkRow.createCell(1).setCellValue("Actual Image Name");
			linkRow.createCell(2).setCellValue("Actual Alt Tag");

			for (int i = 0; i < 3; i++) {
				linkRow.getCell(i).setCellStyle(style);
			}

			for (int j = 1; j < totalRows; j++) {
				String url = inputSheet.getRow(j).getCell(0).getStringCellValue(); // URL from input sheet

				if (url.contains("https://") || url.contains("http://")) {
					pageManager.navigate(url);
				} else {
					url = "https://" + url;
					pageManager.navigate(url);
				}

				if (url.contains("uat") && loggedIn == false) {
					pageManager.getDriver().findElement(By.xpath("//input[@id='username']"))
							.sendKeys("siddharth.jain@perficient.com");
					pageManager.getDriver().findElement(By.xpath("//input[@id='password']")).sendKeys("Qa@56KTf");
					pageManager.getDriver().findElement(By.xpath("//input[@value='Login']")).click();
					loggedIn = true;
				} else if (url.contains("preview") && loggedIn == false) {
					pageManager.getDriver().findElement(By.xpath("//input[@id='username']"))
							.sendKeys("siddharth.jain@perficient.com");
					pageManager.getDriver().findElement(By.xpath("//input[@id='password']")).sendKeys("Qa@56KTf");
					pageManager.getDriver().findElement(By.xpath("//input[@value='Login']")).click();
					loggedIn = true;
					}

				allImg = pageManager.getDriver().findElements(By.tagName("img"));
				System.out.println("Number of image tags - " + allImg.size());

				for (int i = 0; i < allImg.size(); i++) {
					allImg = pageManager.getDriver().findElements(By.tagName("img"));
					try {
						actual_imageName = allImg.get(i).getAttribute("src");
						actual_imageName = actual_imageName.substring(actual_imageName.lastIndexOf("/") + 1);
						System.out.println(actual_imageName);
						actual_altTag = allImg.get(i).getAttribute("alt");
						System.out.println(actual_altTag);
						int rowNum = linkSheet.getLastRowNum();
						linkRow = linkSheet.createRow(rowNum + 1);
						linkRow.createCell(0).setCellValue(url);							
						linkRow.createCell(1).setCellValue(actual_imageName);								
						linkRow.createCell(2).setCellValue(actual_altTag);	
					} catch (Exception exception) {
						System.out.println(exception.getMessage());
						continue;
					}
				}
			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(
					projectPath + "/test-output/altTags/AltTagTest_" + date + ".xlsx");
			workbook.write(out);
			workbook.close();
			out.close();
			System.out.println("Excel Created!");

		}
	}
}
