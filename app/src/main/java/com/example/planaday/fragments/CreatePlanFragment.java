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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.planaday.LocationFetcher;
import com.example.planaday.activities.MainActivity;
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
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.hootsuite.nachos.validator.ChipifyingNachoValidator;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
    private EditText etDate;
    private EditText etStartTime;
    private EditText etEndTime;

    private Switch switchEnvironment;
    private String environmentSelected;

    private Switch switchSetting;
    private String settingSelected;

    private RangeSlider rsDistance;
    private int selectedDistance;

    private NachoTextView ntvKeywords;
    private List<String> keywordsSelected;

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
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
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

        LocationFetcher locationFetcher = new LocationFetcher(getContext(), getActivity(), new OnSuccessListener<LocationFetcher>() {
            @Override
            public void onSuccess(LocationFetcher locationFetcher) {
                lastKnownLocation = locationFetcher.getLocation();
            }
        });



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
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment(new OnSuccessListener<DatePickerFragment>() {
                    @Override
                    public void onSuccess(DatePickerFragment datePickerFragment) {
                        datePickerFragment.setTVField(etDate);
                    }
                });
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        // Start time field
        etStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment newFragment = new TimePickerFragment(new OnSuccessListener<TimePickerFragment>() {
                    @Override
                    public void onSuccess(TimePickerFragment timePickerFragment) {
                        timePickerFragment.setTVField(etStartTime);
                    }
                });
                newFragment.show(getChildFragmentManager(), "startTimePicker");
            }
        });

        // End time field
        etEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment(new OnSuccessListener<TimePickerFragment>() {
                    @Override
                    public void onSuccess(TimePickerFragment timePickerFragment) {
                        timePickerFragment.setTVField(etEndTime);
                    }
                });
                newFragment.show(getChildFragmentManager(), "endTimePicker");
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

                    String currentLocation;
                    if (lastKnownLocation == null) {
                        Log.d(TAG, "used default location");
                        currentLocation = "47.6162683,-122.0355736";
                    } else {
                        Log.d(TAG, "got location from current user's position");
                        currentLocation = lastKnownLocation.getLatitude() + "," + lastKnownLocation.getLongitude();
                    }

                    planGenerator = new GeneratePlan( CreatePlanFragment.this,
                            etPlanName.getText().toString(), etDate.getText().toString(),
                            etStartTime.getText().toString(), etEndTime.getText().toString(),
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

        rlMainContent = view.findViewById(R.id.rlMainContent);

        // Plan Name
        etPlanName = view.findViewById(R.id.etPlanName);
        // Date Picker
        etDate = view.findViewById(R.id.tvDateField);
        // Start Time Picker
        etStartTime = view.findViewById(R.id.tvStartTimeField);
        // End Time Picker
        etEndTime = view.findViewById(R.id.tvEndTimeField);
        // Setting Switch
        settingSwitchResult(view);
        // Environment Switch
        environmentSwitchResult(view);
        // Distance Range Slider
        distanceRangeSliderResult(view);
        // Keywords Selection
        keywordsNTVResult(view);

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
//        rsDistance.setLabelFormatter(new LabelFormatter() {
//            @NonNull
//            @Override
//            public String getFormattedValue(float value) {
//                return value + " mi";
//            }
//        });
        selectedDistance = 0;
        rsDistance.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                selectedDistance = (int) (value);
            }
        });
    }


}