package com.example.planaday.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.TransactionTooLargeException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.planaday.R;
import com.example.planaday.adapters.MainActivityFragmentPagerAdapter;
import com.example.planaday.fragments.CreatePlanFragment;
import com.example.planaday.fragments.ExploreFragment;
import com.example.planaday.fragments.ProfileFragment;
import com.example.planaday.fragments.SavedPlansFragment;
import com.example.planaday.fragments.widgets.CalendarFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
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

//        // Get the ViewPager and set it's PagerAdapter so that it can display items
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        viewPager.setAdapter(new MainActivityFragmentPagerAdapter(getSupportFragmentManager(),
//                MainActivity.this) {
//        });
//
//        // Give the TabLayout the ViewPager
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
//        tabLayout.setupWithViewPager(viewPager);

        Fragment fragment = new SavedPlansFragment();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.flContainer, fragment).addToBackStack(null).commit();

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
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.flContainer, fragment).addToBackStack(null).commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CreatePlanFragment();
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            }
        });

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProfileFragment();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.flContainer, fragment).addToBackStack(null).commit();
            }
        });

        bottomAppBar.setOnMenuItemClickListener(new BottomAppBar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Fragment fragment = new CalendarFragment();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.flContainer, fragment).addToBackStack(null).commit();
                return true;
            }
        });
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
                Fragment fragment = new ProfileFragment();
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.flContainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            case R.id.miCalendar:

        }
        return super.onOptionsItemSelected(item);
    }

}