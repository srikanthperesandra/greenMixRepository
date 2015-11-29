/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.entity.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.tomcat.util.codec.binary.Base64;

import com.msc.stuttgart.iot.greenmix.util.IStatus;
import com.msc.stuttgart.iot.greenmix.util.Status;

/**
 * @author srikanth
 *
 *
 *
 */
public class BluemixUtil {

	public static String encode(String content){
		return new String(Base64.encodeBase64(content.getBytes()));
	}
	
	public static IStatus invokeService(String url,String method,
			String body,String user, String password,String contentType){
		try{
			URL urlObj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection)urlObj.openConnection();
			
			
			connection.setDoOutput(true);
			connection.setRequestMethod(method);
			if(contentType!=null)
				connection.setRequestProperty("Content-type", contentType);
			connection.setRequestProperty("Authorization", "Basic " + encode(user+":"+password));
			connection.connect();
			
			
			if(body!=null)
				connection.getOutputStream().write(body.getBytes());		
			System.out.println(connection.getResponseMessage());
			if(connection.getResponseCode()==200 || connection.getResponseCode() == 201){
				return new Status(IStatus.OK, getURLResponse(connection.getInputStream()), null);
				
			}else if(connection.getResponseCode()==204){
				return new Status(IStatus.OK, "no Content", null);
			}else
				return new Status(IStatus.ERROR, getURLResponse(connection.getErrorStream()), null);
			
		}catch(Exception e){
			e.printStackTrace();
			return new Status(IStatus.ERROR, e.getMessage(), e);
		
		}
		
	}
	


	private static String getURLResponse(InputStream in) throws IOException{
		BufferedReader buffer =new BufferedReader(new InputStreamReader(in));
		String line =null;
		StringBuffer response = new StringBuffer();
		while((line=buffer.readLine())!=null)
		response.append(line);
		System.out.println(response.toString());
		return response.toString();
	}
}
