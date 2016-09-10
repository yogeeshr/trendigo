package com.hackday.utils;

import com.hackday.dao.ESDao;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Created by tarun.mehrotra on 9/10/16.
 */
public class EsQueryBuilder {

    public static void main(String[] args) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("categories", "workshop"));

        System.out.println(queryBuilder);
//        TransportClient client = ESDao.getClient();

//        SearchResponse response = client.prepareSearch(Constants.Events)
//                .setTypes(Constants.Liveevents)
//                .setSearchType(SearchType.QUERY_AND_FETCH)
//                .setQuery(queryBuilder)
//                .execute()
//                .actionGet();

//        System.out.println(response.toString());

    }
}
