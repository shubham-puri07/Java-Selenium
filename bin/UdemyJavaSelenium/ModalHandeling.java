package UdemyJavaSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import Utility.basepackage;

public class ModalHandeling extends basepackage {

    public static void main(String[] args) {
        
        driver = new ChromeDriver();
        driver.get("https://www.makemytrip.com/");

        WebElement model = driver.findElement(By.id(""));
        driver.findElement(By.id(""));

    }    
}
