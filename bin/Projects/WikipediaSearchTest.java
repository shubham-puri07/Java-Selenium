package Projects;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WikipediaSearchTest {
	
		
		    private WebDriver driver;
		    private String baseUrl = "https://www.wikipedia.org/";
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
		    public void testWikipediaSearch() {
		        driver.get(baseUrl);

		        // Find the search input field and enter a query
		        WebElement searchField = driver.findElement(By.id("searchInput"));
		        searchField.sendKeys("Selenium (software)");
		        sleep();

		        // Click the search button
		        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
		        searchButton.click();
		        sleep();

		        // Verify that the search result page contains the expected article title
		        WebElement heading = driver.findElement(By.id("firstHeading"));
		        assertEquals("Selenium (software)", heading.getText());
		        sleep();
		    }

		    @AfterMethod
		    public void tearDown() {
		        if (driver != null) {
		            driver.quit();
		        }
		    }
		    
		    private void sleep() {
		        try {
		            Thread.sleep(3000);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }
		}