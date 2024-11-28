package UdemyJavaSelenium.Dropdowns;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utility.basepackage;

public class Static_Dropdown_52 extends basepackage {

    public static void main(String[] args) {
        //String url = "https://rahulshettyacademy.com/dropdownsPractise/";
        driver = new ChromeDriver(); // This will launch the chrome browser
        driver.manage().window().maximize();// This will maximize the browser window
        driver.get(weburl);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement staticdropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_mainContent_DropDownListCurrency")));
       // WebElement staticdropdown = driver.findElement(By.id("ctl00$mainContent$DropDownListCurrency"));
        Select dropdown = new Select(staticdropdown);
        dropdown.selectByIndex(3);
        System.out.println(dropdown.getFirstSelectedOption().getText());
        driver.quit();
    }
}


















// driver = new ChromeDriver();
// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
// driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
// driver.manage().window().maximize();
// WebElement staticdropdown = driver.findElement(By.id("ctl00_mainContent_DropDownListCurrency"));
// Select dropdown = new Select(staticdropdown);
// dropdown.selectByIndex(2);
// System.out.println(dropdown.getOptions());
// dropdown.selectByValue("USD");
// System.out.println(dropdown.getFirstSelectedOption().getText());
// driver.quit();
