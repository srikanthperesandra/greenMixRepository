package com.msc.stuttgart.iot.greenmix.db;
/**
 * @author srikanth
 */
public abstract class DBServiceFactory {

	public static IDBService createInstance(){
		IDBService dbService = new DBServiceImpl();
		return dbService;
	}
}
