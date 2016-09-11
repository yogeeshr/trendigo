package android.inmobi.com.trendigo;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by deepak.jha on 2/20/16.
 */
public class StarOutletSearch extends Activity {

    private TextView v1,v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, search;
    private int nv1,nv2, nv3, nv4, nv5, nv6, nv7, nv8, nv9, nv10, nv11, nv12, nv13, nv14 = 0;
    private int i=1;
    private String key="";
    private String latlong, response;
    private double lat, lng;
    public ArrayList<String> listname, listlat, listlng, listaddress, listpopulation, listdistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchstaroutlets);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

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
        v1 = (TextView) findViewById(R.id.mcd);
        v2 = (TextView) findViewById(R.id.dominos);
        v3 = (TextView) findViewById(R.id.kfc);
        v4 = (TextView) findViewById(R.id.subway);
        v5 = (TextView) findViewById(R.id.brgrking);
        v6 = (TextView) findViewById(R.id.ccd);
        v7 = (TextView) findViewById(R.id.starbcks);
        v8 = (TextView) findViewById(R.id.mghnafoods);
        v9 = (TextView) findViewById(R.id.bhagini);
        v10 = (TextView) findViewById(R.id.tdkasingh);
        v11 = (TextView) findViewById(R.id.pizzacornr);
        v12 = (TextView) findViewById(R.id.pizzahut);
        v13 = (TextView) findViewById(R.id.taco);
        v14 = (TextView) findViewById(R.id.dunkin);

        search = (TextView) findViewById(R.id.srchstarrstrants);

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv1==0) {
                    v1.setBackgroundColor(Color.GRAY);
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
                    nv6 = 1;
                }else{
                    nv6=0;
                    v6.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv7==0) {
                    v7.setBackgroundColor(Color.GRAY);
                    nv7 = 1;
                }else{
                    nv7=0;
                    v7.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv8==0) {
                    v8.setBackgroundColor(Color.GRAY);
                    nv8 = 1;
                }else{
                    nv8=0;
                    v8.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv9==0) {
                    v9.setBackgroundColor(Color.GRAY);
                    nv9 = 1;
                }else{
                    nv9=0;
                    v9.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv10==0) {
                    v10.setBackgroundColor(Color.GRAY);
                    nv10 = 1;
                }else{
                    nv10=0;
                    v10.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv11==0) {
                    v11.setBackgroundColor(Color.GRAY);
                    nv11 = 1;
                }else{
                    nv11=0;
                    v11.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv12==0) {
                    v12.setBackgroundColor(Color.GRAY);
                    nv12 = 1;
                }else{
                    nv12=0;
                    v12.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv13==0) {
                    v13.setBackgroundColor(Color.GRAY);
                    nv13 = 1;
                }else{
                    nv13=0;
                    v13.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv14==0){
                    v14.setBackgroundColor(Color.GRAY);
                    nv14 = 1;
                }else{
                    nv14=0;
                    v14.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });

        GPSTracker gpsTracker = new GPSTracker(this);
        if(gpsTracker.canGetLocation()){
            lat = gpsTracker.getLatitude();
            lng = gpsTracker.getLongitude();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("search button is clicked");
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

                key="";
                if(nv1==1){
                    key = key+"mcdonald";
                }
                if(nv2==1){
                    key = key+"+dominos";
                }
                if(nv3==1){
                    key = key+"+kfc";
                }
                if(nv4==1){
                    key = key+"+subway";
                }
                if(nv5==1){
                    key = key+"+burger king";
                }
                if(nv6==1){
                    key = key+"+cafe coffee day";
                }
                if(nv7==1){
                    key = key+"+starbucks";
                }
                if(nv8==1){
                    key = key+"+meghana foods";
                }
                if(nv9==1){
                    key = key+"+bhagini";
                }
                if(nv10==1){
                    key = key+"+tadka singh";
                }
                if(nv11==1){
                    key = key+"+pizza corner";
                }
                if(nv12==1){
                    key = key+"+pizza hut";
                }
                if(nv13==1){
                    key = key+"+taco bell";
                }
                if(nv14==1){
                    key = key+"+dunkin donut";
                }

                System.out.println("Key to be searched is: " + key);
//                Intent nextpage = new Intent(getApplicationContext(), ApiRequest.class);
//                nextpage.putExtra("lat", String.valueOf(lat));
//                nextpage.putExtra("lng", String.valueOf(lng));
//                nextpage.putExtra("keyparams", key);
//                startActivity(nextpage);

                try {
                    if(key==""){
                        Toast toast = Toast.makeText(StarOutletSearch.this, "Please choose one!", Toast.LENGTH_SHORT);
                        LinearLayout toastLayout = (LinearLayout) toast.getView();
                        TextView toastTV = (TextView) toastLayout.getChildAt(0);
                        toastTV.setTextSize(15);
                        toast.show();
                    }else {
                        System.out.println("inside staroutlet search");


                        ApiRequest requestMethod = new ApiRequest(StarOutletSearch.this);
                        String resp = requestMethod.makeRequest(lat, lng, key);
                        System.out.println("Resp is : " + resp);

                    try {
                        parseresponse(resp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    }

                } catch (IOException e) {
                    System.out.println("Exception thrown");
                    e.printStackTrace();
                }
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
        JSONObject json = new JSONObject(apiResponse);
        JSONObject responseObject = json.getJSONObject("response");
        String addressstore, name, lati, longi, addrs, population;
        JSONArray array = responseObject.getJSONArray("venues");
        if (array.length() == 0) {
            Toast toast = Toast.makeText(StarOutletSearch.this, "Sorry, no restaurant is present.", Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(15);
            toast.show();

        } else {
            for (int i = 0; i < array.length(); i++) {
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

                Location locationA = new Location("point a");
                locationA.setLatitude(Double.valueOf(lati));
                locationA.setLongitude(Double.valueOf(longi));
                Location locationB = new Location("point b");
                locationB.setLatitude(lat);
                locationB.setLongitude(lng);

                double distance = Math.floor((locationA.distanceTo(locationB)) / 1000);
                listdistance.add(String.valueOf(distance));

                addressstore = locationObj.getString("formattedAddress");
                listaddress.add(addressstore);


            }
            System.out.println("going to other activity with the lists");
            Intent intent = new Intent(StarOutletSearch.this, RestaurantSearchResult.class);
            intent.putStringArrayListExtra("restaurantnames", listname);
            intent.putStringArrayListExtra("distance", listdistance);
            intent.putStringArrayListExtra("population", listpopulation);
            intent.putStringArrayListExtra("lat", listlat);
            intent.putStringArrayListExtra("lng", listlng);
            intent.putExtra("latitude", String.valueOf(lat));
            intent.putExtra("longitude", String.valueOf(lng));
            startActivity(intent);
        }
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
