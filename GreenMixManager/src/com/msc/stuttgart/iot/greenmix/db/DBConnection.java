package com.msc.stuttgart.iot.greenmix.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private Connection connection;
	//private Statement statement;
	private static DBConnection dbConnnection;
	private DBConnection(){
		
	}
	
	public static DBConnection getInstance() throws Exception{
		if(dbConnnection == null){
			dbConnnection = new DBConnection();
			Class.forName(IConfig.DB_DRIVER);
			dbConnnection.connection=DriverManager.getConnection(IConfig.DB_JDBC_URL,IConfig.DB_USER,IConfig.DB_PASSWORD);
		}		
		return dbConnnection;
	}
	
	public Connection getConnection(){
		return connection;
	}
	
	
	public Statement getStatement() throws SQLException{
		//System.out.println(dbConnnection.connection);
		return dbConnnection.connection.createStatement();
	}
}
