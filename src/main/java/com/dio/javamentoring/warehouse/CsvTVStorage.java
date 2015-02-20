package com.dio.javamentoring.warehouse;

import java.io.FileReader;

import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

public class CsvTVStorage extends CommonTVStorage {
	
	public CsvTVStorage(String fileName) throws Exception {
		super(fileName);
	}
	
	@SuppressWarnings("finally")
	public TVStorageInterface fillFromFile(String fileName) throws Exception {
		CsvBeanReader beanReader = null;
		
		try {
            beanReader = new CsvBeanReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE);
            
            // the header elements are used to map the values to the bean (names must match)
            final String[] header = beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();
            
            TV item;
            while( (item = beanReader.read(TV.class, header, processors)) != null) {
                addItem(item);
            }    
		}
		finally {
			if( beanReader != null ) {
				beanReader.close();
            }
            return this;
		}
	}

	private static CellProcessor[] getProcessors() {
        
        //final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an example, not very robust!
        //StrRegEx.registerMessage(emailRegex, "must be a valid email address");
        
        final CellProcessor[] processors = new CellProcessor[] { 
                //new UniqueHashCode(), // customerNo (must be unique)
                new NotNull(), // brand
                new ParseInt(), // diagonal
                new ParseEnum(MatrixType.class), // matrixtype
                new ParseDate("MM/dd/yyyy"), // dateMade
                /*new Optional(new ParseBool()), // married
                new Optional(new ParseInt()), // numberOfKids
                new NotNull(), // favouriteQuote
                new StrRegEx(emailRegex), // email
                new LMinMax(0L, LMinMax.MAX_LONG) // loyaltyPoints*/
        };
        
        return processors;
	}

	public boolean saveToFile(String fileName) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
