/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.catalina.realm.GenericPrincipal;

import com.msc.stuttgart.iot.greenmix.util.LoggerUtil;

/**
 * @author srikanth
 *
 */
@Path("/users")
public class UserService {
	
	@Path("/logout")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response logOut(@Context HttpServletRequest request){
		try {
			
			HttpSession session=request.getSession(false);
			if(session!=null){
				session.invalidate();
				return Response.ok().entity("successfully logout").build();
			}else{
				return Response.status(Status.NOT_FOUND).entity("Did not find active seesion").build();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().entity(LoggerUtil.toString(e)).build();
		}
	}
}
