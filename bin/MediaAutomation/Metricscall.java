package MediaAutomation;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Metricscall {

    public static void main(String[] args) throws IOException {
        // Paths for input and output files
        String inputFilePath = "C:\\Users\\shubham.puri\\Desktop\\TestWeb\\InputURLs.xlsx";
        String outputFolderPath = "C:\\Users\\shubham.puri\\Desktop\\TestWeb\\Output data\\";
        // ChromeDriver setup
       // System.setProperty("webdriver.chrome.driver", "path_to_chromedriver"); // Replace with your ChromeDriver path
        ChromeOptions options = new ChromeOptions();

        WebDriver driver = new ChromeDriver(options);

        // Read input Excel file
        FileInputStream fis = new FileInputStream(inputFilePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet inputSheet = workbook.getSheetAt(0);
        int rowCount = inputSheet.getPhysicalNumberOfRows();

        // Prepare output data
        Workbook outputWorkbook = new XSSFWorkbook();
        Sheet outputSheet = outputWorkbook.createSheet("Output");
        Row header = outputSheet.createRow(0);
        header.createCell(0).setCellValue("URL");
        header.createCell(1).setCellValue("GA4 Firing");
        header.createCell(2).setCellValue("Adobe Analytics Firing");
        header.createCell(3).setCellValue("Status");

        int outputRowNum = 1;

        // Process each URL
        for (int i = 1; i < rowCount; i++) {
            Row row = inputSheet.getRow(i);
            String url = row.getCell(0).getStringCellValue();
            System.out.println("Checking URL: " + url);

            // Navigate to URL
            driver.get(url);

            // Add delay to allow Omnibug to capture requests
            try {
                Thread.sleep(5000); // Adjust as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Manually check the browser's Omnibug panel for analytics calls
            // NOTE: Programmatic access to Omnibug logs isn't straightforward. 
            // For automation, use Chrome DevTools as a fallback.

            // Write results to output sheet
            Row outputRow = outputSheet.createRow(outputRowNum++);
            outputRow.createCell(0).setCellValue(url);
            outputRow.createCell(1).setCellValue("Yes/No"); // Replace with actual result
            outputRow.createCell(2).setCellValue("Yes/No"); // Replace with actual result
            outputRow.createCell(3).setCellValue("Pass/Fail"); // Replace with actual result
        }

        // Save output Excel file
        String outputFilePath = outputFolderPath + "OutputMetrics.xlsx";
        FileOutputStream fos = new FileOutputStream(outputFilePath);
        outputWorkbook.write(fos);
        fos.close();

        // Clean up
        workbook.close();
        outputWorkbook.close();
        driver.quit();

        System.out.println("Execution completed. Output saved to: " + outputFilePath);
    }
}
