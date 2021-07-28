package com.example.planaday.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.planaday.R;
import com.example.planaday.activities.LoginActivity;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView tvName;
    private TextView tvLogOut;
    private TabLayout tabLayout;
    private BottomAppBar navBar;
    private FloatingActionButton fabCreatePlan;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.tvName);
        tvLogOut = view.findViewById(R.id.tvLogOut);

        tabLayout = getActivity().findViewById(R.id.tabLayout);
        navBar = getActivity().findViewById(R.id.bottomAppBar);
        fabCreatePlan = getActivity().findViewById(R.id.fabCreatePlan);

        tabLayout.setVisibility(View.GONE);
        navBar.setVisibility(View.GONE);
        fabCreatePlan.setVisibility(View.GONE);

        tvName.setText(ParseUser.getCurrentUser().get("name").toString());
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tabLayout.setVisibility(View.VISIBLE);
        navBar.setVisibility(View.VISIBLE);
        fabCreatePlan.setVisibility(View.VISIBLE);
    }
}