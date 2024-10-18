package Practice;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PrintPageTitle {

	public static void main(String[] args) {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.strikingly.com/");
		
		String title = driver.getTitle();
		System.out.println("\033[1m"+title+"\033[1m");

		driver.quit();

	}

}
