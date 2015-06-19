package com.dio.javamentoring.warehouse;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;


public class DateFromStringType implements UserType {
	
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		 return (Serializable) value;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return ObjectUtils.equals(x, y);
	}

	public int hashCode(Object arg0) throws HibernateException {
		return arg0.hashCode();
	}

	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor si, Object owner) throws HibernateException,	SQLException {
		//assert names.length == 1;
        String stringValue = (String) StringType.INSTANCE.get(rs, names[0], si); // already handles null check
        Date value = null;
		try {
			value = DATE_FORMAT.parse(stringValue);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return stringValue == null ? null : value;
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor si) throws HibernateException, SQLException {
		if (value == null) {
            StringType.INSTANCE.set( st, null, index, si );
        }
        else {
            final Date date = (Date) value;
            StringType.INSTANCE.set( st, DATE_FORMAT.format(date), index, si );
        }
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	public Class returnedClass() {
		return Date.class;
	}

	public int[] sqlTypes() {
		return new int[] {
                StringType.INSTANCE.sqlType(),
        };
	}

}
