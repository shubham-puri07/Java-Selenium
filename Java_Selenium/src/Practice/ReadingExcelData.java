package Practice;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadingExcelData {

    public static void main(String[] args) throws IOException {

        FileInputStream input = new FileInputStream("C:\\Automation\\Excel Files for selenium\\Test URL functionality.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(input);
        XSSFSheet sheet = workbook.getSheetAt(0);

        //Printing the data from the excel sheet using for loop

        int rows = sheet.getLastRowNum();
        int colm = sheet.getRow(1).getLastCellNum();

        for (int i =0; i<rows; i++){

            XSSFRow row = sheet.getRow(i);

            for (int j = 0; j<colm; j++) {

                XSSFCell cell = row.getCell(j);

              switch (cell.getCellType()){

                   case STRING: System.out.println("String object");
                   case NUMERIC: System.out.println("Num object");
              }
            }
                System.out.println();

        }
    }
}
