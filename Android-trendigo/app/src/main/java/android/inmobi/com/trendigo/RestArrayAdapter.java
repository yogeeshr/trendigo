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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by deepak.jha on 2/21/16.
 */
public class RestArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> restaurants,calcdistance, calcrating, imageUrl, searchUrl;

    public RestArrayAdapter(Context context, ArrayList<String> restaurants,
                            ArrayList<String> calcdistance, ArrayList<String> calcrating,
                            ArrayList<String> imageUrl,ArrayList<String> searchUrl) {
        super(context, R.layout.listrestaurants, restaurants);
        this.context = context;
        this.restaurants = restaurants;
        this.calcdistance = calcdistance;
        this.calcrating = calcrating;
        this.imageUrl = imageUrl;
        this.searchUrl = searchUrl;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.listrestaurants, parent, false);
        TextView restaurantName = (TextView) rowView.findViewById(R.id.restaurantname);
        TextView distance = (TextView) rowView.findViewById(R.id.distance);
        RatingBar rating = (RatingBar) rowView.findViewById(R.id.rating);
        ImageView image = (ImageView) rowView.findViewById(R.id.rest_image);

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
        distance.setText("Distance: " + calcdistance.get(position) + "km");
        rating.setRating(Float.valueOf(calcrating.get(position)));
        return rowView;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
