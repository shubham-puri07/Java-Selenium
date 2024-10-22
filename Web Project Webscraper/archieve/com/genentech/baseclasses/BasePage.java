package com.genentech.baseclasses;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.genentech.util.ExcelReader;
import com.genentech.util.PageManager;
import com.genentech.util.PageObject;

public class BasePage extends PageObject {
	
	//New Login 
	@FindBy(id = "identifierInput")
	public WebElement signonUsername;
	
	@FindBy(id = "submitBtn")
	public WebElement submitBtn;
	
	
	//Old Login
	@FindBy(xpath = ".//input[@id='username']")
	public WebElement username;
	
	@FindBy(xpath = ".//input[@id='password']")
	public WebElement password;

	@FindBy(xpath = ".//input[@value='Login']")
	public WebElement submit;
	
	public BasePage(PageManager pm, ExcelReader xl) {
		super(pm, xl);
	}
	
	/**
	 * This method to login.
	 */
	public void login(String env) 
	{
		if(env.contains("uat")) {
			//pageManager.sendKeys(username, "siddharth.jain@perficient.com");
			//New Login
			pageManager.sendKeys(signonUsername, "khergadv");
			pageManager.click(submitBtn);
			
			pageManager.sendKeys(password, "VishTester!1706");
			pageManager.click(submit);
		} else if (env.contains("preview")) {
			//pageManager.sendKeys(username, "siddharth.jain@perficient.com");
			//New Login
			pageManager.sendKeys(signonUsername, "khergadv");
			pageManager.click(submitBtn);
			
			pageManager.sendKeys(password, "VishTester!1706");
			pageManager.click(submit);
		} 
	}
}
