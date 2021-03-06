package android.inmobi.com.trendigo.restaurant;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.inmobi.com.trendigo.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by deepak.jha on 2/21/16.
 */
public class RestaurantSearchResult extends ListActivity {

    public String latfrmobj;
    public String lonfrmobj;
    public ArrayList<String> lati;
    public ArrayList<String> longi, restaurantsName, calcdistance, calcrating, imageUrl, searchUrl,
            address, cuisine;
    LayoutInflater layoutinflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView test = (ListView) findViewById(R.id.listview);
//
//
//        //code to add header and footer to listview
//        LayoutInflater inflater = getLayoutInflater();
//
//        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.listview_footer, test,
//                false);
//        test.addFooterView(footer, null, false);


        restaurantsName = (ArrayList<String>)getIntent().getStringArrayListExtra("restaurantnames");
        calcdistance = (ArrayList<String>) getIntent().getStringArrayListExtra("distance");
        calcrating = (ArrayList<String>) getIntent().getStringArrayListExtra("rating");
        latfrmobj = getIntent().getExtras().getString("latitude");
        lonfrmobj = getIntent().getExtras().getString("longitude");
        lati = (ArrayList<String>) getIntent().getStringArrayListExtra("lat");
        longi = (ArrayList<String>) getIntent().getStringArrayListExtra("lng");
        imageUrl = (ArrayList<String>) getIntent().getStringArrayListExtra("imageUrl");
        searchUrl = (ArrayList<String>) getIntent().getStringArrayListExtra("searchUrl");
        address = (ArrayList<String>) getIntent().getStringArrayListExtra("address");
        cuisine = (ArrayList<String>) getIntent().getStringArrayListExtra("cuisine");

        System.out.println("latitude is"+latfrmobj);
        System.out.println("longitude is is" + lonfrmobj);

//        layoutinflater = getLayoutInflater();
//        System.out.println("bfrr setting");
//
//        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.listview_footer,test,false);
//
//        test.addFooterView(footer);
        System.out.println("aftr setting");

        if(isNetworkAvailable()) {
            System.out.println("going inside");
            setListAdapter(new RestaurantArrayAdapter(this.getBaseContext(), restaurantsName,
                    calcdistance, calcrating, imageUrl, searchUrl, cuisine, lati, longi, latfrmobj,
                    lonfrmobj));
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Please check your internet " +
                    "connection.", Toast.LENGTH_LONG);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(15);
            toast.show();
        }
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        String selectedValue = (String) getListAdapter().getItem(position);
        try {
            PackageManager pm = getPackageManager();
            pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
            String uri = "uber://?action=setPickup&&pickup[latitude]=" +latfrmobj + "&pickup" +
                    "[longitude]=" + lonfrmobj + "&dropoff[latitude]="+ lati.get(position) +
                    "&dropoff[longitude]="+longi.get(position)+"&pickup[nickname]=mylocation&drop" +
                    "off[nickname]="+restaurantsName.get(position);
            System.out.println("URI is : " + uri);
            if(isNetworkAvailable()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(), "Please check your internet" +
                        " connection.", Toast.LENGTH_LONG);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(15);
                toast.show();
            }
            } catch (PackageManager.NameNotFoundException e) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.uber.com" +
                    "/sign-up?client_id=YOUR_CLIENT_ID"));
            startActivity(browserIntent);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}