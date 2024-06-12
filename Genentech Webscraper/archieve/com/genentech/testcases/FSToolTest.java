package com.genentech.testcases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.genentech.baseclasses.BasePage;
import com.genentech.baseclasses.FSToolPage;
import com.genentech.util.TestCaseBase;

public class FSToolTest extends TestCaseBase {

//	// ************************************************ FS TOOL SCENARIOS
//	// ******
	@Test(priority = 1)
	public void fsToolTest() throws Exception {
		BasePage bp = new BasePage(pageManager, excelReader);
		FSToolPage fstool = new FSToolPage(pageManager, excelReader);
		String projectPath = System.getProperty("user.dir");
		String path = "/testdata/fs_tool_input.xlsx";
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		String date = df.format(new Date());
		Boolean loggedIn = false;
		String houseHold_actual_Url = "";

		JavascriptExecutor js = (JavascriptExecutor) pageManager.getDriver();
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
			linkSheet = workbook.createSheet("FS Tool Output");
			linkRow = linkSheet.createRow(0);
			linkRow.createCell(0).setCellValue("URL");
			linkRow.createCell(1).setCellValue("For which of the following indications?");
			linkRow.createCell(2).setCellValue("What type of primary insurance?");
			linkRow.createCell(3).setCellValue("Receiving any other form of financial assistance?");
			linkRow.createCell(4).setCellValue("What type of assistance?");
			linkRow.createCell(5).setCellValue("Does insurance cover Brand X?");
			linkRow.createCell(6).setCellValue("Meets Household Size/Income Limit");
			linkRow.createCell(7).setCellValue("Expected Landing URL");
			linkRow.createCell(8).setCellValue("Actual Landing URL");
			linkRow.createCell(9).setCellValue("Status");

			for (int i = 0; i < 10; i++) {
				linkRow.getCell(i).setCellStyle(style);
			}

			for (int j = 1; j < totalRows; j++) {
				String url = inputSheet.getRow(j).getCell(0).getStringCellValue(); // URL from input sheet
				String indications = inputSheet.getRow(j).getCell(1).getStringCellValue();
				String insType = inputSheet.getRow(j).getCell(2).getStringCellValue();
				String financeAssist = inputSheet.getRow(j).getCell(3).getStringCellValue();
				String typeOfAssist = inputSheet.getRow(j).getCell(4).getStringCellValue();
				String insCover = inputSheet.getRow(j).getCell(5).getStringCellValue();
				String householdTool = inputSheet.getRow(j).getCell(6).getStringCellValue();
				String expectedURL = inputSheet.getRow(j).getCell(7).getStringCellValue();

				System.out.println("Scenario:" + j);

				pageManager.navigateWithCacheClear(url); // This will clear all the cookies and HCP pop-up will be
															// displayed

				if (url.contains("uat") && loggedIn == false) {
					bp.login("uat");
					loggedIn = true;
				} else if (url.contains("preview2") || url.contains("preview") && loggedIn == false) {
					bp.login("preview");
					loggedIn = true;
				}

//				try {
//					// Handling the CCPA pop-up
//					pageManager.switchToNewWindow();
//					js.executeScript("arguments[0].click()", fstool.ccpaBtn);
//					pageManager.switchToMainWindow();
//					// *******************************//
//				} catch (Exception exception) {
//					System.out.println("CCPA accepted");
//				}
				
				try {
				// Handling the HCP pop-up
				pageManager.switchToNewWindow();
				js.executeScript("arguments[0].click()", fstool.hcpPopup);
				//pageManager.switchToMainWindow();
				// *******************************//
			} catch (Exception exception) {
				System.out.println("HCP accepted");
			}
				

				// ******************************************** Question No.1
				// *****************************************//
				Select indicationDrpDwn;
				indicationDrpDwn = new Select(fstool.dropDown);
				if (indications.equals("Approved FDA Indication")) {
					indicationDrpDwn.selectByIndex(1);
				} else if (indications.equals("None of the above")) {
					int selectOptions = indicationDrpDwn.getOptions().size();
					indicationDrpDwn.selectByIndex(selectOptions - 1);
				}
				Thread.sleep(2000);

				// ******************************************** Question No.2
				// ******************************************//
				if (insType.equals("Private / Commercial")) {
					js.executeScript("arguments[0].click()", fstool.privateRadioBtn);
				} else if (insType.equals("Public (Government)")) {
					js.executeScript("arguments[0].click()", fstool.pubRadioBtn);
				} else {
					js.executeScript("arguments[0].click()", fstool.noneRadioBtn);
				}
				Thread.sleep(2000);

				// ******************************************** Question No.3
				// ******************************************//
				if (financeAssist.equals("Y")) {
					js.executeScript("arguments[0].click()", fstool.yesOARadioBtn);
				} else {
					js.executeScript("arguments[0].click()", fstool.noOARadioBtn);
				}
				Thread.sleep(2000);

				// ****************************************** Question No.4
				// ******************************************//
				if (typeOfAssist.equals("Co-pay Assistance Program")) {
					js.executeScript("arguments[0].click()", fstool.coPayRadioBtn);
				} else if (typeOfAssist.equals("Genentech Patient Foundation")) {
					js.executeScript("arguments[0].click()", fstool.patientFoundationRadioBtn);

				} else if (typeOfAssist.equals("Assistance from any other charitable organization")) {
					js.executeScript("arguments[0].click()", fstool.independentRadioBtn);
				} else if (typeOfAssist.equals("N/A")) {
					try {
						new WebDriverWait(getDriver(), Duration.ofSeconds(2))
								.until(ExpectedConditions.textToBePresentInElement(fstool.yesCBIRadioBtn, "Yes"));
					} catch (Exception e) {
						System.out.print("");
						// rest of code block
					}
				}
				Thread.sleep(2000);
				// ****************************************** Question No.5
				// ******************************************//
				if (insCover.equals("Y")) {
					js.executeScript("arguments[0].click()", fstool.yesCBIRadioBtn);
				} else if (insCover.equals("N")) {
					js.executeScript("arguments[0].click()", fstool.noCBIRadioBtn);
				} else if (insCover.equals("Not sure")) {
					js.executeScript("arguments[0].click()", fstool.unsureCBIRadioBtn);
				} else {
					try {
						new WebDriverWait(getDriver(), Duration.ofSeconds(2))
								.until(ExpectedConditions.textToBePresentInElement(fstool.submitBtn, "Next"));
					} catch (Exception e) {
						System.out.print("");
						// rest of code block
					}
				}
				Thread.sleep(2000);
				// ****************************************** Submit
				// ******************************************//
				js.executeScript("arguments[0].scrollIntoView();", fstool.submitBtn);
				fstool.submitBtn.click();
				String actual_Url = pageManager.getDriver().getCurrentUrl();
				System.out.println(actual_Url);

				// ****************************************** Output
				// ******************************************//
				int rowNum = linkSheet.getLastRowNum();
				linkRow = linkSheet.createRow(rowNum + 1);
				linkRow.createCell(0).setCellValue(url);
				linkRow.createCell(1).setCellValue(indications);
				linkRow.createCell(2).setCellValue(insType);
				linkRow.createCell(3).setCellValue(financeAssist);
				linkRow.createCell(4).setCellValue(typeOfAssist);
				linkRow.createCell(5).setCellValue(insCover);
				linkRow.createCell(6).setCellValue(householdTool);
				linkRow.createCell(7).setCellValue(expectedURL);

				// ****************************************** Household Income tool
				// ******************************************//
				if (actual_Url.contains("gpf-financial-eligibility.html")) {

					Select meetHouseholdDrpDwn = new Select(fstool.memberDrpDwn);
					Select meetIncomedDrpDwn = new Select(fstool.incomeDrpDwn);
					if (householdTool.equals("Y")) {
						meetHouseholdDrpDwn.selectByIndex(1);
						meetIncomedDrpDwn.selectByIndex(1);
					} else if (householdTool.equals("N")) {
						meetHouseholdDrpDwn.selectByIndex(1);
						meetIncomedDrpDwn.selectByIndex(5);
					}

					js.executeScript("arguments[0].click()", fstool.confirmBtn);
					houseHold_actual_Url = pageManager.getDriver().getCurrentUrl();
					System.out.println(houseHold_actual_Url);
					linkRow.createCell(8).setCellValue(houseHold_actual_Url);
				} else {
					linkRow.createCell(8).setCellValue(actual_Url);
				}

				if (actual_Url.contains(expectedURL) || houseHold_actual_Url.contains(expectedURL)) {
					linkRow.createCell(9).setCellValue("Pass");
				} else {
					linkRow.createCell(9).setCellValue("Fail");
				}

			}

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(
					projectPath + "/test-output/fstool/fstoolv2/FSToolTest_" + date + ".xlsx");
			workbook.write(out);
			workbook.close();
			out.close();
			System.out.println("Excel Created!");
		}

	}
}
