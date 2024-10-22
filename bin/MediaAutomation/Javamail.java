package MediaAutomation;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class Javamail {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String CREDENTIALS_FILE_PATH = "C:\\Users\\shubham.puri\\Desktop\\TestWeb\\credentials.json"; // Specify the path to your credentials.json file

    public static void main(String[] args) throws Exception {
        // Load OAuth2 credentials
        Credential credential = getOAuth2Credential();

        // Create properties for IMAP access
        Properties props = new Properties();
        props.put("mail.imap.ssl.enable", "true");
        props.put("mail.imap.sasl.enable", "true");
        props.put("mail.imap.sasl.mechanisms", "XOAUTH2");

        // Connect to Gmail's IMAP server using the access token
        Session session = Session.getInstance(props);
        try (Store store = new IMAPStore(session, null) {
            @Override
            protected boolean protocolConnect(String host, int port, String user, String password) throws MessagingException {
                return super.protocolConnect(host, port, user, credential.getAccessToken());
            }
        }) {
            store.connect("imap.gmail.com", 993, "qatest2951@gmail.com", "74824267S"); 

            
            // Access the inbox folder
            IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            
            try (// Get the subject to search from the user
            Scanner scanner = new Scanner(System.in)) {
                System.out.print("Enter the subject to search: ");
                String subjectToSearch = scanner.nextLine();
                
                // Search for the email with the provided subject
                Message[] messages = inbox.search(new SubjectTerm(subjectToSearch));
                if (messages.length == 0) {
                    System.out.println("No messages found with the subject: " + subjectToSearch);
                } else {
                    for (Message message : messages) {
                        System.out.println("Found message with subject: " + message.getSubject());
                        
                        // Write email body to a text file
                        String emailContent = getTextFromMessage(message);
                        writeEmailBodyToFile(emailContent, "email_body.txt");
                    }
                }
            }
            inbox.close(false);
        }
    }

    // Method to get OAuth2 credentials
    private static Credential getOAuth2Credential() throws IOException, GeneralSecurityException {
        // Load client secrets.
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream(CREDENTIALS_FILE_PATH)));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Collections.singletonList("https://mail.google.com/"))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

                LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
                return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    // Helper method to extract text from email message
    private static String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return (String) message.getContent();
        } else if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    return (String) bodyPart.getContent();
                }
            }
        }
        return "";
    }

    // Helper method to write email body to a text file
    private static void writeEmailBodyToFile(String emailBody, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(emailBody);
            System.out.println("Email body has been written to " + fileName);
        } catch (IOException e) {
        }
    }
}
