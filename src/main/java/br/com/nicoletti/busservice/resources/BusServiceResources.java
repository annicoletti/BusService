package br.com.nicoletti.busservice.resources;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.cpqd.avm.sdk.v1.model.to.RequestTO;
import br.com.cpqd.avm.sdk.v1.model.to.ResponseTO;
import br.com.nicoletti.busservice.facade.api.BusServiceFacade;

@Path("busservice")
public class BusServiceResources {

	@EJB
	private BusServiceFacade facade;
	
	@GET
	@Path("/echo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response echo() {
		String out = "ok";
		return Response.ok().entity(out).build();
	}

	@POST
	@Path("/climatempo/v1/getCityByNameAndState")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetCityByNameAndState(@Context HttpServletRequest request, RequestTO to) {
		ResponseTO out = facade.findCity(to);
		return Response.ok().entity(out).build();
	}
}
