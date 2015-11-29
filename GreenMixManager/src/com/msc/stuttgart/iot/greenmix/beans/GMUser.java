/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author srikanth
 *
 */
@XmlRootElement
public class GMUser {

	private int userId;
	private String email;
	private String latsName;
	private String firstName;
	private String address;
	
	public GMUser(){
		
	}
	public GMUser(int userId,
				   String email,
				   String lastName,
				   String firstName,
				   String address){
						this.userId = userId;
						this.email = email;
						this.latsName = lastName;
						this.firstName = firstName;
						this.address = address;
				   }
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLatsName() {
		return latsName;
	}
	public void setLatsName(String latsName) {
		this.latsName = latsName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
