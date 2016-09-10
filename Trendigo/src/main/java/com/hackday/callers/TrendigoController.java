package com.hackday.callers;

import com.hackday.manager.Manager;
import com.hackday.utils.Constants;
import com.hackday.utils.TrendigoUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Created by yogeesh.rajendra on 9/10/16.
 */
@Path("/")
public class TrendigoController {

    @POST
    @Path("/getfiresales")
    @Produces("application/json")
    @Consumes("application/json")
    public Response getFireSalesNearBy(InputStream incomingData) {

        JSONObject payload = null;
        JSONArray jsonArray = null;

        try {

            payload = TrendigoUtils.getRequestJson(incomingData);

            if (null == payload || !payload.has(Constants.LAT) ||
                    !payload.has(Constants.LONG) ) {
                return Response.status(400).entity("Request JSON is empty or latlong is missing").build();
            }

             jsonArray = Manager.getFireSalesNearBy(payload.getString(Constants.LAT), payload
                    .getString(Constants.LONG));

        } catch (Exception e){
            e.printStackTrace();
            return Response.status(503).entity("Internal Server Error").build();
        }

        return Response.status(200).entity(jsonArray.get(0).toString()).build();
    }

    @POST
    @Path("/getevents")
    @Produces("application/json")
    @Consumes("application/json")
    public Response getTrandingEvents(InputStream incomingData) {

        JSONObject payload = null;
        JSONArray jsonArray = null;

        try {

            payload = TrendigoUtils.getRequestJson(incomingData);

            if (null == payload || !payload.has(Constants.LAT) ||
                    !payload.has(Constants.LONG) ) {
                return Response.status(400).entity("Request JSON is empty or latlong is missing").build();
            }

            jsonArray = Manager.getTrendingEvents(payload.getString(Constants.LAT), payload
                    .getString(Constants.LONG));

        } catch (Exception e){
            e.printStackTrace();
            return Response.status(503).entity("Internal Server Error").build();
        }

        return Response.status(200).entity(jsonArray.toString()).build();
    }
}
