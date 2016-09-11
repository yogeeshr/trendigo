package android.inmobi.com.trendigo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by deepak.jha on 2/20/16.
 */
public class SearchEvent extends Activity {

    private TextView v1,v2, v3, v4, v5, v6, search;
    private int nv1,nv2, nv3, nv4, nv5, nv6 = 0;
    private int i=1;
    private String key="";
    private String latlong, response, location;
    private double lat, lng;
    public ArrayList<String> listname, listlat, listlng, listaddress, listpopulation, listdistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchevents);

        ImageView fireSales = (ImageView) findViewById(R.id.firesales);
        String imageUrl = "http://ntibanear.com/wp-content/uploads/2014/03/320x50.png";
        final String clickUrl = "http://www.google.com";
        new DownloadImageTask((ImageView) findViewById(R.id.firesales))
                .execute(imageUrl);
        fireSales.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(clickUrl));
                startActivity(intent);
            }
        });

        listname = new ArrayList<String>();
        listlat = new ArrayList<String>();
        listlng = new ArrayList<String>();
        listaddress = new ArrayList<String>();
        listpopulation = new ArrayList<String>();
        listdistance = new ArrayList<String>();
        v1 = (TextView) findViewById(R.id.business);
        v2 = (TextView) findViewById(R.id.concerts);
        v3 = (TextView) findViewById(R.id.music);
        v4 = (TextView) findViewById(R.id.festival);
        v5 = (TextView) findViewById(R.id.parties);
        v6 = (TextView) findViewById(R.id.exhibition);

        search = (TextView) findViewById(R.id.srchevnts);

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv1==0) {
                    v1.setBackgroundColor(Color.GRAY);
                    v2.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v3.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v4.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v5.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v6.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    nv2=nv3=nv4=nv5=nv6=0;
                    nv1 = 1;
                }else{
                    nv1=0;
                    v1.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv2==0) {
                    v2.setBackgroundColor(Color.GRAY);
                    v1.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v3.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v4.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v5.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v6.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    nv1=nv3=nv4=nv5=nv6=0;
                    nv2 = 1;
                }else{
                    nv2=0;
                    v2.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv3==0) {
                    v3.setBackgroundColor(Color.GRAY);
                    v2.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v1.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v4.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v5.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v6.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    nv2=nv1=nv4=nv5=nv6=0;
                    nv3 = 1;
                }else{
                    nv3=0;
                    v3.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv4==0) {
                    v4.setBackgroundColor(Color.GRAY);
                    v2.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v3.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v1.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v5.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v6.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    nv2=nv3=nv1=nv5=nv6=0;
                    nv4 = 1;
                }else{
                    nv4=0;
                    v4.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv5==0) {
                    v5.setBackgroundColor(Color.GRAY);
                    v2.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v3.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v4.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v1.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v6.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    nv2=nv3=nv4=nv1=nv6=0;
                    nv5 = 1;
                }else{
                    nv5=0;
                    v5.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv6==0) {
                    v6.setBackgroundColor(Color.GRAY);
                    v2.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v3.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v4.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v5.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    v1.setBackgroundColor(Color.parseColor("#ffb12f00"));
                    nv2=nv3=nv4=nv5=nv1=0;
                    nv6 = 1;
                }else{
                    nv6=0;
                    v6.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });

        GPSTracker gpsTracker = new GPSTracker(this);
        if(gpsTracker.canGetLocation()){
            lat = gpsTracker.getLatitude();
            lng = gpsTracker.getLongitude();
        }
        location = lat+","+lng;

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key="";
                if(nv1==1){
                    key = "business";
                }else if(nv2==1){
                    key ="concert";
                }else if(nv3==1){
                    key ="music";
                }else if(nv4==1){
                    key ="festival";
                }else if(nv5==1){
                    key = "parties";
                }else if(nv6==1){
                    key = "exhibition";
                }


                System.out.println("Key to be searched is: " + key);
                if(key!=""){
                    try {
                        try {
                            searchEvent(key, location);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast toast = Toast.makeText(SearchEvent.this, "Please choose an event.", Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(15);
                    toast.show();                }

            }
        });
    }


    private final void parseresponse(String apiResponse) throws JSONException {
        listname.clear();
        listaddress.clear();
        listdistance.clear();
        listlat.clear();
        listlng.clear();
        listpopulation.clear();
        System.out.println("into parse response method");
        System.out.println("Thre response is \n" + apiResponse);
        JSONObject json = new JSONObject(apiResponse);
        JSONArray jsarray = (JSONArray) json.getJSONArray("eventObjectList");
        String name, latlongi, lati, longi, population, distance, link;

        for(int i=0; i<jsarray.length();i++){
            name = jsarray.getJSONObject(i).getString("eventName");
            population = jsarray.getJSONObject(i).getString("population");
            //distance = jsarray.getJSONObject(i).getString("distance");
            latlongi = jsarray.getJSONObject(i).getString("latlong");
            link = jsarray.getJSONObject(i).getString("link");
            lati = latlongi.split(",")[0];
            longi = latlongi.split(",")[1];


            Location locationA = new Location("point a");
            locationA.setLatitude(Double.valueOf(lati));
            locationA.setLongitude(Double.valueOf(longi));
            Location locationB = new Location("point b");
            locationB.setLatitude(lat);
            locationB.setLongitude(lng);

            double distancecalc = Math.floor((locationA.distanceTo(locationB)) / 1000);
            if(distancecalc<15) {
                listname.add(name);
                listpopulation.add(population);
                listdistance.add(String.valueOf(distancecalc));
                listlat.add(lati);
                listlng.add(longi);
            }

        }
        Intent intent = new Intent(SearchEvent.this, RestaurantSearchResult.class);
        intent.putStringArrayListExtra("restaurantnames", listname);
        intent.putStringArrayListExtra("distance", listdistance);
        intent.putStringArrayListExtra("population", listpopulation);
        intent.putStringArrayListExtra("lat", listlat);
        intent.putStringArrayListExtra("lng", listlng);

        intent.putExtra("latitude", String.valueOf(lat));
        intent.putExtra("longitude",String.valueOf(lng));
        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void searchEvent(String keyparams, String loc) throws IOException, JSONException {
        URL url = null;
        String jsonReply ="";
        System.out.println("JSON repoly1 is: "+jsonReply);
        HttpURLConnection conn = null;
        StringBuffer contentXML = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            url = new URL("http://10.14.120.237:8080/InMobiHack2016/api/getEvents");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            Long tsLong = System.currentTimeMillis()/1000;
            //String ts = tsLong.toString();
            String input = "{\"uid\":\"11aaa-11aaa-11aaa-11aaa\",\"type\":\""+keyparams+"\",\"loc\":\""+loc+"\" }";
            System.out.println(input);

            Boolean networkconnected = isNetworkConnected();
            if(networkconnected) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(input);
                out.close();
                if (conn.getResponseCode() == 200) {
                    System.out.println("got the response for event");
                    InputStream response = conn.getInputStream();
                    jsonReply = convertStreamToString(response);
                    System.out.println("JSON repoly is: " + jsonReply);

                    parseresponse(jsonReply);

//                    Intent intent = new Intent(SearchEvent.this, RestaurantSearchResult.class);
//                    intent.putStringArrayListExtra("restaurantnames", listname);
//                    intent.putStringArrayListExtra("distance", listdistance);
//                    intent.putStringArrayListExtra("population", listpopulation);
//                    intent.putStringArrayListExtra("lat", listlat);
//                    intent.putStringArrayListExtra("lng", listlng);
//
//                    intent.putExtra("latitude", String.valueOf(lat));
//                    intent.putExtra("longitude",String.valueOf(lng));
//                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(SearchEvent.this, "Not present.", Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(15);
                    toast.show();
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());

                }
            }else{

                Toast toast = Toast.makeText(SearchEvent.this, "Please check your internet connection.", Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(15);
                toast.show();

            }
        }catch (ProtocolException pex) {
            Log.d("Invalid api call", pex.getMessage());
            pex.printStackTrace();
        } catch (MalformedURLException mex) {
            Log.d("Error", mex.getMessage());
            mex.printStackTrace();
        } catch (IOException ioex) {
            Log.d("Error", ioex.getMessage());
            ioex.printStackTrace();
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
