package android.inmobi.com.trendigo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.SystemClock;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by deepak.jha on 9/10/16.
 */
public class ApiZomato extends Activity {
    private static String client_Id = "18a7c3cc91b76a26869e0f6edf8894e3";
    private String params, latlong;
    private double latitude, longitude;
    private String finalresponse;
    GoogleMap googlemap;
    Geocoder geocoder;
    List<Address> addresses;
    protected BitmapDescriptor accept, yourlocation;
    public ArrayList<String> listname, listlat, listlng, listaddress, listpopulation, listdistance;
    private Context previousClass;

    public String makeRequest(double lat, double lng)  throws IOException {
        latitude = lat;
        longitude = lng;
        latlong = latitude+","+longitude;
        thread.start();
        SystemClock.sleep(6000);
        return finalresponse;
    }


    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                    if (latlong == "0.0,0.0") {
                        latitude = (double) 12.994604;
                        longitude = (double) 77.625725;
                    }
                    //String url = "https://developers.zomato.com/api/v2.1/geocode?lat=" + latitude + "&lon=" + longitude;
                    String url = "https://developers.zomato.com/api/v2.1/geocode?lat=12.9669965&lon=77.5934489";
                    System.out.println("The zomato url is : " + url);

                    URL obj = new URL(url);
                    System.out.println("just before opening conn");
                    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();


                    con.setRequestMethod("POST");
                    con.setRequestProperty("user-key", "18a7c3cc91b76a26869e0f6edf8894e3");
                    con.setRequestProperty("Accept", "application/json");
                    int responseCode = con.getResponseCode();
                    System.out.println("\nSending 'POST' request to URL : " + url);
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

                        finalresponse = response.toString();
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

    public ApiZomato(Context previousClass) {
        this.previousClass = previousClass;
    }
}
