package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UncoveredScenario {

    public static void main(String[] args) throws IOException {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000));
        FileInputStream inputfile = new FileInputStream(new File("C://Users//shubham.puri//Desktop//TestWeb//InputData.xlsx"));
        Workbook workbook = new XSSFWorkbook(inputfile);
        Sheet sheet = workbook.getSheetAt(0);

        // printing the output data for each urls in the separate columns
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        // Iterating through the rows to get urls data
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Cell siteUrlCell = row.getCell(0); // Site URLs column
            Cell pdfUrlCell = row.getCell(1); // Add the PDF URL in the second column

            String siteUrls = siteUrlCell.getStringCellValue();
            String pdfUrl = pdfUrlCell.getStringCellValue();

            // Spliting the Site URLs you can use ; or anyother
            String[] siteUrlArray = siteUrls.split(",");

            // Iterate through each Site URL
            int columnIndex = 2; // Start from column 3 (index 2)
            for (String siteUrl : siteUrlArray) {
                siteUrl = siteUrl.trim();
                Cell resultCell = row.createCell(columnIndex);

                // Check if the URL starts with "https://"
                if (siteUrl.startsWith("https://")) {
                    boolean isPdfFound = false;
                    try {
                        driver.get(siteUrl);
                        // Wait for the page to load completely
                        new WebDriverWait(driver, Duration.ofSeconds(5000)).until(
                                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

                        // Fetching all PDF links from the site
                        List<WebElement> pdfLinks = driver.findElements(By.xpath("//a[contains(@href, '.pdf')] | //iframe[contains(@src, '.pdf')] | //embed[contains(@src, '.pdf')]"));

                        // Checking PDF URL is present on the site
                        for (WebElement link : pdfLinks) {
                            String href = link.getAttribute("href");
                            if (href != null && href.equals(pdfUrl)) {
                                isPdfFound = true;
                                break;
                            }
                        }

                        if (isPdfFound) {
                            resultCell.setCellValue("PDF Present on site url");
                        } else {
                            resultCell.setCellValue("PDF not present on site url");
                        }
                    } catch (NoSuchElementException e) {
                        // Adding exception here for if URL not found then moving to next one
                        System.out.println("Element not found on page for URL: " + siteUrl + " - " + e.getMessage());
                        resultCell.setCellValue("Element Not Found");
                    } catch (Exception e) {
                        // exception for if the URL navigate to error page or 404 then script don't get failed should jump on next URL
                        System.out.println("Error processing URL: " + siteUrl + " - " + e.getMessage());
                        resultCell.setCellValue("Error processing URL");
                    }
                } else {
                    // If the URL doesn't start with "https://", skip it
                    System.out.println("Skipping invalid URL: " + siteUrl);
                    resultCell.setCellValue("Invalid URL");
                }
                resultCell.setCellStyle(cellStyle);

                columnIndex++; // Move to the next column
            }
        }
        inputfile.close();

        // Generate a new filename with the current date and time
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String outputFileName = "output_" + timestamp + ".xlsx";
        FileOutputStream outputfile = new FileOutputStream(new File("C:\\Users\\shubham.puri\\Desktop\\TestWeb\\Output data\\" + outputFileName));

        // Write the output to a new file
        workbook.write(outputfile);
        outputfile.close();

        workbook.close();
        driver.quit();

        System.out.println("Results saved to: " + outputFileName);
    }
}