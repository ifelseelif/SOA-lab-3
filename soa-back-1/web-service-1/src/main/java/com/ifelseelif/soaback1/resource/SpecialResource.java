package com.ifelseelif.soaback1.resource;

import com.ifelseelif.soaback1.exceptions.HttpException;
import com.ifelseelif.soaback1.service.ProductService;
import com.ifelseelif.soaback1.util.RemoteBeanLookupUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/special")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpecialResource {
    private final ProductService productService;

    public SpecialResource() throws HttpException {
        productService = RemoteBeanLookupUtil.lookupProductBean();
    }

    @DELETE
    public Response deleteByManufactureCost(@QueryParam("manufactureCost") Long id) {
        try {
            productService.deleteByManufactureCost(id);
            return Response.ok().build();
        } catch (HttpException exception) {
            return Response.status(exception.getStatusCode(), exception.getMessage()).build();
        }
    }
}
