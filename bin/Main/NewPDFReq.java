package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NewPDFReq {
    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\shubham.puri\\Genentech\\Genentech Webscraper\\testdata\\test.xlsx";
        String outputFilePath = "C:\\Users\\shubham.puri\\Genentech\\Genentech Webscraper\\testdata\\output.xlsx";

        // Load the input file
        List<String> urls = loadURLsFromExcel(inputFilePath);

        // Set up WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Store output data
        List<String[]> outputData = new ArrayList<>();

        // Iterate through URLs
        for (String url : urls) {
            try {
                new URL(url); // Validate the URL
                driver.get(url);

                // Handle HCP modal if present
                handleHcpModal(driver);

                // Wait for the page to load completely
                waitForPageLoad(driver);

                // Iterate through tabs if present
                List<WebElement> tabs = driver.findElements(By.cssSelector("css-selector-for-tabs")); // Replace with actual CSS selector for tabs
                if (!tabs.isEmpty()) {
                    for (WebElement tab : tabs) {
                        tab.click();
                        waitForPageLoad(driver); // Ensure the page is loaded after clicking the tab
                        extractPdfLinks(driver, url, outputData);
                    }
                } else {
                    // If no tabs are present, extract PDFs from the current page
                    extractPdfLinks(driver, url, outputData);
                }
            } catch (MalformedURLException e) {
                System.out.println("Invalid URL: " + url);
                // You can also add the invalid URL to a separate list for further analysis
            }
        }

        // Write to output Excel file
        writeOutputToExcel(outputFilePath, outputData);

        // Close the driver
        driver.quit();
    }

    public static void handleHcpModal(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hcp-modal-id"))); // Replace with actual ID of the modal
            WebElement closeButton = modal.findElement(By.cssSelector("button.close-modal")); // Replace with actual CSS selector for the close button
    
            closeButton.click();
            wait.until(ExpectedConditions.invisibilityOf(modal));
        } catch (Exception e) {
            System.out.println("No HCP modal found or it could not be closed.");
        }
    }

    public static void extractPdfLinks(WebDriver driver, String url, List<String[]> outputData) {
        List<WebElement> pdfLinks = driver.findElements(By.xpath("//a[contains(@href,'.pdf')]"));
        for (WebElement link : pdfLinks) {
            outputData.add(new String[]{url, link.getAttribute("href")});
        }
    }

    public static List<String> loadURLsFromExcel(String filePath) {
        List<String> urls = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    urls.add(cell.getStringCellValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urls;
    }

    public static void writeOutputToExcel(String filePath, List<String[]> data) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("PDF Links");

            // Create header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("URL");
            header.createCell(1).setCellValue("PDF Link");

            // Write data
            int rowCount = 1;
            for (String[] entry : data) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(entry[0]);
                row.createCell(1).setCellValue(entry[1]);
            }

            // Save the output file
            try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
                workbook.write(fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void waitForPageLoad(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }
}
