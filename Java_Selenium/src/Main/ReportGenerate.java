package Main;

import java.time.Duration;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ReportGenerate {
    public static void main(String[] args) throws InterruptedException {

        Scanner input = new Scanner(System.in);
        WebDriver driver = new ChromeDriver();
        driver.get("https://gcampaign.gene.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.findElement(By.id("login-username")).sendKeys("shubham.puri@perficient.com");
        driver.findElement(By.id("login-password")).sendKeys("jZ9cH7yR7");
        Thread.sleep(3000);

        driver.findElement(By.xpath("//button[@id='login-login']")).click();
        
        Thread.sleep(3000);

        System.out.println("Enter the Tactic code"); //student
        String TacticCode = input.next();
        driver.findElement(By.xpath("//input[@placeholder='Search for a code']")).sendKeys(TacticCode);
        driver.findElement(By.xpath("//i[@class='icon-search']")).click();
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get the search result element
        WebElement searchResult = driver.findElement(By.xpath("//div[@class='search-result']"));
        
        // Print the search result to the terminal
        System.out.println("Search Result:");
        System.out.println(searchResult.getText());

        driver.close();
    }
}