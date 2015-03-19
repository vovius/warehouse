package com.dio.javamentoring.warehouse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TV {
	int id;
	private String brand;
	private int diagonal;
	private MatrixType matrixType;
	Date dateMade;

	interface TVChecker {
		public boolean checker(Date fromDate, Date thruDate) throws Exception;
	}
	
	public TV() {};
	
	public TV(int id, String brand, int diagonal, MatrixType matrixType, Date dateMade) {
		super();
		this.id = id;
		this.brand = brand;
		this.diagonal = diagonal;
		this.matrixType = matrixType;
		this.dateMade = dateMade;
	}

    public TV(Builder builder)
    {
		this.id = builder.id;
		this.brand = builder.brand;
		this.diagonal = builder.diagonal;
		this.matrixType = builder.matrixType;
		this.dateMade = builder.dateMade;
    }
    
    
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getDiagonal() {
		return diagonal;
	}

	public void setDiagonal(int diagonal) {
		this.diagonal = diagonal;
	}

	public MatrixType getMatrixType() {
		return matrixType;
	}

	public void setMatrixType(MatrixType matrixType) {
		this.matrixType = matrixType;
	}

	public Date getDateMade() {
		return dateMade;
	}
	
	public String getDateMadeStr() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormatter.format(dateMade);
	}

	public void setDateMade(Date dateMade) {
		this.dateMade = dateMade;
	}

	@Override
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		result.append("id=").append(id).append(", ")
			  .append("brand=").append(brand).append(", ")
			  .append("diagonal=").append(diagonal).append(", ")
			  .append("matrixType=").append(matrixType.toString()).append(", ")
			  .append("dateMade=").append(getDateMadeStr());
		return result.toString();
	}
	
	// static nested
	public static class Builder {
		private int id;
		private String brand;
		private int diagonal;
		private MatrixType matrixType;
		private Date dateMade;

        public Builder(){}
        
        public Builder(TV item) {
        	this.id = item.id;
        	this.brand = item.brand;
        	this.diagonal = item.diagonal;
        	this.matrixType = item.matrixType;
        	this.dateMade = item.dateMade;
        }
        
        public Builder id(int id) {
        	this.id = id;
        	return this;
        }
        
        public Builder brand(String brand) {
        	this.brand = brand;
        	return this;
        }
        
        public Builder diagonal(int diagonal) {
        	this.diagonal = diagonal;
        	return this;
        }
        
        public Builder matrixType(MatrixType matrixType) {
        	this.matrixType = matrixType;
        	return this;
        }
        
        public Builder dateMade(Date dateMade) {
        	this.dateMade = dateMade;
        	return this;
        }
        
        public TV build() {
        	return new TV(this);
        }
	}
	
	// local inner
	public class InnerTV {
		
		
		public String getDateMadeByFormat(String format) throws Exception {
			
			// local anonymous
			TVChecker checker = new TVChecker() {
				
				public boolean checker(Date fromDate, Date thruDate) throws Exception {
					Date date = dateMade;
					if (date.before(fromDate) || date.after(thruDate))
						throw new Exception("dateMade out of range!");
					return true;
				}
			};
			
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			String result = dateFormat.format(getDateMade());
			if (checker.checker(dateFormat.parse("01/01/2010"), dateFormat.parse("12/31/2015"))) {
				return result;
			}
			
			return null;
			
		}
	}
		
}
