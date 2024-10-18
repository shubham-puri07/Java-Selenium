package Practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class URL_Functionality {

    public static void main(String[] args) throws IOException {

    WebDriver driver = new ChromeDriver();

    FileInputStream abc = new FileInputStream("C:\\Users\\shubham.puri\\2.Selenium\\Test URL functionality.xlsx");
    XSSFWorkbook xsf = new XSSFWorkbook(abc);
    XSSFSheet sheet = xsf.getSheetAt(0);
    for (Row row : sheet) {

        Cell cell = row.getCell(0);

        if (cell!= null) {
            String url = cell.getStringCellValue();
            System.out.println("Checking URL: " + url);
            System.out.println("Passed");

        }

        else {
            System.out.println("URL is null");
        }
    } driver.quit();
// } finally{ driver.quit();}

}

public void Create_Output_Sheet()throws IOException {{

    
		// Create new workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet linkSheet_title;
		Row titleRow,metaRow,otherRow;
		// Create a new font and alter it.
		XSSFFont font = workbook.createFont();
		font.setBold(true);

		// Fonts are set into a style so create a new one to use.
		CellStyle style = workbook.createCellStyle();
		style.setFont(font);
		
		linkSheet_title = workbook.createSheet("Title_Output");
		titleRow = linkSheet_title.createRow(0);
		titleRow.createCell(0).setCellValue("Url");
		titleRow.createCell(1).setCellValue("Expected Title");
		titleRow.createCell(2).setCellValue("Actual Title");
		titleRow.createCell(3).setCellValue("Status");
		
		for (int i = 0; i < titleRow.getLastCellNum(); i++) {
			titleRow.getCell(i).setCellStyle(style);
		} 
   }
  
 }
}


            //driver.get("https://wamua.roche.com/idp/eo5WD/resumeSAML20/idp/SSO.ping");
            // driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[1]/div[1]/div[2]/b[1]/b[1]/form[1]/ul[1]/li[1]")).sendKeys("PURIS1");
            // driver.findElement(By.xpath("/html[1]/body[1]/div[3]/div[2]/div[1]/div[1]/div[1]/form[1]/div[1]/p[1]/input[1]")).sendKeys("Spidercat07");
            // driver.findElement(By.xpath("/html[1]/body[1]/div[3]/div[2]/div[1]/div[1]/div[1]/form[1]/p[3]/input[1]")).click();
            // driver.get(url);

