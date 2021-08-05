package com.example.planaday.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.planaday.LocationFetcher;
import com.example.planaday.R;
import com.example.planaday.fragments.CreatePlanFragment;
import com.example.planaday.fragments.ExploreFragment;
import com.example.planaday.fragments.ProfileFragment;
import com.example.planaday.fragments.SavedPlansFragment;
import com.example.planaday.fragments.CalendarFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private BottomAppBar bottomAppBar;
    private FloatingActionButton fab;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.bottomAppBar);
        fab = findViewById(R.id.fabCreatePlan);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        launchFragment(new SavedPlansFragment());

        // Tab Layout Listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                int pos = tab.getPosition();
                switch (pos) {
                    case 0:
                    default:
                        fragment = new SavedPlansFragment();
                        break;
                    case 1:
                        fragment = new ExploreFragment();
                        break;

                }
                launchFragment(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Create plan Floating Action Button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFragment(new CreatePlanFragment());
            }
        });

        // Bottom App Bar - navigation
        bottomAppBar.setNavigationOnClickListener(v -> launchFragment(new ProfileFragment()));

        // Bottom App Bar - menu items
        bottomAppBar.setOnMenuItemClickListener(item -> {
            launchFragment(new CalendarFragment());
            return true;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LocationFetcher.LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "permission to background location granted");
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "permission to background location denied");
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Replaces frame layot container with given Fragment
     * @param fragment
     */
    private void launchFragment(Fragment fragment) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.flContainer, fragment).addToBackStack(null).commit();
    }

    public void setAppBarsVisibility(int visibility) {
        bottomAppBar.setVisibility(visibility);
        fab.setVisibility(visibility);
        tabLayout.setVisibility(visibility);
    }
}