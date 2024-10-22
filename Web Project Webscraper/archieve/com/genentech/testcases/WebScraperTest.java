package com.genentech.testcases;

import org.testng.annotations.Test;

import com.genentech.baseclasses.WebScraperPage;
import com.genentech.util.TestCaseBase;

public class WebScraperTest extends TestCaseBase {
	
	@Test
	public void verification() throws Exception {
		
		String filePath = "/testdata/webscraper_input_urls.xlsx";

		WebScraperPage wsp = new WebScraperPage(pageManager, excelReader);

		// --- uat, preview, live ---//

		wsp.login("live");
		
		wsp.brokenLinksVerification(filePath);
	}
}

// 404,500