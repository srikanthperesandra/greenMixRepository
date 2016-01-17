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
	
	public synchronized static DBConnection getInstance() throws Exception{
		if(dbConnnection == null){
			dbConnnection = new DBConnection();
			Class.forName(IConfig.DB_DRIVER);
			dbConnnection.connection=DriverManager.getConnection(IConfig.DB_JDBC_URL,IConfig.DB_USER,IConfig.DB_PASSWORD);
		}		
		return dbConnnection;
	}
	
	public synchronized Connection getConnection(){
		return connection;
	}
	
	
	public synchronized Statement getStatement() throws SQLException{
		//System.out.println(dbConnnection.connection);
		return dbConnnection.connection.createStatement();
	}
}
