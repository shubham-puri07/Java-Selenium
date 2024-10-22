package Practice;


import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Automate_Amazon {

    public static void main(String[] args) throws IOException{

        
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
       

        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        driver.navigate().to("https://www.amazon.in/");

        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Smarthphone");
        driver.findElement(By.id("nav-search-submit-button")).click();
        driver.findElement(By.xpath("//span[normalize-space()='Apple iPhone 15 Plus (128 GB) - Yellow']")).click();
        driver.findElement(
                By.xpath("//div[@class='a-section a-spacing-none a-padding-none']//input[@id='add-to-cart-button']"))
                .click();
        // driver.findElement(By.id("add-to-cart-button")).click();

        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        driver.quit();

    }

}
