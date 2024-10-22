package UdemyJavaSelenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Utility {

    public static WebDriver driver;

    static void Webdrivers(){
        WebDriver driver = new ChromeDriver();   
    }
}
