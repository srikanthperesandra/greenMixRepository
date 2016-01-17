package com.msc.stuttgart.iot.greenmix.db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

import com.msc.stuttgart.iot.greenmix.util.LoggerUtil;

public class DBServiceImpl implements IDBService {
/**
 * @author srikanth
 */
	
	private Logger log = Logger.getLogger(DBServiceImpl.class.getName());
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		DBConnection.getInstance().getConnection().close();
	}

	@Override
	public ResultSet executeSelectQuery(String query) {
		// TODO Auto-generated method stub
		ResultSet set = null;
		try{
			System.out.println(query);
			set = DBConnection.getInstance().getStatement().executeQuery(query); 
		}catch(Exception e){
			e.printStackTrace();
			//log.severe(LoggerUtil.toString(e));
		}
		return set;
	}

	@Override
	public int executeInsertUpdateQuery(String query) {
		// TODO Auto-generated method stub
		int result = -1;
		try{
			Statement st = DBConnection.getInstance().getStatement();
					result =st.executeUpdate(query); 
					st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
