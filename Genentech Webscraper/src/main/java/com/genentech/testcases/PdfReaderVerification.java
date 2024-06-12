package com.genentech.testcases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.genentech.util.TestCaseBase;

import com.genentech.baseclasses.BasePage;
import com.genentech.baseclasses.PdfReaderBasePage;

public class PdfReaderVerification extends TestCaseBase {
	
	@Test(priority=0)
	public void getInputExcelSheetData() throws IOException {
		BasePage bp = new BasePage(pageManager, excelReader);
		PdfReaderBasePage pdfrBase = new PdfReaderBasePage(pageManager, excelReader);
		bp.loadDefaultExcelFile();
		pdfrBase.readData();
	}
	
	@Test(priority=1)
	public void loginToEnvironment() {
		
		BasePage bp = new BasePage(pageManager, excelReader);
		
		//-----"live" OR "uat" OR "preview"-----//
		bp.login("uat");
	}
	
	@Test(priority=2)
	public void verifyRedirects() throws IOException {
		PdfReaderBasePage rBase = new PdfReaderBasePage(pageManager, excelReader);
		rBase.verifyPDFReader();
	}
	
	@Test(priority=3) 
	public void addDataToOutputExcelFile() throws IOException {
		PdfReaderBasePage pdfrBase = new PdfReaderBasePage(pageManager, excelReader);
		pdfrBase.createOutputExcelFile();
	}

}
