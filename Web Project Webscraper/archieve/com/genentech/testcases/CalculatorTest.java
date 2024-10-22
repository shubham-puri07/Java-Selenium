package com.genentech.testcases;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

import com.genentech.baseclasses.BasePage;
import com.genentech.baseclasses.CalculatorPage;
import com.genentech.util.TestCaseBase;

public class CalculatorTest extends TestCaseBase {

//	@Test(priority = 1)
//	public void calculatorTestRA() throws Exception, IOException {
//		BasePage bp = new BasePage(pageManager, database, excelReader, ftpTransfer);
//		CalculatorPage cal = new CalculatorPage(pageManager, database, excelReader, ftpTransfer);
//
//		String projectPath = System.getProperty("user.dir");
//		String path = "/testdata/calculator_input.xlsx";
//
//		Boolean loggedIn = false;
//
//		// Create new workbook
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		XSSFSheet linkSheet_RA;
//		Row linkRow;
//		String mgVials = null;
//		String vials = null;
//
//		try {
//			// Existing workbook
//			FileInputStream fileInput = new FileInputStream(projectPath + path);
//			XSSFWorkbook inputWorkbook = new XSSFWorkbook(fileInput);
//			XSSFSheet inputSheet = inputWorkbook.getSheetAt(0); // Input sheet RA 4mg
//			DataFormatter formatter = new DataFormatter();
//
//			// String result;
//
//			int totalRows = inputSheet.getLastRowNum();
//			if ((totalRows > 0) || (inputSheet.getPhysicalNumberOfRows() > 0)) {
//				totalRows++;
//			}
//
//			System.out.println("Rows count: " + totalRows);
//
//			// Create a new font and alter it.
//			XSSFFont font = workbook.createFont();
//			font.setBold(true);
//
//			// Fonts are set into a style so create a new one to use.
//			CellStyle style = workbook.createCellStyle();
//			style.setFont(font);
//
//			// Set column names for output sheet RA file
//			linkSheet_RA = workbook.createSheet("CalculatorOutput_RA4Mg");
//			linkRow = linkSheet_RA.createRow(0);
//			linkRow.createCell(0).setCellValue("URL");
//			linkRow.createCell(1).setCellValue("Weight");
//
//			linkRow.createCell(2).setCellValue("Expected_mg");
//			linkRow.createCell(3).setCellValue("Actual_mg");
//			linkRow.createCell(4).setCellValue("Status_mg");
//
//			linkRow.createCell(5).setCellValue("Expected_mL");
//			linkRow.createCell(6).setCellValue("Actual_mL");
//			linkRow.createCell(7).setCellValue("Status_mL");
//
//			linkRow.createCell(8).setCellValue("Expected_80 mg vial(s)");
//			linkRow.createCell(9).setCellValue("Actual_80 mg vial(s)");
//			linkRow.createCell(10).setCellValue("Status_80mg vial(s)");
//			// linkRow.createCell(11).setCellValue("80 mg quantity");
//
//			linkRow.createCell(11).setCellValue("Expected_200 mg vial(s)");
//			linkRow.createCell(12).setCellValue("Actual_200 mg vial(s)");
//			linkRow.createCell(13).setCellValue("Status_200mg vial(s)");
//			// linkRow.createCell(15).setCellValue("200 mg quantity");
//
//			linkRow.createCell(14).setCellValue("Expected_400 mg vial(s)");
//			linkRow.createCell(15).setCellValue("Actual_400 mg vial(s)");
//			linkRow.createCell(16).setCellValue("Status_400mg vial(s)");
//			// linkRow.createCell(19).setCellValue("400 mg quantity");
//
//			linkRow.createCell(17).setCellValue("Expected_Unused Quantity");
//			linkRow.createCell(18).setCellValue("Actual_Unused Quantity");
//			linkRow.createCell(19).setCellValue("Status_Unused Quantity");
//
//			for (int i = 0; i < 1; i++) {
//				linkRow.getCell(i).setCellStyle(style);
//			}
//
//			for (int j = 1; j <= totalRows; j++) {
//
//				String url = inputSheet.getRow(j).getCell(0).getStringCellValue(); // URL from input sheet
//				String weight = formatter.formatCellValue(inputSheet.getRow(j).getCell(1));
//				String expected_mg = inputSheet.getRow(j).getCell(2).getStringCellValue();
//				expected_mg = expected_mg.trim();
//				String expected_mL = inputSheet.getRow(j).getCell(3).getStringCellValue();
//				expected_mL = expected_mL.trim();
//				String expected_80mgVials = inputSheet.getRow(j).getCell(4).getStringCellValue();
//				expected_80mgVials = expected_80mgVials.trim();
//				String expected_200mgVials = inputSheet.getRow(j).getCell(5).getStringCellValue();
//				expected_200mgVials = expected_200mgVials.trim();
//				String expected_400mgVials = inputSheet.getRow(j).getCell(6).getStringCellValue();
//				expected_400mgVials = expected_400mgVials.trim();
//				String expected_UnusedVials = inputSheet.getRow(j).getCell(7).getStringCellValue();
//				expected_UnusedVials = expected_UnusedVials.trim();
//
//				System.out.println("Weight: " + weight);
//				// String weight = inputSheet.getRow(j).getCell(1).getStringCellValue();
//				// //Weight
//
//				pageManager.navigateWithCacheClear(url); //This will clear all the cookies and HCP pop-up will be displayed
//
//				if (url.contains("uat") && loggedIn == false) {
//					bp.login("uat");
//					loggedIn = true;
//				} else if (url.contains("author") && loggedIn == false) {
//					bp.login("author");
//					loggedIn = true;
//				}				
//			
//				cal.calculateDosageRA(weight); // Calculator RA indication				
//				
//				String mgML = cal.purpleTxt.getText(); // Purple Text mg/ml
//
//				String qtyMG = mgML.substring(0, mgML.lastIndexOf("/"));
//				qtyMG = qtyMG.trim();
//				String qtyML = mgML.substring(mgML.lastIndexOf("/") + 1);
//				qtyML = qtyML.trim();
//				System.out.println("Purple Text: " + mgML);
//
//				String unusedValue = cal.unusedQty.getText(); // unused quantity
//				unusedValue = unusedValue.substring(13);
//				unusedValue = unusedValue.trim();
//				System.out.println("Unused Text: " + unusedValue);
//
//				int rowNum = linkSheet_RA.getLastRowNum();
//				linkRow = linkSheet_RA.createRow(rowNum + 1);
//				linkRow.createCell(0).setCellValue(url);
//				linkRow.createCell(1).setCellValue(weight);
//				// ***********Expected and Actual Mg comparison*******************//
//				linkRow.createCell(2).setCellValue(expected_mg);
//				linkRow.createCell(3).setCellValue(qtyMG);
//
//				if (qtyMG.equals(expected_mg)) {
//					linkRow.createCell(4).setCellValue("Pass");
//				} else {
//					linkRow.createCell(4).setCellValue("Fail");
//				}
//
//				// ***********Expected and Actual mL comparison*******************//
//				linkRow.createCell(5).setCellValue(expected_mL);
//				linkRow.createCell(6).setCellValue(qtyML);
//				if (qtyML.equals(expected_mL)) {
//					linkRow.createCell(7).setCellValue("Pass");
//				} else {
//					linkRow.createCell(7).setCellValue("Fail");
//				}
//
//				// ***********Expected and Actual unused comparison*******************//
//				linkRow.createCell(17).setCellValue(expected_UnusedVials);
//				linkRow.createCell(18).setCellValue(unusedValue);
//				if (unusedValue.equals(expected_UnusedVials)) {
//					linkRow.createCell(19).setCellValue("Pass");
//				} else {
//					linkRow.createCell(19).setCellValue("Fail");
//				}
//				// **************************Setting Expected Vials for Comparison in o/p file***********************************//
//				linkRow.createCell(8).setCellValue(expected_80mgVials);
//				linkRow.createCell(11).setCellValue(expected_200mgVials);
//				linkRow.createCell(14).setCellValue(expected_400mgVials);
//
//				// **************************Loop for Vials************************************//
//
//				for (int i = 1; i <= cal.vialsCol.size(); i++) {
//					try {
//
//						mgVials = cal.vialsCol.get(i - 1).findElement(By.xpath(
//								"//*[@id='results-dosing-ra']/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div/div["
//										+ i + "]/div/div/div/div[1]/div/p[1]/strong"))
//								.getText();
//						mgVials = mgVials.trim();
//
//						vials = cal.vialsCol.get(i - 1).findElement(By.xpath(
//								"//*[@id='results-dosing-ra']/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div/div["
//										+ i + "]/div/div/div/div[3]/div/p"))
//								.getText();
//						vials = vials.trim();
//
//						switch (mgVials) {
//						case "80 mg":
//							linkRow.createCell(9).setCellValue(vials);
////							linkRow.createCell(11).setCellValue(mgVials);
////							linkRow.createCell(15).setCellValue("200 mg");
////							linkRow.createCell(19).setCellValue("400 mg");
//
//							break;
//						case "200 mg":
//							linkRow.createCell(12).setCellValue(vials);
////							linkRow.createCell(15).setCellValue(mgVials);
////							linkRow.createCell(11).setCellValue("80 mg");
////							linkRow.createCell(19).setCellValue("400 mg");
//							break;
//						case "400 mg":
//							linkRow.createCell(15).setCellValue(vials);
////							linkRow.createCell(19).setCellValue(mgVials);
////							linkRow.createCell(15).setCellValue("200 mg");
////							linkRow.createCell(11).setCellValue("80 mg");
//							break;
//						}
//
//						if (vials.equals(expected_80mgVials)) {
//							linkRow.createCell(10).setCellValue("Pass");
//						} else {
//							linkRow.createCell(10).setCellValue("Fail");
//						}
//
//						if (vials.equals(expected_200mgVials)) {
//							linkRow.createCell(13).setCellValue("Pass");
//						} else {
//							linkRow.createCell(13).setCellValue("Fail");
//						}
//
//						if (vials.equals(expected_400mgVials)) {
//							linkRow.createCell(16).setCellValue("Pass");
//						} else {
//							linkRow.createCell(16).setCellValue("Fail");
//						}
//
//						System.out.println("Vials: " + vials);
//						System.out.println("Mg: " + mgVials);
//
//					} catch (NoSuchElementException e) {
//						System.out.println("0");
//						continue;
//					}
//
//				}
//
//			}
//
//		} catch (Exception exception) {
//			System.out.println(exception.getMessage());
//		} finally {
//			// Write the workbook in file system
//			FileOutputStream out = new FileOutputStream(
//					projectPath + "/test-output/calculator/DosingCalculator_Output_RA4mg.xlsx");
//			workbook.write(out);
//			workbook.close();
//			out.close();
//			System.out.println("Excel Created!");
//
//		}
//	}

