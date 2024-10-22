package com.genentech.baseclasses;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.genentech.util.ExcelReader;
import com.genentech.util.PageManager;
import com.genentech.util.PageObject;

public class WebScraperPage extends PageObject {
	
	//New Login 
	@FindBy(id = "identifierInput")
	public WebElement signonUsername;
	
	@FindBy(id = "submitBtn")
	public WebElement submitBtn;
	
	@FindBy(xpath = ".//input[@id='username']")
	public WebElement username;

	@FindBy(xpath = ".//input[@id='password']")
	public WebElement password;

	@FindBy(xpath = ".//input[@type='submit']")
	public WebElement submit;

	public WebScraperPage(PageManager pm, ExcelReader xl) {
		super(pm, xl);
	}

	/**
	 * This method launches the site.
	 */
	public void launch(String url) {
		pageManager.navigate(url);
	}

	public void login(String env) {
		if (env.contains("uat")) {
			pageManager.navigate("https://uat-rituxan-core.gene.com/");
			//New Login
			//New Login
			pageManager.sendKeys(signonUsername, "khergadv");
			pageManager.click(submitBtn);
			
			pageManager.sendKeys(password, "VishTester!1706");
			pageManager.click(submit);
			pageManager.waitForSeconds(5000);
		} else if (env.contains("preview")) {
			pageManager.navigate("https://preview-cotellic.gene.com/");
			//New Login
			pageManager.sendKeys(signonUsername, "khergadv");
			pageManager.click(submitBtn);
			
			pageManager.sendKeys(password, "VishTester!1706");
			pageManager.click(submit);
			pageManager.waitForSeconds(5000);
		} else if (env.contains("live")) {
		}
	}
	
	public void brokenLinksVerification(String filePath) throws IOException, Exception {
		pageManager.findBrokenLinksAndImages(filePath);
		}
}