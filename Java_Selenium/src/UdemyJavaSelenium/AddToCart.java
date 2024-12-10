package UdemyJavaSelenium;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * In this class added the code where searching the number of product and adding them into the cart
 */
public class AddToCart {

    public static void main(String[] args) {

        // Declare an array of strings
        // String[] products = {"Smartphone", "Laptop", "Tablet"};

        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/seleniumPractise");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        List<WebElement> Products = driver.findElements(By.cssSelector("[class$='product-name']"));

        String[] fruit = { "Brocolli", "Onion", "Banana" };

        for (WebElement product : Products) {
            String name = product.getText();
            // System.out.println(name);
            for (String fruitName : fruit) {
                if (name.contains(fruitName)) {
                    product.findElement(By.xpath("./following-sibling::div/button")).click();
                    System.out.println(fruitName + " added to the cart");
                    break;
                }
            }
        } driver.close();

    }
}
