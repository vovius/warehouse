package com.dio.javamentoring.warehouse;

import java.sql.*;

public class DbTVStorage extends DbCommonTVStorage {
	
	@SuppressWarnings("unused")
	private DbTVStorage(){};
	
	public DbTVStorage(String dbInitString) throws Exception {
		super(dbInitString);
	}

	public TVStorage fillBySourceString(String sourceString) throws Exception {
		
		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection(sourceString);
		if (connection != null) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from tvlist;");
			fillGoodsList(resultSet);
			
			connection.close();
			
			this.sourceString = sourceString;
		}
		
		return this;
	}
	
	public boolean saveBySourceString(String fileName) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection(sourceString);
		if (connection != null) {
			connection.setAutoCommit(false);
			
			try {
				Statement statement = connection.createStatement();
				statement.executeUpdate("delete from tvlist;");
				
				for (TV item : goodsList) {
					StringBuilder updateString = new StringBuilder();
					updateString.append("insert into tvlist (id,brand,diagonal,matrixType,dateMade,description) values (")
						.append(item.getId()).append(",")
						.append("'").append(item.getBrand()).append("'").append(",")
						.append(item.getDiagonal()).append(",")
						.append("'").append(item.getMatrixType()).append("'").append(",")
						.append("'").append(item.getDateMadeStr()).append("'").append(",")
						.append("'").append(item.getDescription()).append("'")
						.append(");");
					statement.executeUpdate(updateString.toString());
				}
				
				connection.commit();
			} catch (Exception e) {
				e.printStackTrace();
				connection.rollback();
			}
			
			connection.close();
		}
		
		return true;
	}

}
