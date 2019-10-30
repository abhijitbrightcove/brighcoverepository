import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

public class ReadInput
{
    public static void main(String[] args)
    {
        try {
            FileInputStream file = new FileInputStream(new File("D:\\brightcovescript\\video_cloud.xlsx"));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);


            //we will search for column index containing string "Your Column Name" in the row 0 (which is first row of a worksheet
            String[] columnWantedArray = {"account_id", "video_id", "patch_request"};
            Integer columnNo = null;
            //output all not null values to the list
            List<Cell> cells = new ArrayList<Cell>();
            List<String> account_id_list = new ArrayList<String>();

            List<String> video_id_list = new ArrayList<String>();
            List<String> patch_request_list = new ArrayList<String>();
            Map<String,Patch_update> updateMap = new HashMap<String,Patch_update>();
            Row firstRow = sheet.getRow(0);
        for(String  columnWanted: columnWantedArray){

            for (Cell cell : firstRow) {
                if (cell.getStringCellValue().equals(columnWanted)) {
                    columnNo = cell.getColumnIndex();
                }
            }


            if (columnNo != null) {
                for (Row row : sheet) {

                    Cell c = row.getCell(columnNo);
                    if (c == null || c.getCellType() == CellType.BLANK || (c.getCellType() == CellType.STRING && columnWanted.equals(c.getStringCellValue()))) {

                        // Nothing in the cell in this row, skip it
                    } else {
                        if("account_id".equals(columnWanted)){
                            account_id_list.add(new BigDecimal((c.getNumericCellValue())).toString());
                        }
                        if("video_id".equals(columnWanted)){
                            video_id_list.add(new BigDecimal((c.getNumericCellValue())).toString());
                        }
                        if("patch_request".equals(columnWanted)){
                            patch_request_list.add(String.valueOf(c.getStringCellValue()));
                        }

                    }
                }
            } else {
                System.out.println("could not find column " + columnWanted + " in first row of " + file.toString());
            }
        }

            /*for (String account_id : account_id_list) {
                updateMap.put(account_id)
            }*/


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
