package MediaAutomation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Mergecode {

    private static final String PDF_FILE_PATH = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\Input\\testPDF.pdf";
    private static final String FINAL_OUTPUT_PATH = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\Output\\FinalOutput.txt";

    public static void main(String[] args) {
        // Extract text from PDF file
        String pdfText = extractTextFromPDF(PDF_FILE_PATH);

        // Extract text from Gmail email
        String emailText = extractTextFromEmail();

        // Compare and save the results
        compareAndSaveResults(pdfText, emailText);
    }

    // Method to extract text from a PDF file
    private static String extractTextFromPDF(String pdfFilePath) {
        String extractedText = "";
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            extractedText = pdfStripper.getText(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractedText;
    }

    // Method to extract text from an email using Selenium WebDriver
    private static String extractTextFromEmail() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        String emailText = "";

        try {
            driver.get("https://mail.google.com/");
            Thread.sleep(25000); // Wait for Gmail to load

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the subject line of the email to search: ");
            String emailSubject = scanner.nextLine();
            scanner.close();

            wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("subject:" + emailSubject);
            searchBox.submit();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='main']")));
            List<WebElement> emailRows = driver.findElements(By.cssSelector("tr.zA"));

            if (!emailRows.isEmpty()) {
                WebElement mostRecentEmail = emailRows.get(0);
                mostRecentEmail.click();
                WebElement emailBody = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='listitem']")));
                emailText = emailBody.getText();
            } else {
                System.out.println("No email found with the subject: " + emailSubject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return emailText;
    }

    // Method to compare PDF and email text and save the results
    private static void compareAndSaveResults(String pdfText, String emailText) {
        StringBuilder result = new StringBuilder();
        boolean match = pdfText.equals(emailText);

        if (match) {
            result.append("The texts match.");
        } else {
            result.append("The texts do not match.\n");

            // Compare line by line
            String[] pdfLines = pdfText.split("\n");
            String[] emailLines = emailText.split("\n");
            int maxLines = Math.max(pdfLines.length, emailLines.length);

            for (int i = 0; i < maxLines; i++) {
                String pdfLine = i < pdfLines.length ? pdfLines[i] : "";
                String emailLine = i < emailLines.length ? emailLines[i] : "";

                if (!pdfLine.equals(emailLine)) {
                    result.append("Mismatch at line ").append(i + 1).append(":\n");
                    result.append("PDF: ").append(pdfLine).append("\n");
                    result.append("Email: ").append(emailLine).append("\n");
                }
            }
        }

        // Write the result to a file with timestamp
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String timestamp = LocalDateTime.now().format(formatter);
            String fileName = FINAL_OUTPUT_PATH.replace("FinalOutput.txt", "FinalOutput_" + timestamp + ".txt");

            try (FileWriter fileWriter = new FileWriter(fileName)) {
                fileWriter.write(result.toString());
                System.out.println("Comparison results saved to: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}