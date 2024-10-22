package UdemyJavaSelenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumLocators extends Utility{

    public static void main(String[] args) throws InterruptedException {
        driver = new ChromeDriver();
        // Byid();
        // Xpath();
        // cssselector();
        Practicecode();
        driver.close();
    }

    static void Byid(){

		driver.get("https://rahulshettyacademy.com/loginpagePractise/");
		driver.findElement(By.id("username")).sendKeys("rahulshettyacademy");
		driver.findElement(By.id("password")).sendKeys("learning");
		driver.findElement(By.id("signInBtn")).click();
        System.out.println("Code executed successufully");
    }
    static void Xpath(){

        driver.get("https://rahulshettyacademy.com/locatorspractice/");
        driver.findElement(By.xpath("//input[@placeholder= 'Username']")).sendKeys("test");
        System.out.println("executed");
    }
    static void cssselector(){
        driver.get("https://rahulshettyacademy.com/locatorspractice/");
        driver.findElement(By.cssSelector("input[id=inputUsername]")).sendKeys("rahul");
        driver.findElement(By.cssSelector("input[type*=pass]")).sendKeys("rahulshettyacademy");
        driver.findElement(By.xpath("//button[contains(@class, 'submit')]")).click();
        System.out.println(driver.getTitle());
    }


static void Practicecode() throws InterruptedException {

    //this code is refered from udemy course

driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
driver.get("https://rahulshettyacademy.com/locatorspractice/");
driver.findElement(By.id("inputUsername")).sendKeys("rahul");
driver.findElement(By.name("inputPassword")).sendKeys("hello123");
driver.findElement(By.className("signInBtn")).click();
System.out.println(driver.findElement(By.cssSelector("p.error")).getText());
driver.findElement(By.linkText("Forgot your password?")).click();
Thread.sleep(1000);
driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys("John");
driver.findElement(By.cssSelector("input[placeholder='Email']")).sendKeys("john@rsa.com");
driver.findElement(By.xpath("//input[@type='text'][2]")).clear();
driver.findElement(By.cssSelector("input[type='text']:nth-child(3)")).sendKeys("john@gmail.com");
driver.findElement(By.xpath("//form/input[3]")).sendKeys("9864353253");
driver.findElement(By.cssSelector(".reset-pwd-btn")).click();
System.out.println(driver.findElement(By.cssSelector("form p")).getText());
driver.findElement(By.xpath("//div[@class='forgot-pwd-btn-conainer']/button[1]")).click();
Thread.sleep(1000);
driver.findElement(By.cssSelector("#inputUsername")).sendKeys("rahul");
driver.findElement(By.cssSelector("input[type*='pass']")).sendKeys("rahulshettyacademy");
driver.findElement(By.id("chkboxOne")).click();
driver.findElement(By.xpath("//button[contains(@class,'submit')]")).click();
    }
}