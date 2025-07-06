package utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelUtil {

	private Workbook workbook;
    private Sheet sheet;
	private String filePath;

	public ExcelUtil(Class<?> testClass) {
		String className = testClass.getSimpleName(); // e.g., AltTagTest
		String baseDir = System.getProperty("user.dir");
		this.filePath = baseDir + "/src/test/resources/InputTestData/" + className + ".xlsx";

		try (FileInputStream fis = new FileInputStream(filePath)) {
			workbook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			throw new RuntimeException("❌ Unable to open Excel file: " + filePath + "\n" + e.getMessage());
		}
	}

	public ExcelUtil(String excelPath, String methodName) {
		// TODO Auto-generated constructor stub
	}

	public String getCellData(String sheetName, int rowNum, int colNum) {
		Sheet sheet = workbook.getSheet(sheetName);
		if (sheet == null)
			throw new RuntimeException("Sheet not found: " + sheetName);
		Row row = sheet.getRow(rowNum);
		if (row == null)
			return "";
		Cell cell = row.getCell(colNum);
		return getCellValueAsString(cell);
	}

	public List<Map<String, String>> getDataAsList(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		if (sheet == null)
			throw new RuntimeException("Sheet not found: " + sheetName);

		List<Map<String, String>> dataList = new ArrayList<>();
		Row headerRow = sheet.getRow(0);
		int lastRowNum = sheet.getLastRowNum();

		for (int i = 1; i <= lastRowNum; i++) {
			Row row = sheet.getRow(i);
			Map<String, String> dataMap = new LinkedHashMap<>();
			for (int j = 0; j < headerRow.getLastCellNum(); j++) {
				String key = getCellValueAsString(headerRow.getCell(j));
				String value = getCellValueAsString(row.getCell(j));
				dataMap.put(key, value);
			}
			dataList.add(dataMap);
		}

		return dataList;
	}

	private String getCellValueAsString(Cell cell) {
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		case BLANK:
			return "";
		default:
			return "Unsupported Cell Type";
		}
	}

//**Below Method can be used in Data Provider**
	
	public Object[][] getDataAs2DArray() {
        List<Map<String, String>> dataList = new ArrayList<>();

        Row headerRow = sheet.getRow(0);
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = headerRow.getLastCellNum();

        for (int i = 1; i < rowCount; i++) {
            Map<String, String> rowData = new HashMap<>();
            Row row = sheet.getRow(i);

            for (int j = 0; j < colCount; j++) {
                Cell headerCell = headerRow.getCell(j);
                Cell dataCell = row.getCell(j);

                String key = headerCell.getStringCellValue();
                String value = getCellValueAsString(dataCell);

                rowData.put(key, value);
            }
            dataList.add(rowData);
        }

        // Convert List<Map> to Object[][]
        Object[][] dataArray = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            dataArray[i][0] = dataList.get(i);
        }

        return dataArray;
    }
	public void close() {
		try {
			workbook.close();
		} catch (IOException e) {
			System.err.println("Failed to close workbook: " + e.getMessage());
		}
	}
	

}
