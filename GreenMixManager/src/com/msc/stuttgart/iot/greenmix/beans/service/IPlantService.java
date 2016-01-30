/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.beans.service;
import com.msc.stuttgart.iot.greenmix.beans.GMUser;
import com.msc.stuttgart.iot.greenmix.beans.PlantInputs;
import com.msc.stuttgart.iot.greenmix.beans.PlantSpec;
import com.msc.stuttgart.iot.greenmix.util.IStatus;

/**
 * @author srikanth
 *
 */
public interface IPlantService {

	public IStatus getAllPlants(int userId);
	public IStatus getAllPlantSpecs(int userId);
	public IStatus addPlant(PlantInputs inputs,GMUser user);
	public IStatus deletePlant(int plantDevice);
	public IStatus fetchLiveData(int limit,String device);
	public IStatus fetchHistoryData(String ts1, String ts2);
	public IStatus addPlantSpec(PlantSpec spec,GMUser user);
	public IStatus deletePlantSpec(int specId);
	
}
