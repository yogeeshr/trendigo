package com.hackday.dao;

import com.google.gson.Gson;
import com.hackday.entities.EsEvent;
import com.hackday.utils.Constants;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by yogeesh.rajendra on 9/10/16.
 */
public class ESDao {
  
      private TransportClient client = null;

    private TransportClient getClient() {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "trendigo").build();
        try {
            client = TransportClient.builder().settings(settings).build().addTransportAddress(
                    (new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300)));
            return client;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean writeESProduct(List<? extends EsEvent> products) throws Exception {

        try {

            if (client == null) {
                client = getClient();
            }


            BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

            //TODO : Check if merchant id always exist : fallback

            for (EsEvent product : products) {

                //JSON With user story tagging which will be used for new document creation
                String json = new Gson().toJson(product);


                //JSON without user story tagging - which will be used for updating
                String updateJson = new Gson().toJson(product);

                IndexRequest indexRequest = new IndexRequest(Constants.Trendigo, Constants.Events,
                        product.getEventId()).
                        source(json);

                UpdateRequest updateRequest = new UpdateRequest(Constants.Trendigo, Constants.Events,
                        product.getEventId()).
                        upsert(indexRequest);

                boolean indexYes = client.admin().indices().prepareExists("trendigo").execute().actionGet().isExists();

                bulkRequestBuilder.add(updateRequest);
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

    private void closeClients() {
        client.close();
    }
  
}
