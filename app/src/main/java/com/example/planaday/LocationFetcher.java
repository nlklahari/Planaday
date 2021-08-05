package com.example.planaday;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationFetcher {
    public static final int LOCATION_PERMISSION_CODE = 1;
    private static final String KEY_LOCATION = "location";
    private static final String TAG = LocationFetcher.class.getSimpleName();

    private Context context;
    private Activity activity;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    public LocationFetcher(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        fetchLocation();
    }

    /**
     * Fetches the user's current location if user allows access to location
     */
    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.d(TAG, "Location successfully accessed");

                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.d(TAG, "location was not null");
                                Log.d(TAG, "latitude: " + location.getLatitude());
                                lastKnownLocation = location;
                            } else {
                                Log.e(TAG, "location was null");
                            }
                        }
                    }).addOnFailureListener(activity, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Location access failed");
                }
            });
        } else if (activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            new AlertDialog.Builder(activity)
                    .setTitle("Permission needed")
                    .setMessage("Location permission needed to calculate distance from your location to events in your plans.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "onClick ok in alert dialog");
                            ActivityCompat.requestPermissions(activity, new String[]
                                            {Manifest.permission.ACCESS_COARSE_LOCATION},
                                    LOCATION_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "onClick cancel in alert dialog");
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            Log.i(TAG, "Show permissions to accept");
            ActivityCompat.requestPermissions(activity, new String[]
                            {Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_CODE);
        }
    }


    /**
     * Retrieve location and camera position from saved instance state.
     */
    public Location getLocation(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }
        return lastKnownLocation;
    }
}
