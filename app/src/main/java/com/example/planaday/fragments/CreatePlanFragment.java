package com.example.planaday.fragments;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planaday.GeneratePlan;
import com.example.planaday.R;
import com.example.planaday.activities.MainActivity;
import com.example.planaday.activities.PlanDetailsActivity;
import com.example.planaday.fragments.widgets.DatePickerFragment;
import com.example.planaday.fragments.widgets.TimePickerFragment;
import com.example.planaday.models.Plan;
import com.example.planaday.networking.APIRequestsCompleteListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    private TextView tvAdvancedPreferences;
    private Button btnFinish;
    private Button btnCancel;

    private ProgressBar progressBar;

    private TabLayout tabLayout;
    private BottomAppBar navBar;
    private FloatingActionButton fabCreatePlan;

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

        tabLayout = getActivity().findViewById(R.id.tabLayout);
        navBar = getActivity().findViewById(R.id.bottomAppBar);
        fabCreatePlan = getActivity().findViewById(R.id.fabCreatePlan);

        tabLayout.setVisibility(View.GONE);
        // navBar.setVisibility(View.GONE);
        navBar.performHide();
        fabCreatePlan.hide();
        //fabCreatePlan.setVisibility(View.GONE);

        rlMainContent = view.findViewById(R.id.rlMainContent);

        etPlanName = view.findViewById(R.id.etPlanName);
        tvDateField = view.findViewById(R.id.tvDateField);
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate);
        tvStartTimeField = view.findViewById(R.id.tvStartTimeField);
        tvSelectedStartTime = view.findViewById(R.id.tvSelectedStartTime);
        tvEndTimeField = view.findViewById(R.id.tvEndTimeField);
        tvSelectedEndTime = view.findViewById(R.id.tvSelectedEndTime);

        tvAdvancedPreferences = view.findViewById(R.id.tvAdvancedPreferences);
        btnFinish = view.findViewById(R.id.btnFinish);
        btnCancel = view.findViewById(R.id.btnCancel);

        progressBar = view.findViewById(R.id.pbLoading);

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
                    rlMainContent.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    planGenerator = new GeneratePlan( CreatePlanFragment.this,"group");
                }
            }
        });



        // Cancel Button - Returns to MainActivity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    /**
     * Handle back button and cancel button actions
     */
    private void handleOnExitFragment() {
        // getParentFragmentManager().beginTransaction().remove(CreatePlanFragment.this).commit();
        tabLayout.setVisibility(View.VISIBLE);
        navBar.performShow();
        // navBar.setVisibility(View.VISIBLE);
        fabCreatePlan.show();
        //fabCreatePlan.setVisibility(View.VISIBLE);
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
    }

    private void parseTime() {
        String startTime = tvSelectedStartTime.getText().toString();

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
        if (etPlanName.getText().toString().isEmpty() /** || tvSelectedDate.getText().toString().isEmpty()
        || tvSelectedStartTime.getText().toString().isEmpty() || tvSelectedEndTime.getText().toString().isEmpty() **/) {
            Toast.makeText(getContext(), "Missing input, please check and try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     *
     * @param plan
     */
    private void launchPlanDetailsActivity(Plan plan) {
        Intent intent = new Intent(getActivity(), PlanDetailsActivity.class);
        intent.putExtra("plan", plan);
        getContext().startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onComplete() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        plan = planGenerator.getPlan();

        String name = etPlanName.getText().toString();
        plan.setPlanName(name);
        plan.setUser(ParseUser.getCurrentUser());
        plan.setDuration(5);
        plan.setPlanDate(new Date(2021,3,4));

        plan.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                } else {
                    Log.i(TAG, "Plan save was successful");
                    // should plan detials be called from in here?
                }
            }
        });
        launchPlanDetailsActivity(plan);
    }
}