package com.genentech.baseclasses;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.genentech.util.ExcelReader;
import com.genentech.util.PageManager;
import com.genentech.util.PageObject;

public class CalculatorPage extends PageObject {
	@FindBy(id = "onetrust-accept-btn-handler")
	public WebElement ccpaBtn;

	@FindBy(xpath = ".//input[@class = 'cmp-form-text__text']")
	public WebElement weightInput_RA;

	@FindBy(xpath = "//*[@id='cmp-form-field__form-options-1501284165']/label[2]/span/strong")
	public WebElement amountRadio;

//	@FindBy(xpath = ".//span[contains(text(), 'PJIA ')]")
//	public WebElement pjiaTab;
//
//	@FindBy(xpath = ".//span[contains(text(), 'SJIA')]")
//	public WebElement sjiaTab;
//
//	@FindBy(xpath = ".//span[contains(text(), 'GCA')]")
//	public WebElement gcaTab;

	@FindBy(xpath = ".//button[contains(text(),'    Calculate Dose and Vial Combination')]")
	public WebElement calculateBtn;

	@FindBy(xpath = ".//div[@class = 'text cmp-text--purple-bg']")
	public WebElement purpleTxt;

	@FindBy(xpath = "//*[@id='results-dosing-ra']/div/div[2]/div/div/div/div/div/div/div/div/div[3]/div/p")
	public WebElement unusedQty;

	@FindBy(xpath = "//*[@id='results-dosing-pjia']/div/div[2]/div/div/div/div/div/div/div/div/div[3]/div/p")
	public WebElement unusedQty_PJIA;

	@FindBy(xpath = "//*[@id='results-dosing-sjia']/div/div[2]/div/div/div/div/div/div/div/div/div[3]/div/p")
	public WebElement unusedQty_SJIA;

	@FindBy(xpath = "//*[@id='results-dosing-gca']/div/div[2]/div/div/div/div/div/div/div/div/div[3]/div/p")
	public WebElement unusedQty_GCA;

	@FindBy(xpath = "//*[@id='results-dosing-covid']/div/div[2]/div/div/div/div/div/div/div/div/div[3]/div/p")
	public WebElement unusedQty_Covid;

	@FindBy(xpath = ".//*[@id='results-dosing-ra']/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div/div")
	public List<WebElement> vialsCol;

	@FindBy(xpath = ".//*[@id='results-dosing-pjia']/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div/div")
	public List<WebElement> vialsCol_PJIA;

	@FindBy(xpath = ".//*[@id='results-dosing-sjia']/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div/div")
	public List<WebElement> vialsCol_SJIA;

	@FindBy(xpath = ".//*[@id='results-dosing-gca']/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div/div")
	public List<WebElement> vialsCol_GCA;

	@FindBy(xpath = ".//*[@id='results-dosing-covid']/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div/div")
	public List<WebElement> vialsCol_Covid;

	@FindBy(xpath = "//*[@id='results-dosing-ra']/div/div[2]/div/div/div/div/div/div/div/div/div[3]/div/p")
	public WebElement unusedValue;

	@FindBy(xpath = "/html/body/div[3]/div/div/div/div/div/div/div/div/div[1]/footer/div[2]/a")
	public WebElement okBtn;

	@FindBy(id = "auto-b35a-5a2c47dc19ba")
	public WebElement gcaBtn;

	public CalculatorPage(PageManager pm, ExcelReader xl) {
		super(pm, xl);
	}

	JavascriptExecutor js = (JavascriptExecutor) pageManager.getDriver();

	public void calculateDosageRA(String weight) throws InterruptedException {

		pageManager.click(okBtn);
		pageManager.click(weightInput_RA);
		pageManager.sendKeys(weightInput_RA, weight);
		js.executeScript("arguments[0].scrollIntoView();", calculateBtn);
		pageManager.click(calculateBtn);

	}

	public void calculateDosageRA_8mg(String weight) throws InterruptedException {
		pageManager.click(okBtn);
		pageManager.click(amountRadio);
		Thread.sleep(2000);

		pageManager.click(weightInput_RA);
		pageManager.sendKeys(weightInput_RA, weight);
		js.executeScript("arguments[0].scrollIntoView();", calculateBtn);
		pageManager.click(calculateBtn);

	}

	public void calculateDosagePJIA(String weight) throws InterruptedException {
		pageManager.click(okBtn);
		pageManager.click(weightInput_RA);
		pageManager.sendKeys(weightInput_RA, weight);
		js.executeScript("arguments[0].scrollIntoView();", calculateBtn);
		pageManager.click(calculateBtn);

	}

	public void calculateDosageSJIA(String weight) throws InterruptedException {
//		pageManager.click(sjiaTab);
		pageManager.click(okBtn);
		pageManager.click(weightInput_RA);
		pageManager.sendKeys(weightInput_RA, weight);
		js.executeScript("arguments[0].scrollIntoView();", calculateBtn);
		pageManager.click(calculateBtn);

	}

	public void calculateDosageGCA(String weight) throws InterruptedException {

		pageManager.click(okBtn);
		pageManager.click(weightInput_RA);
		pageManager.sendKeys(weightInput_RA, weight);
		js.executeScript("arguments[0].scrollIntoView();", calculateBtn);
		pageManager.click(calculateBtn);

	}

	public void calculateDosageCovid(String weight) throws InterruptedException, AWTException {

		pageManager.click(okBtn);
		pageManager.click(weightInput_RA);
		pageManager.sendKeys(weightInput_RA, weight);
		js.executeScript("arguments[0].scrollIntoView();", calculateBtn);
		pageManager.click(calculateBtn);

	}

}
