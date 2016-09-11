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
            Double eventLat = jsonObject.getDouble(Constants.LATITUDE);
            Double eventLong = jsonObject.getDouble(Constants.LONGITUDE);

            double dist = distance(latitude, eventLat, longitude, eventLong, 0, 0);

            if ( dist < 5 ) {
                System.out.println(dist);
                trendingEventsArray.put(jsonObject);
            }
        }

        return trendingEventsArray;
    }

    /**
     * utility to get distance in kms
     * @param lat1
     * @param lat2
     * @param lon1
     * @param lon2
     * @param el1
     * @param el2
     * @return
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return (Math.sqrt(distance))/1000;
    }
}
