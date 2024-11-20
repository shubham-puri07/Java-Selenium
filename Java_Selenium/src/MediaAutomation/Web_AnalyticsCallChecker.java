package MediaAutomation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v128.network.Network;

public class Web_AnalyticsCallChecker {

    public static void main(String[] args) throws IOException {

        String inputFilePath = "C:\\Users\\shubham.puri\\Desktop\\TestWeb\\InputURLs.xlsx";
        String outputFolderPath = "C:\\Users\\shubham.puri\\Desktop\\TestWeb\\Output data\\";
        
        // Create timestamp for output file name
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String outputFilePath = outputFolderPath + "MetricsCallOutput_" + timestamp + ".xlsx";

        // Initialize ChromeDriver and DevTools
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);

        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Read input Excel sheet
        FileInputStream fis = new FileInputStream(inputFilePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet inputSheet = workbook.getSheetAt(0);
        int rowCount = inputSheet.getPhysicalNumberOfRows();

        // Prepare output Excel workbook
        Workbook outputWorkbook = new XSSFWorkbook();
        Sheet outputSheet = outputWorkbook.createSheet("Output");
        Row header = outputSheet.createRow(0);
        header.createCell(0).setCellValue("URL");
        header.createCell(1).setCellValue("Google Analytics");
        header.createCell(2).setCellValue("Adobe Analytics");
        header.createCell(3).setCellValue("Status");

        int outputRowNum = 1;

        // Register the network request listener (only once)
        final boolean[] ga4Fired = new boolean[1];
        final boolean[] adobeAnalyticsFired = new boolean[1];

        devTools.addListener(Network.requestWillBeSent(), request -> {
            String requestUrl = request.getRequest().getUrl();

            // Check for Google Analytics 4 (GA4)
            if (requestUrl.contains("https://analytics.google.com")) {
                ga4Fired[0] = true;
                System.out.println("Google Analytics is Firing");
            }

            // Check for Adobe Analytics
            if (requestUrl.contains("/b/ss/")) {
                adobeAnalyticsFired[0] = true;
                System.out.println("Adobe Analytics call is Firing");
            }
        });

        // Iterate over each URL in the input sheet
        for (int i = 1; i < rowCount; i++) {
            Row row = inputSheet.getRow(i);
            String url = row.getCell(0).getStringCellValue();
            System.out.println("Checking URL: " + url);

            // Reset flags for each URL
            ga4Fired[0] = false;
            adobeAnalyticsFired[0] = false;

            // Navigate to URL
            driver.get(url);
            try {
                // Wait for page to load (or adjust time as needed)
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Write the results to the output Excel file
            Row outputRow = outputSheet.createRow(outputRowNum++);
            outputRow.createCell(0).setCellValue(url);
            outputRow.createCell(1).setCellValue(ga4Fired[0] ? "Yes" : "No");
            outputRow.createCell(2).setCellValue(adobeAnalyticsFired[0] ? "Yes" : "No");
            outputRow.createCell(3).setCellValue((ga4Fired[0] || adobeAnalyticsFired[0]) ? "Pass" : "Fail");
        }

        // Save the output file
        FileOutputStream fos = new FileOutputStream(outputFilePath);
        outputWorkbook.write(fos);
        fos.close();
        workbook.close();
        outputWorkbook.close();
        driver.quit();

        System.out.println("Execution completed. Output saved to: " + outputFilePath);
    }
}
