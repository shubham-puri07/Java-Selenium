package AutomationAssignments;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Assignment2 {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        
        try {
            driver.get("http://magento-demo.lexiconn.com/");
            driver.manage().window().maximize();

            List<WebElement> footerLinks = driver.findElements(By.xpath("//div[@class='footer']//a"));
            for (WebElement link : footerLinks) {
                System.out.println("Text of Link: " + link.getText());
                System.out.println("Link URLs: " + link.getAttribute("href"));
            }
        } catch (Exception e) {
        } finally {
            driver.quit();
        }
    }
}