	/*************************************************************
	 * RA 8Mg
	 *****************************************************/
//	@Test(priority = 1)
//	public void calculatorTestRA8Mg() throws Exception, IOException {
//		BasePage bp = new BasePage(pageManager, database, excelReader, ftpTransfer);
//		CalculatorPage cal = new CalculatorPage(pageManager, database, excelReader, ftpTransfer);
//
//		String projectPath = System.getProperty("user.dir");
//		String path = "/testdata/calculator_input.xlsx";
//
//		 Boolean loggedIn = false;
//
//		// Create new workbook
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		XSSFSheet linkSheet_RA8mg;
//		Row linkRow;
//		String mgVials = null;
//		String vials = null;
//
//		try {
//			// Existing workbook
//			FileInputStream fileInput = new FileInputStream(projectPath + path);
//			XSSFWorkbook inputWorkbook = new XSSFWorkbook(fileInput);
//			XSSFSheet inputSheet = inputWorkbook.getSheetAt(1); // Input sheet RA 8mg
//			DataFormatter formatter = new DataFormatter();
//
//			// String result;
//
//			int totalRows = inputSheet.getLastRowNum();
//			if ((totalRows > 0) || (inputSheet.getPhysicalNumberOfRows() > 0)) {
//				totalRows++;
//			}
//
//			System.out.println("Rows count: " + totalRows);
//
//			// Create a new font and alter it.
//			XSSFFont font = workbook.createFont();
//			font.setBold(true);
//
//			// Fonts are set into a style so create a new one to use.
//			CellStyle style = workbook.createCellStyle();
//			style.setFont(font);
//
//			// Set column names for output sheet RA file
//			linkSheet_RA8mg = workbook.createSheet("Calculator Output_RA8Mg");
//			linkRow = linkSheet_RA8mg.createRow(0);
//
//			linkRow.createCell(0).setCellValue("URL");
//			linkRow.createCell(1).setCellValue("Weight");
//
//			linkRow.createCell(2).setCellValue("Expected_mg");
//			linkRow.createCell(3).setCellValue("Actual_mg");
//			linkRow.createCell(4).setCellValue("Status_mg");
//
//			linkRow.createCell(5).setCellValue("Expected_mL");
//			linkRow.createCell(6).setCellValue("Actual_mL");
//			linkRow.createCell(7).setCellValue("Status_mL");
//
//			linkRow.createCell(8).setCellValue("Expected_80 mg vial(s)");
//			linkRow.createCell(9).setCellValue("Actual_80 mg vial(s)");
//			linkRow.createCell(10).setCellValue("Status_80mg vial(s)");
//			// linkRow.createCell(11).setCellValue("80 mg quantity");
//
//			linkRow.createCell(11).setCellValue("Expected_200 mg vial(s)");
//			linkRow.createCell(12).setCellValue("Actual_200 mg vial(s)");
//			linkRow.createCell(13).setCellValue("Status_200mg vial(s)");
//			// linkRow.createCell(15).setCellValue("200 mg quantity");
//
//			linkRow.createCell(14).setCellValue("Expected_400 mg vial(s)");
//			linkRow.createCell(15).setCellValue("Actual_400 mg vial(s)");
//			linkRow.createCell(16).setCellValue("Status_400mg vial(s)");
//			// linkRow.createCell(19).setCellValue("400 mg quantity");
//
//			linkRow.createCell(17).setCellValue("Expected_Unused Quantity");
//			linkRow.createCell(18).setCellValue("Actual_Unused Quantity");
//			linkRow.createCell(19).setCellValue("Status_Unused Quantity");
//
//			for (int i = 0; i < 1; i++) {
//				linkRow.getCell(i).setCellStyle(style);
//			}
//
//			for (int j = 1; j <= totalRows; j++) {
//
//				String url = inputSheet.getRow(j).getCell(0).getStringCellValue(); // URL from input sheet
//				String weight = formatter.formatCellValue(inputSheet.getRow(j).getCell(1));
//				String expected_mg = inputSheet.getRow(j).getCell(2).getStringCellValue();
//				expected_mg = expected_mg.trim();
//				String expected_mL = inputSheet.getRow(j).getCell(3).getStringCellValue();
//				expected_mL = expected_mL.trim();
//				String expected_80mgVials = inputSheet.getRow(j).getCell(4).getStringCellValue();
//				expected_80mgVials = expected_80mgVials.trim();
//				String expected_200mgVials = inputSheet.getRow(j).getCell(5).getStringCellValue();
//				expected_200mgVials = expected_200mgVials.trim();
//				String expected_400mgVials = inputSheet.getRow(j).getCell(6).getStringCellValue();
//				expected_400mgVials = expected_400mgVials.trim();
//				String expected_UnusedVials = inputSheet.getRow(j).getCell(7).getStringCellValue();
//				expected_UnusedVials = expected_UnusedVials.trim();
//
//				System.out.println("Weight: " + weight);
//
//				pageManager.navigateWithCacheClear(url); //This will clear all the cookies and HCP pop-up will be displayed
//
//				if (url.contains("uat") && loggedIn == false) {
//					bp.login("uat");
//					loggedIn = true;
//				} else if (url.contains("author") && loggedIn == false) {
//					bp.login("author");
//					loggedIn = true;
//				}
//
//				cal.calculateDosageRA_8mg(weight); // Calculator RA indication
//				Thread.sleep(2000);
//
//				String mgML = cal.purpleTxt.getText(); // Purple Text mg/ml
//
//				String qtyMG = mgML.substring(0, mgML.lastIndexOf("/"));
//				qtyMG = qtyMG.trim();
//
//				String qtyML = mgML.substring(mgML.lastIndexOf("/") + 1);
//				qtyML = qtyML.trim();
//				System.out.println("Purple Text: " + mgML);
//
//				String unusedValue = cal.unusedQty.getText(); // unused quantity
//				unusedValue = unusedValue.substring(13);
//				unusedValue = unusedValue.trim();
//				System.out.println("Unused Text: " + unusedValue);
//
//				int rowNum = linkSheet_RA8mg.getLastRowNum();
//				linkRow = linkSheet_RA8mg.createRow(rowNum + 1);
//
//				linkRow.createCell(0).setCellValue(url);
//				linkRow.createCell(1).setCellValue(weight);
//				// ***********Expected and Actual Mg comparison*******************//
//				linkRow.createCell(2).setCellValue(expected_mg);
//				linkRow.createCell(3).setCellValue(qtyMG);
//
//				if (qtyMG.equals(expected_mg)) {
//					linkRow.createCell(4).setCellValue("Pass");
//				} else {
//					linkRow.createCell(4).setCellValue("Fail");
//				}
//
//				// ***********Expected and Actual mL comparison*******************//
//				linkRow.createCell(5).setCellValue(expected_mL);
//				linkRow.createCell(6).setCellValue(qtyML);
//				if (qtyML.equals(expected_mL)) {
//					linkRow.createCell(7).setCellValue("Pass");
//				} else {
//					linkRow.createCell(7).setCellValue("Fail");
//				}
//
//				// ***********Expected and Actual unused comparison*******************//
//				linkRow.createCell(17).setCellValue(expected_UnusedVials);
//				linkRow.createCell(18).setCellValue(unusedValue);
//
//				if (unusedValue.equals(expected_UnusedVials)) {
//					linkRow.createCell(19).setCellValue("Pass");
//				} else {
//					linkRow.createCell(19).setCellValue("Fail");
//				}
//				// **************************Setting Expected Vials for Comparison in o/p file***********************************//
//				linkRow.createCell(8).setCellValue(expected_80mgVials);
//				linkRow.createCell(11).setCellValue(expected_200mgVials);
//				linkRow.createCell(14).setCellValue(expected_400mgVials);
//
//				// **************************Loop for Vials************************************//
//				for (int i = 1; i <= cal.vialsCol.size(); i++) {
//					try {
//
//						mgVials = cal.vialsCol.get(i - 1).findElement(By.xpath(
//								"//*[@id='results-dosing-ra']/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div/div["
//										+ i + "]/div/div/div/div[1]/div/p[1]/strong"))
//								.getText();
//						mgVials = mgVials.trim();
//
//						vials = cal.vialsCol.get(i - 1).findElement(By.xpath(
//								"//*[@id='results-dosing-ra']/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div/div["
//										+ i + "]/div/div/div/div[3]/div/p"))
//								.getText();
//						vials = vials.trim();
//
//						switch (mgVials) {
//						case "80 mg":
//							linkRow.createCell(9).setCellValue(vials);
////							linkRow.createCell(11).setCellValue(mgVials);
////							linkRow.createCell(15).setCellValue("200 mg");
////							linkRow.createCell(19).setCellValue("400 mg");							
//							break;
//						case "200 mg":
//							linkRow.createCell(12).setCellValue(vials);
////							linkRow.createCell(15).setCellValue(mgVials);
////							linkRow.createCell(11).setCellValue("80 mg");
////							linkRow.createCell(19).setCellValue("400 mg");
//							break;
//						case "400 mg":
//							linkRow.createCell(15).setCellValue(vials);
////							linkRow.createCell(19).setCellValue(mgVials);
////							linkRow.createCell(15).setCellValue("200 mg");
////							linkRow.createCell(11).setCellValue("80 mg");
//							break;
//						}
//
//						if (vials.equals(expected_80mgVials)) {
//							linkRow.createCell(10).setCellValue("Pass");
//						} else {
//							linkRow.createCell(10).setCellValue("Fail");
//						}
//
//						if (vials.equals(expected_200mgVials)) {
//							linkRow.createCell(13).setCellValue("Pass");
//						} else {
//							linkRow.createCell(13).setCellValue("Fail");
//						}
//
//						if (vials.equals(expected_400mgVials)) {
//							linkRow.createCell(16).setCellValue("Pass");
//						} else {
//							linkRow.createCell(16).setCellValue("Fail");
//						}
//						System.out.println("Vials: " + vials);
//						System.out.println("Mg: " + mgVials);
//
//					} catch (NoSuchElementException e) {
//						System.out.println("0");
//						continue;
//					}
//
//				}
//
//			}
//
//		} catch (Exception exception) {
//			System.out.println(exception.getMessage());
//		} finally {
//			// Write the workbook in file system
//			FileOutputStream out = new FileOutputStream(
//					projectPath + "/test-output/calculator/DosingCalculator_Output_RA8mg.xlsx");
//			workbook.write(out);
//			workbook.close();
//			out.close();
//			System.out.println("Excel Created!");
//
//		}
//	}
//	
	/*************************************************************
	 * PJIA
	 *****************************************************/
//
//	@Test(priority = 2)
//	public void calculatorTestPJIA() throws Exception, IOException {
//		BasePage bp = new BasePage(pageManager, database, excelReader, ftpTransfer);
//		CalculatorPage cal = new CalculatorPage(pageManager, database, excelReader, ftpTransfer);
//
//		String projectPath = System.getProperty("user.dir");
//		String path = "/testdata/calculator_input.xlsx";
//
//		Boolean loggedIn = false;
//
//		// Create new workbook
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		XSSFSheet linkSheet_PJIA;
//		Row linkRow;
//		String mgVials = null;
//		String vials = null;
//
//		try {
//			// Existing workbook
//			FileInputStream fileInput = new FileInputStream(projectPath + path);
//			XSSFWorkbook inputWorkbook = new XSSFWorkbook(fileInput);
//			XSSFSheet inputSheet = inputWorkbook.getSheetAt(2); // Input sheet RA 8mg
//			DataFormatter formatter = new DataFormatter();
//
//			// String result;
//
//			int totalRows = inputSheet.getLastRowNum();
//			if ((totalRows > 0) || (inputSheet.getPhysicalNumberOfRows() > 0)) {
//				totalRows++;
//			}
//
//			System.out.println("Rows count: " + totalRows);
//
//			// Create a new font and alter it.
//			XSSFFont font = workbook.createFont();
//			font.setBold(true);
//
//			// Fonts are set into a style so create a new one to use.
//			CellStyle style = workbook.createCellStyle();
//			style.setFont(font);
//
//			// Set column names for output sheet RA file
//			linkSheet_PJIA = workbook.createSheet("Calculator Output_PJIA");
//			linkRow = linkSheet_PJIA.createRow(0);
//
//			linkRow.createCell(0).setCellValue("URL");
//			linkRow.createCell(1).setCellValue("Weight");
//
//			linkRow.createCell(2).setCellValue("Expected_mg");
//			linkRow.createCell(3).setCellValue("Actual_mg");
//			linkRow.createCell(4).setCellValue("Status_mg");
//
//			linkRow.createCell(5).setCellValue("Expected_mL");
//			linkRow.createCell(6).setCellValue("Actual_mL");
//			linkRow.createCell(7).setCellValue("Status_mL");
//
//			linkRow.createCell(8).setCellValue("Expected_80 mg vial(s)");
//			linkRow.createCell(9).setCellValue("Actual_80 mg vial(s)");
//			linkRow.createCell(10).setCellValue("Status_80mg vial(s)");
//			// linkRow.createCell(11).setCellValue("80 mg quantity");
//
//			linkRow.createCell(11).setCellValue("Expected_200 mg vial(s)");
//			linkRow.createCell(12).setCellValue("Actual_200 mg vial(s)");
//			linkRow.createCell(13).setCellValue("Status_200mg vial(s)");
//			// linkRow.createCell(15).setCellValue("200 mg quantity");
//
//			linkRow.createCell(14).setCellValue("Expected_400 mg vial(s)");
//			linkRow.createCell(15).setCellValue("Actual_400 mg vial(s)");
//			linkRow.createCell(16).setCellValue("Status_400mg vial(s)");
//			// linkRow.createCell(19).setCellValue("400 mg quantity");
//
//			linkRow.createCell(17).setCellValue("Expected_Unused Quantity");
//			linkRow.createCell(18).setCellValue("Actual_Unused Quantity");
//			linkRow.createCell(19).setCellValue("Status_Unused Quantity");
//
//			for (int i = 0; i < 1; i++) {
//				linkRow.getCell(i).setCellStyle(style);
//			}
//
//			for (int j = 1; j <= totalRows; j++) {
//				String url = inputSheet.getRow(j).getCell(0).getStringCellValue(); // URL from input sheet
//				String weight = formatter.formatCellValue(inputSheet.getRow(j).getCell(1));
//				String expected_mg = inputSheet.getRow(j).getCell(2).getStringCellValue();
//				expected_mg = expected_mg.trim();
//				String expected_mL = inputSheet.getRow(j).getCell(3).getStringCellValue();
//				expected_mL = expected_mL.trim();
//				String expected_80mgVials = inputSheet.getRow(j).getCell(4).getStringCellValue();
//				expected_80mgVials = expected_80mgVials.trim();
//				String expected_200mgVials = inputSheet.getRow(j).getCell(5).getStringCellValue();
//				expected_200mgVials = expected_200mgVials.trim();
//				String expected_400mgVials = inputSheet.getRow(j).getCell(6).getStringCellValue();
//				expected_400mgVials = expected_400mgVials.trim();
//				String expected_UnusedVials = inputSheet.getRow(j).getCell(7).getStringCellValue();
//				expected_UnusedVials = expected_UnusedVials.trim();
//
//				System.out.println("Weight: " + weight);
//
//				pageManager.navigateWithCacheClear(url);      //This will clear all the cookies and HCP pop-up will be displayed
//
//				if (url.contains("uat") && loggedIn == false) {
//					bp.login("uat");
//					loggedIn = true;
//				} else if (url.contains("author") && loggedIn == false) {
//					bp.login("author");
//					loggedIn = true;
//				}
//				
//				cal.calculateDosagePJIA(weight); // Calculator RA indication
//				Thread.sleep(2000);
//
//				String mgML = cal.purpleTxt.getText(); // Purple Text mg/ml
//
//				String qtyMG = mgML.substring(0, mgML.lastIndexOf("/"));
//				qtyMG = qtyMG.trim();
//
//				String qtyML = mgML.substring(mgML.lastIndexOf("/") + 1);
//				qtyML = qtyML.trim();
//				System.out.println("Purple Text: " + mgML);
//
//				String unusedValue = cal.unusedQty_PJIA.getText(); // unused quantity
//				unusedValue = unusedValue.substring(13);
//				unusedValue = unusedValue.trim();
//				System.out.println("Unused Text: " + unusedValue);
//
//				int rowNum = linkSheet_PJIA.getLastRowNum();
//				linkRow = linkSheet_PJIA.createRow(rowNum + 1);
//
//				linkRow.createCell(0).setCellValue(url);
//				linkRow.createCell(1).setCellValue(weight);
//				// ***********Expected and Actual Mg comparison*******************//
//				linkRow.createCell(2).setCellValue(expected_mg);
//				linkRow.createCell(3).setCellValue(qtyMG);
//
//				if (qtyMG.equals(expected_mg)) {
//					linkRow.createCell(4).setCellValue("Pass");
//				} else {
//					linkRow.createCell(4).setCellValue("Fail");
//				}
//
//				// ***********Expected and Actual mL comparison*******************//
//				linkRow.createCell(5).setCellValue(expected_mL);
//				linkRow.createCell(6).setCellValue(qtyML);
//				if (qtyML.equals(expected_mL)) {
//					linkRow.createCell(7).setCellValue("Pass");
//				} else {
//					linkRow.createCell(7).setCellValue("Fail");
//				}
//
//				// ***********Expected and Actual unused comparison*******************//
//				linkRow.createCell(17).setCellValue(expected_UnusedVials);
//				linkRow.createCell(18).setCellValue(unusedValue);
//
//				if (unusedValue.equals(expected_UnusedVials)) {
//					linkRow.createCell(19).setCellValue("Pass");
//				} else {
//					linkRow.createCell(19).setCellValue("Fail");
//				}
//				// **************************Setting Expected Vials for Comparison in o/p file***********************************//
//				linkRow.createCell(8).setCellValue(expected_80mgVials);
//				linkRow.createCell(11).setCellValue(expected_200mgVials);
//				linkRow.createCell(14).setCellValue(expected_400mgVials);
//
//				// **************************Loop for Vials************************************//
//				for (int i = 1; i <= cal.vialsCol_PJIA.size(); i++) {
//					try {
//
//						mgVials = cal.vialsCol_PJIA.get(i - 1).findElement(By.xpath(
//								"//*[@id='results-dosing-pjia']/div/div[2]/div/div/div/div/div/div"
//								+ "/div/div/div[2]/div/div["+i+"]/div/div/div/div[1]/div/p[1]/strong")).getText();
//						mgVials = mgVials.trim();
//
//						vials = cal.vialsCol_PJIA.get(i - 1).findElement(By.xpath(
//								"//*[@id='results-dosing-pjia']/div/div[2]/div/div/div/div/div"
//								+ "/div/div/div/div[2]/div/div["+i+"]/div/div/div/div[3]/div/p")).getText();
//						vials = vials.trim();
//
//						switch (mgVials) {
//						case "80 mg":
//							linkRow.createCell(9).setCellValue(vials);
////							linkRow.createCell(11).setCellValue(mgVials);
////							linkRow.createCell(15).setCellValue("200 mg");
////							linkRow.createCell(19).setCellValue("400 mg");							
//							break;
//						case "200 mg":
//							linkRow.createCell(12).setCellValue(vials);
////							linkRow.createCell(15).setCellValue(mgVials);
////							linkRow.createCell(11).setCellValue("80 mg");
////							linkRow.createCell(19).setCellValue("400 mg");
//							break;
//						case "400 mg":
//							linkRow.createCell(15).setCellValue(vials);
////							linkRow.createCell(19).setCellValue(mgVials);
////							linkRow.createCell(15).setCellValue("200 mg");
////							linkRow.createCell(11).setCellValue("80 mg");
//							break;
//						}
//
//						if (vials.equals(expected_80mgVials)) {
//							linkRow.createCell(10).setCellValue("Pass");
//						} else {
//							linkRow.createCell(10).setCellValue("Fail");
//						}
//
//						if (vials.equals(expected_200mgVials)) {
//							linkRow.createCell(13).setCellValue("Pass");
//						} else {
//							linkRow.createCell(13).setCellValue("Fail");
//						}
//
//						if (vials.equals(expected_400mgVials)) {
//							linkRow.createCell(16).setCellValue("Pass");
//						} else {
//							linkRow.createCell(16).setCellValue("Fail");
//						}
//						System.out.println("Vials: " + vials);
//						System.out.println("Mg: " + mgVials);
//
//					} catch (NoSuchElementException e) {
//						System.out.println("0");
//						continue;
//					}
//
//				}
//
//			}
//
//		} catch (Exception exception) {
//			System.out.println(exception.getMessage());
//		} finally {
//			// Write the workbook in file system
//			FileOutputStream out = new FileOutputStream(
//					projectPath + "/test-output/calculator/DosingCalculator_Output_PJIA.xlsx");
//			workbook.write(out);
//			workbook.close();
//			out.close();
//			System.out.println("Excel Created!");
//
//		}
//	}

