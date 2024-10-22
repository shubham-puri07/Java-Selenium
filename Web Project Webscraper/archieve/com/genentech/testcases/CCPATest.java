package com.genentech.testcases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.genentech.baseclasses.BasePage;
import com.genentech.baseclasses.CCPAPage;
import com.genentech.util.TestCaseBase;

public class CCPATest extends TestCaseBase {

//	// ************************************************ CCPA & Footer Links
//	// ******
	@Test
	public void ccpaTest() throws Exception {
		BasePage bp = new BasePage(pageManager, excelReader);
		CCPAPage ccpa = new CCPAPage(pageManager, excelReader);

		String projectPath = System.getProperty("user.dir");
		String path = "/testdata/ccpa_input_url.xlsx";
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		String date = df.format(new Date());
		Boolean loggedIn = false;
		JavascriptExecutor js = (JavascriptExecutor) pageManager.getDriver();
		String footer_urls = null;
		String currentUrl;
		String title = null;
		String originalHandle;
		String ccpaModalURL = null;

		// Create new workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet linkSheet;
		Row linkRow;
		String actualURL;

		try {
			// Existing workbook
			FileInputStream fileInput = new FileInputStream(projectPath + path);
			XSSFWorkbook inputWorkbook = new XSSFWorkbook(fileInput);
			XSSFSheet inputSheet = inputWorkbook.getSheetAt(0);

			// Create a new font and alter it.
			XSSFFont font = workbook.createFont();
			font.setBold(true);

			// Fonts are set into a style so create a new one to use.
			CellStyle style = workbook.createCellStyle();
			style.setFont(font);

			// Set column names for output file
			linkSheet = workbook.createSheet("CCPAOutput");
			linkRow = linkSheet.createRow(0);
			linkRow.createCell(0).setCellValue("Site URL");
			linkRow.createCell(1).setCellValue("Link Text");
			linkRow.createCell(2).setCellValue("Actual URL");
			linkRow.createCell(3).setCellValue("Title");
			linkRow.createCell(4).setCellValue("Opened in a new tab?(Y/N)");

			for (int i = 0; i < 4; i++) {
				linkRow.getCell(i).setCellStyle(style);
			}

			int totalRows = inputSheet.getLastRowNum();
			if ((totalRows > 0) || (inputSheet.getPhysicalNumberOfRows() > 0)) {
				totalRows++;
			}

			String url = inputSheet.getRow(1).getCell(0).getStringCellValue();
			System.out.println(url);
			pageManager.navigateWithCacheClear(url); // This will clear all the cookies and HCP pop-up will be displayed

			if (url.contains("uat") && loggedIn == false) {
				bp.login("uat");
				loggedIn = true;
			} else if (url.contains("preview") || url.contains("preview2") && loggedIn == false) {
				bp.login("preview");
				loggedIn = true;
			}

//*******************************************************************************************************//	
			// Genentech-logo click
			ccpa.genentechLogo.click();
			// pageManager.switchToMainWindow();
			originalHandle = pageManager.getDriver().getWindowHandle();
			for (String handle : pageManager.getDriver().getWindowHandles()) {
				if (!handle.equals(originalHandle)) {
					pageManager.getDriver().switchTo().window(handle);
					break;
				}
			}
			// Get the URL of the current tab
			currentUrl = pageManager.getDriver().getCurrentUrl();
			title = pageManager.getTitle();
			System.out.println(currentUrl);
			// Close the current tab
			pageManager.getDriver().close();
			// Switch back to the original tab
			pageManager.getDriver().switchTo().window(originalHandle);

			int rowNum = linkSheet.getLastRowNum();
			linkRow = linkSheet.createRow(rowNum + 1);
			linkRow.createCell(0).setCellValue(url);
			linkRow.createCell(1).setCellValue(title + " logo");
			linkRow.createCell(2).setCellValue(currentUrl);
			linkRow.createCell(3).setCellValue(title);
			// Validate if the link opened in a new tab
			linkRow.createCell(4).setCellValue(currentUrl.equals("about:blank") ? "No" : "Yes");

//****************************************************************************************************//
			for (int i = 1; i <= ccpa.ccpa_links.size(); i++) {
				try {
					WebElement footer_Links = ccpa.ccpa_links.get(i - 1)
							.findElement(By.xpath("//div[@class='cmp-footer__nav ']/a[" + i + "]"));
					String target = footer_Links.getAttribute("target");
					footer_urls = footer_Links.getText();
					System.out.println(footer_urls);

					// ***********************Footer links*************************************//

					if (!footer_urls.equals("Your Privacy Choices")
							|| footer_urls.equals("Sus opciones de privacidad")) {

						if (target.equals("_blank")) {
							footer_Links.click();
							// Wait for the new tab to open
							pageManager.getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

							// Switch to the new tab
							originalHandle = pageManager.getDriver().getWindowHandle();
							for (String handle : pageManager.getDriver().getWindowHandles()) {
								if (!handle.equals(originalHandle)) {
									pageManager.getDriver().switchTo().window(handle);
									break;
								}
							}
							// Get the URL of the current tab
							currentUrl = pageManager.getDriver().getCurrentUrl();
							title = pageManager.getTitle();
							// Close the current tab
							pageManager.getDriver().close();
							// Switch back to the original tab
							pageManager.getDriver().switchTo().window(originalHandle);

							// Validate if the link opened in a new tab
							if (target.equals("_blank")) {
								System.out.println("Link '" + footer_urls + "' opened in a new tab.");
							} else {
								System.out.println("Link '" + footer_urls + "' did not open in a new tab.");
							}

							rowNum = linkSheet.getLastRowNum();
							linkRow = linkSheet.createRow(rowNum + 1);
							linkRow.createCell(0).setCellValue(url);
							linkRow.createCell(1).setCellValue(footer_urls);
							linkRow.createCell(2).setCellValue(currentUrl);
							linkRow.createCell(3).setCellValue(title);
							linkRow.createCell(4).setCellValue("Yes");
						}

						else if (target.equals("_self")) {
							footer_Links.click();
							currentUrl = pageManager.getDriver().getCurrentUrl();
							title = pageManager.getTitle();
							rowNum = linkSheet.getLastRowNum();
							linkRow = linkSheet.createRow(rowNum + 1);
							linkRow.createCell(0).setCellValue(url);
							linkRow.createCell(1).setCellValue(footer_urls);
							linkRow.createCell(2).setCellValue(currentUrl);
							linkRow.createCell(3).setCellValue(title);
							linkRow.createCell(4).setCellValue("No");

							pageManager.navigate(url);
						}
					}

					// ********************************************************************************//
					// *********************************** CCPA
					// Modal***********************************//
					else {
						footer_Links.click();
						boolean isModalDisplayed = footer_Links.isDisplayed();

						if (isModalDisplayed) {
							System.out.println("OneTrust CCPA modal is displayed.");
							new WebDriverWait(pageManager.getDriver(), Duration.ofSeconds(2)).until(
									ExpectedConditions.textToBePresentInElement(ccpa.pp_cookies, "Privacy Policy"));

							try {
								// Handling the CCPA pop-up
								pageManager.switchToNewWindow();
								js.executeScript("arguments[0].click()", ccpa.pp_cookies);
								// pageManager.switchToMainWindow();
								originalHandle = pageManager.getDriver().getWindowHandle();
								for (String handle : pageManager.getDriver().getWindowHandles()) {
									if (!handle.equals(originalHandle)) {
										pageManager.getDriver().switchTo().window(handle);
										break;
									}
								}
								// Get the URL of the current tab
								currentUrl = pageManager.getDriver().getCurrentUrl();
								System.out.println(currentUrl);
								// Close the current tab
								pageManager.getDriver().close();
								// Switch back to the original tab
								pageManager.getDriver().switchTo().window(originalHandle);
								// *******************************//
							} catch (Exception exception) {
								System.out.println("CCPA accepted");
							}

							// Strictly Necessary cookies
							ccpa.sn_cookies.click();
							// Functional Cookies
							ccpa.f_cookies.click();
							js.executeScript("arguments[0].click()", ccpa.toggle_f);
							// Performance Cookies
							ccpa.p_cookies.click();
							js.executeScript("arguments[0].click()", ccpa.toggle_p);
							// ccpa.toggle_p.click();
							// Targeting Cookies
							ccpa.t_cookies.click();
							js.executeScript("arguments[0].click()", ccpa.toggle_t);
							// Social Media Cookies
							ccpa.sm_cookies.click();
							js.executeScript("arguments[0].click()", ccpa.toggle_sm);					
							
							// Allow all Cookies
							ccpa.allow_cookies.click();

							// Again click Your privacy choices
							footer_Links.click();

							// Reject all Cookies
							ccpa.reject_cookies.click();
							footer_Links.click();

							// Confirm My cookies choices
							// Functional Cookies
							ccpa.f_cookies.click();
							js.executeScript("arguments[0].click()", ccpa.toggle_f_off);

							// Targeting Cookies
							ccpa.p_cookies.click();
							js.executeScript("arguments[0].click()", ccpa.toggle_p_off);

							ccpa.confirm_cookies.click();

							System.out.println("OneTrust CCPA modal is working.");

							ccpaModalURL = pageManager.getDriver().getCurrentUrl();

							rowNum = linkSheet.getLastRowNum();
							linkRow = linkSheet.createRow(rowNum + 1);
							linkRow.createCell(0).setCellValue(url);
							linkRow.createCell(1).setCellValue(footer_urls);
							linkRow.createCell(2).setCellValue(ccpaModalURL);
							linkRow.createCell(3).setCellValue(footer_urls);
							// Validate if the link opened in a new tab
							linkRow.createCell(4).setCellValue("CCPA Modal is validated and Passing");

						} else {

							rowNum = linkSheet.getLastRowNum();
							linkRow = linkSheet.createRow(rowNum + 1);
							linkRow.createCell(0).setCellValue(url);
							linkRow.createCell(1).setCellValue(footer_urls);
							linkRow.createCell(2).setCellValue(ccpaModalURL);
							linkRow.createCell(3).setCellValue(footer_urls);
							// Validate if the link opened in a new tab
							linkRow.createCell(4).setCellValue("CCPA Modal is validated and Failing");

						}

					}
				} catch (NoSuchElementException e) {

				}
			}

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(
					projectPath + "/test-output/ccpaFooter/ccpaFooterTest_" + date + ".xlsx");
			workbook.write(out);
			workbook.close();
			out.close();
			System.out.println("Excel Created!");
		}

	}
}
