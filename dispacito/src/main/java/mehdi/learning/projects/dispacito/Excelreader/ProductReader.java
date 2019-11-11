package mehdi.learning.projects.dispacito.Excelreader;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.management.InstanceAlreadyExistsException;

import java.util.ArrayList;

public class ProductReader {
	ArrayList<ArrayList<String>>  productData = new ArrayList<ArrayList<String>>();
	Workbook workbook = null;
	Sheet datatypeSheet = null;
	String filePath = null;

	public ProductReader(String FILE_NAME) {  	
		this.filePath = FILE_NAME;
		FileInputStream excelFile;
		try {
			excelFile = new FileInputStream(new File(FILE_NAME));
			workbook = new XSSFWorkbook(excelFile);
			datatypeSheet = workbook.getSheetAt(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<ArrayList<String>> ProductInfos() {
		int rowStart  = this.datatypeSheet.getFirstRowNum();
		System.out.println("rowstart :" + rowStart );
		int rowEnd  = this.datatypeSheet.getLastRowNum();
		System.out.println("rowEnd :" + rowEnd );

		////////    data array //////////

		for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
			Row r = datatypeSheet.getRow(rowNum);
			if (r == null) {
				continue;
			}

			int lastcolumn = r.getLastCellNum();
			productData.add(new ArrayList<String>());

			for(int columnNum = 0 ; columnNum < lastcolumn ; columnNum++) {
				Cell cellValue = r.getCell(columnNum);
				if (cellValue == null){
					productData.get(rowNum).add("---");
					continue;
				}
				switch (cellValue.getCellType()) {	            		
				case STRING:
					productData.get(rowNum).add(cellValue.getRichStringCellValue().getString());
					break;

				case NUMERIC:
					if (DateUtil.isCellDateFormatted(cellValue)) {
						productData.get(rowNum).add(String.valueOf(cellValue.getDateCellValue()));
					} else {
						productData.get(rowNum).add(String.valueOf(cellValue.getNumericCellValue()));
					}
					break;

				case BOOLEAN:
					productData.get(rowNum).add(String.valueOf(cellValue.getBooleanCellValue()));
					break;

				case FORMULA:
					productData.get(rowNum).add(String.valueOf(cellValue.getCellFormula()));
					break;
				case BLANK:
					productData.get(rowNum).add("---");
					break;

				default:
					productData.get(rowNum).add("***");
				}
			}	 
		}

		return productData;
	}
	
	public Object[][] ProductInfosBeta() {

		int rowStart  = this.datatypeSheet.getFirstRowNum();
		int objectIndex = 0;
		System.out.println("rowstart :" + rowStart );
		int rowEnd  = this.datatypeSheet.getLastRowNum();
		System.out.println("rowEnd :" + rowEnd );
		Object[][] productDatabeta = new Object[rowEnd][7];
		////////    data array //////////

		for (int rowNum = 1; rowNum <= rowEnd; rowNum++) {

			Row r = datatypeSheet.getRow(rowNum);
			if (r == null) {
				continue;
			}


			int lastcolumn = r.getLastCellNum();
			productDatabeta[objectIndex][6] = rowNum;
			for(int columnNum = 0 ; columnNum < lastcolumn ; columnNum++) {
				Cell cellValue = r.getCell(columnNum);
				if (cellValue == null){
					productDatabeta[objectIndex][columnNum] = "---";
					continue;
				}
				switch (cellValue.getCellType()) {	            		
				case STRING:
					productDatabeta[objectIndex][columnNum] = cellValue.getRichStringCellValue().getString();
					break;

				case NUMERIC:
					if (DateUtil.isCellDateFormatted(cellValue)) {
						productDatabeta[objectIndex][columnNum] = String.valueOf(cellValue.getDateCellValue());
					} else {
						productDatabeta[objectIndex][columnNum] = String.valueOf(cellValue.getNumericCellValue());
					}
					break;

				case BOOLEAN:
					productDatabeta[objectIndex][columnNum] = String.valueOf(cellValue.getBooleanCellValue());
					break;

				case FORMULA:
					productDatabeta[objectIndex][columnNum] = String.valueOf(cellValue.getCellFormula());
					break;
				case BLANK:
					productDatabeta[objectIndex][columnNum] = "---";
					break;

				default:
					productDatabeta[objectIndex][columnNum] = "***";
				}
			}	 
			objectIndex++;
		}

		return productDatabeta;
	}


	public void readProductInfos(ArrayList<ArrayList<String>> productData) {
		int rowlength = 0;
		for(int indexRow = 0; indexRow< productData.size(); indexRow++) { 
			System.out.println("new row");
			rowlength = productData.get(indexRow).size();
			for (int indexColumn = 0; indexColumn < rowlength; indexColumn++) {
				System.out.println("value : " + productData.get(indexRow).get(indexColumn));
			}			
		}
	}

	public void writeValueToCell(int indexRow, int indexColumn, Object cellValue) {
		Row actualRow = datatypeSheet.getRow(indexRow);
		if(actualRow == null) {
			System.out.println("row null");
		}
		Cell actualCell = actualRow.getCell(indexColumn);
		if(actualCell == null) {
			actualCell = actualRow.createCell(indexColumn);
		}
		System.out.println("type " + cellValue.getClass().getName());
		if(cellValue.getClass().getName().equals("java.lang.String")) {
			System.out.println("true");
			actualCell.setCellType(CellType.STRING);
			actualCell.setCellValue(String.valueOf(cellValue));
		}
	}

	public void closeWorkbook() {
		try {
			OutputStream fileout = new FileOutputStream(filePath);
			try {
				workbook.write(fileout);
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
