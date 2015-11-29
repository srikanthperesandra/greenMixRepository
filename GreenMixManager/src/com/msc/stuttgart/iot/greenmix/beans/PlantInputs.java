/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.beans;

import org.json.JSONObject;


/**
 * @author srikanth
 *
 */

public class PlantInputs {

	private String plantName;
	private String plantDevice;
	private String location;
	private int plantSpecId;
	
	public PlantInputs(){
		
	}
	
	
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public String getPlantDevice() {
		return plantDevice;
	}
	public void setPlantDevice(String plantDevice) {
		this.plantDevice = plantDevice;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getPlantSpecId() {
		return plantSpecId;
	}
	public void setPlantSpecId(int plantSpecId) {
		this.plantSpecId = plantSpecId;
	}
	
}
