package com.genentech.testcases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.genentech.baseclasses.BasePage;
import com.genentech.baseclasses.FSToolPage;
import com.genentech.util.TestCaseBase;

public class HouseHoldIncomeTest extends TestCaseBase {

	@Test
	public void householdIncomeTest() throws Exception {
		BasePage bp = new BasePage(pageManager, excelReader);
		FSToolPage fstool = new FSToolPage(pageManager, excelReader);

		String projectPath = System.getProperty("user.dir");
		String path = "/testdata/household_income_input.xlsx";
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		String date = df.format(new Date());
		Boolean loggedIn = false;

		JavascriptExecutor js = (JavascriptExecutor) pageManager.getDriver();
		// Create new workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet linkSheet;
		Row linkRow;
		String actualURL;

		try {
			// Existing workbook
			FileInputStream fileInput = new FileInputStream(projectPath + path);
			XSSFWorkbook inputWorkbook = new XSSFWorkbook(fileInput);
			XSSFSheet inputSheet = inputWorkbook.getSheetAt(0);

			// Create a new font and alter it.
			XSSFFont font = workbook.createFont();
			font.setBold(true);

			// Fonts are set into a style so create a new one to use.
			CellStyle style = workbook.createCellStyle();
			style.setFont(font);

			// Set column names for output file
			linkSheet = workbook.createSheet("HouseHoldIncome");
			linkRow = linkSheet.createRow(0);
			linkRow.createCell(0).setCellValue("URL");
			linkRow.createCell(1).setCellValue("Household Members");
			linkRow.createCell(2).setCellValue("Income");
			linkRow.createCell(3).setCellValue("Actual URL");

			for (int i = 0; i < 4; i++) {
				linkRow.getCell(i).setCellStyle(style);
			}

			int totalRows = inputSheet.getLastRowNum();
			if ((totalRows > 0) || (inputSheet.getPhysicalNumberOfRows() > 0)) {
				totalRows++;
			}

			String url = inputSheet.getRow(1).getCell(0).getStringCellValue();
			System.out.println(url);
			url = url.replace(".html", "/gpf-financial-eligibility.html");
			pageManager.navigateWithCacheClear(url); // This will clear all the cookies and HCP pop-up will be displayed

			if (url.contains("uat") && loggedIn == false) {
				bp.login("uat");
				loggedIn = true;
			} else if (url.contains("preview") || url.contains("preview2") && loggedIn == false) {
				bp.login("preview");
				loggedIn = true;
			}

			Select meetHouseholdDrpDwn = new Select(fstool.memberDrpDwn);
			List<WebElement> memberCount = meetHouseholdDrpDwn.getOptions();

			Select meetIncomedDrpDwn = new Select(fstool.incomeDrpDwn);
			List<WebElement> incomeCount = meetIncomedDrpDwn.getOptions();



			for (int i = 1; i < memberCount.size(); i++) {
				for (int k = 1; k < incomeCount.size(); k++) {

					// *********************Output*******************//
					int rowNum = linkSheet.getLastRowNum();
					linkRow = linkSheet.createRow(rowNum + 1);
					linkRow.createCell(0).setCellValue(url);

					meetHouseholdDrpDwn = new Select(fstool.memberDrpDwn);
					memberCount = meetHouseholdDrpDwn.getOptions();
					meetHouseholdDrpDwn.selectByIndex(i);
					linkRow.createCell(1).setCellValue(memberCount.get(i).getText());

					pageManager.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					meetIncomedDrpDwn = new Select(fstool.incomeDrpDwn);
					incomeCount = meetIncomedDrpDwn.getOptions();
					meetIncomedDrpDwn.selectByIndex(k);
					linkRow.createCell(2).setCellValue(incomeCount.get(k).getText());

					js.executeScript("arguments[0].click()", fstool.confirmBtn);
					actualURL = pageManager.getDriver().getCurrentUrl();
					linkRow.createCell(3).setCellValue(actualURL);

					pageManager.navigateWithCacheClear(url);
				}

			}

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(
					projectPath + "/test-output/houseHoldIncome/HouseHoldIncomeTest_" + date + ".xlsx");
			workbook.write(out);
			workbook.close();
			out.close();
			System.out.println("Excel Created!");
		}

	}

}
