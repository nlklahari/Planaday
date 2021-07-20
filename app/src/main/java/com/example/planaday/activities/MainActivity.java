package com.example.planaday.activities;

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

import com.example.planaday.R;
import com.example.planaday.fragments.CreatePlanFragment;
import com.example.planaday.fragments.ProfileFragment;
import com.example.planaday.fragments.SavedPlansFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Saved Plans Fragment
        Fragment fragment = new SavedPlansFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        transaction.commit();

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