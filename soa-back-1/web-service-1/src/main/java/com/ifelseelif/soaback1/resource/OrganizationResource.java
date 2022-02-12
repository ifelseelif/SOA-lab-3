package com.ifelseelif.soaback1.resource;

import com.ifelseelif.soaback1.exceptions.HttpException;
import com.ifelseelif.soaback1.model.Body;
import com.ifelseelif.soaback1.model.Organization;
import com.ifelseelif.soaback1.service.OrganizationService;
import com.ifelseelif.soaback1.util.RemoteBeanLookupUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationResource {
    private final OrganizationService service;

    public OrganizationResource() throws HttpException {
        service = RemoteBeanLookupUtil.lookupOrganizationBean();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        try {
            return Response.ok(service.getById(id)).build();
        } catch (HttpException e) {
            Body body = new Body(e.getMessage());
            return Response.status(e.getStatusCode()).entity(body).build();
        }
    }

    @GET
    public Response getAll(@Context HttpServletRequest request) {
        try {
            List<Organization> organizationList = service.getAll(request.getParameterMap());
            return Response.ok(organizationList).build();
        } catch (HttpException e) {
            Body body = new Body(e.getMessage());
            return Response.status(e.getStatusCode()).entity(body).build();
        }
    }

    @POST
    public Response add(Organization organization) {
        try {
            service.save(organization);
            return Response.ok().build();
        } catch (HttpException e) {
            Body body = new Body(e.getMessage());
            return Response.status(e.getStatusCode()).entity(body).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@PathParam("id") int id, Organization organization) {
        try {
            service.updateById(id, organization);
            return Response.ok().build();
        } catch (HttpException e) {
            Body body = new Body(e.getMessage());
            return Response.status(e.getStatusCode()).entity(body).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") int id) {
        try {
            service.deleteById(id);
            return Response.ok().build();
        } catch (HttpException e) {
            Body body = new Body(e.getMessage());
            return Response.status(e.getStatusCode()).entity(body).build();
        }
    }
}