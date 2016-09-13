package android.inmobi.com.trendigo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.inmobi.com.trendigo.R;

/**
 * Created by deepak.jha on 9/13/16.
 */
public class AlertDialogManager {

    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            // Setting alert dialog icon
            alertDialog.setIcon((status) ? R.drawable.trendigo_logo : R.drawable.trendigo);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}