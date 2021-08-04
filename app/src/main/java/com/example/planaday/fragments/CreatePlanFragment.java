package com.example.planaday.fragments;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.planaday.LocationFetch;
import com.example.planaday.plan_generation.GeneratePlan;
import com.example.planaday.R;
import com.example.planaday.activities.PlanDetailsActivity;
import com.example.planaday.fragments.widgets.DatePickerFragment;
import com.example.planaday.fragments.widgets.TimePickerFragment;
import com.example.planaday.models.Plan;
import com.example.planaday.networking.listeners.APIRequestsCompleteListener;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.tabs.TabLayout;

import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.hootsuite.nachos.validator.ChipifyingNachoValidator;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePlanFragment extends Fragment implements APIRequestsCompleteListener {
    private static final String TAG = CreatePlanFragment.class.getSimpleName();

    private GeneratePlan planGenerator;
    private Plan plan;

    private RelativeLayout rlMainContent;

    private EditText etPlanName;
    private TextView tvDateField;
    private TextView tvSelectedDate;
    public TextView tvStartTimeField;
    public TextView tvSelectedStartTime;
    public TextView tvEndTimeField;
    public TextView tvSelectedEndTime;

    private Switch switchEnvironment;
    private String environmentSelected;

    private Switch switchSetting;
    private String settingSelected;

    private RangeSlider rsDistance;
    private int selectedDistance;

    private NachoTextView ntvKeywords;
    private List<String> keywordsSelected;

    private TextView tvAdvancedPreferences;
    private Button btnFinish;
    private Button btnCancel;

    private LottieAnimationView animLoading;

    private TabLayout tabLayout;
    private BottomAppBar navBar;
    private FloatingActionButton fabCreatePlan;

    private Location lastKnownLocation;

    public CreatePlanFragment() {
        // Required empty public constructor
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

        LocationFetch locationFetch = new LocationFetch(getContext(), getActivity());
        lastKnownLocation = locationFetch.getLocation(savedInstanceState);
        if (lastKnownLocation != null) {
            Log.i(TAG, "latitude: " + lastKnownLocation.getLatitude());
        }

        setupFieldsByID(view);
        fieldsSetOnClickListener();
        buttonSetOnClickListeners();
    }

    /**
     * Handle back button and cancel button actions
     */
    private void handleOnExitFragment() {
        tabLayout.setVisibility(View.VISIBLE);
        navBar.performShow();
        fabCreatePlan.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handleOnExitFragment();
    }

    /**
     * Sets the onClick listeners for all fields in the Create Plan Screen
     */
    private void fieldsSetOnClickListener() {
        // Date field
        tvDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment(new OnSuccessListener<DatePickerFragment>() {
                    @Override
                    public void onSuccess(DatePickerFragment datePickerFragment) {
                        datePickerFragment.setTVField(tvSelectedDate);
                    }
                });
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        // Start time field
        tvStartTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment newFragment = new TimePickerFragment(tvSelectedStartTime);
                newFragment.getTime();
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

        // Advanced Preferences
        tvAdvancedPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * Sets the onClick listeners for all buttons in the Create Plan Screen
     */
    private void buttonSetOnClickListeners() {
        // Finish Button - Launches new plan details activity screen
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywordsSelected = ntvKeywords.getChipValues();
                if (verifyRequiredFieldsInput()) {
                    rlMainContent.setVisibility(View.INVISIBLE);
                    animLoading.setVisibility(View.VISIBLE);
                    animLoading.playAnimation();

                    String currentLocation = "47,-122";

                    planGenerator = new GeneratePlan( CreatePlanFragment.this,
                            etPlanName.getText().toString(), tvSelectedDate.getText().toString(),
                            tvSelectedStartTime.getText().toString(), tvSelectedEndTime.getText().toString(),
                            selectedDistance*1609, currentLocation, keywordsSelected,
                            environmentSelected, settingSelected);
                }
            }
        });

        // Cancel Button - Returns to main activity screen
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    /**
     * Verifies that user input for all required fields is entered
     */
    private boolean verifyRequiredFieldsInput() {
        if (etPlanName.getText().toString().isEmpty() /** || tvSelectedDate.getText().toString().isEmpty()
        || tvSelectedStartTime.getText().toString().isEmpty() || tvSelectedEndTime.getText().toString().isEmpty() **/) {
            Toast.makeText(getActivity(), "Missing input, please check and try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Launches new activity with generated plan and its details
     * @param plan
     */
    private void launchPlanDetailsActivity(Plan plan) {
        Intent intent = new Intent(getActivity(), PlanDetailsActivity.class);
        intent.putExtra("plan", plan);
        getContext().startActivity(intent);
        getActivity().finish();
    }

    /**
     * Listener's callback to get and save generated plan
     */
    @Override
    public void onCompleteAllRequests() {
        plan = planGenerator.getPlan();
        plan.setUser(ParseUser.getCurrentUser());
        plan.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                } else {
                    Log.i(TAG, "Plan save was successful");
                }
            }
        });
        launchPlanDetailsActivity(plan);
    }

    /**
     * Finds and sets all screen components on this screen
     * @param view
     */
    private void setupFieldsByID(View view) {
        tabLayout = getActivity().findViewById(R.id.tabLayout);
        navBar = getActivity().findViewById(R.id.bottomAppBar);
        fabCreatePlan = getActivity().findViewById(R.id.fabCreatePlan);

        // Hide tab layout, bottom app bar, and floating action button
        tabLayout.setVisibility(View.GONE);
        navBar.performHide();
        fabCreatePlan.hide();

        rlMainContent = view.findViewById(R.id.rlMainContent);

        etPlanName = view.findViewById(R.id.etPlanName);

        // Date Picker
        tvDateField = view.findViewById(R.id.tvDateField);
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate);

        // Start Time Picker
        tvStartTimeField = view.findViewById(R.id.tvStartTimeField);
        tvSelectedStartTime = view.findViewById(R.id.tvSelectedStartTime);

        // End Time Picker
        tvEndTimeField = view.findViewById(R.id.tvEndTimeField);
        tvSelectedEndTime = view.findViewById(R.id.tvSelectedEndTime);

        // Setting Switch
        settingSwitchResult(view);
        // Environment Switch
        environmentSwitchResult(view);
        // Distance Range Slider
        distanceRangeSliderResult(view);
        // Keywords Selection
        keywordsNTVResult(view);

        tvAdvancedPreferences = view.findViewById(R.id.tvAdvancedPreferences);
        btnFinish = view.findViewById(R.id.btnFinish);
        btnCancel = view.findViewById(R.id.btnCancel);

        // Loading animation
        animLoading = view.findViewById(R.id.animLoading);
    }

    /**
     *
     * @param view
     */
    private void keywordsNTVResult(View view) {
        ntvKeywords = view.findViewById(R.id.ntvKeywords);
        String[] suggestions = new String[]{"education", "park", "sports", "roller coaster", "hiking", "mountain"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, suggestions);
        ntvKeywords.addChipTerminator(';', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
        ntvKeywords.setAdapter(adapter);

        // Set Illegal Characters
        ntvKeywords.setIllegalCharacters('$', '#', '1', '2', '3', '4', '5', '6', '7', '8', '9', '@', '%', '^', '&', '*');
        ntvKeywords.setNachoValidator(new ChipifyingNachoValidator());
    }

    /**
     * Finds and gets the result from the Setting Switch
     * @param view
     */
    private void settingSwitchResult(View view) {
        switchSetting = view.findViewById(R.id.switchSetting);
        settingSelected = switchSetting.getTextOff().toString();
        switchSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    settingSelected = switchSetting.getTextOn().toString();
                } else {
                    settingSelected = switchSetting.getTextOff().toString();
                }
            }
        });
    }

    /**
     * Finds and gets the result from the Setting Switch
     * @param view
     */
    private void environmentSwitchResult(View view) {
        switchEnvironment = view.findViewById(R.id.switchEnvironment);
        environmentSelected = switchEnvironment.getTextOff().toString();
        switchEnvironment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    environmentSelected = switchEnvironment.getTextOn().toString();
                } else {
                    environmentSelected = switchEnvironment.getTextOff().toString();
                }
            }
        });
    }

    /**
     * Finds and gets the result from the Range Slider for the distance
     * @param view
     */
    private void distanceRangeSliderResult(View view) {
        rsDistance = view.findViewById(R.id.rsDistance);
        selectedDistance = 0;
        rsDistance.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                selectedDistance = (int) (value);
            }
        });
    }


}