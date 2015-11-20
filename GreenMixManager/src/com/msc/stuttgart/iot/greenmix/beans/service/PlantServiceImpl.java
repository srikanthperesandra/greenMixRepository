/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.beans.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.msc.stuttgart.iot.greenmix.beans.Plant;
import com.msc.stuttgart.iot.greenmix.db.DBServiceFactory;
import com.msc.stuttgart.iot.greenmix.db.IDBService;
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
	public IStatus getAllPlants() {
		// TODO Auto-generated method stub
		List<Plant> plants = new ArrayList<Plant>();
		IDBService service=DBServiceFactory.createInstance();
		ResultSet result =service.executeSelectQuery("select ID,PLANT_NAME,PLANT_DEVICE,PLANT_DEVICE_TOKEN,LOCATION,DEVICE_TEMPLATE from "+IEntity.PLANTS);
		
		try {
			while(result.next()){
				Plant plant = new Plant();
				plant.setId(result.getInt(1));
				plant.setPlantName(result.getString(2));
				plant.setPlantDevice(result.getString(3));
				plant.setPlantDeviceToken(result.getString(4));
				plant.setLocation(result.getString(5));
				plant.setPlantTemplate(result.getInt(6));
				plants.add(plant);
			}
			
			return new Status(IStatus.OK, plants, null);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Status(IStatus.ERROR, LoggerUtil.toString(e), e);
		}
		
	}

}
