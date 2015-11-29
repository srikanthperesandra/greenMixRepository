/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.entity.services;

import com.msc.stuttgart.iot.greenmix.util.IStatus;

/**
 * @author srikanth
 *
 */
public interface IIOTFoundationServices {
	
	public static String ORG="j3yjz2";
	public static String DEVICE_TYPE="MqttDeevice";
	public static String URL_END_POINT="https://internetofthings.ibmcloud.com/"
			+ "api/v0001/organizations/"+ORG+"/devices";
	public static String API_Key ="a-j3yjz2-emcg0fhum7";
	public static String TOKEN = "AU+UJD0@rttEYtIUn9";

	public IStatus addDevice(String deviceId);
	public IStatus deleteDevice(String deviceId);
}
