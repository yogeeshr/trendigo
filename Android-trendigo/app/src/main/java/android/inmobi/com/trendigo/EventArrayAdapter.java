package android.inmobi.com.trendigo;

import android.content.Context;
import android.content.Intent;
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
 * Created by deepak.jha on 9/11/16.
 */
public class EventArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> restaurants, startDate, endDate, imageUrl, searchUrl;

    public EventArrayAdapter(Context context, ArrayList<String> restaurants,
                             ArrayList<String> startDate, ArrayList<String> endDate,
                             ArrayList<String> imageUrl, ArrayList<String> searchUrl) {
        super(context, R.layout.listevents, restaurants);
        this.context = context;
        this.restaurants = restaurants;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
        this.searchUrl = searchUrl;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.listevents, parent, false);
        TextView restaurantName = (TextView) rowView.findViewById(R.id.eventname);
        TextView startdate = (TextView) rowView.findViewById(R.id.startdate);
        TextView enddate = (TextView) rowView.findViewById(R.id.enddate);
        TextView details = (TextView) rowView.findViewById(R.id.details);
        ImageView image = (ImageView) rowView.findViewById(R.id.event_image);

        new DownloadImageTask((ImageView) rowView.findViewById(R.id.event_image))
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
        details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(searchUrl.get(position)));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_LONG);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(15);
                    toast.show();
                }
            }
        });



        restaurantName.setText(restaurants.get(position));
        startdate.setText("Start Date: " + startDate.get(position));
        enddate.setText("End Date: " + startDate.get(position));

        return rowView;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
