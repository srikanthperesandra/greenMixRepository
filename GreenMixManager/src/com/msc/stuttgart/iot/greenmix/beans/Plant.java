/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author srikanth
 *
 */
/*
 * 
 * ID
PLANT_NAME
PLANT_DEVICE
PLANT_DEVICE_TROKEN
LOCATION
PLANT_TEMPLATE
USER_ID

 * 
 */
@XmlRootElement
public class Plant {

	private int id;
	private String plantName;
	private String plantDevice;
	private String plantDeviceToken;
	private String location;
	private int plantTemplate;
	private int userId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getPlantDeviceToken() {
		return plantDeviceToken;
	}
	public void setPlantDeviceToken(String plantDeviceToken) {
		this.plantDeviceToken = plantDeviceToken;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getPlantTemplate() {
		return plantTemplate;
	}
	public void setPlantTemplate(int plantTemplate) {
		this.plantTemplate = plantTemplate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
}
