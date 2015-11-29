/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.services;

import java.security.Principal;
import java.sql.ResultSet;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.msc.stuttgart.iot.greenmix.beans.GMUser;
import com.msc.stuttgart.iot.greenmix.beans.service.IUserService;
import com.msc.stuttgart.iot.greenmix.db.DBServiceFactory;
import com.msc.stuttgart.iot.greenmix.db.IDBService;
import com.msc.stuttgart.iot.greenmix.util.LoggerUtil;

/**
 * @author srikanth
 *
 */
public class UserServiceImpl implements IUserService{

	Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
	@Override
	public GMUser getLoggedInUser(@Context HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		Principal name=request.getUserPrincipal();
		if(name==null)
			return IUserService.TEST_USER;
		GMUser gmUser = new GMUser();
		try{
			
			String query="SELECT ID,USERID,LASTNAME,FIRSTNAME,ADDRESS FROM USERS where USERID='"+name.getName()+"'";
			IDBService dbService=DBServiceFactory.createInstance();
			ResultSet set=dbService.executeSelectQuery(query);
			if(set.next()){
				gmUser.setUserId(set.getInt(1));
				gmUser.setEmail(set.getString(2));
				gmUser.setLatsName(set.getString(3));
				gmUser.setFirstName(set.getString(4));
				gmUser.setAddress(set.getString(5));
				
			}
			return gmUser;
		}catch(Exception e){
			e.printStackTrace();
			logger.severe(LoggerUtil.toString(e));
			return gmUser;
		}
		
	}

	
	
}
