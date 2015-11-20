/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.services;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author srikanth
 *
 */
@Path("/greet")
public class HelloWorldService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response returnGreetingMessage(@QueryParam("name") String name){
		return Response.ok().entity("hello "+name+" welcome to jersy jax-rs").build();
	}
}
