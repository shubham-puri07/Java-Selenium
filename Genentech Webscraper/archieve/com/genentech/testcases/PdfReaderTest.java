package com.genentech.testcases;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Cookie;
import org.testng.annotations.Test;

import com.genentech.baseclasses.BasePage;
import com.genentech.util.TestCaseBase;

public class PdfReaderTest extends TestCaseBase {

	BasePage bp;

	@Test
	public void pdfTitleAndMeta() throws Exception {
		bp = new BasePage(pageManager, excelReader);

		String projectPath = System.getProperty("user.dir");
		String path = "/testdata/pdf_input_urls.xlsx";
		Boolean loggedIn = false;

		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		String date = df.format(new Date());

		FileInputStream fileInput = new FileInputStream(projectPath + path);
		XSSFWorkbook inputWorkbook = new XSSFWorkbook(fileInput);
		inputWorkbook.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);
		Cell cell;
		XSSFSheet inputSheet = inputWorkbook.getSheetAt(0);

		int totalRows = inputSheet.getLastRowNum();
		if ((totalRows > 0) || (inputSheet.getPhysicalNumberOfRows() > 0)) {
			totalRows++;
		}
		System.out.println("Total URLs: " + (totalRows - 1));

		XSSFWorkbook outputWorkbook = new XSSFWorkbook();
		XSSFSheet outputSheet;
		Row outputRow;

		outputSheet = outputWorkbook.createSheet("Details");
		outputRow = outputSheet.createRow(0);
		outputRow.createCell(0).setCellValue("Status");
		outputRow.createCell(1).setCellValue("Site");
		outputRow.createCell(2).setCellValue("Expected");
		outputRow.createCell(3).setCellValue("Actual");

		for (int i = 1; i < totalRows; i++) {
			String site = inputSheet.getRow(i).getCell(0).getStringCellValue();
			String etitle;
			String esubject;
			String ekeywords;
			try {
				cell = inputSheet.getRow(i).getCell(1, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					etitle = cell.getStringCellValue();
					etitle = etitle.trim();
				} else {
					etitle = "";
				}
			} catch (Exception e) {
				etitle = null;
			}
			try {
				cell = inputSheet.getRow(i).getCell(2, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					esubject = cell.getStringCellValue();
					esubject = esubject.trim();
				} else {
					esubject = "";
				}
			} catch (Exception e) {
				esubject = null;
			}
			try {
				cell = inputSheet.getRow(i).getCell(3, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					ekeywords = cell.getStringCellValue();
					ekeywords = ekeywords.trim();
				} else {
					ekeywords = "";
				}
			} catch (Exception e) {
				ekeywords = null;
			}

			System.out.println(i + ". " + site);
			// System.out.println("esubject = " + esubject);
			// System.out.println("etitle = " + etitle);
			// System.out.println("ekeywords = " + ekeywords);

			pageManager.navigate(site);

			if (site.contains("uat") && loggedIn == false) {
				bp.login("uat");
				loggedIn = true;
			} else if (site.contains("preview") && loggedIn == false) {
				bp.login("preview");
				loggedIn = true;
			}

			// to get cookies
			Set<Cookie> cookieSet = getDriver().manage().getCookies();
			String cookie = cookieSet.toString().replace("[", "");
			// System.out.println("Cookie: " + cookie);

			if (pageManager.getDriver().getCurrentUrl().endsWith(".pdf")) {
				try {
					String url = pageManager.getDriver().getCurrentUrl();

					HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
					connection.setRequestProperty("Cookie", cookie);
					BufferedInputStream fileParse = new BufferedInputStream(connection.getInputStream());
					PDDocument document = Loader.loadPDF(fileParse);
					PDDocumentInformation info = document.getDocumentInformation();

					String asubject;
					String atitle;
					String akeywords;

					try {
						asubject = info.getSubject().trim();
					} catch (Exception e) {
						asubject = null;
					}
					try {
						atitle = info.getTitle().trim();
					} catch (Exception e) {
						atitle = null;
					}
					try {
						akeywords = info.getKeywords().trim();
					} catch (Exception e) {
						akeywords = null;
					}

					// System.out.println("asubject = " + asubject);
					// System.out.println("atitle = " + atitle);
					// System.out.println("akeywords = " + akeywords);

					try {
						int rowNum = outputSheet.getLastRowNum();
						outputRow = outputSheet.createRow(rowNum + 1);
						outputRow.createCell(1).setCellValue(site);
						outputRow.createCell(2).setCellValue(etitle);
						outputRow.createCell(3).setCellValue(atitle);
						if (etitle.equals(atitle)) {
							outputRow.createCell(0).setCellValue("Passed");
						} else {
							outputRow.createCell(0).setCellValue("Title mismatched");
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

					try {
						int rowNum = outputSheet.getLastRowNum();
						outputRow = outputSheet.createRow(rowNum + 1);
						outputRow.createCell(1).setCellValue(site);
						outputRow.createCell(2).setCellValue(esubject);
						outputRow.createCell(3).setCellValue(asubject);
						if (esubject.equals(asubject)) {
							outputRow.createCell(0).setCellValue("Passed");
						} else {
							outputRow.createCell(0).setCellValue("Meta mismatched");
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

					try {
						int rowNum = outputSheet.getLastRowNum();
						outputRow = outputSheet.createRow(rowNum + 1);
						outputRow.createCell(1).setCellValue(site);
						outputRow.createCell(2).setCellValue(ekeywords);
						outputRow.createCell(3).setCellValue(akeywords);
						if (ekeywords.equals(akeywords)) {
							outputRow.createCell(0).setCellValue("Passed");
						} else {
							outputRow.createCell(0).setCellValue("Keywords mismatched");
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				System.out.println("URL other than .pdf is " + pageManager.getDriver().getCurrentUrl());
			}
		}

		// Write the workbook in file system
		FileOutputStream out = new FileOutputStream(
				projectPath + "/test-output/pdfreader/PdfReaderTest_" + date + ".xlsx");
		outputWorkbook.write(out);
		outputWorkbook.close();
		inputWorkbook.close();
		out.close();
		System.out.println("Excel Created!");
	}
}
