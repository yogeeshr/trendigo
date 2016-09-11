package com.hackday.manager;

import com.hackday.dao.ESDao;
import com.hackday.dao.MySQLDao;
import com.hackday.utils.Constants;
import com.hackday.utils.TrendigoUtils;
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
     *
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

            jsonObject.put(business, Constants.BUSINESS);
            jsonObject.put(title, Constants.TITLE);
            jsonObject.put(imageurl, Constants.IMAGEURL);
            jsonObject.put(url, Constants.URL);
            jsonObject.put(address, Constants.ADDRESS);

            fireSalesArray.put(jsonObject);
        }

        return fireSalesArray;
    }

    public static JSONArray getTrendingEvents(String lat, String lng) {
        JSONArray trendingEventsArray = new JSONArray();

        Double latitude = Double.parseDouble(lat);
        Double longitude = Double.parseDouble(lng);

        JSONArray internTrendingEventsArray = ESDao.getTopTrendingEvents(latitude, longitude);

        for (int i = 0; ((i < internTrendingEventsArray.length()) && (trendingEventsArray.length()<10)); i++) {

            JSONObject jsonObject = (JSONObject) internTrendingEventsArray.get(i);

            double theta = latitude - jsonObject.getDouble(Constants.LONGITUDE);
            double dist = Math.sin(TrendigoUtils.deg2rad(latitude)) *
                    Math.sin(TrendigoUtils.deg2rad(jsonObject.getDouble(Constants.LATITUDE))) + Math.cos
                    (TrendigoUtils.deg2rad(latitude)) *
                    Math.cos
                            (TrendigoUtils.deg2rad(jsonObject.getDouble(Constants.LATITUDE))) * Math.cos(TrendigoUtils.deg2rad(theta));
            dist = Math.acos(dist);
            dist = TrendigoUtils.rad2deg(dist);
            dist = dist * 60 * 1.1515 * 1.609344;

            if ( dist > 3 ) {
                trendingEventsArray.put(jsonObject);
            }
        }

        return trendingEventsArray;
    }
}
