package UdemyJavaSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumLocators extends Utility{


    public static void main(String[] args) {

        driver = new ChromeDriver();
    
        Xpath();

        driver.close();
    }

    
    static void Byid(){

		driver.get("https://rahulshettyacademy.com/loginpagePractise/");
		driver.findElement(By.id("username")).sendKeys("rahulshettyacademy");
		driver.findElement(By.id("password")).sendKeys("learning");
		driver.findElement(By.id("signInBtn")).click();
		driver.close();
        System.out.println("Code executed successufully");
    }

    static void Xpath(){

        driver.get("https://rahulshettyacademy.com/locatorspractice/");
        driver.findElement(By.xpath("//input[@placeholder= 'Username']")).sendKeys("test");
        System.out.println("executed");
        
    }
}

