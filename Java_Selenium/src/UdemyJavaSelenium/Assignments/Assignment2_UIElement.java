package UdemyJavaSelenium.Assignments;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Assignment2_UIElement {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/angularpractice/");
        driver.findElement(By.cssSelector("input[class='form-control ng-pristine ng-invalid ng-touched']")).sendKeys("Testname");
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys("Test@gmail.com");
        driver.findElement(By.id("exampleInputPassword1")).sendKeys("Test@123");
        driver.findElement(By.xpath("//input[@type='checkbox']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement testdropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("exampleFormControlSelect1")));
        Select dropdown = new Select(testdropdown);
        dropdown.selectByValue("Male");
        driver.findElement(By.id("inlineRadio1")).click();
        driver.findElement(By.name("bday")).sendKeys("02/02/1992");
        driver.findElement(By.cssSelector(".btn-success")).click();
        System.out.println(driver.findElement(By.cssSelector(".alert-success")).getText());
        driver.quit();
    }
    
}
