package UdemyJavaSelenium.Dropdowns;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import Utility.basepackage;

public class Dropdown_Dynamic extends basepackage {
    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.findElement(By.xpath("//input[@id='ctl00_mainContent_ddl_originStation1_CTXT']")).click();
        driver.findElement(By.xpath("//a[@value='BLR']")).click();
        driver.findElement(By.xpath("(//a[@value='TIR'])[2]")).click();
        driver.quit();

    }   
}