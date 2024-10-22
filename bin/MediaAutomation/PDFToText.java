package MediaAutomation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFToText {
    public static void main(String[] args) {
        String pdfFilePath = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\Input\\testPDF.pdf";
        String textFilePath = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\Output\\output.txt"; /// store in excel for better optimization

        try {
            PDDocument document = PDDocument.load(new File(pdfFilePath));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String extractedText = pdfStripper.getText(document);
            document.close();
            

            FileWriter writer = new FileWriter(textFilePath);
            writer.write(extractedText);
            writer.close();


            //FileOutputStream outstream = new FileOutputStream('Resources')       excel me write krna hai

            System.out.println("Text extracted and saved to " + textFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}