	/*************************************************************
	 * SJIA
	 *****************************************************/

//	@Test(priority = 4)
//	public void calculatorTestSJIA() throws Exception, IOException {
//		BasePage bp = new BasePage(pageManager, database, excelReader, ftpTransfer);
//		CalculatorPage cal = new CalculatorPage(pageManager, database, excelReader, ftpTransfer);
//
//		String projectPath = System.getProperty("user.dir");
//		String path = "/testdata/calculator_input.xlsx";
//
//		Boolean loggedIn = false;
//
//		// Create new workbook
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		XSSFSheet linkSheet_SJIA;
//		Row linkRow;
//		String mgVials = null;
//		String vials = null;
//
//		try {
//			// Existing workbook
//			FileInputStream fileInput = new FileInputStream(projectPath + path);
//			XSSFWorkbook inputWorkbook = new XSSFWorkbook(fileInput);
//			XSSFSheet inputSheet = inputWorkbook.getSheetAt(3); // Input sheet RA 8mg
//			DataFormatter formatter = new DataFormatter();
//
//			// String result;
//
//			int totalRows = inputSheet.getLastRowNum();
//			if ((totalRows > 0) || (inputSheet.getPhysicalNumberOfRows() > 0)) {
//				totalRows++;
//			}
//
//			System.out.println("Rows count: " + totalRows);
//
//			// Create a new font and alter it.
//			XSSFFont font = workbook.createFont();
//			font.setBold(true);
//
//			// Fonts are set into a style so create a new one to use.
//			CellStyle style = workbook.createCellStyle();
//			style.setFont(font);
//
//			// Set column names for output sheet RA file
//			linkSheet_SJIA = workbook.createSheet("Calculator Output_SJIA");
//			linkRow = linkSheet_SJIA.createRow(0);
//
//			linkRow.createCell(0).setCellValue("URL");
//			linkRow.createCell(1).setCellValue("Weight");
//
//			linkRow.createCell(2).setCellValue("Expected_mg");
//			linkRow.createCell(3).setCellValue("Actual_mg");
//			linkRow.createCell(4).setCellValue("Status_mg");
//
//			linkRow.createCell(5).setCellValue("Expected_mL");
//			linkRow.createCell(6).setCellValue("Actual_mL");
//			linkRow.createCell(7).setCellValue("Status_mL");
//
//			linkRow.createCell(8).setCellValue("Expected_80 mg vial(s)");
//			linkRow.createCell(9).setCellValue("Actual_80 mg vial(s)");
//			linkRow.createCell(10).setCellValue("Status_80mg vial(s)");
//			// linkRow.createCell(11).setCellValue("80 mg quantity");
//
//			linkRow.createCell(11).setCellValue("Expected_200 mg vial(s)");
//			linkRow.createCell(12).setCellValue("Actual_200 mg vial(s)");
//			linkRow.createCell(13).setCellValue("Status_200mg vial(s)");
//			// linkRow.createCell(15).setCellValue("200 mg quantity");
//
//			linkRow.createCell(14).setCellValue("Expected_400 mg vial(s)");
//			linkRow.createCell(15).setCellValue("Actual_400 mg vial(s)");
//			linkRow.createCell(16).setCellValue("Status_400mg vial(s)");
//			// linkRow.createCell(19).setCellValue("400 mg quantity");
//
//			linkRow.createCell(17).setCellValue("Expected_Unused Quantity");
//			linkRow.createCell(18).setCellValue("Actual_Unused Quantity");
//			linkRow.createCell(19).setCellValue("Status_Unused Quantity");
//
//			for (int i = 0; i < 1; i++) {
//				linkRow.getCell(i).setCellStyle(style);
//			}
//
//			for (int j = 1; j <= totalRows; j++) {
//				String url = inputSheet.getRow(j).getCell(0).getStringCellValue(); // URL from input sheet
//				String weight = formatter.formatCellValue(inputSheet.getRow(j).getCell(1));
//				String expected_mg = inputSheet.getRow(j).getCell(2).getStringCellValue();
//				expected_mg = expected_mg.trim();
//				String expected_mL = inputSheet.getRow(j).getCell(3).getStringCellValue();
//				expected_mL = expected_mL.trim();
//				String expected_80mgVials = inputSheet.getRow(j).getCell(4).getStringCellValue();
//				expected_80mgVials = expected_80mgVials.trim();
//				String expected_200mgVials = inputSheet.getRow(j).getCell(5).getStringCellValue();
//				expected_200mgVials = expected_200mgVials.trim();
//				String expected_400mgVials = inputSheet.getRow(j).getCell(6).getStringCellValue();
//				expected_400mgVials = expected_400mgVials.trim();
//				String expected_UnusedVials = inputSheet.getRow(j).getCell(7).getStringCellValue();
//				expected_UnusedVials = expected_UnusedVials.trim();
//
//				System.out.println("Weight: " + weight);
//
//				pageManager.navigateWithCacheClear(url);    //This will clear all the cookies and HCP pop-up will be displayed
//
//				if (url.contains("uat") && loggedIn == false) {
//					bp.login("uat");
//					loggedIn = true;
//				} else if (url.contains("author") && loggedIn == false) {
//					bp.login("author");
//					loggedIn = true;
//				}
//				
//				cal.calculateDosageSJIA(weight); // Calculator RA indication
//
//				String mgML = cal.purpleTxt.getText(); // Purple Text mg/ml
//
//				String qtyMG = mgML.substring(0, mgML.lastIndexOf("/"));
//				qtyMG = qtyMG.trim();
//
//				String qtyML = mgML.substring(mgML.lastIndexOf("/") + 1);
//				qtyML = qtyML.trim();
//				System.out.println("Purple Text: " + mgML);
//
//				String unusedValue = cal.unusedQty_SJIA.getText(); // unused quantity
//				unusedValue = unusedValue.substring(13);
//				unusedValue = unusedValue.trim();
//				System.out.println("Unused Text: " + unusedValue);
//
//				int rowNum = linkSheet_SJIA.getLastRowNum();
//				linkRow = linkSheet_SJIA.createRow(rowNum + 1);
//
//				linkRow.createCell(0).setCellValue(url);
//				linkRow.createCell(1).setCellValue(weight);
//				// ***********Expected and Actual Mg comparison*******************//
//				linkRow.createCell(2).setCellValue(expected_mg);
//				linkRow.createCell(3).setCellValue(qtyMG);
//
//				if (qtyMG.equals(expected_mg)) {
//					linkRow.createCell(4).setCellValue("Pass");
//				} else {
//					linkRow.createCell(4).setCellValue("Fail");
//				}
//
//				// ***********Expected and Actual mL comparison*******************//
//				linkRow.createCell(5).setCellValue(expected_mL);
//				linkRow.createCell(6).setCellValue(qtyML);
//				if (qtyML.equals(expected_mL)) {
//					linkRow.createCell(7).setCellValue("Pass");
//				} else {
//					linkRow.createCell(7).setCellValue("Fail");
//				}
//
//				// ***********Expected and Actual unused comparison*******************//
//				linkRow.createCell(17).setCellValue(expected_UnusedVials);
//				linkRow.createCell(18).setCellValue(unusedValue);
//
//				if (unusedValue.equals(expected_UnusedVials)) {
//					linkRow.createCell(19).setCellValue("Pass");
//				} else {
//					linkRow.createCell(19).setCellValue("Fail");
//				}
//				// **************************Setting Expected Vials for Comparison in o/p file***********************************//
//				linkRow.createCell(8).setCellValue(expected_80mgVials);
//				linkRow.createCell(11).setCellValue(expected_200mgVials);
//				linkRow.createCell(14).setCellValue(expected_400mgVials);
//
//				// **************************Loop for Vials************************************//
//				for (int i = 1; i <= cal.vialsCol_SJIA.size(); i++) {
//					try {
//
//						mgVials = cal.vialsCol_SJIA.get(i - 1).findElement(By.xpath(
//								"//*[@id='results-dosing-sjia']/div/div[2]/div/div/div/div/div/div"
//								+ "/div/div/div[2]/div/div["+i+"]/div/div/div/div[1]/div/p[1]/strong")).getText();
//						mgVials = mgVials.trim();
//
//						vials = cal.vialsCol_SJIA.get(i - 1).findElement(By.xpath(
//								"//*[@id='results-dosing-sjia']/div/div[2]/div/div/div/div/div"
//								+ "/div/div/div/div[2]/div/div["+i+"]/div/div/div/div[3]/div/p")).getText();
//						vials = vials.trim();
//
//						switch (mgVials) {
//						case "80 mg":
//							linkRow.createCell(9).setCellValue(vials);
////							linkRow.createCell(11).setCellValue(mgVials);
////							linkRow.createCell(15).setCellValue("200 mg");
////							linkRow.createCell(19).setCellValue("400 mg");							
//							break;
//						case "200 mg":
//							linkRow.createCell(12).setCellValue(vials);
////							linkRow.createCell(15).setCellValue(mgVials);
////							linkRow.createCell(11).setCellValue("80 mg");
////							linkRow.createCell(19).setCellValue("400 mg");
//							break;
//						case "400 mg":
//							linkRow.createCell(15).setCellValue(vials);
////							linkRow.createCell(19).setCellValue(mgVials);
////							linkRow.createCell(15).setCellValue("200 mg");
////							linkRow.createCell(11).setCellValue("80 mg");
//							break;
//						}
//
//						if (vials.equals(expected_80mgVials)) {
//							linkRow.createCell(10).setCellValue("Pass");
//						} else {
//							linkRow.createCell(10).setCellValue("Fail");
//						}
//
//						if (vials.equals(expected_200mgVials)) {
//							linkRow.createCell(13).setCellValue("Pass");
//						} else {
//							linkRow.createCell(13).setCellValue("Fail");
//						}
//
//						if (vials.equals(expected_400mgVials)) {
//							linkRow.createCell(16).setCellValue("Pass");
//						} else {
//							linkRow.createCell(16).setCellValue("Fail");
//						}
//						System.out.println("Vials: " + vials);
//						System.out.println("Mg: " + mgVials);
//
//					} catch (NoSuchElementException e) {
//						System.out.println("0");
//						continue;
//					}
//
//				}
//
//			}
//
//		} catch (Exception exception) {
//			System.out.println(exception.getMessage());
//		} finally {
//			// Write the workbook in file system
//			FileOutputStream out = new FileOutputStream(
//					projectPath + "/test-output/calculator/DosingCalculator_Output_SJIA.xlsx");
//			workbook.write(out);
//			workbook.close();
//			out.close();
//			System.out.println("Excel Created!");
//
//		}
//	}
//	/*************************************************************
//	 * GCA
//	 *****************************************************/
//	@Test(priority = 1)
//	public void calculatorTestGCA() throws Exception, IOException {
//		BasePage bp = new BasePage(pageManager, database, excelReader, ftpTransfer);
//		CalculatorPage cal = new CalculatorPage(pageManager, database, excelReader, ftpTransfer);
//
//		String projectPath = System.getProperty("user.dir");
//		String path = "/testdata/calculator_input.xlsx";
//
//		Boolean loggedIn = false;
//
//		// Create new workbook
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		XSSFSheet linkSheet_GCA;
//		Row linkRow;
//		String mgVials = null;
//		String vials = null;
//
//		try {
//			// Existing workbook
//			FileInputStream fileInput = new FileInputStream(projectPath + path);
//			XSSFWorkbook inputWorkbook = new XSSFWorkbook(fileInput);
//			XSSFSheet inputSheet = inputWorkbook.getSheetAt(4); // Input sheet RA 8mg
//			DataFormatter formatter = new DataFormatter();
//
//			// String result;
//
//			int totalRows = inputSheet.getLastRowNum();
//			if ((totalRows > 0) || (inputSheet.getPhysicalNumberOfRows() > 0)) {
//				totalRows++;
//			}
//
//			System.out.println("Rows count: " + totalRows);
//
//			// Create a new font and alter it.
//			XSSFFont font = workbook.createFont();
//			font.setBold(true);
//
//			// Fonts are set into a style so create a new one to use.
//			CellStyle style = workbook.createCellStyle();
//			style.setFont(font);
//
//			// Set column names for output sheet RA file
//			linkSheet_GCA = workbook.createSheet("Calculator Output_GCA");
//			linkRow = linkSheet_GCA.createRow(0);
//
//			linkRow.createCell(0).setCellValue("URL");
//			linkRow.createCell(1).setCellValue("Weight");
//
//			linkRow.createCell(2).setCellValue("Expected_mg");
//			linkRow.createCell(3).setCellValue("Actual_mg");
//			linkRow.createCell(4).setCellValue("Status_mg");
//
//			linkRow.createCell(5).setCellValue("Expected_mL");
//			linkRow.createCell(6).setCellValue("Actual_mL");
//			linkRow.createCell(7).setCellValue("Status_mL");
//
//			linkRow.createCell(8).setCellValue("Expected_80 mg vial(s)");
//			linkRow.createCell(9).setCellValue("Actual_80 mg vial(s)");
//			linkRow.createCell(10).setCellValue("Status_80mg vial(s)");
//			// linkRow.createCell(11).setCellValue("80 mg quantity");
//
//			linkRow.createCell(11).setCellValue("Expected_200 mg vial(s)");
//			linkRow.createCell(12).setCellValue("Actual_200 mg vial(s)");
//			linkRow.createCell(13).setCellValue("Status_200mg vial(s)");
//			// linkRow.createCell(15).setCellValue("200 mg quantity");
//
//			linkRow.createCell(14).setCellValue("Expected_400 mg vial(s)");
//			linkRow.createCell(15).setCellValue("Actual_400 mg vial(s)");
//			linkRow.createCell(16).setCellValue("Status_400mg vial(s)");
//			// linkRow.createCell(19).setCellValue("400 mg quantity");
//
//			linkRow.createCell(17).setCellValue("Expected_Unused Quantity");
//			linkRow.createCell(18).setCellValue("Actual_Unused Quantity");
//			linkRow.createCell(19).setCellValue("Status_Unused Quantity");
//
//			for (int i = 0; i < 1; i++) {
//				linkRow.getCell(i).setCellStyle(style);
//			}
//
//			for (int j = 1; j <= totalRows; j++) {
//				String url = inputSheet.getRow(j).getCell(0).getStringCellValue(); // URL from input sheet
//				String weight = formatter.formatCellValue(inputSheet.getRow(j).getCell(1));
//				String expected_mg = inputSheet.getRow(j).getCell(2).getStringCellValue();
//				expected_mg = expected_mg.trim();
//				String expected_mL = inputSheet.getRow(j).getCell(3).getStringCellValue();
//				expected_mL = expected_mL.trim();
//				String expected_80mgVials = inputSheet.getRow(j).getCell(4).getStringCellValue();
//				expected_80mgVials = expected_80mgVials.trim();
//				String expected_200mgVials = inputSheet.getRow(j).getCell(5).getStringCellValue();
//				expected_200mgVials = expected_200mgVials.trim();
//				String expected_400mgVials = inputSheet.getRow(j).getCell(6).getStringCellValue();
//				expected_400mgVials = expected_400mgVials.trim();
//				String expected_UnusedVials = inputSheet.getRow(j).getCell(7).getStringCellValue();
//				expected_UnusedVials = expected_UnusedVials.trim();
//
//				System.out.println("Weight: " + weight);
//
//				pageManager.navigateWithCacheClear(url);        //This will clear all the cookies and HCP pop-up will be displayed
//
//				if (url.contains("uat") && loggedIn == false) {
//					bp.login("uat");
//					loggedIn = true;
//				} else if (url.contains("author") && loggedIn == false) {
//					bp.login("author");
//					loggedIn = true;
//				}
//				
//	
//				cal.calculateDosageGCA(weight); // Calculator RA indication
//				
//				String mgML = cal.purpleTxt.getText(); // Purple Text mg/ml
//				System.out.println("Purple Text: " + mgML);
//				String qtyMG = mgML.substring(0, mgML.lastIndexOf("/"));
//				qtyMG = qtyMG.trim();
//
//				String qtyML = mgML.substring(mgML.lastIndexOf("/") + 1);
//				qtyML = qtyML.trim();
//				
//
//				String unusedValue = cal.unusedQty_GCA.getText(); // unused quantity
//				unusedValue = unusedValue.substring(13);
//				unusedValue = unusedValue.trim();
//				System.out.println("Unused Text: " + unusedValue);
//
//				int rowNum = linkSheet_GCA.getLastRowNum();
//				linkRow = linkSheet_GCA.createRow(rowNum + 1);
//
//				linkRow.createCell(0).setCellValue(url);
//				linkRow.createCell(1).setCellValue(weight);
//				// ***********Expected and Actual Mg comparison*******************//
//				linkRow.createCell(2).setCellValue(expected_mg);
//				linkRow.createCell(3).setCellValue(qtyMG);
//
//				if (qtyMG.equals(expected_mg)) {
//					linkRow.createCell(4).setCellValue("Pass");
//				} else {
//					linkRow.createCell(4).setCellValue("Fail");
//				}
//
//				// ***********Expected and Actual mL comparison*******************//
//				linkRow.createCell(5).setCellValue(expected_mL);
//				linkRow.createCell(6).setCellValue(qtyML);
//				if (qtyML.equals(expected_mL)) {
//					linkRow.createCell(7).setCellValue("Pass");
//				} else {
//					linkRow.createCell(7).setCellValue("Fail");
//				}
//
//				// ***********Expected and Actual unused comparison*******************//
//				linkRow.createCell(17).setCellValue(expected_UnusedVials);
//				linkRow.createCell(18).setCellValue(unusedValue);
//
//				if (unusedValue.equals(expected_UnusedVials)) {
//					linkRow.createCell(19).setCellValue("Pass");
//				} else {
//					linkRow.createCell(19).setCellValue("Fail");
//				}
//				// **************************Setting Expected Vials for Comparison in o/p file***********************************//
//				linkRow.createCell(8).setCellValue(expected_80mgVials);
//				linkRow.createCell(11).setCellValue(expected_200mgVials);
//				linkRow.createCell(14).setCellValue(expected_400mgVials);
//
//				// **************************Loop for Vials************************************//
//				for (int i = 1; i <= cal.vialsCol_GCA.size(); i++) {
//					try {
//
//						mgVials = cal.vialsCol_GCA.get(i - 1).findElement(By.xpath(
//								"//*[@id='results-dosing-gca']/div/div[2]/div/div/div/div/div/div"
//								+ "/div/div/div[2]/div/div["+i+"]/div/div/div/div[1]/div/p[1]/strong")).getText();
//						mgVials = mgVials.trim();
//
//						vials = cal.vialsCol_GCA.get(i - 1).findElement(By.xpath(
//								"//*[@id='results-dosing-gca']/div/div[2]/div/div/div/div/div"
//								+ "/div/div/div/div[2]/div/div["+i+"]/div/div/div/div[3]/div/p")).getText();
//						vials = vials.trim();
//
//						switch (mgVials) {
//						case "80 mg":
//							linkRow.createCell(9).setCellValue(vials);
////							linkRow.createCell(11).setCellValue(mgVials);
////							linkRow.createCell(15).setCellValue("200 mg");
////							linkRow.createCell(19).setCellValue("400 mg");							
//							break;
//						case "200 mg":
//							linkRow.createCell(12).setCellValue(vials);
////							linkRow.createCell(15).setCellValue(mgVials);
////							linkRow.createCell(11).setCellValue("80 mg");
////							linkRow.createCell(19).setCellValue("400 mg");
//							break;
//						case "400 mg":
//							linkRow.createCell(15).setCellValue(vials);
////							linkRow.createCell(19).setCellValue(mgVials);
////							linkRow.createCell(15).setCellValue("200 mg");
////							linkRow.createCell(11).setCellValue("80 mg");
//							break;
//						}
//
//						if (vials.equals(expected_80mgVials)) {
//							linkRow.createCell(10).setCellValue("Pass");
//						} else {
//							linkRow.createCell(10).setCellValue("Fail");
//						}
//
//						if (vials.equals(expected_200mgVials)) {
//							linkRow.createCell(13).setCellValue("Pass");
//						} else {
//							linkRow.createCell(13).setCellValue("Fail");
//						}
//
//						if (vials.equals(expected_400mgVials)) {
//							linkRow.createCell(16).setCellValue("Pass");
//						} else {
//							linkRow.createCell(16).setCellValue("Fail");
//						}
//						System.out.println("Vials: " + vials);
//						System.out.println("Mg: " + mgVials);
//
//					} catch (NoSuchElementException e) {
//						System.out.println("0");
//						continue;
//					}
//
//				}
//
//			}
//
//		} catch (Exception exception) {
//			System.out.println(exception.getMessage());
//		} finally {
//			// Write the workbook in file system
//			FileOutputStream out = new FileOutputStream(
//					projectPath + "/test-output/calculator/DosingCalculator_Output_GCA.xlsx");
//			workbook.write(out);
//			workbook.close();
//			out.close();
//			System.out.println("Excel Created!");
//
//		}
//	}

