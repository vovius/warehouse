package com.dio.javamentoring.warehouse;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TxtTVStorage extends CommonTVStorage {
	
	public TxtTVStorage(String fileName) throws Exception {
		super(fileName);
	}

	public TVStorageInterface fillFromFile(String fileName) throws Exception {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		/*String regex = "\\t";
	    Pattern pattern = Pattern.compile(regex);
		scanner.useDelimiter(pattern);*/
		
		try {
			scanner.nextLine();
			for (String line = scanner.nextLine() ; ; line = scanner.nextLine()) {
				line = line.replace("\"", "");
				String[] fields = line.split("\\t");

				TV item = new TV.Builder()
							.brand(fields[0])
							.diagonal(Integer.valueOf(fields[1]).intValue())
							.matrixType(MatrixType.valueOf(fields[2]))
							.dateMade(DATE_FORMAT.parse(fields[3]))
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
		
		return this;
	}

	public boolean saveToFile(String fileName) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
