package com.example.planaday.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

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