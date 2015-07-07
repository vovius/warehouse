package com.dio.javamentoring.warehouse;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="tvlist")
@XmlRootElement
public class TV implements Serializable {
	
	@Id @GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	private int id;
	
	@Column
	private String brand;
	@Column
	private int diagonal;
	@Column
	private MatrixType matrixType;
	@Column
	private Date dateMade;
	@Column
	private String description;

	private Set<Bonus> bonuses;
	

	interface TVChecker {
		public boolean checker(Date fromDate, Date thruDate) throws Exception;
	}
	
	public TV() {
		this.matrixType = MatrixType.UNDEFINED;
		this.dateMade = new Date();
	};
	
	public TV(int id, String brand, int diagonal, MatrixType matrixType, Date dateMade, String description) {
		super();
		this.id = id;
		this.brand = brand;
		this.diagonal = diagonal;
		this.matrixType = matrixType;
		this.dateMade = dateMade;
		this.description = description;
	}

    public TV(Builder builder)
    {
		this.id = builder.id;
		this.brand = builder.brand;
		this.diagonal = builder.diagonal;
		this.matrixType = builder.matrixType;
		this.dateMade = builder.dateMade;
		this.description = builder.description;
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
	
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		result.append("id=").append(id).append(", ")
			  .append("brand=").append(brand).append(", ")
			  .append("diagonal=").append(diagonal).append(", ")
			  .append("matrixType=").append(matrixType.toString()).append(", ")
			  .append("dateMade=").append(getDateMadeStr()).append(", ")
			  .append("description=").append(description == null ? "" : description);
		return result.toString();
	}
	
	public void setByKeyValue(String key, String value) {
		String setterName = "set" + Character.toString(key.charAt(0)).toUpperCase() + key.substring(1);
		Class<? extends Object> cls = this.getClass();
		try {
			for (Method method : cls.getMethods()) {
				if (method.getName().equals(setterName)) {
					Class paramClass = method.getParameterTypes()[0];
					if (paramClass.equals(Integer.class)) {
						Integer convertedValue = Integer.valueOf(value);
						method.invoke(this, convertedValue);
					}
					else if (paramClass.equals(int.class)) {
						int convertedValue = Integer.valueOf(value).intValue();
						method.invoke(this, convertedValue);
					}
					else if (paramClass.equals(MatrixType.class)) {
						MatrixType convertedValue = MatrixType.valueOf(value);
						if (convertedValue != null)
							method.invoke(this, convertedValue);
					}
					else {
						method.invoke(this, value);
					}
					
					break;
				}
			}
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Set<Bonus> getBonuses() {
		return bonuses;
	}
	
	
	
	public void setBonuses(Set<Bonus> bonuses) {
		this.bonuses = bonuses;
	}

	public Bonus getBonusByName(String name) {
		for (Bonus bonus : bonuses) {
			if (bonus.getName().equals(name))
				return bonus;
		}
		
		return null;
	}


	// static nested
	public static class Builder {
		private int id;
		private String brand;
		private int diagonal;
		private MatrixType matrixType;
		private Date dateMade;
		private String description = null;

        public Builder(){}
        
        public Builder(TV item) {
        	this.id = item.id;
        	this.brand = item.brand;
        	this.diagonal = item.diagonal;
        	this.matrixType = item.matrixType;
        	this.dateMade = item.dateMade;
        	this.description = item.description;
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
        
        public Builder description(String description) {
        	if (description != null && !description.equals("null"))
        		this.description = description;
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
