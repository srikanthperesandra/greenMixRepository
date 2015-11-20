/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.util;

import java.util.Arrays;


/**
 * @author srikanth
 *
 */
public class LoggerUtil {

	public static String toString(Exception e){
		if(e.getMessage()!=null)
			return e.getMessage()+"\n"+Arrays.toString(e.getStackTrace()).replace(",", "\n");
		else
			return Arrays.toString(e.getStackTrace()).replace(",", "\n");
	}
}
