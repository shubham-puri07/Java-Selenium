package UdemyJavaSelenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Alert {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        driver.findElement(By.id("name")).sendKeys("Shubham");
        driver.findElement(By.cssSelector("input[value='Alert']")).click();
        System.out.println(driver.switchTo().alert().getText());
        driver.switchTo().alert().accept();

        driver.findElement(By.id("name")).sendKeys("Shubham");
        driver.findElement(By.cssSelector("input[value='Confirm']")).click();
        System.out.println(driver.switchTo().alert().getText());
        driver.switchTo().alert().dismiss();
        driver.quit();

    }
}

// public static void main(String[] args) {
// String text="Rahul";
// // System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
// WebDriver driver = new ChromeDriver();
// driver.get("https://rahulshettyacademy.com/AutomationPractice/");
// driver.findElement(By.id("name")).sendKeys(text);
// driver.findElement(By.cssSelector("[id='alertbtn']")).click();
// System.out.println(driver.switchTo().alert().getText());
// driver.switchTo().alert().accept();
// driver.findElement(By.id("confirmbtn")).click();
// System.out.println(driver.switchTo().alert().getText());
// driver.switchTo().alert().dismiss();
// }
// }