/*To introduce delays automatically without calling a sleep method each time, 
you can use Selenium's WebDriverWait in combination with an ExpectedConditions class. 
This approach waits for a certain condition to be met before proceeding, 
which can help with element visibility or interactability without hardcoding sleep times.*/

package Projects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;


public class Login_Logout2 {

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
        enterText(By.id("user-name"), "standard_user");

        // Enter password
        enterText(By.id("password"), "secret_sauce");

        // Click login button
        clickElement(By.id("login-button"));

        // Verify login by checking the presence of the logout button
        verifyElementDisplayed(By.id("react-burger-menu-btn"));

        // Click the burger menu button
        clickElement(By.id("react-burger-menu-btn"));

        // Click the logout button
        clickElement(By.id("logout_sidebar_link"));

        // Verify logout by checking the presence of the login button
        verifyElementDisplayed(By.id("login-button"));
    }

    @AfterMethod
    public void tearDown() {
    	 if (driver != null) {
             driver.quit();
        }
    }
    
    private void enterText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.sendKeys(text);
        sleep();
    }

    private void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        sleep();
    }

    private void verifyElementDisplayed(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        AssertJUnit.assertTrue(element.isDisplayed());
        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
