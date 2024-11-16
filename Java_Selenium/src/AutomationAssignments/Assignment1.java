//"Write a program to launch following browsers -
// 1. Chrome Browser, navigate to ""http://automationpractice.com/index.php"" site, Maximize it, getTitle of page, print a message ""Chrome Browser Launched"" and close the browser.
// 2. Firefox Browser, navigate to ""http://automationpractice.com/index.php"" site, Maximize it, getTitle of page, print a message ""Firefox Browser Launched"" and close the browser."

package AutomationAssignments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Assignment1 {

    static void firefoxbrowser(){        
        WebDriver driver = new FirefoxDriver();
        driver.get("http://automationpractice.com/index.php");
        driver.manage().window().maximize();
        driver.getTitle();
        System.out.println("Firefox Browser Launched");
        driver.close();
    }

    static void chromedriver(){
        WebDriver driver = new ChromeDriver();
        driver.get("http://automationpractice.com/index.php");
        driver.manage().window().maximize();
        driver.getTitle();
        System.out.println("Chrome Browser Launched");
        driver.close();
    }

    public static void main(String[] args) {
        // firefoxbrowser();
        chromedriver();
    }
    
}
