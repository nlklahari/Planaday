package com.example.planaday.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.planaday.R;
import com.example.planaday.activities.MainActivity;
import com.example.planaday.adapters.SavedPlansAdapter;
import com.example.planaday.models.Plan;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedPlansFragment extends Fragment {

    private static final String KEY_LOCATION = "location";
    private static final int LOCATION_PERMISSION_CODE = 1;
    private static final String TAG = SavedPlansFragment.class.getSimpleName();

    private RecyclerView rvSavedPlans;
    protected List<Plan> savedPlans;
    private SavedPlansAdapter adapter;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    FloatingActionButton fabCreatePlan;

    public SavedPlansFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_plans, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        final FragmentManager fragmentManager = getChildFragmentManager();

        rvSavedPlans = view.findViewById(R.id.rvSavedPlans);
        fabCreatePlan = view.findViewById(R.id.fabCreatePlan);

        // Floating Action Button to create a new plan
        fabCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLocation();
                Fragment fragment = new CreatePlanFragment();
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.flContainer_SavedPlans, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        savedPlans = new ArrayList<>();
        adapter = new SavedPlansAdapter(getContext(), savedPlans);

        rvSavedPlans.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvSavedPlans.setLayoutManager(layoutManager);
        queryPlans();

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    private void queryPlans() {
        Log.i(TAG, "Querying posts");
        ParseQuery<Plan> query = ParseQuery.getQuery(Plan.class);
        query.include(Plan.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder("date");
        query.findInBackground(new FindCallback<Plan>() {
            @Override
            public void done(List<Plan> plans, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "issue with getting plans");
                    return;
                }

                for (Plan p: plans) {
                    Log.d(TAG, p.getPlanName());
                }
                savedPlans.addAll(plans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Fetches the user's current location if user allows access to location
     */
    private void fetchLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.d(TAG, "Location successfully accessed");
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.d(TAG, "location was not null");
                                // Logic to handle location object
                                Double latitude = location.getLatitude();
                                Double longitude = location.getLongitude();
                                lastKnownLocation = location;
                            }
                        }
                    });
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("Location permission needed to calculate distance from your location to events in your plans.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "onClick ok in alert dialog");
                            ActivityCompat.requestPermissions(getActivity(), new String[]
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
            ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        getActivity().onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "permission to background location granted");
                Toast.makeText(getContext(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "permission to background location denied");
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}