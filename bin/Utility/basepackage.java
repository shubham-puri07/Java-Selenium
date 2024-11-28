package Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class basepackage{
    
    public static WebDriver driver;
    public static String weburl = "https://rahulshettyacademy.com/dropdownsPractise/";
    // public static String urls = "https://www.makemytrip.com/";
    // public static String flightbooking = "https://www.ixigo.com/";

    static void Webdrivers(){
        WebDriver driver = new ChromeDriver();   
    }
    
    static void url (){
        String weburl = "https://rahulshettyacademy.com/dropdownsPractise/";
    }
}
