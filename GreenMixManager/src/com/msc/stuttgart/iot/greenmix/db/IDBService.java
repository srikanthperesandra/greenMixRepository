package com.msc.stuttgart.iot.greenmix.db;

import java.sql.ResultSet;
/**
 * @author srikanth
 */
public interface IDBService {

	
	public void close() throws Exception;
	public ResultSet executeSelectQuery(String query);
	public int executeInsertUpdateQuery(String query);
}