	/*************************************************************
	 * COVID
	 *****************************************************/
	@Test(priority = 1)
	public void calculatorTestCovid() throws Exception, IOException {
		BasePage bp = new BasePage(pageManager, excelReader);
		CalculatorPage cal = new CalculatorPage(pageManager, excelReader);

		String projectPath = System.getProperty("user.dir");
		String path = "/testdata/calculator_input.xlsx";

		Boolean loggedIn = false;

		// Create new workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet linkSheet_Covid;
		Row linkRow;
		String mgVials = null;
		String vials = null;
		String expected_80mgVials = null;
		String expected_200mgVials = null;
		String expected_400mgVials = null;
		Object mgVials_null = null;
		// MissingCellPolicy Row;
		try {
			// Existing workbook
			FileInputStream fileInput = new FileInputStream(projectPath + path);
			XSSFWorkbook inputWorkbook = new XSSFWorkbook(fileInput);
			XSSFSheet inputSheet = inputWorkbook.getSheetAt(5); // Input sheet RA 8mg
			DataFormatter formatter = new DataFormatter();
			inputWorkbook.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Cell cell;

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

			// Set column names for output sheet RA file
			linkSheet_Covid = workbook.createSheet("Calculator Output_Covid");
			linkRow = linkSheet_Covid.createRow(0);

			linkRow.createCell(0).setCellValue("URL");
			linkRow.createCell(1).setCellValue("Weight");

			linkRow.createCell(2).setCellValue("Expected_mg");
			linkRow.createCell(3).setCellValue("Actual_mg");
			linkRow.createCell(4).setCellValue("Status_mg");

			linkRow.createCell(5).setCellValue("Expected_mL");
			linkRow.createCell(6).setCellValue("Actual_mL");
			linkRow.createCell(7).setCellValue("Status_mL");

			linkRow.createCell(8).setCellValue("Expected_80 mg vial(s)");
			linkRow.createCell(9).setCellValue("Actual_80 mg vial(s)");
			linkRow.createCell(10).setCellValue("Status_80mg vial(s)");
			// linkRow.createCell(11).setCellValue("80 mg quantity");

			linkRow.createCell(11).setCellValue("Expected_200 mg vial(s)");
			linkRow.createCell(12).setCellValue("Actual_200 mg vial(s)");
			linkRow.createCell(13).setCellValue("Status_200mg vial(s)");
			// linkRow.createCell(15).setCellValue("200 mg quantity");

			linkRow.createCell(14).setCellValue("Expected_400 mg vial(s)");
			linkRow.createCell(15).setCellValue("Actual_400 mg vial(s)");
			linkRow.createCell(16).setCellValue("Status_400mg vial(s)");
			// linkRow.createCell(19).setCellValue("400 mg quantity");

			linkRow.createCell(17).setCellValue("Expected_Unused Quantity");
			linkRow.createCell(18).setCellValue("Actual_Unused Quantity");
			linkRow.createCell(19).setCellValue("Status_Unused Quantity");

			for (int i = 0; i < 20; i++) {
				linkRow.getCell(i).setCellStyle(style);
			}

			for (int j = 1; j <= totalRows; j++) {
				String url = inputSheet.getRow(j).getCell(0).getStringCellValue(); // URL from input sheet
				String weight = formatter.formatCellValue(inputSheet.getRow(j).getCell(1));
				String expected_mg = inputSheet.getRow(j).getCell(2).getStringCellValue();
				expected_mg = expected_mg.trim();
				String expected_mL = inputSheet.getRow(j).getCell(3).getStringCellValue();
				expected_mL = expected_mL.trim();

				cell = inputSheet.getRow(j).getCell(4, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					expected_80mgVials = cell.getStringCellValue();
					expected_80mgVials = expected_80mgVials.trim();
				} else {
					expected_80mgVials = "";
					System.out.println("null");
				}

				cell = inputSheet.getRow(j).getCell(5, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					expected_200mgVials = cell.getStringCellValue();
					expected_200mgVials = expected_200mgVials.trim();
				} else {
					expected_200mgVials = "";
					System.out.println("null");
				}

				cell = inputSheet.getRow(j).getCell(6, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					expected_400mgVials = cell.getStringCellValue();
					expected_400mgVials = expected_400mgVials.trim();
				} else {
					expected_400mgVials = "";
					System.out.println("null");
				}
				String expected_UnusedVials = inputSheet.getRow(j).getCell(7).getStringCellValue();
				expected_UnusedVials = expected_UnusedVials.trim();

				System.out.println("Weight: " + weight);

				pageManager.navigateWithCacheClear(url); // This will clear all the cookies and HCP pop-up will be
															// displayed

				if (url.contains("uat") && loggedIn == false) {
					bp.login("uat");
					loggedIn = true;
				} else if (url.contains("preview") && loggedIn == false) {
					bp.login("preview");
					loggedIn = true;
				}
				
				cal.calculateDosageCovid(weight); // Calculator RA indication

				String mgML = cal.purpleTxt.getText(); // Purple Text mg/ml
				System.out.println("Purple Text: " + mgML);
				String qtyMG = mgML.substring(0, mgML.lastIndexOf("/"));
				qtyMG = qtyMG.trim();

				String qtyML = mgML.substring(mgML.lastIndexOf("/") + 1);
				qtyML = qtyML.trim();

				String unusedValue = cal.unusedQty_Covid.getText(); // unused quantity
				unusedValue = unusedValue.substring(13);
				unusedValue = unusedValue.trim();
				System.out.println("Unused Text: " + unusedValue);

				int rowNum = linkSheet_Covid.getLastRowNum();
				linkRow = linkSheet_Covid.createRow(rowNum + 1);

				linkRow.createCell(0).setCellValue(url);
				linkRow.createCell(1).setCellValue(weight);
				// ***********Expected and Actual Mg comparison*******************//
				linkRow.createCell(2).setCellValue(expected_mg);
				linkRow.createCell(3).setCellValue(qtyMG);

				if (qtyMG.equals(expected_mg)) {
					linkRow.createCell(4).setCellValue("Pass");
				} else {
					linkRow.createCell(4).setCellValue("Fail");
				}

				// ***********Expected and Actual mL comparison*******************//
				linkRow.createCell(5).setCellValue(expected_mL);
				linkRow.createCell(6).setCellValue(qtyML);
				if (qtyML.equals(expected_mL)) {
					linkRow.createCell(7).setCellValue("Pass");
				} else {
					linkRow.createCell(7).setCellValue("Fail");
				}

				// ***********Expected and Actual unused comparison*******************//
				linkRow.createCell(17).setCellValue(expected_UnusedVials);
				linkRow.createCell(18).setCellValue(unusedValue);

				if (unusedValue.equals(expected_UnusedVials)) {
					linkRow.createCell(19).setCellValue("Pass");
				} else {
					linkRow.createCell(19).setCellValue("Fail");
				}
				// **************************Setting Expected Vials for Comparison in o/p
				// file***********************************//
				linkRow.createCell(8).setCellValue(expected_80mgVials);
				linkRow.createCell(11).setCellValue(expected_200mgVials);
				linkRow.createCell(14).setCellValue(expected_400mgVials);

				// **************************Loop for
				// Vials************************************//
				for (int i = 1; i <= cal.vialsCol_Covid.size(); i++) {
					try {

						mgVials = cal.vialsCol_Covid.get(i - 1).findElement(
								By.xpath("//*[@id='results-dosing-covid']/div/div[2]/div/div/div/div/div/div"
										+ "/div/div/div[2]/div/div[" + i + "]/div/div/div/div[1]/div/p[1]/strong"))
								.getText();
						mgVials = mgVials.trim();

						vials = cal.vialsCol_Covid.get(i - 1)
								.findElement(By.xpath("//*[@id='results-dosing-covid']/div/div[2]/div/div/div/div/div"
										+ "/div/div/div/div[2]/div/div[" + i + "]/div/div/div/div[3]/div/p"))
								.getText();
						vials = vials.trim();
					} catch (NoSuchElementException e) {
						System.out.println("0");
						mgVials = "";
						if (i == 1) {
							System.out.println("1");
							if (expected_80mgVials == "" & mgVials == "") {
								linkRow.createCell(10).setCellValue("Pass");
							} else {
								linkRow.createCell(10).setCellValue("Fail");
							}
						} else if (i == 2) {
							System.out.println("2");
							if (expected_200mgVials == "" & mgVials == "") {
								linkRow.createCell(13).setCellValue("Pass");
							} else {
								linkRow.createCell(13).setCellValue("Fail");
							}
						} else if (i == 3) {
							System.out.println("3");
							if (expected_400mgVials == "" & mgVials == "") {
								linkRow.createCell(16).setCellValue("Pass");
							} else {
								linkRow.createCell(16).setCellValue("Fail");
							}
						}
						continue;
					}

					switch (mgVials) {
					case "80 mg":
						linkRow.createCell(9).setCellValue(vials);
						if (vials.equals(expected_80mgVials)) {
							linkRow.createCell(10).setCellValue("Pass");
						} else {
							linkRow.createCell(10).setCellValue("Fail");
						}
						break;

					case "200 mg":
						linkRow.createCell(12).setCellValue(vials);
						if (vials.equals(expected_200mgVials)) {
							linkRow.createCell(13).setCellValue("Pass");
						} else {
							linkRow.createCell(13).setCellValue("Fail");
						}
						break;

					case "400 mg":
						linkRow.createCell(15).setCellValue(vials);

						if (vials.equals(expected_400mgVials)) {
							linkRow.createCell(16).setCellValue("Pass");
						} else {
							linkRow.createCell(16).setCellValue("Fail");
						}
						break;
					}

					System.out.println("Vials: " + vials);
					System.out.println("Mg: " + mgVials);
				}

			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(
					projectPath + "/test-output/calculator/DosingCalculator_Output_Covid.xlsx");
			workbook.write(out);
			workbook.close();
			out.close();
			System.out.println("Excel Created!");

		}
	}

}
