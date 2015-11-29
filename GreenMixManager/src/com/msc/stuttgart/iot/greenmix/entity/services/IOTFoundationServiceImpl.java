/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.entity.services;

import org.json.JSONException;
import org.json.JSONObject;

import com.msc.stuttgart.iot.greenmix.util.IStatus;
import com.msc.stuttgart.iot.greenmix.util.Status;

/**
 * @author srikanth
 *
 */
public class IOTFoundationServiceImpl implements IIOTFoundationServices {

	/* (non-Javadoc)
	 * @see com.msc.stuttgart.iot.greenmix.entity.services.IIOTFoundationServices#addDevice(java.lang.String, java.lang.String)
	 */
	protected IOTFoundationServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public IStatus addDevice(String deviceId) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("type", IIOTFoundationServices.DEVICE_TYPE);
			jsonObject.put("id", deviceId);
		} catch (JSONException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			return new Status(IStatus.ERROR, e.getMessage(), e);
		}
		System.out.println(jsonObject);
		return BluemixUtil.invokeService(IIOTFoundationServices.URL_END_POINT, "POST",jsonObject.toString() , 
				IIOTFoundationServices.API_Key, IIOTFoundationServices.TOKEN,"application/json");
		
	}

	/* (non-Javadoc)
	 * @see com.msc.stuttgart.iot.greenmix.entity.services.IIOTFoundationServices#deleteDevice(java.lang.String)
	 */
	@Override
	public IStatus deleteDevice(String deviceId) {
		// TODO Auto-generated method stub
		return BluemixUtil.invokeService(IIOTFoundationServices.URL_END_POINT+"/"+DEVICE_TYPE+"/"+deviceId, "DELETE",
				null, IIOTFoundationServices.API_Key, IIOTFoundationServices.TOKEN,null);
	}

}
