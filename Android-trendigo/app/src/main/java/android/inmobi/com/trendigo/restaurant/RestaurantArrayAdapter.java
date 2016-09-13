package android.inmobi.com.trendigo.restaurant;

import android.content.Context;
import android.content.Intent;
import android.inmobi.com.trendigo.utils.DownloadImageTask;
import android.inmobi.com.trendigo.R;
import android.inmobi.com.trendigo.utils.GPSTracker;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by deepak.jha on 2/21/16.
 */
public class RestaurantArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> restaurants,calcdistance, calcrating, imageUrl, searchUrl
            ,cuisine,latrest,lngrest;
    String latuser,lnguser;


    public RestaurantArrayAdapter(Context context, ArrayList<String> restaurants,
                                  ArrayList<String> calcdistance, ArrayList<String> calcrating,
                                  ArrayList<String> imageUrl, ArrayList<String> searchUrl,
                                  ArrayList<String> cuisine, ArrayList<String> latrest,
                                  ArrayList<String> longrest, String latuser,
                                  String longuser) {
        super(context, R.layout.listrestaurants, restaurants);
        this.context = context;
        this.restaurants = restaurants;
        this.calcdistance = calcdistance;
        this.calcrating = calcrating;
        this.imageUrl = imageUrl;
        this.searchUrl = searchUrl;
        this.cuisine = cuisine;
        this.latrest = latrest;
        this.lngrest = longrest;
        GPSTracker gpsTracker = new GPSTracker(context);
        if (gpsTracker.canGetLocation()) {
            this.latuser = String.valueOf(gpsTracker.getLatitude());
            this.lnguser = String.valueOf(gpsTracker.getLongitude());
        }
//        this.latuser = latuser;
//        this.lnguser = longuser;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.listrestaurants, parent, false);
        TextView restaurantName = (TextView) rowView.findViewById(R.id.restaurantname);
        TextView distance = (TextView) rowView.findViewById(R.id.distance);
        TextView rating = (TextView) rowView.findViewById(R.id.rating);
        ImageView image = (ImageView) rowView.findViewById(R.id.rest_image);
        ImageView direction = (ImageView) rowView.findViewById(R.id.direction);


        new DownloadImageTask((ImageView) rowView.findViewById(R.id.rest_image))
                .execute(imageUrl.get(position));
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(searchUrl.get(position)));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_LONG);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(15);
                    toast.show();
                }
            }
        });

        image.setAlpha((float) 10);

        restaurantName.setText(restaurants.get(position));
        //distance.setText("Distance: " + calcdistance.get(position) + "km");
        distance.setText(cuisine.get(position));
        rating.setText(" " + calcrating.get(position));


        direction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("https://www.google.co.in/maps/dir/" + latuser + ","
                            + lnguser + "/" + latrest.get(position) + "," + lngrest.get(position) + "/"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    System.out.println("just before show");

                } else {
                    Toast toast = Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_LONG);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(15);
                    toast.show();
                }
            }
        });

        return rowView;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
