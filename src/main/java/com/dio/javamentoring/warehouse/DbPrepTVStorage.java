package com.dio.javamentoring.warehouse;

import java.sql.*;

public class DbPrepTVStorage extends DbCommonTVStorage {
	
	@SuppressWarnings("unused")
	private DbPrepTVStorage(){};
	
	public DbPrepTVStorage(String dbInitString) throws Exception {
		super(dbInitString, StorageType.DBPREP);
	}
	
	public TVStorage fillBySourceString(String sourceString) throws Exception {
		
		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection(sourceString);
		if (connection != null) {
			PreparedStatement statement = connection.prepareStatement("select * from tvlist;");
			ResultSet resultSet = statement.executeQuery();
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
				PreparedStatement statement = connection.prepareStatement("delete from tvlist;");
				statement.executeUpdate();

				// preparing the update
				statement = connection.prepareStatement("insert into tvlist (id,brand,diagonal,matrixType,dateMade,description) values (?,?,?,?,?,?)");
				
				for (TV item : goodsList) {
					statement.setInt(1, item.getId());
					statement.setString(2, item.getBrand());
					statement.setInt(3, item.getDiagonal());
					statement.setString(4, item.getMatrixType().name());
					statement.setString(5, item.getDateMadeStr());
					statement.setString(6, item.getDescription());
					
					statement.executeUpdate();
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
