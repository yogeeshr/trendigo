package android.inmobi.com.trendigo.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by deepak.jha on 9/10/16.
 */
public class ApiZomato extends AppCompatActivity {
    private static String client_Id_dj = "18a7c3cc91b76a26869e0f6edf8894e3";
    private static String client_Id_tm = "8e14d3ec015ad33b0df5ff8be22e960b";
    private static Context context;
    private String params;
    private static String latlong;
    private static double latitude;
    private static double longitude;
    private static double radius;
    private static int count;
    private static String finalresponse;
    private Context previousClass;

    public String makeRequest(double lat, double lng, int countParam, double radiusParam) throws IOException, InterruptedException {
        latitude = lat;
        longitude = lng;
        Log.e("latitude obtained is ",String.valueOf(lat));
        Log.e("longitude obtained is ",String.valueOf(lng));
        radius = radiusParam;
        count = countParam;
        thread.start();
        thread.join();
        return finalresponse;
    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                    if (latlong == "0.0,0.0") {
                        System.out.println("latlong are 0,0");
                        latitude = (double) 12.9669965;
                        longitude = (double) 77.5934489;
                    }
                    String url = "https://developers.zomato.com/api/v2.1/search?count="+count+"&lat="+latitude+"&lon="+longitude+"&radius="+radius+"&sort=rating&order=desc";
                    //String url = "https://developers.zomato.com/api/v2.1/geocode?lat=12.9669965&lon=77.5934489";
                    System.out.println("The zomato url is : " + url);
                    URL obj = new URL(url);
                    System.out.println("just before opening conn");
                    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("user-key", client_Id_tm);
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
        context = previousClass;
    }
}
