package android.inmobi.com.trendigo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.SystemClock;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepak.jha on 2/21/16.
 */
public class ApiRequest extends Activity {

    private static String client_Id = "YMBI1UKA4DJACDAFPSORRZQIJ4A3BJIHFZCRIVKMKTQQX5IU";
    private static String client_Secret = "EDI3SSXPDSBX22ZAGXWUABYB5YPIIDRU1QRZZ5MON0XKYYL4";
    private String params, latlong;
    private double latitude, longitude;
    private String finalresponse;
    GoogleMap googlemap;
    Geocoder geocoder;
    List<Address> addresses;
    protected BitmapDescriptor accept, yourlocation;
    public ArrayList<String> listname, listlat, listlng, listaddress, listpopulation, listdistance;
    private Context previousClass;

    public String makeRequest(double lat, double lng, String keyparams)  throws IOException {
        listname = new ArrayList<String>();
        listlat = new ArrayList<String>();
        listlng = new ArrayList<String>();
        listaddress = new ArrayList<String>();
        listpopulation = new ArrayList<String>();
        listdistance = new ArrayList<String>();
        params = keyparams;
        latitude = lat;
        longitude = lng;
        latlong = latitude+","+longitude;
        thread.start();
        SystemClock.sleep(4000);
        System.out.println("Final response in activity is: "+finalresponse);
        return finalresponse;
    }
    Thread background = new Thread() {
        public void run() {

            try {
                sleep(4*1000);
                Intent i=new Intent(getBaseContext(),HomePage.class);
                startActivity(i);
                finish();

            } catch (Exception e) {

            }
        }
    };


    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                if(latlong=="0.0,0.0"){
                    latlong="bangalore";
                }
                String url = "https://api.foursquare.com/v2/venues/search?client_id=" + client_Id + "&client_secret=" + client_Secret + "&v=20160220&near="+latlong+"&query=" + params;
                System.out.println("The url is : " + url);

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));

                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    in.close();

                    finalresponse=response.toString();
//                    try {
//                        parseresponse(finalresponse);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("The api response is: ");
//                    System.out.println(finalresponse);
//                    for (int i=0;i<listname.size(); i++){
//                        System.out.println(listaddress.get(i));
//                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    });

    public ApiRequest(Context previousClass) {
        this.previousClass = previousClass;
    }

    private final void parseresponse(String apiResponse) throws JSONException {
        System.out.println("into parse response method");
        System.out.println("Thre response is \n" + apiResponse);
        JSONObject json = new JSONObject(apiResponse);
        JSONObject responseObject = json.getJSONObject("response");
        String addressstore, name, lati, longi, addrs, population;
        JSONArray array = responseObject.getJSONArray("venues");
        for(int i = 0 ; i < array.length() ; i++){
            System.out.println(array.getJSONObject(i).getString("name"));
            name = array.getJSONObject(i).getString("name");
            listname.add(name);
            JSONObject locationObj = array.getJSONObject(i).getJSONObject("location");
            JSONObject populationObj = array.getJSONObject(i).getJSONObject("stats");
            LatLng ll = new LatLng(Double.valueOf(locationObj.getString("lat")), Double.valueOf(locationObj.getString("lng")));
            population = populationObj.getString("usersCount");
            lati = locationObj.getString("lat");
            longi = locationObj.getString("lng");
            listlat.add(lati);
            listlng.add(longi);
            listpopulation.add(population);
            System.out.println(name);
            System.out.println(population);

            Location locationA = new Location("point a");
            locationA.setLatitude(Double.valueOf(lati));
            locationA.setLongitude(Double.valueOf(longi));
            Location locationB = new Location("point b");
            locationB.setLatitude(latitude);
            locationB.setLongitude(longitude);

            double distance = Math.floor((locationA.distanceTo(locationB)) / 1000);
            System.out.println("The distance is: " + distance);

            listdistance.add(String.valueOf(distance));



            addressstore = locationObj.getString("formattedAddress");
            listaddress.add(addressstore);


        }
        System.out.println("going to other activity with the lists");
//        Intent intent = new Intent(previousClass, RestaurantSearchResult.class);
//        intent.putStringArrayListExtra("restaurantnames", listname);
//        intent.putStringArrayListExtra("distance", listdistance);
//        intent.putStringArrayListExtra("population", listpopulation);
//        startActivity(intent);
    }
}
