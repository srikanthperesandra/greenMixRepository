/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.beans.service;

import javax.servlet.http.HttpServletRequest;

import com.msc.stuttgart.iot.greenmix.beans.GMUser;

/**
 * @author srikanth
 *
 */
public interface IUserService {

	public GMUser getLoggedInUser(HttpServletRequest request);
	public static GMUser TEST_USER = new GMUser(1, "testuser@bogus.bad", "Srikanth", "pl", "Test Address");
}
