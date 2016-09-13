package com.hackday.dao;

import com.google.gson.Gson;
import com.hackday.entities.EsEvent;
import com.hackday.utils.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.joda.time.Instant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogeesh.rajendra on 9/10/16.
 */
public class ESDao {

    private static TransportClient client = null;

    /**
     * Utility to create client
     */
    private static void createClient() {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", Constants.TRENDIGO).build();
        try {
            client = TransportClient.builder().settings(settings).build().addTransportAddress(
                    (new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300)));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Utility to close clients
     */
    private static void closeClients() {
        if (null != client)
            client.close();
    }

    public static JSONArray getTopTrendingEvents(double lat, double lng) {

        String [] include = {"eventName", "startTime", "endTime", "location", "eventUrl", "bannerUrl", "latitude", "longitude"};
        String [] exclude = {"categories", "tags", "eventId"};
        long currentEpoc = (Instant.now().getMillis()) / 1000;

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.rangeQuery(Constants.END_TIME).gt(currentEpoc));
        queryBuilder.mustNot(QueryBuilders.matchQuery(Constants.BANNER_URL, ""));
        queryBuilder.mustNot(QueryBuilders.matchQuery("latitude", 0));
        queryBuilder.mustNot(QueryBuilders.matchQuery("longitude", 0));

        createClient();

        SearchResponse response = client.prepareSearch(Constants.Events)
                .setTypes(Constants.Liveevents)
                .setSearchType(SearchType.QUERY_AND_FETCH)
                .setQuery(queryBuilder)
                .setFetchSource(include, exclude)
                .setSize(300)
                .execute()
                .actionGet();

        closeClients();

        return esResponseBuilder(response.toString());
    }

    private static JSONArray esResponseBuilder(String data) throws JSONException {

        JSONObject esResponse = new JSONObject(data);
        JSONObject hits, product, sourceJson;
        JSONArray productsArray;

        productsArray = esResponse.getJSONObject("hits").getJSONArray("hits");

        JSONArray respArray = new JSONArray();

        for (int i = 0; i < productsArray.length(); i++) {
            product = productsArray.getJSONObject(i);
            sourceJson = product.getJSONObject("_source");
            respArray.put(sourceJson);
        }

        return respArray;
    }

    /**
     * Utility to write event into ES
     *
     * @param eventList
     * @return
     * @throws Exception
     */
    public static Boolean writeESEvent(List<EsEvent> eventList) throws Exception {

        try {
            createClient();

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
        event.setScore(jsonObject.getDouble(Constants.SCORE));
        event.setBannerUrl(jsonObject.getString("banner_url"));

        JSONArray tagArray = jsonObject.getJSONArray(Constants.TAGS);

        StringBuilder tagString = new StringBuilder();
        for (int i = 0; i < tagArray.length(); i++) {
            tagString.append(tagArray.get(i));
            tagString.append(" ");
        }
        event.setTags(tagString.toString());

        JSONArray categoriesArray = jsonObject.getJSONArray(Constants.CATEGORIES);

        StringBuilder categoriesString = new StringBuilder();
        for (int i = 0; i < categoriesArray.length(); i++) {
            categoriesString.append(categoriesArray.get(i));
            categoriesString.append(" ");
        }

        event.setCategories(categoriesString.toString());

        return event;
    }

    //EVENTS INGESTION

    public static void main(String[] args) {
        HttpClient httpclient = HttpClients.createDefault();

        try {
            URIBuilder builder = new URIBuilder("https://api.allevents.in/events/list/");

            builder.setParameter("city", "Chandigarh");
            builder.setParameter("state", "punjab");
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
                System.out.println(jsonArray.toString());
                List<EsEvent> eventList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    EsEvent newEvent = getNormalizedEvent(jsonArray.getJSONObject(i));
                    eventList.add(newEvent);
                }
                writeESEvent(eventList);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
