package com.example.planaday.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planaday.R;
import com.example.planaday.activities.PlanDetailsActivity;
import com.example.planaday.fragments.widgets.DatePickerFragment;
import com.example.planaday.fragments.widgets.TimePickerFragment;
import com.example.planaday.models.Plan;
import com.example.planaday.networking.APIClients;
import com.example.planaday.networking.BoredAPIRequests;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePlanFragment extends Fragment {

    private EditText etPlanName;
    private TextView tvDateField;
    private TextView tvSelectedDate;
    public TextView tvStartTimeField;
    public TextView tvSelectedStartTime;
    public TextView tvEndTimeField;
    public TextView tvSelectedEndTime;
    private Switch switchEnvironment;
    BottomNavigationView navBar;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.INVISIBLE);

        etPlanName = view.findViewById(R.id.etPlanName);
        tvDateField = view.findViewById(R.id.tvDateField);
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate);
        tvStartTimeField = view.findViewById(R.id.tvStartTimeField);
        tvSelectedStartTime = view.findViewById(R.id.tvSelectedStartTime);
        tvEndTimeField = view.findViewById(R.id.tvEndTimeField);
        tvSelectedEndTime = view.findViewById(R.id.tvSelectedEndTime);
        switchEnvironment = view.findViewById(R.id.switchEnvironment);

        tvAdvancedPreferences = view.findViewById(R.id.tvAdvancedPreferences);
        btnFinish = view.findViewById(R.id.btnFinish);
        btnCancel = view.findViewById(R.id.btnCancel);

        APIClients client = new APIClients();
        fieldsSetOnClickListener();

        // TODO: Verify all user inputs are valid (i.e start time < end time)

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
                if (verifyRequiredFieldsInput()) {
//                    int startHour = Integer.parseInt(tvSelectedStartTime.getText().toString().substring(0,2));
//                    int startMinute = Integer.parseInt(tvSelectedStartTime.getText().toString().substring(2));
//                    int endHour = Integer.parseInt(tvSelectedEndTime.getText().toString().substring(0,2));
//                    int endMinute = Integer.parseInt(tvSelectedEndTime.getText().toString().substring(2));

                    // Fix code to get time difference
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    long difference = 0;
                    try {
                        Time time = new Time((format.parse(tvSelectedStartTime.toString())).getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    Log.i("duration", ""+difference);

                    BoredAPIRequests.getEventParticipants(1); // remove
                    launchPlanDetailsActivity();
                }
                // TODO-----------------------------
                // Algorithm to retrieve correct plan
                // Upload that plan to Parse
            }
        });

        // Cancel Button - Returns to MainActivity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnExitFragment();
            }
        });

        // Back button press
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handleOnExitFragment();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

    /**
     * Handle back button and cancel button actions
     */
    private void handleOnExitFragment() {
        getParentFragmentManager().beginTransaction().remove(CreatePlanFragment.this).commit();
        navBar.setVisibility(View.VISIBLE);
    }

    /**
     * Sets the onClick listeners for all fields in the Create Plan Screen
     */
    private void fieldsSetOnClickListener() {
        // Date field
        tvDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment(tvSelectedDate);
                newFragment.show(getChildFragmentManager(), "datePicker");
                // TODO: check if date is after today
            }
        });

        // Start time field
        tvStartTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment newFragment = new TimePickerFragment(tvSelectedStartTime);
                newFragment.show(getChildFragmentManager(), "startTimePicker");
                // TODO: check if start time is after current time and end time > start time
            }
        });

        // End time field
        tvEndTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment(tvSelectedEndTime);
                newFragment.show(getChildFragmentManager(), "endTimePicker");
            }
        });
    }

    /**
     * Verifies that user inputs are valid
     */
    private void verifyValidUserInput() {

    }

    /**
     * Verifies that user input for all required fields is entered
     */
    private boolean verifyRequiredFieldsInput() {
        if (etPlanName.getText().toString().isEmpty() || tvSelectedDate.getText().toString().isEmpty()
        || tvSelectedStartTime.getText().toString().isEmpty() || tvSelectedEndTime.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Missing input, please check and try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void launchPlanDetailsActivity() {
        Plan plan = new Plan();
        plan.setPlanName(etPlanName.getText().toString());
        Intent intent = new Intent(getActivity(), PlanDetailsActivity.class);
        intent.putExtra("plan", plan);
        getContext().startActivity(intent);
        getActivity().finish();
    }

}