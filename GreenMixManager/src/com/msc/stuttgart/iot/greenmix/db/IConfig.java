package com.msc.stuttgart.iot.greenmix.db;
/**
 * @author srikanth
 */
public interface IConfig {

	public static String DB_HOST = LoadProperties.getInstance().getProperty().getProperty("host","localhost");
	public static String DB_USER = LoadProperties.getInstance().getProperty().getProperty("user","root");
	public static String DB_PASSWORD = LoadProperties.getInstance().getProperty().getProperty("password","");
	public static String DB = LoadProperties.getInstance().getProperty().getProperty("db","");
	public static String DB_PORT = LoadProperties.getInstance().getProperty().getProperty("port","");
	public static String DB_JDBC_URL = LoadProperties.getInstance().getProperty().getProperty("jdbcurl","");
	public static String DB_DRIVER = LoadProperties.getInstance().getProperty().getProperty("driver","com.ibm.db2.jcc.DB2Driver");
}
