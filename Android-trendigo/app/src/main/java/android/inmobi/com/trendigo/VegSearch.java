package android.inmobi.com.trendigo;

import android.app.Activity;
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
public class VegSearch extends Activity{

    private TextView v1,v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, v15, v16, v17, v18, v19, v20, v21, v22, v23, v24, v25, v26, v27, v28;
    private int nv1,nv2, nv3, nv4, nv5, nv6, nv7, nv8, nv9, nv10, nv11, nv12, nv13, nv14, nv15, nv16, nv17, nv18, nv19, nv20, nv21, nv22, nv23, nv24, nv25, nv26, nv27, nv28 = 0;
    private int i=1;
    private String key="";
    private double lat, lng;
    public ArrayList<String> listname, listlat, listlng, listaddress, listpopulation, listdistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchvegrestaurant);

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
        v1 = (TextView) findViewById(R.id.nvmeal);
        v2 = (TextView) findViewById(R.id.svmeal);
        v3 = (TextView) findViewById(R.id.vpizza);
        v4 = (TextView) findViewById(R.id.vbrgr);
        v5 = (TextView) findViewById(R.id.vpasta);
        v6 = (TextView) findViewById(R.id.vrolls);
        v7 = (TextView) findViewById(R.id.vmomos);
        v8 = (TextView) findViewById(R.id.mshrms);
        v9 = (TextView) findViewById(R.id.mchrian);
        v10 = (TextView) findViewById(R.id.chaat);
        v11 = (TextView) findViewById(R.id.vadapav);
        v12 = (TextView) findViewById(R.id.dosa);
        v13 = (TextView) findViewById(R.id.frdrice);
        v14 = (TextView) findViewById(R.id.paneer);
        v15 = (TextView) findViewById(R.id.vsoups);
        v16 = (TextView) findViewById(R.id.vshwrma);
        v17 = (TextView) findViewById(R.id.vbyn);
        v18 = (TextView) findViewById(R.id.vsalad);
        v19 = (TextView) findViewById(R.id.dal);
        v20 = (TextView) findViewById(R.id.chole);
        v21= (TextView) findViewById(R.id.idli);
        v22 = (TextView) findViewById(R.id.tofu);
        v23 = (TextView) findViewById(R.id.mixvg);
        v24 = (TextView) findViewById(R.id.parathas);
        v25 = (TextView) findViewById(R.id.breads);
        v26 = (TextView) findViewById(R.id.rajma);
        v27 = (TextView) findViewById(R.id.pavbhaji);
        v28 = (TextView) findViewById(R.id.vnoodles);


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
                if(nv14==0) {
                    v14.setBackgroundColor(Color.GRAY);
                    nv14 = 1;
                }else{
                    nv14=0;
                    v14.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv15==0) {
                    v15.setBackgroundColor(Color.GRAY);
                    nv15 = 1;
                }else{
                    nv15=0;
                    v15.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv16==0) {
                    v16.setBackgroundColor(Color.GRAY);
                    nv16 = 1;
                }else{
                    nv16=0;
                    v16.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv17==0) {
                    v17.setBackgroundColor(Color.GRAY);
                    nv17 = 1;
                }else{
                    nv17=0;
                    v17.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv18==0) {
                    v18.setBackgroundColor(Color.GRAY);
                    nv18 = 1;
                }else{
                    nv18=0;
                    v18.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv19==0) {
                    v19.setBackgroundColor(Color.GRAY);
                    nv19 = 1;
                }else{
                    nv19=0;
                    v19.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv20==0) {
                    v20.setBackgroundColor(Color.GRAY);
                    nv20 = 1;
                }else{
                    nv20=0;
                    v20.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv21==0) {
                    v21.setBackgroundColor(Color.GRAY);
                    nv21 = 1;
                }else{
                    nv21=0;
                    v21.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv22==0) {
                    v22.setBackgroundColor(Color.GRAY);
                    nv22 = 1;
                }else{
                    nv22=0;
                    v22.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv23==0) {
                    v23.setBackgroundColor(Color.GRAY);
                    nv23 = 1;
                }else{
                    nv23=0;
                    v23.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv24==0) {
                    v24.setBackgroundColor(Color.GRAY);
                    nv24 = 1;
                }else{
                    nv24=0;
                    v24.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv25==0) {
                    v25.setBackgroundColor(Color.GRAY);
                    nv25 = 1;
                }else{
                    nv25=0;
                    v25.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv26==0) {
                    v26.setBackgroundColor(Color.GRAY);
                    nv26 = 1;
                }else{
                    nv26=0;
                    v26.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv27==0) {
                    v27.setBackgroundColor(Color.GRAY);
                    nv27 = 1;
                }else{
                    nv27=0;
                    v27.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        v28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nv28==0) {
                    v28.setBackgroundColor(Color.GRAY);
                    nv28 = 1;
                }else{
                    nv28=0;
                    v28.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });


        TextView search = (TextView) findViewById(R.id.srchvrestrnts);


        GPSTracker gpsTracker = new GPSTracker(this);
        if(gpsTracker.canGetLocation()){
            lat = gpsTracker.getLatitude();
            lng = gpsTracker.getLongitude();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key="";
                if(nv1==1){
                    key = key+"meal";
                }
                if(nv2==1){
                    key = key+"+meals";
                }
                if(nv3==1){
                    key = key+"+pizza";
                }
                if(nv4==1){
                    key = key+"+burger";
                }
                if(nv5==1){
                    key = key+"+pasta";
                }
                if(nv6==1){
                    key = key+"+rolls";
                }
                if(nv7==1){
                    key = key+"+momos";
                }
                if(nv8==1){
                    key = key+"+mushroooms";
                }
                if(nv9==1){
                    key = key+"+manchurian";
                }
                if(nv10==1){
                    key = key+"+chaat";
                }
                if(nv11==1){
                    key = key+"+goli";
                }
                if(nv12==1){
                    key = key+"+dosa";
                }
                if(nv13==1){
                    key = key+"+fried rice";
                }
                if(nv14==1){
                    key = key+"+paneer";
                }
                if(nv15==1){
                    key = key+"+soups";
                }
                if(nv16==1){
                    key = key+"+shawarma";
                }
                if(nv17==1){
                    key = key+"+biriyani";
                }
                if(nv18==1){
                    key = key+"+salad";
                }
                if(nv19==1){
                    key = key+"+dal";
                }
                if(nv20==1){
                    key = key+"+chole";
                }
                if(nv21==1){
                    key = key+"+idli";
                }
                if(nv22==1){
                    key = key+"+tofu";
                }
                if(nv23==1){
                    key = key+"+veg";
                }
                if(nv24==1){
                    key = key+"+paratha";
                }
                if(nv25==1){
                    key = key+"+paratha";
                }
                if(nv26==1){
                    key = key+"+veg";
                }
                if(nv27==1){
                    key = key+"+goli+pav";
                }
                if(nv28==1){
                    key = key+"+chinese";
                }

                System.out.println("Key to be searched is: " + key);
//                Intent nextpage = new Intent(getApplicationContext(), ApiRequest.class);
//                nextpage.putExtra("lat", String.valueOf(lat));
//                nextpage.putExtra("lng", String.valueOf(lng));
//                nextpage.putExtra("keyparams", key);
//                startActivity(nextpage);
                try {
                    if(key==""){
                        Toast toast = Toast.makeText(VegSearch.this, "Please choose one!", Toast.LENGTH_SHORT);
                        LinearLayout toastLayout = (LinearLayout) toast.getView();
                        TextView toastTV = (TextView) toastLayout.getChildAt(0);
                        toastTV.setTextSize(15);
                        toast.show();
                    }else {
                        ApiRequest requestMethod = new ApiRequest(VegSearch.this);
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
        System.out.println("Thre response is \n" + apiResponse);
        JSONObject json = new JSONObject(apiResponse);
        JSONObject responseObject = json.getJSONObject("response");
        String addressstore, name, lati, longi, addrs, population;
        JSONArray array = responseObject.getJSONArray("venues");
        if (array.length() == 0) {
            Toast toast = Toast.makeText(VegSearch.this, "Sorry, no restaurant is present.", Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(15);
            toast.show();

        } else {
            for (int i = 0; i < array.length(); i++) {
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
                locationB.setLatitude(lat);
                locationB.setLongitude(lng);

                double distance = Math.floor((locationA.distanceTo(locationB)) / 1000);
                System.out.println("The distance is: " + distance);
                listdistance.add(String.valueOf(distance));

                addressstore = locationObj.getString("formattedAddress");
                listaddress.add(addressstore);


            }
            System.out.println("going to other activity with the lists");
            Intent intent = new Intent(VegSearch.this, RestaurantSearchResult.class);
            intent.putStringArrayListExtra("restaurantnames", listname);
            intent.putStringArrayListExtra("distance", listdistance);
            intent.putStringArrayListExtra("population", listpopulation);
            intent.putExtra("latitude", String.valueOf(lat));
            intent.putExtra("longitude", String.valueOf(lng));
            intent.putStringArrayListExtra("lat", listlat);
            intent.putStringArrayListExtra("lng", listlng);
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
