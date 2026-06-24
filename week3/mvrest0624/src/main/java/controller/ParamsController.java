package controller;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.*;

@Path("/params")
public class ParamsController {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(
	        @FormParam("username") String username,
	        @FormParam("password") String password) {
		
		String message = String.format("{\"username\": %s, \"password\": %s}"
				                       , username,password);
		return Response.ok(message).build();
	}
}
