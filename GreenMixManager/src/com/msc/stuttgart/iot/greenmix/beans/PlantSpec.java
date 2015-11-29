/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.beans;

/**
 * @author srikanth
 *
 *
 *
 *
 */
public class PlantSpec {

	private int id;
	private String name;
	private int minTemp;
	private int maxTemp;
	private double minMoisture;
	private double maxMoisture;
	private int wateringLevel;
	private double minLums;
	private double maxLums;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(int minTemp) {
		this.minTemp = minTemp;
	}
	public int getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(int maxInt) {
		this.maxTemp = maxInt;
	}
	public double getMinMoisture() {
		return minMoisture;
	}
	public void setMinMoisture(double minMoisture) {
		this.minMoisture = minMoisture;
	}
	public double getMaxMoisture() {
		return maxMoisture;
	}
	public void setMaxMoisture(double maxMoisture) {
		this.maxMoisture = maxMoisture;
	}
	public int getWateringLevel() {
		return wateringLevel;
	}
	public void setWateringLevel(int wateringLevel) {
		this.wateringLevel = wateringLevel;
	}
	public double getMinLums() {
		return minLums;
	}
	public void setMinLums(double minLums) {
		this.minLums = minLums;
	}
	public double getMaxLums() {
		return maxLums;
	}
	public void setMaxLums(double maxLums) {
		this.maxLums = maxLums;
	}
	
}
