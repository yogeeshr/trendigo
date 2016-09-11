package android.inmobi.com.trendigo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class HomePage extends AppCompatActivity {
    private static LinearLayout searchrestaurant = null;
    private static LinearLayout searchevent = null;
    private static double lat;
    private static double lng;
    private static int countOfRestaurants, countOfEvents;
    private static double radiusOfSearch;
    private static String restaurantApiResponse, eventApiResponse;

    public ArrayList<String> listname, listlat, listlng, listaddress, listRating, listdistance,
            listSearchUrl, listImageUrl, listStartDate, listEndDate;

    public ImageView restimg1, restimg2,eventimg1, eventimg2;
    public TextView resttxt1, resttxt2,eventtxt1, eventtxt2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        listname = new ArrayList<String>();
        listlat = new ArrayList<String>();
        listlng = new ArrayList<String>();
        listaddress = new ArrayList<String>();
        listRating = new ArrayList<String>();
        listdistance = new ArrayList<String>();
        listSearchUrl = new ArrayList<String>();
        listImageUrl = new ArrayList<String>();
        listStartDate = new ArrayList<String>();
        listEndDate = new ArrayList<String>();

        restimg1 = (ImageView) findViewById(R.id.restimg1);
        restimg2 = (ImageView) findViewById(R.id.restimg2);
        eventimg1 = (ImageView) findViewById(R.id.eventimg1);
        eventimg2 = (ImageView) findViewById(R.id.eventimg2);
        resttxt1 = (TextView) findViewById(R.id.resttxt1);
        resttxt2 = (TextView) findViewById(R.id.resttxt2);
        eventtxt1 = (TextView) findViewById(R.id.evnttxt1);
        eventtxt2 = (TextView) findViewById(R.id.evnttxt2);

        searchrestaurant = (LinearLayout) findViewById(R.id.restaurantsearch);
        searchevent = (LinearLayout) findViewById(R.id.eventsearch);


        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {
            lat = gpsTracker.getLatitude();
            lng = gpsTracker.getLongitude();
        }

        try {
            restaurantApiResponse = callRestaurantApi(lat,lng, 2, 5000.0);
            eventApiResponse = callEventApi(lat, lng, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(restaurantApiResponse!=null){
            try {
                parseRestaurantResponseHome(restaurantApiResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(eventApiResponse!=null){
            try {
                parseEventResponseHome(eventApiResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        ImageView fireSales = (ImageView) findViewById(R.id.firesales);

//        Location locationA = new Location("point a");
//        locationA.setLatitude(Double.valueOf(rest_lat));
//        locationA.setLongitude(Double.valueOf(rest_lon));
//        Location locationB = new Location("point b");
//        locationB.setLatitude(lat);
//        locationB.setLongitude(lng);
//        double distance = (locationA.distanceTo(locationB)) / 1000;


        String imageUrl = "http://ntibanear.com/wp-content/uploads/2014/03/320x50.png";
        final String clickUrl = "https://www.mcdonalds.com/us/en-us.html";
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



        searchrestaurant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    //findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                    countOfRestaurants = 10;
                    radiusOfSearch = 5000.0;

                    System.out.println("after");
                    ApiZomato requestZomato = new ApiZomato(HomePage.this);
                    String resp = null;
                    try {
                        resp = requestZomato.makeRequest(lat, lng, countOfRestaurants, radiusOfSearch);
                        System.out.println(" zamato Resp is : " + resp);
                        if(resp!=null) {
                            parseRestaurantResponse(resp);
                        }else{
                            Toast toast = Toast.makeText(HomePage.this, "Nothing to show.", Toast.LENGTH_LONG);
                            LinearLayout toastLayout = (LinearLayout) toast.getView();
                            TextView toastTV = (TextView) toastLayout.getChildAt(0);
                            toastTV.setTextSize(15);
                            toast.show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(" zamato Resp is : " + resp);
                }else{
                    Toast toast = Toast.makeText(HomePage.this, "Please check your internet connection.", Toast.LENGTH_LONG);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(15);
                    toast.show();
                }
            }
        });

        searchevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    ApiEvents requestEvents = new ApiEvents(HomePage.this);
                    String resp = null;
                    try {
                        resp = requestEvents.makeRequest(lat, lng, 10);
                        //resp="{\"events\": [{\"eventUrl\": \"http://allevents.in/bangalore/diy-science-fest-2016/80001864300021\", \"latitude\": 12.982999801636, \"bannerUrl\": \"https://eventthumb.s3.amazonaws.com/thumbs/thumb57bd56f8cdb9a.jpg\", \"eventName\": \"DIY Science Fest 2016\", \"startTime\": 1473411600, \"location\": \"VSR Kalyan Mantapa, Brookefield, Bangalore, 560037\", \"endTime\": 1473706800, \"longitude\": 77.583000183105 }, {\"eventUrl\": \"http://allevents.in/bangalore/onamfest/80001017012638\", \"latitude\": 12.982999801636, \"bannerUrl\": \"https://eventthumb.s3.amazonaws.com/thumbs/thumb57c3bc35d8f9a.jpg\", \"eventName\": \"OnamFest\", \"startTime\": 1473451200, \"location\": \"Next to E-zone Club, Marthahalli bridge outer ring road,Marthahalli\", \"endTime\": 1473894000, \"longitude\": 77.583000183105 }, {\"eventUrl\": \"http://allevents.in/bangalore/engineer-a-story/1567239910252394\", \"latitude\": 12.929394721985, \"bannerUrl\": \"https://scontent.xx.fbcdn.net/v/t1.0-9/p720x720/13932724_1089980104431184_5204166547465477784_n.jpg?oh=3bbbe0e2dc0b1922b50ed69e90375257&oe=58441533\", \"eventName\": \"Engineer a Story\", \"startTime\": 1473501600, \"location\": \"Cilre\", \"endTime\": 1473688800, \"longitude\": 77.62915802002 }, {\"eventUrl\": \"http://allevents.in/bangalore/breakfast-ride-to-basvangudi/1788277694784392\", \"latitude\": 12.970100402832, \"bannerUrl\": \"https://cdn-az.allevents.in/banners/1ead1bb9e28be6d35491b6abfb32ba4b\", \"eventName\": \"Breakfast Ride to Basvangudi\", \"startTime\": 1473660000, \"location\": \"Giant Starkenn Cycling World. Indira Nagar, Bangalore\", \"endTime\": 1473660000, \"longitude\": 77.640151977539 }, {\"eventUrl\": \"http://allevents.in/bangalore/escape-2-manhcinbele-day-outingpaintball-kayaking-and-swimming/258135494585105\", \"latitude\": 13.006110191345, \"bannerUrl\": \"https://cdn-az.allevents.in/banners/1a13ffdfa590688684b20b3ee0554592\", \"eventName\": \"Escape 2 Manhcinbele Day Outing(Paintball, Kayaking and Swimming)\", \"startTime\": 1473661800, \"location\": \"Manchinbele\", \"endTime\": 1473661800, \"longitude\": 77.636619567871 }, {\"eventUrl\": \"http://allevents.in/bangalore/provisional-assessor-training/1142615795813356\", \"latitude\": 12.936043739319, \"bannerUrl\": \"https://cdn-az.allevents.in/banners/3486594489b217f56c63a958dc10d589\", \"eventName\": \"Provisional Assessor Training\", \"startTime\": 1473667200, \"location\": \"Pai Viceroy, Bangalore\", \"endTime\": 1473667200, \"longitude\": 77.583885192871 }, {\"eventUrl\": \"http://allevents.in/bangalore/school-and-day-care-holiday-bakarid/825078210970424\", \"latitude\": 12.858749389648, \"bannerUrl\": \"https://cdn-az.allevents.in/banners/f55debad9340b1bb0273846490a4eb04\", \"eventName\": \"School and Day Care Holiday - Bakarid\", \"startTime\": 1473670800, \"location\": \"Maple Bear Canadian Pre-School, Gottigere, Bangalore\", \"endTime\": 1473670800, \"longitude\": 77.596267700195 }, {\"eventUrl\": \"http://allevents.in/bangalore/change-your-voice-and-change-your-life-by-slp-sanjay-kumar/284344681927758\", \"latitude\": 13.03640460968, \"bannerUrl\": \"https://cdn-az.allevents.in/banners/a4f728d9ee0524bf1daf9a3da7c9597c\", \"eventName\": \"Change Your Voice And Change Your Life By SLP Sanjay Kumar\", \"startTime\": 1473674400, \"location\": \"Sanjay Speech Hearing and Rehabilitation Center\", \"endTime\": 1473674400, \"longitude\": 77.59464263916 }, {\"eventUrl\": \"http://allevents.in/bangalore/kanchanotsava-2016/1816461918576427\", \"latitude\": 12.942589759827, \"bannerUrl\": \"https://cdn-az.allevents.in/banners/fe6982917dda67355780cf732dc1dfe4\", \"eventName\": \"Kanchanotsava - 2016\", \"startTime\": 1473674400, \"location\": \"Our School\", \"endTime\": 1473674400, \"longitude\": 77.557647705078 }, {\"eventUrl\": \"http://allevents.in/bangalore/punarvasu-ganesh-chaturthi-2016/177598852643629\", \"latitude\": 12.983300209045, \"bannerUrl\": \"https://cdn-az.allevents.in/banners/db45333c5a1d08136e5fab5add90e8d8\", \"eventName\": \"Punarvasu Ganesh chaturthi 2016 :)\", \"startTime\": 1473674400, \"location\": \"Rajajinagar 1st block\", \"endTime\": 1473674400, \"longitude\": 77.583297729492 }] }";
                        System.out.println(" events Resp is : " + resp);
                        parseEventResponse(resp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println(" events Resp is : " + resp);
                }else{
                   // findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
                    Toast toast = Toast.makeText(HomePage.this, "Please check your internet connection.", Toast.LENGTH_LONG);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(15);
                    toast.show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private final void parseRestaurantResponse(String apiResponse) throws JSONException {
        listname.clear();
        listaddress.clear();
        listdistance.clear();
        listlat.clear();
        listlng.clear();
        listRating.clear();
        listSearchUrl.clear();
        listImageUrl.clear();
        listStartDate.clear();
        listEndDate.clear();

        String id,name,searchurl,address,rest_lat, rest_lon,imageUrl,rating;
        System.out.println("into parse response method");

        JSONObject json = new JSONObject(apiResponse);
        JSONArray responseObject = json.getJSONArray("restaurants");
        JSONObject singleRestaurant, singleObject, locationObject,user_ratingObject;

        if (responseObject.length() == 0) {
            Toast toast = Toast.makeText(HomePage.this, "Sorry, no restaurant found near you.", Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(15);
            toast.show();

        }else {
            for (int i = 0; i < responseObject.length(); i++) {
                singleRestaurant = responseObject.getJSONObject(i);
                singleObject = singleRestaurant.getJSONObject("restaurant");
                id = singleObject.getString("id");
                name = singleObject.getString("name");
                searchurl = "https://www.google.co.in/#q=" + name;
                locationObject = singleObject.getJSONObject("location");
                address = locationObject.getString("address");
                rest_lat = locationObject.getString("latitude");
                rest_lon = locationObject.getString("longitude");
                imageUrl = singleObject.getString("thumb");
                user_ratingObject = singleObject.getJSONObject("user_rating");
                rating = user_ratingObject.getString("aggregate_rating");

                Location locationA = new Location("point a");
                locationA.setLatitude(Double.valueOf(rest_lat));
                locationA.setLongitude(Double.valueOf(rest_lon));
                Location locationB = new Location("point b");
                locationB.setLatitude(lat);
                locationB.setLongitude(lng);
                double distance = (locationA.distanceTo(locationB)) / 1000;
                String stringDistance = String.format("%.2f", distance);
                listname.add(name);
                listaddress.add(address);
                listdistance.add(stringDistance);
                listlat.add(rest_lat);
                listlng.add(rest_lon);
                listRating.add(rating);
                listImageUrl.add(imageUrl);
                listSearchUrl.add(searchurl);
            }

            Intent intent = new Intent(HomePage.this, RestaurantSearchResult.class);
            intent.putStringArrayListExtra("restaurantnames", listname);
            intent.putStringArrayListExtra("distance", listdistance);
            intent.putStringArrayListExtra("rating", listRating);
            intent.putExtra("latitude", String.valueOf(lat));
            intent.putExtra("longitude", String.valueOf(lng));
            intent.putStringArrayListExtra("lat", listlat);
            intent.putStringArrayListExtra("lng", listlng);
            intent.putStringArrayListExtra("address", listaddress);
            intent.putStringArrayListExtra("imageUrl", listImageUrl);
            intent.putStringArrayListExtra("searchUrl", listSearchUrl);
            startActivity(intent);

        }
    }

    private final void parseEventResponse(String apiResponse) throws JSONException {
        listname.clear();
        listaddress.clear();
        listdistance.clear();
        listlat.clear();
        listlng.clear();
        listRating.clear();
        listSearchUrl.clear();
        listImageUrl.clear();

        String id,name,searchurl,starttime, startdate, endtime, enddate, address,event_lat, event_lon,imageUrl;
        System.out.println("into parse response method");

        JSONObject json = new JSONObject(apiResponse);
        JSONArray responseObject = json.getJSONArray("events");
        JSONObject singleEvent;

        if (responseObject.length() == 0) {
            Toast toast = Toast.makeText(HomePage.this, "Sorry, no events found near you.", Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(15);
            toast.show();

        }else {
            for (int i = 0; i < responseObject.length(); i++) {
                singleEvent = responseObject.getJSONObject(i);
                name = singleEvent.getString("eventName");
                searchurl = singleEvent.getString("eventUrl");
                imageUrl = singleEvent.getString("bannerUrl");
                starttime=singleEvent.getString("startTime");
                endtime = singleEvent.getString("endTime");
                event_lat = singleEvent.getString("latitude");
                event_lon = singleEvent.getString("longitude");
                address = singleEvent.getString("location");

                startdate = epochToDate(Long.valueOf(starttime) * 1000);
                enddate = epochToDate(Long.valueOf(endtime) * 1000);
                listname.add(name);
                listImageUrl.add(imageUrl);
                listSearchUrl.add(searchurl);
//                listdistance.add(stringDistance);
                listlat.add(event_lat);
                listlng.add(event_lon);
                listaddress.add(address);
                listStartDate.add(startdate);
                listEndDate.add(enddate);

            }
            Intent intent = new Intent(HomePage.this, EventSearchResult.class);
            intent.putStringArrayListExtra("eventnames", listname);
            intent.putExtra("latitude", String.valueOf(lat));
            intent.putExtra("longitude", String.valueOf(lng));
            intent.putStringArrayListExtra("lat", listlat);
            intent.putStringArrayListExtra("lng", listlng);
            intent.putStringArrayListExtra("address", listaddress);
            intent.putStringArrayListExtra("imageUrl", listImageUrl);
            intent.putStringArrayListExtra("searchUrl", listSearchUrl);
            intent.putStringArrayListExtra("startDate", listStartDate);
            intent.putStringArrayListExtra("endDate", listEndDate);
            startActivity(intent);

        }
    }

    private static String epochToDate(Long epochTime){
        Date date = new Date(epochTime);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(date);
        format.setTimeZone(TimeZone.getTimeZone("India/Mumbai"));
        formatted = format.format(date);
        return formatted;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private String callRestaurantApi(double latitude, double longitude, int count, double radius) throws IOException {
        if (isNetworkAvailable()) {
            countOfRestaurants = count;
            radiusOfSearch = radius;

            System.out.println("after");
            ApiZomato requestZomato = new ApiZomato(HomePage.this);
            String resp = null;
            resp = requestZomato.makeRequest(latitude, longitude, countOfRestaurants, radiusOfSearch);
            System.out.println(" zamato Resp is : " + resp);
            return resp;

        }else{
            Toast toast = Toast.makeText(HomePage.this, "Please check your internet connection.", Toast.LENGTH_LONG);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(15);
            toast.show();
            return null;
        }
    }


    private String callEventApi(double latitude, double longitude, int count) throws IOException {
        if (isNetworkAvailable()) {
            int countOfEvents = count;


            ApiEvents requestEvents = new ApiEvents(HomePage.this);
            String resp = null;
            resp = requestEvents.makeRequest(latitude, longitude, countOfEvents);
            System.out.println(" events Resp is : " + resp);
            return resp;
        }else{
            Toast toast = Toast.makeText(HomePage.this, "Please check your internet connection.", Toast.LENGTH_LONG);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(15);
            toast.show();
            return null;
        }
    }


    private final void parseRestaurantResponseHome(String apiResponse) throws JSONException {
        String name,searchurl,imageUrl;
        System.out.println("into parse response method for home page after splash");

        JSONObject json = new JSONObject(apiResponse);
        JSONArray responseObject = json.getJSONArray("restaurants");
        JSONObject singleRestaurant, singleObject, locationObject,user_ratingObject;

        if (responseObject.length() == 0) {
            Toast toast = Toast.makeText(HomePage.this, "Sorry, no restaurant found near you.", Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(15);
            toast.show();

        }else {
            for (int i = 0; i < responseObject.length(); i++) {
                singleRestaurant = responseObject.getJSONObject(i);
                singleObject = singleRestaurant.getJSONObject("restaurant");
                name = singleObject.getString("name");
                searchurl = "https://www.google.co.in/#q=" + name;
                imageUrl = singleObject.getString("thumb");

                if(i==0){
                    new DownloadImageTask((ImageView) findViewById(R.id.restimg1))
                            .execute(imageUrl);
                    final String finalSearchurl = searchurl;
                    restimg1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse(finalSearchurl));
                            startActivity(intent);
                        }
                    });
                    resttxt1.setText(name);
                }else{
                    new DownloadImageTask((ImageView) findViewById(R.id.restimg2))
                            .execute(imageUrl);
                    final String finalSearchurl = searchurl;
                    restimg2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse(finalSearchurl));
                            startActivity(intent);
                        }
                    });
                    resttxt2.setText(name);
                }
            }

        }
    }


    private final void parseEventResponseHome(String apiResponse) throws JSONException {

        String name, searchurl, imageUrl;

        JSONObject json = new JSONObject(apiResponse);
        JSONArray responseObject = json.getJSONArray("events");
        JSONObject singleEvent;

        if (responseObject.length() == 0) {
            Toast toast = Toast.makeText(HomePage.this, "Sorry, no events found near you.", Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(15);
            toast.show();

        }else {
            System.out.println("pulled event 2 images "+responseObject.length());
            for (int i = 0; i < responseObject.length(); i++) {
                singleEvent = responseObject.getJSONObject(i);
                name = singleEvent.getString("eventName");
                searchurl = singleEvent.getString("eventUrl");
                imageUrl = singleEvent.getString("bannerUrl");
                if(i==0){
                    new DownloadImageTask((ImageView) findViewById(R.id.eventimg1))
                            .execute(imageUrl);
                    final String finalSearchurl = searchurl;
                    eventimg1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse(finalSearchurl));
                            startActivity(intent);
                        }
                    });
                    eventtxt1.setText(name);
                }else{
                    new DownloadImageTask((ImageView) findViewById(R.id.eventimg2))
                            .execute(imageUrl);
                    final String finalSearchurl = searchurl;
                    eventimg2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse(finalSearchurl));
                            startActivity(intent);
                        }
                    });
                    eventtxt2.setText(name);

                }

            }

        }
    }


}