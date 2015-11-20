/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.util;

/**
 * @author amith
 *
 */
public class Status implements IStatus {

	/* (non-Javadoc)
	 * @see com.eolcontroller.es.IStatus#getSeverity()
	 */
	private Object message;
	private int severity;
	private Throwable exception;
	public Status(int severity,Object message,Throwable exception){
		this.message = message;
		this.severity = severity;
		this.exception = exception;
	}
	@Override
	public int getSeverity() {
		// TODO Auto-generated method stub
		return severity;
	}

	/* (non-Javadoc)
	 * @see com.eolcontroller.es.IStatus#getMessage()
	 */
	@Override
	public Object getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	@Override
	public boolean isOk() {
		// TODO Auto-generated method stub
		return (this.severity == IStatus.OK);
	}

}
