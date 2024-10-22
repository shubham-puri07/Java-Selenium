package Practice;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WritingInExcel {


    public static void main(String[] args) {
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("TestSheet");

        Object Testdata [][] = {{"Days", "Dates", "abc"},{"Monday", "Tuesday", "Wedensday"}, {1, 2, 3}};

        int rows = Testdata.length;
        int columns = Testdata[0].length;


        System.out.println(rows);
        System.out.println(columns);

        for(int r = 0; r<rows; r++){

            XSSFRow row = sheet.createRow(r);

            for (int c = 0; c<columns; c++){

                XSSFCell cell = row.createCell(c);
                Object value = Testdata [r][c];
            }
        }

    }
    
}
