package com.texelz.atgrestful.example;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.texelz.atgrestful.Nucleus;
import com.texelz.atgrestful.core.BaseRESTService;

@Path("/atg-restful")
public class RequestService extends BaseRESTService {

	@Nucleus("/OriginatingRequest")
	private HttpServletRequest request;

	@GET
	@Path("/test")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN })
	public Response getContextPathWithHello(@PathParam("name") String name) {
		String contextPath = request.getContextPath();
		String result = String.format("Hello %s, here it goes: %s ;)", name, contextPath);

		return Response.ok(createResult("result", result)).build();
	}
}