/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.entity.services;

/**
 * @author srikanth
 *
 */
public abstract class IOTFoundationServiceFactory {

	public static IIOTFoundationServices creatInstance(){
		return new IOTFoundationServiceImpl();
	}
}
