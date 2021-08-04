package com.example.planaday;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationFetch implements  ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int LOCATION_PERMISSION_CODE = 1;
    private static final String KEY_LOCATION = "location";
    private static final String TAG = LocationFetch.class.getSimpleName();

    private Context context;
    private Activity activity;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    public LocationFetch(Context context, Activity activity) {
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
                                lastKnownLocation = location;
                            } else {
                                Log.e(TAG, "location was null");
                            }
                        }
                    });
        } else if (activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            new AlertDialog.Builder(context)
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "permission to background location granted");
                Toast.makeText(context, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "permission to background location denied");
                Toast.makeText(context, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
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
