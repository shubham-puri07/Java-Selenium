package MediaAutomation;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmailSearch {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
       // ChromeOptions options = new ChromeOptions();
        //options.addArguments("--disable-web-security', '--allow-running-insecure-content"); 
      //  driver = new ChromeDriver(options);

    //  driver = new ChromeDriver();
 
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Scanner scanner = new Scanner(System.in);  // Scanner to take user input

        try {
            driver.get("https://mail.google.com/");

           Thread.sleep(25000);

            // System.out.print("Enter ipm gmail ID: ");
            // String email = scanner.nextLine();
            // System.out.print("Enter ipm password: ");
            // String password = scanner.nextLine(); 

            //driver.findElement(by);

            // WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("identifierId")));
            // emailField.sendKeys(email);
            // driver.findElement(By.id("identifierNext")).click();
            // WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
            // passwordField.sendKeys(password);
            // driver.findElement(By.id("passwordNext")).click();

            //driver.findElement(By.className("Search mail")).sendKeys("abc");
            
            wait.until(ExpectedConditions.elementToBeClickable(By.name("q"))); 
            System.out.print("Enter the subject line of the email to search: ");
            String emailSubject = scanner.nextLine();  

            WebElement searchBox = driver.findElement(By.name("q"));  
            searchBox.sendKeys("subject:" + emailSubject);
            searchBox.submit();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='main']")));

            List<WebElement> emailRows = driver.findElements(By.cssSelector("tr.zA"));  // 'tr.zA' --> table row identifies email rows in Gmail

            if (!emailRows.isEmpty()) {
                WebElement mostRecentEmail = emailRows.get(0);
                mostRecentEmail.click();
                WebElement emailBody = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='listitem']")));  // Email body container
                String emailText = emailBody.getText();

                // Define the output file path with the current date and time
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
                String timestamp = LocalDateTime.now().format(formatter);
                String fileName = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\Output\\TestSend_Output_" + timestamp + ".txt";

                // Write the email content to the file
                try (FileWriter fileWriter = new FileWriter(fileName)) {
                    fileWriter.write(emailText);
                    System.out.println("Email content saved to: " + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No email found with the subject: " + emailSubject);
            }


            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
            scanner.close(); 
        }
    }
}
