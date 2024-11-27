package AutomationAssignments;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class Assignment3 {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        driver.get("http://magento-demo.lexiconn.com/");
        driver.manage().window().maximize();

        List<WebElement> footerLinks = driver.findElements(By.cssSelector("div.footer a"));
        for (int i = 0; i < footerLinks.size(); i++) {
            footerLinks = driver.findElements(By.cssSelector("div.footer a"));
            WebElement link = footerLinks.get(i);
            String linkText = link.getText();
            link.click();
            System.out.println("Link: " + linkText);
            System.out.println("Title: " + driver.getTitle());
            driver.navigate().back();
            // driver.close();
    } 
    System.out.println("Script executed successfully");
     driver.close();
}}

/*
"Write a program -
1. To launch chrome browser.
2. Navigate to http://magento-demo.lexiconn.com/.
3. Click the footer links by using for loop, get the title of page and print it."
 */