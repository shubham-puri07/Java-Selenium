package AutomationAssignments;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Assignment4 {
    public static void main(String[] args) {
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream("C:/Automation/AssignmentProp.properties")) {
            properties.load(fis);
            String Username = properties.getProperty("username");
            String Password = properties.getProperty("password");
            String URL = properties.getProperty("url");
            String UsernameXpath = properties.getProperty("usernamepath");
            String PasswordXpath = properties.getProperty("passwordxpath"); // Fixed key name
            String SigninButton = properties.getProperty("button");
            WebDriver driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get(URL);
            driver.findElement(By.xpath(UsernameXpath)).sendKeys(Username);
            driver.findElement(By.xpath(PasswordXpath)).sendKeys(Password);
            driver.findElement(By.xpath(SigninButton)).click();
            System.out.println("Page Title: " + driver.getTitle());

            if (driver.findElement(By.xpath("//div[contains(text(), 'Secure Page page for Automation Testing Practice')]")).isDisplayed()) {
                System.out.println("Login Successful!");
                } else {
                    System.out.println("Login Failed!");
                    }
                    driver.quit();
        } catch (IOException e) {
        } 
    }
}