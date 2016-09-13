package android.inmobi.com.trendigo.splash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.inmobi.com.trendigo.HomePage;
import android.inmobi.com.trendigo.R;
import android.inmobi.com.trendigo.api.ApiEvents;
import android.inmobi.com.trendigo.api.ApiZomato;
import android.inmobi.com.trendigo.utils.GPSTracker;
import android.inmobi.com.trendigo.utils.networkConnection;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by deepak.jha on 2/20/16.
 */
public class Splash extends Activity{
    public static String restaurantResponse;
    public static String eventResponse;
    public static double lat;
    public static double lng;
    private Context context;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        context = this;

        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {
            lat = gpsTracker.getLatitude();
            lng = gpsTracker.getLongitude();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        ApiZomato apiZomato = new ApiZomato(context);
                        ApiEvents apiEvents = new ApiEvents(context);
                        if (networkConnection.isNetworkAvailable(context)) {
                            restaurantResponse = apiZomato.makeRequest(lat, lng, 2, 5000.0);
                            ProgressDialog dialog = new ProgressDialog(context); // this = YourActivity
                            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            dialog.setMessage("Loading. Please wait...");
                            dialog.setIndeterminate(true);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                            eventResponse = apiEvents.makeRequest(lat, lng, 2);
                        } else {
                            Toast toast = Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_LONG);
                            LinearLayout toastLayout = (LinearLayout) toast.getView();
                            TextView toastTV = (TextView) toastLayout.getChildAt(0);
                            toastTV.setTextSize(15);
                            toast.show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        Log.e("Interrupted connection Splash", e.getMessage());
                        e.printStackTrace();
                    }
                    Intent mainIntent = new Intent(Splash.this, HomePage.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }, 3000);
        }else{
            Toast toast = Toast.makeText(context, "Please turn on the GPS.", Toast.LENGTH_LONG);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(15);
            toast.show();
        }

    }
}
