package com.dio.javamentoring.warehouse;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class TxtTVStorage extends CommonTVStorage {
	
	public TxtTVStorage(String fileName) throws Exception {
		super(fileName,StorageType.TXT);
	}

	public TVStorage fillBySourceString(String fileName) throws Exception {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		
		try {
			scanner.nextLine(); // passing the header
			for (String line = scanner.nextLine() ; ; line = scanner.nextLine()) {
				line = line.replace("\"", "");
				String[] fields = line.split("\\t");

				TV item = new TV.Builder()
							.id(Integer.valueOf(fields[0]).intValue())
							.brand(fields[1])
							.diagonal(Integer.valueOf(fields[2]).intValue())
							.matrixType(MatrixType.valueOf(fields[3]))
							.dateMade(DATE_FORMAT.parse(fields[4]))
							.build();
				addItem(item);
					
				if (!scanner.hasNextLine())
					break;
			}
		}		
		catch (NoSuchElementException e) {
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
		this.sourceString = fileName;
		
		return this;
	}

	public boolean saveBySourceString(String fileName) throws Exception {
		FileWriter file = new FileWriter(fileName);
		PrintWriter printWriter = new PrintWriter(file);
		
		// printing the header
		String header = StringUtils.join(getHeader(),",");
		printWriter.println(header);
		
		for (TV item : goodsList) {
			StringBuilder result = new StringBuilder();
			result.append("\"").append(item.getId()).append("\"").append("\t")
				  .append("\"").append(item.getBrand()).append("\"").append("\t")
				  .append("\"").append(item.getDiagonal()).append("\"").append("\t")
				  .append("\"").append(item.getMatrixType()).append("\"").append("\t")
				  .append("\"").append(item.getDateMadeStr()).append("\"");
			printWriter.println(result);
		}
		
		printWriter.close();
		
		return true;
	}
	
}
