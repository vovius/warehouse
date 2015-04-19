package com.dio.javamentoring.warehouse;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

public class CsvTVStorage extends CommonTVStorage {
	
	public CsvTVStorage(String fileName) throws Exception {
		super(fileName,StorageType.CSV);
	}
	
	@SuppressWarnings("finally")
	public TVStorage fillFromFile(String fileName) throws Exception {
		CsvBeanReader beanReader = null;
		
		try {
            beanReader = new CsvBeanReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE);
            
            // the header elements are used to map the values to the bean (names must match)
            final String[] header = beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessorsRead();
            
            TV item;
            while( (item = beanReader.read(TV.class, header, processors)) != null) {
                addItem(item);
            }    
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if( beanReader != null ) {
				beanReader.close();
            }
			
			this.fileName = fileName;
			
            return this;
		}
	}

	private CellProcessor[] getProcessorsRead() {
        
        final CellProcessor[] processors = new CellProcessor[] { 
                new ParseInt(), // id
                new NotNull(), // brand
                new ParseInt(), // diagonal
                new ParseEnum(MatrixType.class), // matrixType
                new ParseDate("MM/dd/yyyy"), // dateMade
        };
        
        return processors;
	}

	private CellProcessor[] getProcessorsWrite() {
        
        final CellProcessor[] processors = new CellProcessor[] { 
                new ParseInt(), // id
                new NotNull(), // brand
                new ParseInt(), // diagonal
                new ParseEnum(MatrixType.class), // matrixType
                new FmtDate("MM/dd/yyyy"), // dateMade
        };
        
        return processors;
	}
	
	
	public boolean saveToFile(String fileName) throws Exception {
		
		ICsvBeanWriter beanWriter = null;
        try {
            beanWriter = new CsvBeanWriter(new FileWriter(fileName), CsvPreference.STANDARD_PREFERENCE);                    
            
            // the header elements are used to map the bean values to each column (names must match)
            final String[] header = getHeader();
            final CellProcessor[] processors = getProcessorsWrite();
            
            // write the header
            beanWriter.writeHeader(header);
            
            // write the beans
            for( TV item : goodsList ) {
            	beanWriter.write(item, header, processors);
            }
                
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
	        if( beanWriter != null ) {
	        	beanWriter.close();
	        }
	        
        }
        
        return true;
	}
	
}
