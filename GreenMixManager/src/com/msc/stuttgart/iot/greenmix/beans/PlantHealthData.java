/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.beans;

import java.sql.Timestamp;

/**
 * @author srikanth
 *
 */
public class PlantHealthData {

	private int id;
	private double temperature;
	private double moisture;
	private double light;
	private Timestamp dateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getMoisture() {
		return moisture;
	}
	public void setMoisture(double moisture) {
		this.moisture = moisture;
	}
	public double getLight() {
		return light;
	}
	public void setLight(double light) {
		this.light = light;
	}
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	
}
