package Projects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;


	
public class LoginLogout {

    WebDriver driver;
    String baseUrl = "https://www.saucedemo.com/";  // Demo website for testing
    WebDriverWait wait;

    
	@BeforeMethod
    public void setUp() {
		 WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testLoginLogout() throws InterruptedException {	
        driver.get(baseUrl);

        // Enter username
        WebElement usernameField = driver.findElement(By.id("user-name"));
        usernameField.sendKeys("standard_user");
        Thread.sleep(3000);

        // Enter password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("secret_sauce");
        Thread.sleep(3000);

        // Click login button
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
        Thread.sleep(3000);

        // Verify login by checking the presence of the logout button
        WebElement burgerMenuButton = driver.findElement(By.id("react-burger-menu-btn"));
        AssertJUnit.assertTrue(burgerMenuButton.isDisplayed());
        Thread.sleep(3000);

        // Click the burger menu button
        burgerMenuButton.click();
        Thread.sleep(3000);

        // Click the logout button
        WebElement logoutButton = driver.findElement(By.id("logout_sidebar_link"));
        logoutButton.click();
        Thread.sleep(3000);

        // Verify logout by checking the presence of the login button
        WebElement loginButtonAfterLogout = driver.findElement(By.id("login-button"));
        AssertJUnit.assertTrue(loginButtonAfterLogout.isDisplayed());
        Thread.sleep(3000);
    }

    @AfterMethod
    public void tearDown() {
    	 if (driver != null) {
             driver.quit();
        }
    }
}

