/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.beans.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import com.msc.stuttgart.iot.greenmix.beans.GMUser;
import com.msc.stuttgart.iot.greenmix.beans.Plant;
import com.msc.stuttgart.iot.greenmix.beans.PlantHealthData;
import com.msc.stuttgart.iot.greenmix.beans.PlantInputs;
import com.msc.stuttgart.iot.greenmix.beans.PlantSpec;
import com.msc.stuttgart.iot.greenmix.db.DBServiceFactory;
import com.msc.stuttgart.iot.greenmix.db.IDBService;
import com.msc.stuttgart.iot.greenmix.entity.services.IIOTFoundationServices;
import com.msc.stuttgart.iot.greenmix.entity.services.IOTFoundationServiceFactory;
import com.msc.stuttgart.iot.greenmix.services.IEntity;
import com.msc.stuttgart.iot.greenmix.util.IStatus;
import com.msc.stuttgart.iot.greenmix.util.LoggerUtil;
import com.msc.stuttgart.iot.greenmix.util.Status;

/**
 * @author srikanth
 *
 */
public class PlantServiceImpl implements IPlantService {

	/* (non-Javadoc)
	 * @see com.msc.stuttgart.iot.greenmix.beans.service.IPlantService#getAllPlants()
	 */
	@Override
	public IStatus getAllPlants(int userId) {
		// TODO Auto-generated method stub
		List<Plant> plants = new ArrayList<Plant>();
		IDBService service=DBServiceFactory.createInstance();
		ResultSet result =service.executeSelectQuery("select ID,PLANT_NAME,PLANT_DEVICE,PLANT_DEVICE_TOKEN,LOCATION,DEVICE_TEMPLATE from "+IEntity.PLANTS+" where USER_ID="+userId);
		
		try {
			while(result.next()){
				Plant plant = new Plant();
				plant.setId(result.getInt(1));
				plant.setPlantName(result.getString(2));
				plant.setPlantDevice(result.getString(3));
				plant.setPlantDeviceToken(result.getString(4));
				plant.setLocation(result.getString(5));
				plant.setPlantTemplate(result.getInt(6));
				plant.setUserId(userId);
				plants.add(plant);
			}
			result.getStatement().close();
			//service.close();
			return new Status(IStatus.OK, plants, null);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Status(IStatus.ERROR, LoggerUtil.toString(e), e);
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

	@Override
	public IStatus getAllPlantSpecs(int userId) {
		// TODO Auto-generated method stub
		
		
		List<PlantSpec> plantSpecs = new ArrayList<PlantSpec>();
		IDBService service=DBServiceFactory.createInstance();
		ResultSet result =service.executeSelectQuery("SELECT ID,NAME,MIN_TEMP,MAX_TEMP,MIN_MOISTURE,"
				+ "MAX_MOISTURE,WATERING_INTERVAL,MIN_LUMS,MAX_LUMS FROM PLANT_TEMPLATE"+
				" where USER_ID="+userId);
		
		try {
			while(result.next()){
				PlantSpec plantSpec = new PlantSpec();
				plantSpec.setId(result.getInt(1));
				plantSpec.setName(result.getString(2));
				plantSpec.setMinTemp(result.getInt(3));
				plantSpec.setMaxTemp(result.getInt(4));
				plantSpec.setMinMoisture(result.getDouble(5));
				plantSpec.setMaxMoisture(result.getDouble(6));
				plantSpec.setWateringLevel(result.getInt(7));
				plantSpec.setMinLums(result.getDouble(8));
				plantSpec.setMaxLums(result.getDouble(9));
				plantSpecs.add(plantSpec);
				
			}
			result.getStatement().close();
			//service.close();
			return new Status(IStatus.OK, plantSpecs, null);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Status(IStatus.ERROR, LoggerUtil.toString(e), e);
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

	@Override
	public IStatus addPlant(PlantInputs input,GMUser user) {
		// TODO Auto-generated method stub
		
		IIOTFoundationServices iotService =IOTFoundationServiceFactory.creatInstance();
		IStatus result = iotService.addDevice(input.getPlantDevice());
		System.out.println((String) result.getMessage());
		try {
			if(result.isOk()){
				JSONObject jsonObject = new JSONObject((String)result.getMessage());
				String queryFormat = "insert into PLANT(PLANT_NAME,PLANT_DEVICE,PLANT_DEVICE_TOKEN,"
						+ "LOCATION,USER_ID,DEVICE_TEMPLATE) values('%s','%s','%s','%s',%d,%d)";
				String query = String.format(queryFormat, input.getPlantName(),
														  jsonObject.getString("id"),
														  jsonObject.getString("password"),
														  input.getLocation(),
														  user.getUserId(),
														  input.getPlantSpecId()
														  );
				IDBService service=DBServiceFactory.createInstance();
				if(service.executeInsertUpdateQuery(query)>0){
					//service.close();
					return new Status(IStatus.OK, "successfully added plant "+input.getPlantName(), null);
				}
				else{
					//service.close();
					return new Status(IStatus.ERROR, "Failed to add plant "+input.getPlantName(), null);
				}
			
			}else
				return new Status(IStatus.ERROR, "Failed create Bluemix IOT device , failed to add plant "+input.getPlantName(), null);
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Status(IStatus.ERROR, e.getMessage(), e);
			
		}
		
		
		
		
		
	}

	@Override
	public IStatus deletePlant(int plantDevice) {
		// TODO Auto-generated method stub
		

		IIOTFoundationServices iotService =IOTFoundationServiceFactory.creatInstance();
		String query = " select PLANT_DEVICE from PLANT where id = "+plantDevice;
		IDBService service =null;
		try{
			 service=DBServiceFactory.createInstance();
			ResultSet set = service.executeSelectQuery(query);
			if(set.next()){
				IStatus result = iotService.deleteDevice(set.getString(1));
				set.getStatement().close();
				if(result.isOk()){
					query = "delete from PLANT where id="+plantDevice;
					if(service.executeInsertUpdateQuery(query)>0)
						return new Status(IStatus.OK, "successfully deleted palnt and device ", null);
					else
						return new Status(IStatus.ERROR, "Failed to delete plant"+plantDevice, null);
				}else
					return new Status(IStatus.ERROR, "Failed to delete plant Device", null);
			}else{
				return new Status(IStatus.ERROR, "Failed to fetch Plant details "+plantDevice, null);
			}
		
		}catch(Exception e){
			e.printStackTrace();
			return new Status(IStatus.ERROR, e.getMessage(), e);
		}finally{
			/*if(service!=null)
				try {
					service.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return new Status(IStatus.ERROR, "Failed to close DB connection "+plantDevice, null);
				}
				*/
		}
		
		
		
	}

	@Override
	public IStatus fetchLiveData(int limit,String device) {
		// TODO Auto-generated method stub
		ResultSet set=null;
		try{
			String query = "SELECT ID,TEMPERATURE_DEGREE_CELCIUS,LIGHT,MOISTURE,DEVICEID,"
							+ "DATA_TIME FROM PLANT_HEALTH_DATA where DEVICEID='%s' order "
							+ "by DATA_TIME desc  fetch first %d rows only";
			List<PlantHealthData> data = new LinkedList<PlantHealthData>();
			String resultQuery = String.format(query, device,limit);
			IDBService service=DBServiceFactory.createInstance();
			set = service.executeSelectQuery(resultQuery);
			while(set.next()){
				PlantHealthData healthData = new PlantHealthData();
				healthData.setId(set.getInt(1));
				healthData.setTemperature(set.getDouble(2));
				healthData.setLight(set.getDouble(3));
				healthData.setMoisture(set.getDouble(4));
				healthData.setDevice(set.getString(5));
				healthData.setDateTime(set.getTimestamp(6));
				data.add(healthData);
			}
			set.getStatement().close();
			return new Status(IStatus.OK, data, null);
		}catch(Exception e){
			e.printStackTrace();
			return new Status(IStatus.ERROR, e.getMessage(), e);
		}finally{
			if(set!=null){
				try {
					set.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
	}

	@Override
	public IStatus fetchHistoryData(String ts1, String ts2) {
		// TODO Auto-generated method stub
		ResultSet set=null;
		try{
			String query = "SELECT ID,TEMPERATURE_DEGREE_CELCIUS,LIGHT,MOISTURE,DEVICEID,"
							+ "DATA_TIME FROM PLANT_HEALTH_DATA where DATA_TIME between '"+ts1+"' and '"+ts2+"' order "
							+ "by DATA_TIME";
			List<PlantHealthData> data = new LinkedList<PlantHealthData>();
			IDBService service=DBServiceFactory.createInstance();
			set = service.executeSelectQuery(query);
			while(set.next()){
				PlantHealthData healthData = new PlantHealthData();
				healthData.setId(set.getInt(1));
				healthData.setTemperature(set.getDouble(2));
				healthData.setLight(set.getDouble(3));
				healthData.setMoisture(set.getDouble(4));
				healthData.setDevice(set.getString(5));
				healthData.setDateTime(set.getTimestamp(6));
				data.add(healthData);
			}
			set.getStatement().close();
			return new Status(IStatus.OK, data, null);
		}catch(Exception e){
			e.printStackTrace();
			return new Status(IStatus.ERROR, e.getMessage(), e);
		}finally{
			if(set!=null){
				try {
					set.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

	@Override
	public IStatus addPlantSpec(PlantSpec spec, GMUser user) {
		// TODO Auto-generated method stub
		
		try{
			/*
			 * SELECT ID,NAME,MIN_TEMP,MAX_TEMP,MIN_MOISTURE,"
				+ "MAX_MOISTURE,WATERING_INTERVAL,MIN_LUMS,MAX_LUMS FROM PLANT_TEMPLATE"+
				" where USER_ID="+userId);
			 */
			
			String queryFormat = "insert into PLANT_TEMPLATE(NAME,MIN_TEMP,MAX_TEMP,MIN_MOISTURE,"
				+ "MAX_MOISTURE,WATERING_INTERVAL,MIN_LUMS,MAX_LUMS,USER_ID) values('%s',%d,%d,%f,%f,%d,%f,%f,%d)";
			String query = String.format(queryFormat, spec.getName(),
													  spec.getMinTemp(),
													  spec.getMaxTemp(),
													  spec.getMinMoisture(),
													  spec.getMaxMoisture(),
													  spec.getWateringLevel(),
													  spec.getMinLums(),
													  spec.getMaxLums(),
													  user.getUserId()
													  );
			IDBService service=DBServiceFactory.createInstance();
			if(service.executeInsertUpdateQuery(query)>0){
				//service.close();
				return new Status(IStatus.OK, "successfully added plant Specification "+spec.getName(), null);
			}
			else{
				//service.close();
				return new Status(IStatus.ERROR, "Failed to add plant Specification "+spec.getName(), null);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return new Status(IStatus.ERROR, e.getMessage(), e);
		}
	}

	@Override
	public IStatus deletePlantSpec(int specId) {
		// TODO Auto-generated method stub
		IDBService service =null;			 
		try{
			service=DBServiceFactory.createInstance();
			String query = "delete from PLANT_TEMPLATE where id="+specId;
			if(service.executeInsertUpdateQuery(query)>0)
				return new Status(IStatus.OK, "successfully deleted palnt Spec "+specId, null);
			else
				return new Status(IStatus.ERROR, "Failed to delete plant spec"+specId, null);
		}catch(Exception e){
			e.printStackTrace();
			return new Status(IStatus.ERROR, e.getMessage(), e);
		}
	}

}
