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
public class NonVegSearch extends Activity {

    private TextView nv1, nv2, nv3, nv4, nv5, nv6, nv7, nv8, nv9, nv10, nv11, nv12, nv13, nv14, nv15, nv16, nv17, nv18, nv19, nv20, nv21, nv22, nv23, nv24, nv25, nv26, nv27, nv28;
    private int v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, v15, v16, v17, v18, v19, v20, v21, v22, v23, v24, v25, v26, v27, v28 = 0;
    private int i = 1;
    private String key = "";
    private String latlong, response;
    private double lat, lng;
    public ArrayList<String> listname, listlat, listlng, listaddress, listpopulation, listdistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchnonvegrestaurants);

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
        nv1 = (TextView) findViewById(R.id.nnvmeal);
        nv2 = (TextView) findViewById(R.id.snvmeal);
        nv3 = (TextView) findViewById(R.id.cknpizza);
        nv4 = (TextView) findViewById(R.id.cknburger);
        nv5 = (TextView) findViewById(R.id.cknpasta);
        nv6 = (TextView) findViewById(R.id.cknroll);
        nv7 = (TextView) findViewById(R.id.ckn65);
        nv8 = (TextView) findViewById(R.id.frdckn);
        nv9 = (TextView) findViewById(R.id.chlprwns);
        nv10 = (TextView) findViewById(R.id.chlckn);
        nv11 = (TextView) findViewById(R.id.ptlckn);
        nv12 = (TextView) findViewById(R.id.mtn);
        nv13 = (TextView) findViewById(R.id.egg);
        nv14 = (TextView) findViewById(R.id.sizzlr);
        nv15 = (TextView) findViewById(R.id.fish);
        nv16 = (TextView) findViewById(R.id.cknswrma);
        nv17 = (TextView) findViewById(R.id.cknbyn);
        nv18 = (TextView) findViewById(R.id.mtnbyn);
        nv19 = (TextView) findViewById(R.id.kbab);
        nv20 = (TextView) findViewById(R.id.cknndls);
        nv21 = (TextView) findViewById(R.id.beef);
        nv22 = (TextView) findViewById(R.id.pork);
        nv23 = (TextView) findViewById(R.id.seafds);
        nv24 = (TextView) findViewById(R.id.bbq);
        nv25 = (TextView) findViewById(R.id.btrckn);
        nv26 = (TextView) findViewById(R.id.cknfrdrc);
        nv27 = (TextView) findViewById(R.id.cknbhn);
        nv28 = (TextView) findViewById(R.id.keema);


        nv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v1 == 0) {
                    nv1.setBackgroundColor(Color.GRAY);
                    v1 = 1;
                } else {
                    v1 = 0;
                    nv1.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v2 == 0) {
                    nv2.setBackgroundColor(Color.GRAY);
                    v2 = 1;
                } else {
                    v2 = 0;
                    nv2.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v3 == 0) {
                    nv3.setBackgroundColor(Color.GRAY);
                    v3 = 1;
                } else {
                    v3 = 0;
                    nv3.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v4 == 0) {
                    nv4.setBackgroundColor(Color.GRAY);
                    v4 = 1;
                } else {
                    v4 = 0;
                    nv4.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v5 == 0) {
                    nv5.setBackgroundColor(Color.GRAY);
                    v5 = 1;
                } else {
                    v5 = 0;
                    nv5.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v6 == 0) {
                    nv6.setBackgroundColor(Color.GRAY);
                    v6 = 1;
                } else {
                    v6 = 0;
                    nv6.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v7 == 0) {
                    nv7.setBackgroundColor(Color.GRAY);
                    v7 = 1;
                } else {
                    v7 = 0;
                    nv7.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v8 == 0) {
                    nv8.setBackgroundColor(Color.GRAY);
                    v8 = 1;
                } else {
                    v8 = 0;
                    nv8.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v9 == 0) {
                    nv9.setBackgroundColor(Color.GRAY);
                    v9 = 1;
                } else {
                    v9 = 0;
                    nv9.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v10 == 0) {
                    nv10.setBackgroundColor(Color.GRAY);
                    v10 = 1;
                } else {
                    v10 = 0;
                    nv10.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v11 == 0) {
                    nv11.setBackgroundColor(Color.GRAY);
                    v11 = 1;
                } else {
                    v11 = 0;
                    nv11.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v12 == 0) {
                    nv12.setBackgroundColor(Color.GRAY);
                    v12 = 1;
                } else {
                    v12 = 0;
                    nv12.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v13 == 0) {
                    nv13.setBackgroundColor(Color.GRAY);
                    v13 = 1;
                } else {
                    v13 = 0;
                    nv13.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v14 == 0) {
                    nv14.setBackgroundColor(Color.GRAY);
                    v14 = 1;
                } else {
                    v14 = 0;
                    nv14.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v15 == 0) {
                    nv15.setBackgroundColor(Color.GRAY);
                    v15 = 1;
                } else {
                    v15 = 0;
                    nv15.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v16 == 0) {
                    nv16.setBackgroundColor(Color.GRAY);
                    v16 = 1;
                } else {
                    v16 = 0;
                    nv16.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v17 == 0) {
                    nv17.setBackgroundColor(Color.GRAY);
                    v17 = 1;
                } else {
                    v17 = 0;
                    nv17.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v18 == 0) {
                    nv18.setBackgroundColor(Color.GRAY);
                    v18 = 1;
                } else {
                    v18 = 0;
                    nv18.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v19 == 0) {
                    nv19.setBackgroundColor(Color.GRAY);
                    v19 = 1;
                } else {
                    v19 = 0;
                    nv19.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v20 == 0) {
                    nv20.setBackgroundColor(Color.GRAY);
                    v20 = 1;
                } else {
                    v20 = 0;
                    nv20.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v21 == 0) {
                    nv21.setBackgroundColor(Color.GRAY);
                    v21 = 1;
                } else {
                    v21 = 0;
                    nv21.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v22 == 0) {
                    nv22.setBackgroundColor(Color.GRAY);
                    v22 = 1;
                } else {
                    v22 = 0;
                    nv22.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v23 == 0) {
                    nv23.setBackgroundColor(Color.GRAY);
                    v23 = 1;
                } else {
                    v23 = 0;
                    nv23.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v24 == 0) {
                    nv24.setBackgroundColor(Color.GRAY);
                    v24 = 1;
                } else {
                    v24 = 0;
                    nv24.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v25 == 0) {
                    nv25.setBackgroundColor(Color.GRAY);
                    v25 = 1;
                } else {
                    v25 = 0;
                    nv25.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v26 == 0) {
                    nv26.setBackgroundColor(Color.GRAY);
                    v26 = 1;
                } else {
                    v26 = 0;
                    nv26.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v27 == 0) {
                    nv27.setBackgroundColor(Color.GRAY);
                    v27 = 1;
                } else {
                    v27 = 0;
                    nv27.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });
        nv28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v28 == 0) {
                    nv28.setBackgroundColor(Color.GRAY);
                    v28 = 1;
                } else {
                    v28 = 0;
                    nv28.setBackgroundColor(Color.parseColor("#ffb12f00"));
                }
            }
        });

        TextView search = (TextView) findViewById(R.id.srchnvrstrnts);


        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {
            lat = gpsTracker.getLatitude();
            lng = gpsTracker.getLongitude();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = "";
                if (v1 == 1) {
                    key = key + "meal";
                }
                if (v2 == 1) {
                    key = key + "+meals";
                }
                if (v3 == 1) {
                    key = key + "+pizza";
                }
                if (v4 == 1) {
                    key = key + "+burger";
                }
                if (v5 == 1) {
                    key = key + "+pasta";
                }
                if (v6 == 1) {
                    key = key + "+rolls";
                }
                if (v7 == 1) {
                    key = key + "+chicken 65";
                }
                if (v8 == 1) {
                    key = key + "+fried chicken";
                }
                if (v9 == 1) {
                    key = key + "+prawns";
                }
                if (v10 == 1) {
                    key = key + "+chicken";
                }
                if (v11 == 1) {
                    key = key + "+chicken";
                }
                if (v12 == 1) {
                    key = key + "+mutton";
                }
                if (v13 == 1) {
                    key = key + "+egg";
                }
                if (v14 == 1) {
                    key = key + "+italian";
                }
                if (v15 == 1) {
                    key = key + "+fish";
                }
                if (v16 == 1) {
                    key = key + "+shawarma";
                }
                if (v17 == 1) {
                    key = key + "+biriyani";
                }
                if (v18 == 1) {
                    key = key + "+biriyani";
                }
                if (v19 == 1) {
                    key = key + "+kebab";
                }
                if (v20 == 1) {
                    key = key + "+noodles";
                }
                if (v21 == 1) {
                    key = key + "+beef";
                }
                if (v22 == 1) {
                    key = key + "+pork";
                }
                if (v23 == 1) {
                    key = key + "+sea foods";
                }
                if (v24 == 1) {
                    key = key + "+barbeque";
                }
                if (v25 == 1) {
                    key = key + "+chicken";
                }
                if (v26 == 1) {
                    key = key + "+fried rice";
                }
                if (v27 == 1) {
                    key = key + "+chicken";
                }
                if (v28 == 1) {
                    key = key + "+keema";
                }

                System.out.println("Key to be searched is: " + key);
//                Intent nextpage = new Intent(getApplicationContext(), ApiRequest.class);
//                nextpage.putExtra("lat", String.valueOf(lat));
//                nextpage.putExtra("lng", String.valueOf(lng));
//                nextpage.putExtra("keyparams", key);
//                startActivity(nextpage);
                try {
                    if(key==""){
                        Toast toast = Toast.makeText(NonVegSearch.this, "Please choose one!", Toast.LENGTH_SHORT);
                        LinearLayout toastLayout = (LinearLayout) toast.getView();
                        TextView toastTV = (TextView) toastLayout.getChildAt(0);
                        toastTV.setTextSize(15);
                        toast.show();
                    }else {
                        ApiRequest requestMethod = new ApiRequest(NonVegSearch.this);
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
            Toast toast = Toast.makeText(NonVegSearch.this, "Sorry, no restaurant is present.", Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(15);
            toast.show();

        } else {
            for (int i = 0; i < array.length(); i++) {
                System.out.println("inside test"+array.getJSONObject(i).getString("name"));
                name = array.getJSONObject(i).getString("name");
                listname.add(name);
                JSONObject locationObj = array.getJSONObject(i).getJSONObject("location");
                JSONObject populationObj = array.getJSONObject(i).getJSONObject("stats");
                System.out.println("Just before latlnd error");
                Log.e("Just before latlng error","");
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
            Intent intent = new Intent(NonVegSearch.this, RestaurantSearchResult.class);
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

