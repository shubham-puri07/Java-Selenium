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

public class emailtext {

    private static final String PDF_FILE_PATH = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\Input\\testPDF.pdf";
    private static final String FINAL_OUTPUT_PATH = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\Output\\FinalOutput.html";

    public static void main(String[] args) {
        // Extract text, images, and links from PDF
        String pdfText = extractTextFromPDF(PDF_FILE_PATH);
        List<String> pdfImages = extractImagesFromPDF(PDF_FILE_PATH);

        // Extract text, images, and links from Gmail email
        String emailText = extractTextFromEmail();
        List<String> emailImages = extractImagesFromEmail();
        List<String> emailLinks = extractLinksFromEmail();

        // Compare and save the results
        compareAndSaveResults(pdfText, emailText, pdfImages, emailImages, emailLinks);
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

    // Method to extract images from a PDF file
    private static List<String> extractImagesFromPDF(String pdfFilePath) {
        // Implement image extraction logic if needed
        return List.of(); // Placeholder
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
        } catch (InterruptedException e) {
        } finally {
            driver.quit();
        }
        return emailText;
    }

    // Method to extract images from an email
    private static List<String> extractImagesFromEmail() {
        // Implement image extraction logic if needed
        return List.of(); // Placeholder
    }

    // Method to extract links from an email
    private static List<String> extractLinksFromEmail() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        List<String> emailLinks = List.of();

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
                List<WebElement> links = emailBody.findElements(By.tagName("a"));
                emailLinks = links.stream().map(link -> link.getAttribute("href")).toList();
            } else {
                System.out.println("No email found with the subject: " + emailSubject);
            }
        } catch (InterruptedException e) {
        } finally {
            driver.quit();
        }
        return emailLinks;
    }

    // Method to compare PDF and email content and save the results in HTML format
    private static void compareAndSaveResults(String pdfText, String emailText, List<String> pdfImages, List<String> emailImages, List<String> emailLinks) {
        StringBuilder result = new StringBuilder();
        boolean match = pdfText.equals(emailText);

        result.append("<html><body>");

        if (match) {
            result.append("<h1>The texts match.</h1>");
        } else {
            result.append("<h1>The texts do not match</h1><br>");

            // Compare line by line
            String[] pdfLines = pdfText.split("\n");
            String[] emailLines = emailText.split("\n");
            int maxLines = Math.max(pdfLines.length, emailLines.length);

            for (int i = 0; i < maxLines; i++) {
                String pdfLine = i < pdfLines.length ? pdfLines[i] : "";
                String emailLine = i < emailLines.length ? emailLines[i] : "";

                if (!pdfLine.equals(emailLine)) {
                    result.append("<p><strong>Mismatch at line ").append(i + 1).append(":</strong><br>");
                    result.append("<strong>PDF:</strong> ").append(pdfLine).append("<br>");
                    result.append("<strong>Email:</strong> ").append(emailLine).append("</p>");
                }
            }
        }

        // Add images to the report
        result.append("<h2>Images Comparison:</h2>");
        if (pdfImages.isEmpty() && emailImages.isEmpty()) {
            result.append("<p>No images found.</p>");
        } else {
            result.append("<ul>");
            for (String image : emailImages) {
                result.append("<li><img src=\"").append(image).append("\" alt=\"Image\" width=\"300\"></li>");
            }
            result.append("</ul>");
        }

        // Add links to the report
        result.append("<h2>Links found in Email:</h2>");
        if (emailLinks.isEmpty()) {
            result.append("<p>No links found.</p>");
        } else {
            result.append("<ul>");
            for (String link : emailLinks) {
                result.append("<li><a href=\"").append(link).append("\">").append(link).append("</a></li>");
            }
            result.append("</ul>");
        }

        result.append("</body></html>");

        // Write the result to an HTML file with timestamp
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String timestamp = LocalDateTime.now().format(formatter);
            String fileName = FINAL_OUTPUT_PATH.replace("FinalOutput.html", "FinalOutput_" + timestamp + ".html");

            try (FileWriter fileWriter = new FileWriter(fileName)) {
                fileWriter.write(result.toString());
                System.out.println("Comparison results saved to: " + fileName);
            }
        } catch (IOException e) {
        }
    }
}


