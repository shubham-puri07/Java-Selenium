package UdemyJavaSelenium.Dropdowns;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class tst {
    public static void main(String[] args) {
        
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//input[@id = \"checkBoxOption1\"]")).click();
      //  Assert.assertTrue(driver.findElement(By.xpath("//input[@id = \"checkBoxOption1\"]")).isSelected());
        System.out.println(driver.findElement(By.xpath("//input[@id = \"checkBoxOption1\"]")).isSelected());
        driver.findElement(By.xpath("//input[@id = \"checkBoxOption1\"]")).click();
       // Assert.assertFalse(driver.findElement(By.xpath("//input[@id = \"checkBoxOption1\"]")).isSelected());
       System.out.println(driver.findElement(By.xpath("//input[@id = \"checkBoxOption1\"]")).isSelected());
        List<WebElement> checkboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
        int checkboxCount = checkboxes.size();
        System.out.println(checkboxCount);
        System.out.println(driver.findElements(By.cssSelector("input[type='checkbox']")).size());
        driver.quit();
    }
}
