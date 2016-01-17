/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.services;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import com.msc.stuttgart.iot.greenmix.beans.GMUser;
import com.msc.stuttgart.iot.greenmix.beans.Plant;
import com.msc.stuttgart.iot.greenmix.beans.PlantHealthData;
import com.msc.stuttgart.iot.greenmix.beans.PlantInputs;
import com.msc.stuttgart.iot.greenmix.beans.PlantSpec;
import com.msc.stuttgart.iot.greenmix.beans.service.IPlantService;
import com.msc.stuttgart.iot.greenmix.beans.service.IUserService;
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
	public Response getPlants(@Context HttpServletRequest request){
		/*
		 * TODO
		 * Retrieve User specific plants  
		 */
		try{
			
			IPlantService service =(IPlantService)ItemService.getInstance().getService(IPlantService.class);
			GMUser user = ((IUserService)ItemService.getInstance().getService(IUserService.class)).getLoggedInUser(request);
			IStatus resultStatus = service.getAllPlants(user.getUserId());
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
	
	@Path("/specs")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlantSpecs(@Context HttpServletRequest request){
		/*
		 * TODO
		 * Retrieve User specific plants  
		 */
		try{
			
			IPlantService service =(IPlantService)ItemService.getInstance().getService(IPlantService.class);
			GMUser user = ((IUserService)ItemService.getInstance().getService(IUserService.class)).getLoggedInUser(request);
			IStatus resultStatus = service.getAllPlantSpecs(user.getUserId());
			if(resultStatus.isOk()){
				JSONArray array = new JSONArray((List<PlantSpec>)resultStatus.getMessage());
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
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	
	public Response addPlantDevice(@FormParam("data") String content,@Context HttpServletRequest request){
		
		
		//1. Add Device 
		
		try {
			PlantInputs input = new PlantInputs();
			System.out.println(content);
			JSONObject json = new JSONObject(content);
			input.setPlantName(json.getString("plantName"));
			input.setPlantDevice(json.getString("plantDevice"));
			input.setPlantSpecId(json.getInt("plantSpecId"));
			input.setLocation(json.getString("location"));
			IPlantService service =(IPlantService)ItemService.getInstance().getService(IPlantService.class);
			GMUser user = ((IUserService)ItemService.getInstance().getService(IUserService.class)).getLoggedInUser(request);
			IStatus status=service.addPlant(input, user);
			System.out.println((String)status.getMessage());
			if(status.isOk())
				return Response.ok().entity("successfully added plant "+input.getPlantName()).build();
			else
				return Response.status(Status.NOT_MODIFIED).entity("failed to add plant "+input.getPlantName()).build();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().entity(LoggerUtil.toString(e)).build();
		}
		
	}
	@Path("/{plantId}")
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	public Response deletePlantDevice(@Context HttpServletRequest request,@PathParam("plantId") int plantId){
		
		
		//1. Add Device 
		
		try {
			
			IPlantService service =(IPlantService)ItemService.getInstance().getService(IPlantService.class);
			if(service.deletePlant(plantId).isOk())
				return Response.ok().entity("successfully deleted plant "+plantId).build();
			else
				return Response.status(Status.NOT_FOUND).entity("failed to add plant "+plantId).build();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().entity(LoggerUtil.toString(e)).build();
		}
		
	}
	
	@Path("/healthData")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchHealthData(@QueryParam("limit") int limit, @QueryParam("device") String device){
		try{
			IPlantService service =(IPlantService)ItemService.getInstance().getService(IPlantService.class);
			IStatus resultStatus = service.fetchLiveData(limit, device); 
			if(resultStatus.isOk()){
				JSONArray array = new JSONArray((List<PlantHealthData>)resultStatus.getMessage());
				return Response.ok().entity(array.toString()).build();
			}else{
				JSONObject object = new JSONObject();
				object.put("status", "ERROR");
				object.put("reason", resultStatus.getMessage());
				return Response.status(Status.NOT_FOUND).entity(object.toString()).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.serverError().entity(LoggerUtil.toString(e)).build();	
		}
	}
	@Path("/healthData/history")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchHistoryData(@QueryParam("time1") String time1, @QueryParam("time2") String time2){
		try{
			IPlantService service =(IPlantService)ItemService.getInstance().getService(IPlantService.class);
			IStatus resultStatus = service.fetchHistoryData(time1, time2); 
			if(resultStatus.isOk()){
				JSONArray array = new JSONArray((List<PlantHealthData>)resultStatus.getMessage());
				return Response.ok().entity(array.toString()).build();
			}else{
				JSONObject object = new JSONObject();
				object.put("status", "ERROR");
				object.put("reason", resultStatus.getMessage());
				return Response.status(Status.NOT_FOUND).entity(object.toString()).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.serverError().entity(LoggerUtil.toString(e)).build();	
		}
	}
}
