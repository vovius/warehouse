package com.dio.javamentoring.warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class XlsTVStorage extends CommonTVStorage {

	public XlsTVStorage(String fileName) throws Exception {
		super(fileName,StorageType.XLS);
	}
	
	public TVStorage fillFromFile(String fileName) throws Exception {
		FileInputStream file = new FileInputStream(new File(fileName));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next(); // passing the header
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			
			TV item = new TV.Builder()
							.id(Double.valueOf(cellIterator.next().getNumericCellValue()).intValue())
							.brand(cellIterator.next().getStringCellValue())
							.diagonal(Double.valueOf(cellIterator.next().getNumericCellValue()).intValue())
							.matrixType(MatrixType.valueOf(cellIterator.next().getStringCellValue()))
							.dateMade(DATE_FORMAT.parse(cellIterator.next().getStringCellValue()))
							.build();
			
			addItem(item);
			
		}
	
		workbook.close();
		
		return this;
	}

	public boolean saveToFile(String fileName) {
		// TODO Auto-generated method stub
		return false;
	}

}
