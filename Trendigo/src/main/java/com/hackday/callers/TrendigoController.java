package com.hackday.callers;

import com.hackday.manager.Manager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by yogeesh.rajendra on 9/10/16.
 */
@Path("/")
public class TrendigoController {

    @GET
    @Path("/getfiresales")
    @Produces("application/json")
    @Consumes("application/json")
    public Response getFireSalesNearBy(@QueryParam("lat") String latitude,
                                       @QueryParam("long") String longitude) {

        JSONObject payload = null;
        JSONArray jsonArray = null;

        try {

            if (null==latitude || null==longitude ) {
                return Response.status(400).entity("Request JSON is empty or latlong is missing").build();
            }

             jsonArray = Manager.getFireSalesNearBy(latitude, longitude);

        } catch (Exception e){
            e.printStackTrace();
            return Response.status(503).entity("Internal Server Error").build();
        }

        return Response.status(200).entity(jsonArray.get(0).toString()).build();
    }

    @GET
    @Path("/getevents")
    @Produces("application/json")
    @Consumes("application/json")
    public Response getTrendingEvents(@QueryParam("lat") String latitude,
                                      @QueryParam("long") String longitude) {

        JSONArray responseArray = null;

        try {

            if (null==latitude || null==longitude ) {
                return Response.status(400).entity("Request JSON is empty or latlong is missing").build();
            }

            responseArray = Manager.getTrendingEvents(latitude, longitude);

        } catch (Exception e){
            e.printStackTrace();
            return Response.status(503).entity("Internal Server Error").build();
        }

        return Response.status(200).entity(responseArray.toString()).build();
    }
}
