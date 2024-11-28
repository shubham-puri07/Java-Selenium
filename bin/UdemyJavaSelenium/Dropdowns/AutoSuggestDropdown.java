package UdemyJavaSelenium.Dropdowns;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AutoSuggestDropdown {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));  // Adjust duration as needed
        driver.findElement(By.id("autosuggest")).sendKeys("ind");
        Thread.sleep(5000);
        List<WebElement> options = driver.findElements(By.cssSelector("a.ui-corner-all'"));
        
        for (WebElement option : options) {
            if(option.getText().equalsIgnoreCase("British Indian Ocean Territory")){
                option.click();
                break;
            }
        }
    }    
}
