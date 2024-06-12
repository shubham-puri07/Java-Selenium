package com.genentech.testcases;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.genentech.baseclasses.BasePage;
import com.genentech.util.TestCaseBase;

public class JsonValuesTest extends TestCaseBase {

	BasePage bp;

	@Test
	public void jsonValues() throws Exception {
		
		bp = new BasePage(pageManager, excelReader);
		
		String projectPath = System.getProperty("user.dir");
		String path = "/testdata/jsonvalues_input_urls.xlsx";
		Boolean loggedIn = false;

		FileInputStream fileInput = new FileInputStream(projectPath + path);
		XSSFWorkbook inputWorkbook = new XSSFWorkbook(fileInput);
		XSSFSheet inputSheet = inputWorkbook.getSheetAt(0);

		int totalRows = inputSheet.getLastRowNum();
		if ((totalRows > 0) || (inputSheet.getPhysicalNumberOfRows() > 0)) {
			totalRows++;
		}
		System.out.println("Total URLs: " + totalRows);

		for (int i = 0; i < totalRows; i++) {
			try {
				String site = inputSheet.getRow(i).getCell(0).getStringCellValue();

				pageManager.navigate(site);

				if (site.contains("uat") && loggedIn == false) {
					bp.login("uat");
					loggedIn = true;
				} else if (site.contains("author") && loggedIn == false) {
					bp.login("author");
					loggedIn = true;
				}

				String s = getDriver().findElement(By.xpath("//*[contains(text(),'dataLayer')]")).getAttribute("innerHTML");
				System.out.println(i + 1 + "." + s);
				String[] splitS = s.split("\n");

				for (int j = 0; j < splitS.length; j++) {
					inputSheet.getRow(i).createCell(j + 1).setCellValue(splitS[j].trim());
				}
			} catch (Exception e) {
				System.out.println(i + 1 + "." + " Error");
				inputSheet.getRow(i).createCell(1).setCellValue("Error");
			}
		}

		FileOutputStream fileOut = new FileOutputStream(projectPath + path);
		inputWorkbook.write(fileOut);
		inputWorkbook.close();
		fileInput.close();
		fileOut.close();
	}
}
