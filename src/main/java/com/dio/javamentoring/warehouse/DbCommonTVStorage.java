package com.dio.javamentoring.warehouse;

import java.sql.ResultSet;

public abstract class DbCommonTVStorage extends CommonTVStorage {

	protected DbCommonTVStorage(){};
	
	public DbCommonTVStorage(String dbInitString) throws Exception {
		this(dbInitString,StorageType.DB);
	}

	public DbCommonTVStorage(String dbInitString, StorageType storageType) throws Exception {
		super(dbInitString, storageType);
	}
	
	public abstract TVStorage fillBySourceString(String sourceString) throws Exception;

	public abstract boolean saveBySourceString(String sourceString) throws Exception;

	protected void fillGoodsList(ResultSet resultSet) throws Exception {
		while (resultSet.next()) {
			TV item = new TV.Builder()
							.id(resultSet.getInt("id"))
							.brand(resultSet.getString("brand"))
							.diagonal(resultSet.getInt("diagonal"))
							.matrixType(MatrixType.valueOf(resultSet.getString("matrixType")))
							.dateMade(DATE_FORMAT.parse(resultSet.getString("dateMade")))
							.description(resultSet.getString("description"))
							.build();
			
			addItem(item);
		}
	}

	
}
