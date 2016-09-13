package android.inmobi.com.trendigo.utils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.inmobi.com.trendigo.HomePage;
import android.inmobi.com.trendigo.R;
import android.inmobi.com.trendigo.api.ApiEvents;
import android.inmobi.com.trendigo.api.ApiZomato;
import android.inmobi.com.trendigo.splash.Splash;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

/**
 * Created by deepak.jha on 9/13/16.
 */
public class map extends FragmentActivity implements OnMapReadyCallback {
    public static LatLng latLng;
    private GoogleMap mMap;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Button ok = (Button) findViewById(R.id.buttonok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialogManager alert = new AlertDialogManager();
//                alert.showAlertDialog(map.this, "Location changed", "Search for new location", true);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ApiZomato apiZomato = new ApiZomato(map.this);
                            ApiEvents apiEvents = new ApiEvents(map.this);
                            if (networkConnection.isNetworkAvailable(map.this)) {
                                Splash.restaurantResponse = apiZomato.makeRequest(Splash.lat, Splash.lng, 2, 5000.0);
                                ProgressDialog dialog = new ProgressDialog(map.this); // this = YourActivity
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setMessage("Loading. Please wait...");
                                dialog.setIndeterminate(true);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.show();
                                Splash.eventResponse = apiEvents.makeRequest(Splash.lat, Splash.lng, 2);
                            } else {
                                Toast toast = Toast.makeText(map.this, "Please check your internet connection.", Toast.LENGTH_LONG);
                                LinearLayout toastLayout = (LinearLayout) toast.getView();
                                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                                toastTV.setTextSize(15);
                                toast.show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            Log.e("Interrupted connection Map", e.getMessage());
                            e.printStackTrace();
                        }
                        Intent mainIntent = new Intent(map.this, HomePage.class);
                        map.this.startActivity(mainIntent);
                        map.this.finish();
                    }
                }, 500);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {
            latLng = new LatLng(Splash.lat,Splash.lng);


//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


//            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));



            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latLng.latitude, latLng.longitude))      // Sets the center of the map to location user
                    .zoom(13)                   // Sets the zoom
                    .bearing(359)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            marker=mMap.addMarker(new MarkerOptions().position(latLng)
                    .title("Search location")
                    .snippet("Long press and move the marker to change lat long")
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            marker.showInfoWindow();












            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDrag(Marker arg0) {
                    // TODO Auto-generated method stub
                    Log.d("Marker", "Dragging");
                }

                @Override
                public void onMarkerDragEnd(Marker arg0) {
                    // TODO Auto-generated method stub
                    LatLng markerLocation = marker.getPosition();
                    Toast.makeText(map.this, markerLocation.toString(), Toast.LENGTH_LONG).show();
                    Splash.lat = markerLocation.latitude;
                    Splash.lng = markerLocation.longitude;
                }

                @Override
                public void onMarkerDragStart(Marker arg0) {
                    // TODO Auto-generated method stub
                    Log.d("Marker", "Started");

                }
            });
        }else{
            Toast toast = Toast.makeText(this, "Please turn on GPS.", Toast.LENGTH_LONG);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(18);
            toast.show();
        }
    }
}