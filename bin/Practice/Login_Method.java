package Practice;
import java.time.Duration;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Login_Method {

    public static void main(String[] args){
    	
    	LoginByInput();
    }

    public static void LoginByInput(){

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("https://practicetestautomation.com/practice-test-login/");

        int logintry =0;
        
        while(logintry < 3){

        Scanner input = new Scanner(System.in);
        System.out.println("Enter the username"); //student
        String Username = input.next();
        System.out.println("Enter the Password");
        String Password = input.next();   //username Password12

        driver.findElement(By.id("username")).sendKeys(Username);
        driver.findElement(By.id("password")).sendKeys(Password);
        driver.findElement(By.id("submit")).click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        
        WebElement errorMessage = driver.findElement(By.id("error"));
        if (errorMessage.isDisplayed()) {
            System.out.println(errorMessage.getText());
        }

        // Verify the error message text is "Your username is invalid!"
        else if (errorMessage.getText().equals("Your password is invalid!")) {
            System.out.println("Error message text is 'Your password is invalid!'");
        }
        
        
        else if(driver.getTitle().equals("Logged In Successfully")){
            System.out.println("Successfully Logged in !!");
        }
        
        else {
                System.out.println(driver.getTitle());
                driver.quit();
        }

        logintry++;

    }
    System.out.println("Too Many Failed Attempts\nPlease try again later");
    driver.getTitle();
        driver.quit(); 
    }

    public static void Valid_Login_Scenarion() {
    
        WebDriver driver = new ChromeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");
        driver.manage().window().maximize();

        driver.findElement(By.id("username")).sendKeys("student");
        driver.findElement(By.id("password")).sendKeys("Password123");
        driver.findElement(By.id("submit")).click();
        driver.findElement(By.xpath(
                "/html[1]/body[1]/div[1]/div[1]/section[1]/div[1]/div[1]/article[1]/div[2]/div[1]/div[1]/div[1]/a[1]"))
                .click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.getTitle();

        driver.quit();

    }
}