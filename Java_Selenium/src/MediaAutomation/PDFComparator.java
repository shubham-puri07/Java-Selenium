package MediaAutomation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;


public class PDFComparator {

    // Extract text from a given PDF file
    public static String extractTextFromPDF(String pdfPath) throws IOException {
        PDDocument document = PDDocument.load(new File(pdfPath));
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();
        return text;
    }

    // Compare text and return missing words
    public static HashSet<String> compareText(String text1, String text2) {
        HashSet<String> wordsInPdf1 = new HashSet<>();
        HashSet<String> wordsInPdf2 = new HashSet<>();
        HashSet<String> differences = new HashSet<>();

        for (String word : text1.split("\\s+")) {
            wordsInPdf1.add(word.trim());
        }

        for (String word : text2.split("\\s+")) {
            wordsInPdf2.add(word.trim());
        }

        differences.addAll(wordsInPdf1);
        differences.removeAll(wordsInPdf2);  // Words in PDF 1 but not in PDF 2
        return differences;
    }

    // Highlight text differences in PDF 1
    public static void highlightDifferencesInPdf(String originalPdfPath, HashSet<String> diffText, String outputPdfPath, PDType1Font font) throws IOException {
        PDDocument originalDoc = PDDocument.load(new File(originalPdfPath));
        PDPage page = originalDoc.getPage(0);
        PDPageContentStream contentStream = new PDPageContentStream(originalDoc, page, PDPageContentStream.AppendMode.APPEND, true, true);

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.setNonStrokingColor(255, 0, 0); // Red for missing text

        // Simple placement of text (This can be improved by calculating positions)
        contentStream.newLineAtOffset(50, 700); 
        for (String missingText : diffText) {
            contentStream.showText(missingText + " ");
            contentStream.newLine();
        }

        contentStream.endText();
        contentStream.close();
        originalDoc.save(outputPdfPath);
        originalDoc.close();
    }

    // Extract images from PDF and compare
    public static void compareImagesFromPdfs(String pdf1Path, String pdf2Path) throws IOException {
        PDDocument pdf1 = PDDocument.load(new File(pdf1Path));
        PDDocument pdf2 = PDDocument.load(new File(pdf2Path));

        // Extract images from both PDFs
        extractAndCompareImages(pdf1, "PDF 1");
        extractAndCompareImages(pdf2, "PDF 2");

        pdf1.close();
        pdf2.close();
    }

    // Helper method to extract images from PDF
    public static void extractAndCompareImages(PDDocument document, String pdfLabel) throws IOException {
        int pageIndex = 0;

        for (PDPage page : document.getPages()) {
            PDResources resources = page.getResources();
            for (COSName name : resources.getXObjectNames()) {
                if (resources.isImageXObject(name)) {
                    PDImageXObject image = (PDImageXObject) resources.getXObject(name);
                    BufferedImage bImage = image.getImage();
                    // Save extracted image to file
                    ImageIO.write(bImage, "png", new File(pdfLabel + "_Page_" + pageIndex + "_" + name.getName() + ".png"));
                    System.out.println("Image extracted: " + name.getName());
                    // Add logic to compare the images if needed
                }
            }
            pageIndex++;
        }
    }

    public static void main(String[] args) throws IOException {
        // String pdf1Path = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\gmail file.pdf";  // First PDF file
        String pdf1Path = "C:/Users/shubham.puri/Desktop/TestMedia/gmail file.pdf"; // First PDF file
        String pdf2Path = "C:/Users/shubham.puri/Desktop/TestMedia/AFD file.pdf";  // Second PDF file
        // String pdf2Path = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\AFD file.pdf";  // Second PDF file
        String outputPdfPath = "highlighted_differences.pdf"; // Result PDF with highlighted differences

        // Step 1: Extract text from both PDFs
        String pdf1Text = extractTextFromPDF(pdf1Path);
        String pdf2Text = extractTextFromPDF(pdf2Path);

        // Step 2: Compare text and find differences
        HashSet<String> differencesPdf1 = compareText(pdf1Text, pdf2Text);  // Text in PDF 1 missing in PDF 2
        HashSet<String> differencesPdf2 = compareText(pdf2Text, pdf1Text);  // Text in PDF 2 missing in PDF 1

        // Step 3: Highlight differences in the original PDF and save it
        highlightDifferencesInPdf(pdf1Path, differencesPdf1, outputPdfPath, PDType1Font.HELVETICA_BOLD);

        // Step 4: Compare images between PDFs
        compareImagesFromPdfs(pdf1Path, pdf2Path);

        System.out.println("Text and image comparison complete. Output PDF generated: " + outputPdfPath);
    }
}
