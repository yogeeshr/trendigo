package android.inmobi.com.trendigo;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by deepak.jha on 9/11/16.
 */
public class EventSearchResult extends ListActivity {
    public String latfrmobj;
    public String lonfrmobj;
    public ArrayList<String> lati;
    public ArrayList<String> longi, eventsName, imageUrl, searchUrl, address, startDate,
            endDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView test = (ListView) findViewById(R.id.listview);
        setListAdapter(null);

        eventsName = (ArrayList<String>) getIntent().getStringArrayListExtra("eventnames");
//        eventDistance = (ArrayList<String>) getIntent().getStringArrayListExtra("distance");
        latfrmobj = getIntent().getExtras().getString("latitude");
        lonfrmobj = getIntent().getExtras().getString("longitude");
        lati = (ArrayList<String>) getIntent().getStringArrayListExtra("lat");
        longi = (ArrayList<String>) getIntent().getStringArrayListExtra("lng");
        imageUrl = (ArrayList<String>) getIntent().getStringArrayListExtra("imageUrl");
        searchUrl = (ArrayList<String>) getIntent().getStringArrayListExtra("searchUrl");
        address = (ArrayList<String>) getIntent().getStringArrayListExtra("address");
        startDate = (ArrayList<String>) getIntent().getStringArrayListExtra("startDate");
        endDate = (ArrayList<String>) getIntent().getStringArrayListExtra("startDate");

        System.out.println("latitude is" + latfrmobj);
        System.out.println("longitude is is" + lonfrmobj);

        if(isNetworkAvailable()) {
            setListAdapter(new EventArrayAdapter(this.getBaseContext(), eventsName, startDate, endDate,
                    imageUrl, searchUrl));
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Please check your internet connection.", Toast.LENGTH_LONG);
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
            String uri = "uber://?action=setPickup&&pickup[latitude]=" + latfrmobj + "&pickup[longitude]=" + lonfrmobj + "&dropoff[latitude]=" + lati.get(position) + "&dropoff[longitude]=" + longi.get(position) + "&pickup[nickname]=mylocation&dropoff[nickname]=" + eventsName.get(position);
            System.out.println("URI is : " + uri);

            if(isNetworkAvailable()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(), "Please check your internet connection.", Toast.LENGTH_LONG);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(15);
                toast.show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.uber.com/sign-up?client_id=YOUR_CLIENT_ID"));
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
