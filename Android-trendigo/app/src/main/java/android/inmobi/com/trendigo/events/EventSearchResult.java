package android.inmobi.com.trendigo.events;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.inmobi.com.trendigo.R;
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
        endDate = (ArrayList<String>) getIntent().getStringArrayListExtra("endDate");

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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
