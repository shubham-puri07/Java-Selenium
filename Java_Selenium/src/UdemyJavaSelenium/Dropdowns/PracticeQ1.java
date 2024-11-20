package UdemyJavaSelenium.Dropdowns;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class PracticeQ1 {

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) { 
        WebDriver driver = new ChromeDriver();
        driver.get("https://practice.expandtesting.com/dropdown");
        Select dropdown = new Select(driver.findElement(By.xpath("//*[@id = 'dropdown']")));
        System.out.println("Enter the option that needs to be selected (1 or 2):");
        int option = sc.nextInt();
        if(option == 1){
            dropdown.selectByValue("1");
        } else if(option == 2){
            dropdown.selectByValue("2");
        } else {
            System.out.println("Invalid option selected");
        }

        Select dropdown2 = new Select(driver.findElement(By.xpath("//*[@id = 'elementsPerPageSelect']")));
        System.out.println("Enter the option that needs to be selected (10, 20, 50, or 100):");
        int option2 = sc.nextInt();
        switch (option2) {
            case 10 -> dropdown2.selectByValue("10");
            case 20 -> dropdown2.selectByValue("20");
            case 50 -> dropdown2.selectByValue("50");
            case 100 -> dropdown2.selectByValue("100");
            default -> System.out.println("Invalid option selected");
        }
        //System.out.println(dropdown2.getFirstSelectedOption().getText());
        driver.close();
    }
}
// I am trying to automate a dropdown menu using Selenium WebDriver with Java. The dropdown menu has two options: "Option 1" and "Option 2"