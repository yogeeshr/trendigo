package com.hackday.callers;

import com.hackday.utils.TrendigoUtils;
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

        try {

            JSONObject payload = TrendigoUtils.getRequestJson(incomingData);

            if (null == payload) {
                return Response.status(400).entity("Request JSON is empty").build();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        JSONObject replyJson = new JSONObject("{'businessname' : 'ITC Gardenia', '':}");

        return Response.status(200).entity(replyJson.toString()).build();
    }
}
