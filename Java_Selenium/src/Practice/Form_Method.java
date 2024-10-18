package Practice;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Form_Method {

    public static void main(String[] args) {

        
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.techlistic.com/p/selenium-practice-form.html");

        driver.findElement(By.xpath(
                "/html[1]/body[1]/div[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[1]/div[1]/article[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/h2[2]/div[1]/div[1]/div[2]/input[1]"))
                .sendKeys("TestName");

        driver.findElement(By.name("lastname")).sendKeys("Test_LastName");

        driver.findElement(By.id("sex-0")).click();
        driver.findElement(By.id("exp-1")).click();
        driver.findElement(By.id("datepicker")).sendKeys("29/05/2004");
        driver.findElement(By.id("profession-1")).click();
        driver.findElement(By.id("tool-2")).click();
        driver.findElement(By.id("continents")).click();

        WebElement dropdown = driver.findElement(By.id("continents"));
        dropdown.findElement(By.xpath("//option[. = 'Europe']")).click();

        // final WebElement dropdown = driver.findElement(By.id("selenium_commands"));
        dropdown.findElement(By.xpath("//option[. = 'Browser Commands']")).click();

        // Scroll Vertical

        // js.executeScript("window.scrollTo(0,675.5555419921875)");

        // Click Submit
        driver.findElement(By.id("submit")).click();
    }
}
