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
public class PubSearch extends Activity {

    private TextView v1,v2, v3, v4, v5, v6, v7, v8;
    private int nv1,nv2, nv3, nv4, nv5, nv6, nv7, nv8;
    private int i=1;
    private String key="";
    private String latlong, response;
    private double lat, lng;
    public ArrayList<String> listname, listlat, listlng, listaddress, listpopulation, listdistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchpubs);

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
        v1 = (TextView) findViewById(R.id.beer);
        v2 = (TextView) findViewById(R.id.bar);
        v3 = (TextView) findViewById(R.id.pub);
        v4 = (TextView) findViewById(R.id.pitcher);
        v5 = (TextView) findViewById(R.id.brew);
        v6 = (TextView) findViewById(R.id.disco);
        v7 = (TextView) findViewById(R.id.hard);
        v8 = (TextView) findViewById(R.id.wines);

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
        TextView search = (TextView) findViewById(R.id.srchpubs);


        GPSTracker gpsTracker = new GPSTracker(this);
        if(gpsTracker.canGetLocation()){
            lat = gpsTracker.getLatitude();
            lng = gpsTracker.getLongitude();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = "";
                if (nv1 == 1) {
                    key = key + "beer";
                }
                if (nv2 == 1) {
                    key = key + "+bar";
                }
                if (nv3 == 1) {
                    key = key + "+pub";
                }
                if (nv4 == 1) {
                    key = key + "+pitcher";
                }
                if (nv5 == 1) {
                    key = key + "+brew";
                }
                if (nv6 == 1) {
                    key = key + "+disco";
                }
                if (nv7 == 1) {
                    key = key + "+hard";
                }
                if (nv8 == 1) {
                    key = key + "+wines";
                }
                System.out.println("Key to be searched is: " + key);
//                Intent nextpage = new Intent(getApplicationContext(), ApiRequest.class);
//                nextpage.putExtra("lat", String.valueOf(lat));
//                nextpage.putExtra("lng", String.valueOf(lng));
//                nextpage.putExtra("keyparams", key);
//                startActivity(nextpage);
                try {
                    if(key==""){
                        Toast toast = Toast.makeText(PubSearch.this, "Please choose one!", Toast.LENGTH_SHORT);
                        LinearLayout toastLayout = (LinearLayout) toast.getView();
                        TextView toastTV = (TextView) toastLayout.getChildAt(0);
                        toastTV.setTextSize(15);
                        toast.show();
                    }else {
                        ApiRequest requestMethod = new ApiRequest(PubSearch.this);
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
            Toast toast = Toast.makeText(PubSearch.this, "Sorry, no restaurant is present.", Toast.LENGTH_SHORT);
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
            Intent intent = new Intent(PubSearch.this, RestaurantSearchResult.class);
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
