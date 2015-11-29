/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.util;

/**
 * @author srikanth
 *
 */
public interface IStatus {

	public static final int ERROR = 0x0001;
	public static final int OK = 0x0000;
	public int getSeverity();
	public Object getMessage();
	public boolean isOk();
}
