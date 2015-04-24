package com.dio.javamentoring.warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class XlsTVStorage extends CommonTVStorage {

	public XlsTVStorage(String fileName) throws Exception {
		super(fileName,StorageType.XLS);
	}
	
	public TVStorage fillBySourceString(String fileName) throws Exception {
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
		
		this.sourceString = fileName;
		
		return this;
	}

	public boolean saveBySourceString(String fileName) throws Exception {
		FileInputStream file = new FileInputStream(new File(fileName));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);

		// writing the header
		String[] header = getHeader();
		for (int i=0; i < header.length; i++) {
			Row row = sheet.getRow(0); 
			row.getCell(i).setCellValue(header[i]);
		}
		
		int rowNum = 1;
		for (TV item : goodsList) {
			Row row = sheet.getRow(rowNum);
			if (row == null)
				row = sheet.createRow(rowNum);
			row.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellValue(item.getId());
			row.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellValue(item.getBrand());
			row.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellValue(item.getDiagonal());
			row.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellValue(item.getMatrixType().name());
			row.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellValue(item.getDateMadeStr());
			rowNum++;
		}
		
		file.close();
		
		FileOutputStream outFile =new FileOutputStream(new File(fileName));
	    workbook.write(outFile);
	    outFile.close();

		workbook.close();
	    
		return true;
	}

}
