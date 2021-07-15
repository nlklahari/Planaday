package com.example.planaday;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String KEY_LOCATION = "location";
    private static final int LOCATION_PERMISSION_CODE = 1;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    FloatingActionButton fabCreatePlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fabCreatePlan = findViewById(R.id.fabCreatePlan);

        // Floating Action Button to create a new plan
        fabCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLocation();
                Fragment fragment = new CreatePlanFragment();
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.flContainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * Fetches the user's current location if user allows access to location
     */
    private void fetchLocation() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.d(TAG, "Location successfully accessed");
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.d(TAG, "location was not null");
                                // Logic to handle location object
                                Double latitude = location.getLatitude();
                                Double longitude = location.getLongitude();
                            }
                        }
                    });
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Location permission needed to calculate distance from your location to events in your plans.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "onClick ok in alert dialog");
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]
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
            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                    {Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "permission to background location granted");
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "permission to background location denied");
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miProfile:
                // TODO: Replace this with appropriate profile fragment which contains a log out button
                // TODO: why is this not done in background?
                ParseUser.logOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            case R.id.miCalendar:

        }
        return super.onOptionsItemSelected(item);
    }
}