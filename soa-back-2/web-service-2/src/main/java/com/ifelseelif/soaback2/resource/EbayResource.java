package com.ifelseelif.soaback2.resource;

import com.ifelseelif.soaback2.exceptions.HttpException;
import com.ifelseelif.soaback2.service.EbayService;
import com.ifelseelif.soaback2.util.RemoteBeanLookupUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("/ebay")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EbayResource {

    private final EbayService ebayService;

    public EbayResource() throws HttpException {
        this.ebayService = RemoteBeanLookupUtil.lookupEbayBean();
    }

    @Path("/filter/manufacturer/{manufacturer-id}")
    @GET
    public Response getAllProducts(@PathParam("manufacturer-id") int manufacturerId) {
        try {

            File folder = new File(".");
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    System.out.println(file.getName());
                }
            }
            return Response.ok(ebayService.getAllProducts(manufacturerId)).build();
        } catch (HttpException e) {
            return Response.status(e.getStatusCode(), e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @Path("/price/increase/{increase-percent}")
    @POST
    public Response increasePrice(@PathParam("increase-percent") int percent) {
        try {
            ebayService.increasePrice(percent);
            return Response.noContent().build();
        } catch (HttpException e) {
            return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(500, e.getMessage()).build();
        }
    }

}