package com.example.planaday.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.planaday.R;
import com.example.planaday.networking.APIClients;
import com.example.planaday.networking.BoredAPIRequests;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePlanFragment extends Fragment {


    private EditText etPlanName;
    private DatePicker dpPlanDate;
    private TimePicker tpPlanTime;
    private Switch switchEnvironment;

    private TextView tvAdvancedPreferences;
    private Button btnFinish;
    private Button btnCancel;

    public CreatePlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etPlanName = view.findViewById(R.id.etPlanName);
        // date picker and time picker
        switchEnvironment = view.findViewById(R.id.switchEnvironment);

        tvAdvancedPreferences = view.findViewById(R.id.tvAdvancedPreferences);
        btnFinish = view.findViewById(R.id.btnFinish);
        btnCancel = view.findViewById(R.id.btnCancel);

        APIClients client = new APIClients();


        // Advanced Preferences
        tvAdvancedPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Finish Button
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoredAPIRequests.getEventParticipants(1);
                // TODO-----------------------------
                // Algorithm to retrieve correct plan
                // Upload that plan to Parse
            }
        });

        // Cancel Button - Returns to MainActivity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}