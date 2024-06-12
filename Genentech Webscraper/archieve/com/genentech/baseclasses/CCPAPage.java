package com.genentech.baseclasses;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.genentech.util.ExcelReader;
import com.genentech.util.PageManager;
import com.genentech.util.PageObject;

public class CCPAPage extends PageObject {
	
	@FindBy(xpath = "//a[contains(., 'OK')]")
	public WebElement hcpPopup;
	
	@FindBy(css = "img.cmp-footer__logo")
	public WebElement genentechLogo;	
	
	@FindBy(xpath = "//div[@class='cmp-footer__nav ']/a")
	public List<WebElement> ccpa_links;
	
	@FindBy(xpath = "//div[@class='ot-sdk-four ot-sdk-columns ot-tab-list']/ul/li")
	public List<WebElement> ccpa_Modal_tabs;	
	
	@FindBy(xpath = "//.cmp-footer__nav a[contains(., 'Contact Us')]")
	public WebElement contactUs;
	
	@FindBy(xpath = "//.cmp-footer__nav a[contains(., 'Your Privacy Choices')]")
	public WebElement ccpaModal;
	
	@FindBy(xpath = "//*[@id='ot-pc-desc']/p/a")
	public WebElement pp_cookies;
	
	@FindBy(xpath = "//*[@id='ot-header-id-C0001' or text()='Strictly Necessary Cookies']")
	public WebElement sn_cookies;
	
	@FindBy(xpath = "//*[@id='ot-header-id-C0003' or text()='Functional Cookies']")
	public WebElement f_cookies;
	
	@FindBy(xpath = "//input[@id = 'ot-group-id-C0003' and @aria-checked = 'true']")
	public WebElement toggle_f;
	
	@FindBy(xpath = "//input[@id = 'ot-group-id-C0003' and @aria-checked = 'false']")
	public WebElement toggle_f_off;
	
	@FindBy(xpath = "//*[@id='ot-header-id-C0002' or text()='Performance Cookies']")
	public WebElement p_cookies;
	
	@FindBy(xpath = "//input[@id = 'ot-group-id-C0002' and @aria-checked = 'true']")
	public WebElement toggle_p;
	
	@FindBy(xpath = "//input[@id = 'ot-group-id-C0002' and @aria-checked = 'false']")
	public WebElement toggle_p_off;
	
	@FindBy(xpath = "//*[@id='ot-header-id-C0004' or text()='Targeting Cookies']")
	public WebElement t_cookies;	
	
	@FindBy(xpath = "//input[@id = 'ot-group-id-C0004' and @aria-checked = 'true']")
	public WebElement toggle_t;
	
	
	
	@FindBy(xpath = "//*[@id='ot-header-id-C0005' or text()='Social Media Cookies']")
	public WebElement sm_cookies;
	
	@FindBy(xpath = "//input[@id = 'ot-group-id-C0005' and @aria-checked = 'true']")
	public WebElement toggle_sm;
	
	@FindBy(xpath = "//*[@class='ot-pc-refuse-all-handler' or text()='Reject All Cookies']")
	public WebElement reject_cookies;
	
	@FindBy(xpath = "//*[@id='accept-recommended-btn-handler' or text()='Allow All Cookies']")
	public WebElement allow_cookies;
	
	@FindBy(xpath = "//*[@class='save-preference-btn-handler onetrust-close-btn-handler' or text()='Confirm My Cookies Choices']")
	public WebElement confirm_cookies;
	
	
	

	public CCPAPage(PageManager pm, ExcelReader xl) {
		super(pm, xl);
		// TODO Auto-generated constructor stub
	}

}
