package MediaAutomation;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ContentTesting {

    // Method to extract text from a PDF file
    public static String extractTextFromPDF(String pdfFilePath) {
        String extractedText = "";
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            extractedText = pdfStripper.getText(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractedText;
    }

    // Method to extract text from Gmail by subject
    public static String extractTextFromEmail(String subject, String email, String password) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", "imap.gmail.com");
        properties.put("mail.imaps.port", "993");

        // Creating a mail session
        Session emailSession = Session.getDefaultInstance(properties);
        Store store = emailSession.getStore("imaps");

        // Connecting to the mail server with email and password (App Password recommended)
        store.connect("imap.gmail.com", email, password);

        // Opening the inbox folder
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        // Searching for the email with the given subject
        Message[] messages = inbox.search(new SubjectTerm(subject));

        if (messages.length > 0) {
            Message message = messages[0];
            // Handling plain text emails
            if (message.isMimeType("text/plain")) {
                return message.getContent().toString();
            } 
            // Handling multipart emails (common for modern emails)
            else if (message.isMimeType("multipart/*")) {
                Multipart multipart = (Multipart) message.getContent();
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    if (bodyPart.isMimeType("text/plain")) {
                        return bodyPart.getContent().toString();
                    }
                }
            }
        }
        return "";
    }

    // Method to compare the content of two texts and highlight differences
    public static void compareTexts(String pdfText, String emailText) {
        if (pdfText == null || emailText == null) {
            System.out.println("One or both of the input texts are null.");
            return;
        }

        if (pdfText.equals(emailText)) {
            System.out.println("The texts are identical.");
        } else {
            System.out.println("The texts do not match. Differences are:");

            // Using DiffRowGenerator to generate the differences between the texts
        //    DiffRowGenerator generator = DiffRowGenerator.create()
            //         .showInlineDiffs(true)
            //         .inlineDiffByWord(true)
            //         .build();

            // List<DiffRow> rows = generator.generateDiffRows(
            //         List.of(pdfText.split("\n")),
            //         List.of(emailText.split("\n"))
            // );

            // Displaying the differences line by line
        //     for (DiffRow row : rows) {
        //         System.out.println("Original: " + row.getOldLine());
        //         System.out.println("Revised: " + row.getNewLine());
        //         System.out.println("Change: " + row.getTag());
        //     }
        }
    }

    public static void main(String[] args) throws Exception {
        // Path to the PDF file
        String pdfPath = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\Input\\testPDF.pdf";

        // Gmail credentials and email subject
        String email = "qatest2951@gmail.com";
        
        // Use the app-specific password instead of the regular Gmail password
        String password = "your-app-password";  // Replace with the generated App Password
        String emailSubject = "Fwd: [article] Official Atlassian Events > Enhancing Your Team24 Europe Experience with Agenda Builder";

        // Extract text from the PDF file
        String pdfText = extractTextFromPDF(pdfPath);

        // Extract text from the email using the subject
        String emailText = extractTextFromEmail(emailSubject, email, password);

        // Compare the extracted text from the PDF and the email
        compareTexts(pdfText, emailText);
    }
}
