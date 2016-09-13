package android.inmobi.com.trendigo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by deepak.jha on 9/12/16.
 */
public class networkConnection {
    public static boolean isNetworkAvailable(Context previousActivity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) previousActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
