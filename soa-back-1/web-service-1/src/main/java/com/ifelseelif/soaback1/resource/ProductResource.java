package com.ifelseelif.soaback1.resource;

import com.ifelseelif.soaback1.exceptions.HttpException;
import com.ifelseelif.soaback1.model.Body;
import com.ifelseelif.soaback1.model.Product;
import com.ifelseelif.soaback1.service.ProductService;
import com.ifelseelif.soaback1.util.RemoteBeanLookupUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
    private final ProductService productService;

    public ProductResource() throws HttpException {
        productService = RemoteBeanLookupUtil.lookupProductBean();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        try {
            return Response.ok(productService.getById(id)).build();
        } catch (HttpException e) {
            Body body = new Body(e.getMessage());
            return Response.status(e.getStatusCode()).entity(body).build();
        }
    }

    @GET
    public Response getAll(@Context HttpServletRequest request) {
        try {
            List<Product> productList = productService.getAll(request.getParameterMap());

            return Response.ok(productList).build();
        } catch (HttpException e) {
            Body body = new Body(e.getMessage());
            return Response.status(e.getStatusCode()).entity(body).build();
        }
    }

    @POST
    public Response add(Product product) {
        try {
            productService.save(product);
            return Response.ok().build();
        } catch (HttpException e) {
            Body body = new Body(e.getMessage());
            return Response.status(e.getStatusCode()).entity(body).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@PathParam("id") Long id, Product product) {
        try {
            productService.updateById(id, product);
            return Response.ok().build();
        } catch (HttpException e) {
            Body body = new Body(e.getMessage());
            return Response.status(e.getStatusCode()).entity(body).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        try {
            productService.deleteById(id);
            return Response.ok().build();
        } catch (HttpException e) {
            Body body = new Body(e.getMessage());
            return Response.status(e.getStatusCode()).entity(body).build();
        }
    }
}
