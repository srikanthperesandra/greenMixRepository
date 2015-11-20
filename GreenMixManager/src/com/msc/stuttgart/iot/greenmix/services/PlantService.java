/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.services;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import com.msc.stuttgart.iot.greenmix.beans.Plant;
import com.msc.stuttgart.iot.greenmix.beans.service.IPlantService;
import com.msc.stuttgart.iot.greenmix.beans.service.ItemService;
import com.msc.stuttgart.iot.greenmix.util.IStatus;
import com.msc.stuttgart.iot.greenmix.util.LoggerUtil;

/**
 * @author srikanth
 *
 */

@Path("/plants")
public class PlantService {

	public static Logger logger = Logger.getLogger(PlantService.class.getName());
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlants(){
		/*
		 * TODO
		 * Retrieve User specific plants  
		 */
		try{
			
			IPlantService service =(IPlantService)ItemService.getInstance().getService(IPlantService.class);
			
			IStatus resultStatus = service.getAllPlants();
			if(resultStatus.isOk()){
				JSONArray array = new JSONArray((List<Plant>)resultStatus.getMessage());
				return Response.ok().entity(array.toString()).build();
			}else{
				JSONObject object = new JSONObject();
				object.put("status", "ERROR");
				object.put("reason", resultStatus.getMessage());
				return Response.status(Status.NOT_FOUND).entity(object.toString()).build();
			}
				
				
		}catch(Exception e){
			e.printStackTrace();
			logger.severe(LoggerUtil.toString(e));
			return Response.serverError().entity(LoggerUtil.toString(e)).build();
		}
		
	}
}
