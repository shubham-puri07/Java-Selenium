package Practice;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Automate_Amazon {

    public static void main(String[] args) throws IOException, InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        String[] Product = {"Smartphone", "toys", "Shirt"};
        driver.navigate().to("https://www.amazon.in/");

        for (String prod : Product) {
            // Print the product to the console
            System.out.println("Searching for: " + prod);
            
            // Send the product to the search bar
            driver.findElement(By.id("twotabsearchtextbox")).clear(); // Clear the search bar before entering a new product
            driver.findElement(By.id("twotabsearchtextbox")).sendKeys(prod);
            driver.findElement(By.id("nav-search-submit-button")).click();
            JavascriptExecutor js = (JavascriptExecutor)driver;
            // js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

// Scroll to the bottom slowly
for (int i = 0; i < 10; i++) {
    js.executeScript("window.scrollBy(0, 250);"); // Scrolls by 250 pixels at a time
    try {
        Thread.sleep(1000); // Wait for 1 second before the next scroll
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

            Thread.sleep(5000);
            // You can add a wait here if necessary to allow the page to load
            // For example, using Thread.sleep(2000); for a 2-second wait
            
            // Optionally, you can add code to select a product and add it to the cart here
            // For example:
            // driver.findElement(By.xpath("//span[normalize-space()='Some Product']")).click();
            // driver.findElement(By.id("add-to-cart-button")).click();
            
            // Navigate back to the main page for the next product search
            driver.navigate().to("https://www.amazon.in/");
        }

        driver.quit();
    }
}