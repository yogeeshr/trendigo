package com.hackday.dao;

import com.google.gson.Gson;
import com.hackday.entities.EsEvent;
import com.hackday.entities.VenueDetails;
import com.hackday.utils.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yogeesh.rajendra on 9/10/16.
 */
public class ESDao {

    private static TransportClient client = null;

    public static TransportClient getClient() {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", Constants.Trendigo).build();
        try {
            client = TransportClient.builder().settings(settings).build().addTransportAddress(
                    (new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300)));
            return client;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean writeESProduct(List<EsEvent> eventList) throws Exception {

        try {
            if (client == null) {
                client = getClient();
            }
            BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
            for (EsEvent event : eventList) {
                bulkRequestBuilder.add(client.prepareIndex(Constants.Events, Constants.Liveevents,
                        event.getEventId()).setSource(new Gson().toJson(event)));
            }
            BulkResponse bulkResponse = bulkRequestBuilder.get();

            if (bulkResponse.hasFailures()) {
                System.out.println(bulkResponse.buildFailureMessage() + " Bulk Update Error");
            }

            return true;
        } catch (Exception e) {
            System.out.println("Exception occurred in bulk request builder - " + e);
            return false;
        } finally {
            closeClients();
        }
    }

    private static void closeClients() {
        client.close();
    }


    public static void main(String[] args) {
        HttpClient httpclient = HttpClients.createDefault();

        try {
            URIBuilder builder = new URIBuilder("https://api.allevents.in/events/list/");

            builder.setParameter("city", "bangalore");
            builder.setParameter("state", "karnataka");
            builder.setParameter("country", "India");
            builder.setParameter("sdate", "11-09-2016");
            builder.setParameter("edate", "31-12-2016");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", "73007f16253246d5b65d5885c9150407");

            // Request body
            StringEntity reqEntity = new StringEntity("{body}");
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                JSONObject object = new JSONObject(EntityUtils.toString(entity));
                JSONArray jsonArray = object.getJSONArray("data");
                List<EsEvent> eventList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    EsEvent newEvent = getNormalizedEvent(jsonArray.getJSONObject(i));
                    eventList.add(newEvent);
                }
//                writeESProduct(eventList);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static EsEvent getNormalizedEvent(JSONObject jsonObject) {

        EsEvent event = new EsEvent();
        event.setEventId(jsonObject.getString("event_id"));

        JSONObject venueObject = (JSONObject) jsonObject.get("venue");
        event.setLatitude(venueObject.getDouble("latitude"));
        event.setLongitude(venueObject.getDouble("longitude"));
        event.setEventName(jsonObject.getString("eventname"));
        event.setEventUrl(jsonObject.getString("event_url"));
        event.setStartTime(jsonObject.getLong("start_time"));
        event.setEndTime(jsonObject.getLong("end_time"));
        event.setLabel(jsonObject.getString("label"));
        event.setLocation(jsonObject.getString("location"));
        event.setScore(jsonObject.getDouble("score"));

        JSONArray tagArray = jsonObject.getJSONArray("tags");

        StringBuilder tagString = new StringBuilder();
        for (int i = 0; i < tagArray.length(); i++) {
            tagString.append(tagArray.get(i));
            tagString.append(" ");
        }
        event.setTags(tagString.toString());

        JSONArray categoriesArray = jsonObject.getJSONArray("categories");

        StringBuilder categoriesString = new StringBuilder();
        for (int i = 0; i < categoriesArray.length(); i++) {
            categoriesString.append(categoriesArray.get(i));
            categoriesString.append(" ");
        }

        event.setCategories(categoriesString.toString());

        return event;
    }
}
