package com.hackday.manager;

import com.hackday.dao.ESDao;
import com.hackday.dao.MySQLDao;
import com.hackday.utils.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by yogeesh.rajendra on 9/10/16.
 */
public class Manager {

    /**
     * Utility to get fire sales near by
     * @param lat
     * @return
     */
    public static JSONArray getFireSalesNearBy(String lat, String lng) throws Exception {

        JSONArray fireSalesArray = new JSONArray();

        Double latitude = Double.parseDouble(lat);
        Double longitude = Double.parseDouble(lng);

        List<Map<String, String>> rows = MySQLDao.getAllRowsOfFireSalesWithinMile(latitude, longitude, 3);

        for (Map row : rows) {

            String business = (String) row.get(Constants.BUSINESS);
            String title = (String) row.get(Constants.TITLE);
            String imageurl = (String) row.get(Constants.IMAGEURL);
            String url = (String) row.get(Constants.URL);
            String address = (String) row.get(Constants.ADDRESS);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put(business ,Constants.BUSINESS);
            jsonObject.put(title ,Constants.TITLE);
            jsonObject.put(imageurl ,Constants.IMAGEURL);
            jsonObject.put(url ,Constants.URL);
            jsonObject.put(address ,Constants.ADDRESS);

            fireSalesArray.put(jsonObject);
        }

        return fireSalesArray;
    }

    public static String getTrendingEvents(String lat, String lng) {
        String trendingEventsArray = null;

        Double latitude = Double.parseDouble(lat);
        Double longitude = Double.parseDouble(lng);

        trendingEventsArray = ESDao.getTopTrendingEvents(latitude, longitude);

        return trendingEventsArray;
    }
}
