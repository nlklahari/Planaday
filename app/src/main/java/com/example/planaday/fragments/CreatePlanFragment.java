package com.example.planaday.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.planaday.R;
import com.example.planaday.activities.PlanDetailsActivity;
import com.example.planaday.fragments.widgets.DatePickerFragment;
import com.example.planaday.fragments.widgets.TimePickerFragment;
import com.example.planaday.models.Plan;
import com.example.planaday.networking.APIClients;
import com.example.planaday.networking.BoredAPIRequests;

import org.jetbrains.annotations.NotNull;

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

    private TextView tvAdvancedPreferences;
    private Button btnFinish;
    private Button btnCancel;

    public CreatePlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getParentFragmentManager().beginTransaction().remove(CreatePlanFragment.this).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
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

        // Date field
        tvDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment(tvSelectedDate);
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        // Start time field
        tvStartTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment newFragment = new TimePickerFragment(tvSelectedStartTime);
                newFragment.show(getChildFragmentManager(), "startTimePicker");
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

        // TODO: Verify that time picker values are valid

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
                // TODO: verification to ensure that user entered all required preferences
                BoredAPIRequests.getEventParticipants(1);
                Plan plan = new Plan();
                Intent intent = new Intent(getActivity(), PlanDetailsActivity.class);
                intent.putExtra("plan", plan);
                getContext().startActivity(intent);
                // TODO-----------------------------
                // Algorithm to retrieve correct plan
                // Upload that plan to Parse
            }
        });

        // Cancel Button - Returns to MainActivity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().remove(CreatePlanFragment.this).commit();
            }
        });
    }

//    public static class StartTimePickerFragment extends TimePickerFragment {
//
//        private TextView tvSelectedStartTime;
//
//        @Override
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            tvSelectedStartTime = getActivity().findViewById(R.id.tvSelectedStartTime);
//            String time = "";
//            if (hourOfDay < 10) {
//                time += 0;
//            }
//            time += hourOfDay + ":";
//            if (minute < 10) {
//                time += 0;
//            }
//            time += String.valueOf(minute);
//            tvSelectedStartTime.setText(time);
//        }
//    }
//
//    public static class EndTimePickerFragment extends TimePickerFragment {
//
//        private TextView tvSelectedEndTime;
//
//        @Override
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            tvSelectedEndTime = getActivity().findViewById(R.id.tvSelectedEndTime);
//            String time = "";
//            if (hourOfDay < 10) {
//                time += 0;
//            }
//            time += hourOfDay + ":";
//            if (minute < 10) {
//                time += 0;
//            }
//            time += String.valueOf(minute);
//            tvSelectedEndTime.setText(time);
//        }
//    }

}