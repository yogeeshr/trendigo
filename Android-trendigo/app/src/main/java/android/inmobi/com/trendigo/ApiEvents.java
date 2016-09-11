package android.inmobi.com.trendigo;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;

import com.google.android.gms.maps.model.BitmapDescriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by deepak.jha on 9/11/16.
 */
public class ApiEvents extends Activity {
    private String params, latlong;
    private double latitude, longitude;
    private String finalresponse;
    private int count;
    private Context previousClass;

    public String makeRequest(double lat, double lng, int countParam)  throws IOException {
        latitude = lat;
        longitude = lng;
        latlong = latitude+","+longitude;
        count = countParam;
        thread.start();
        SystemClock.sleep(6000);
        return finalresponse;
    }


    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                if(latlong=="0.0,0.0"){
                    System.out.println("lat long 0 for events");
                    latitude = (double) 12.9669965;
                    longitude = (double) 77.5934489;
                }
                String url = "http://192.168.43.71:9099/trendigo/api/getevents?lat="+latitude+"&long="+longitude+"&limit="+count;
                System.out.println("The events url is : " + url);

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();


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

    public ApiEvents(Context previousClass) {
        this.previousClass = previousClass;
    }

}
