package com.example.planaday.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.planaday.R;
import com.example.planaday.callbacks.SwipeToDeleteCallback;
import com.example.planaday.adapters.SavedPlansAdapter;
import com.example.planaday.fragments.widgets.DatePickerFragment;
import com.example.planaday.models.Plan;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedPlansFragment extends Fragment {

    private static final String TAG = SavedPlansFragment.class.getSimpleName();

    private RecyclerView rvSavedPlans;
    protected List<Plan> savedPlans;
    private SavedPlansAdapter adapter;

    private TextView tvDisplayTextWhenNoSavedPlans;

    private FloatingActionButton fabCreatePlan;

    public SavedPlansFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_plans, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fabCreatePlan = getActivity().findViewById(R.id.fabCreatePlan);

        rvSavedPlans = view.findViewById(R.id.rvSavedPlans);
        savedPlans = new ArrayList<>();
        adapter = new SavedPlansAdapter(getContext(), getActivity(), savedPlans);

        tvDisplayTextWhenNoSavedPlans = view.findViewById(R.id.tvDisplayTextWhenNoSavedPlans);
        tvDisplayTextWhenNoSavedPlans.setVisibility(View.GONE);

        rvSavedPlans.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvSavedPlans.setLayoutManager(layoutManager);

        setUpRecyclerView();
        queryPlans();
    }

    /**
     *
     */
    private void queryPlans() {
        Log.i(TAG, "Querying posts");
        SimpleDateFormat formatter = new SimpleDateFormat(DatePickerFragment.DATE_FORMAT);
        Date currentDate = Calendar.getInstance().getTime();
        String currentDateString = formatter.format(currentDate);
        ParseQuery<Plan> query = ParseQuery.getQuery(Plan.class);
        query.include(Plan.KEY_AUTHOR);
        query.whereEqualTo(Plan.KEY_AUTHOR, ParseUser.getCurrentUser());
        // query.whereGreaterThanOrEqualTo("dateString", currentDateString);
        query.setLimit(20);
        query.addAscendingOrder("dateString");
        query.findInBackground(new FindCallback<Plan>() {
            @Override
            public void done(List<Plan> plans, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "issue with getting plans");
                    return;
                }
                savedPlans.addAll(plans);
                adapter.notifyDataSetChanged();
                if (savedPlans.isEmpty()) {
                    tvDisplayTextWhenNoSavedPlans.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     *
     */
    private void setUpRecyclerView() {
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(rvSavedPlans);
    }
}