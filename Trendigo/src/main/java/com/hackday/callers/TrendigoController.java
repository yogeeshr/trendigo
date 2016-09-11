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
                                      @QueryParam("long") String longitude,
                                      @QueryParam("limit") int limit) {

        JSONArray responseArray = new JSONArray();
        JSONArray tmpArray = null;

        try {

            if (null==latitude || null==longitude || limit<=0) {
                return Response.status(400).entity("Request JSON is empty or latlong is missing").build();
            }

            tmpArray = Manager.getTrendingEvents(latitude, longitude);

        } catch (Exception e){
            e.printStackTrace();
            return Response.status(503).entity("Internal Server Error").build();
        }

        for (int i=0; i<limit && i<tmpArray.length(); i++) {
            if (null== tmpArray || null==tmpArray.get(i)) {
                break;
            }
            responseArray.put(tmpArray.get(i));
        }

        JSONObject object = new JSONObject();
        object.put("events", responseArray);
        return Response.status(200).entity(object.toString()).build();
    }
}
