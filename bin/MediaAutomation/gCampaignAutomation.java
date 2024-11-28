package MediaAutomation;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class gCampaignAutomation {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://gcampaign.gene.com/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//input[@id='login-username']")).sendKeys("shubham.puri@perficient.com");
        driver.findElement(By.xpath("//input[@id='login-password']")).sendKeys("jZ9cH7yR7");
        driver.findElement(By.xpath("//button[@id='login-login']")).click();

        // // Add explicit wait
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-login")));

        // loginButton.click();
    }
}
