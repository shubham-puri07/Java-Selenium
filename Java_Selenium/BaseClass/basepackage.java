package BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class basepackage{
    
    public static WebDriver driver;

    static void Webdrivers(){
        WebDriver driver = new ChromeDriver();   
    }
}
