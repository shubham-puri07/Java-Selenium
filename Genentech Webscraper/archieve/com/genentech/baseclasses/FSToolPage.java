package com.genentech.baseclasses;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.genentech.util.ExcelReader;
import com.genentech.util.PageManager;
import com.genentech.util.PageObject;

public class FSToolPage extends PageObject {
	
	@FindBy(xpath = "//a[contains(., 'OK')]")
	public WebElement hcpPopup;
	
	@FindBy(id = "onetrust-accept-btn-handler")
    public WebElement ccpaBtn;
	
	@FindBy(xpath = ".//select[@name='prescribed-for']")
	public WebElement dropDown;
	
	@FindBy(xpath = ".//input[@name='type-of-insurance'][@value='private-commercial']")
	public WebElement privateRadioBtn;
	
	@FindBy(xpath = ".//input[@name='type-of-insurance'][@value='government']")
	public WebElement pubRadioBtn;
	
	@FindBy(xpath = ".//input[@value='none']")
	public WebElement noneRadioBtn;
	
	@FindBy(xpath = ".//input[@name='other-assistance'][@value='yes']")
	public WebElement yesOARadioBtn;
	
	@FindBy(xpath = ".//input[@name='other-assistance'][@value= 'no']")
	public WebElement noOARadioBtn;
	
	@FindBy(xpath = ".//input[@value='co-pay']")
	public WebElement coPayRadioBtn;
	
	@FindBy(xpath = ".//input[@value='patient-foundation']")
	public WebElement patientFoundationRadioBtn;
	
	@FindBy(xpath = ".//input[@value='independent-other']")
	public WebElement independentRadioBtn;
	
	@FindBy(xpath = ".//input[@name='covered-by-insurance'][@value='yes']")
	public WebElement yesCBIRadioBtn;
	
	@FindBy(xpath = ".//input[@name='covered-by-insurance'][@value='no']")
	public WebElement noCBIRadioBtn;
	
	@FindBy(xpath = ".//input[@name='covered-by-insurance'][@value='unsure']")
	public WebElement unsureCBIRadioBtn;
	
	@FindBy(xpath = "//button[contains(., 'Next')]")
	public WebElement submitBtn;
	
	//*********************Household Income tool***********************//
	@FindBy(xpath = ".//select[@name='number-of-household-members']")
	public WebElement memberDrpDwn;
	
	@FindBy(xpath = ".//select[@name='net-household-income']")
	public WebElement incomeDrpDwn;
	
	@FindBy(xpath = "//button[contains(., 'Confirm')]")
	public WebElement confirmBtn;
	
	
	//*********************FS Tool version 3 extra webElements********************************//
	@FindBy(xpath = ".//input[@name='covered-by-insurance'][@value='dont-have']")
	public WebElement donthaveCBIRadioBtn;

	public FSToolPage(PageManager pm, ExcelReader xl) {
		super(pm, xl);
		// TODO Auto-generated constructor stub
	}
	
	JavascriptExecutor js = (JavascriptExecutor) pageManager.getDriver();
	
	public void fstoolScenarios() {
		Select indicationDrpDwn = new Select(dropDown);
		indicationDrpDwn.selectByIndex(1);
		js.executeScript("arguments[0].click()", privateRadioBtn);
		//privateRadioBtn.click();
		js.executeScript("arguments[0].click()", noOARadioBtn);
		//coPayRadioBtn.click();
		js.executeScript("arguments[0].click()", yesCBIRadioBtn);

		submitBtn.click();
		
		
	}

}
