/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.msc.stuttgart.iot.greenmix.beans.GMUser;
import com.msc.stuttgart.iot.greenmix.beans.service.IUserService;
import com.msc.stuttgart.iot.greenmix.beans.service.ItemService;
import com.msc.stuttgart.iot.greenmix.db.DBServiceFactory;
import com.msc.stuttgart.iot.greenmix.db.IDBService;
import com.msc.stuttgart.iot.greenmix.util.LoggerUtil;

/**
 * @author srikanth
 *
 */
@Path("/alerts")
public class AlertService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAlerts(@Context HttpServletRequest request){
		IDBService service=DBServiceFactory.createInstance();
		GMUser user = ((IUserService)ItemService.getInstance().getService(IUserService.class)).getLoggedInUser(request);
		String query = "select id,DEVICEID,MESSAGE from ALERTS where USERID="+user.getUserId()+" order by ID desc";
		ResultSet result =service.executeSelectQuery(query);
		try{
			
			JSONArray resultArray = new JSONArray();
			
			while(result.next()){
				JSONObject alert = new JSONObject();
				alert.put("deviceIdTime",result.getString(2)+"_"+result.getInt(1));
				alert.put("message",result.getString(3));
				resultArray.put(alert);
			}
			result.getStatement().close();
			return Response.ok().entity(resultArray.toString()).build();
		}catch(Exception e){
			return Response.serverError().entity(LoggerUtil.toString(e)).build();	
		}finally{
			if(result!=null){
				try {
					result.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
