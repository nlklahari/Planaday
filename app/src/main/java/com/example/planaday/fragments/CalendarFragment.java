package com.example.planaday.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.planaday.R;
import com.example.planaday.adapters.CalendarPlansAdapter;
import com.example.planaday.models.Plan;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    private static final String TAG = CalendarFragment.class.getSimpleName();

    private CalendarView calendar;

    private RecyclerView rvTodayPlans;
    private List<Plan> todayPlans;
    private CalendarPlansAdapter adapter;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendar = view.findViewById(R.id.calendar);

        rvTodayPlans = view.findViewById(R.id.rvTodayPlans);
        todayPlans = new ArrayList<>();
        adapter = new CalendarPlansAdapter(getContext(), getActivity(), todayPlans);

        Log.d(TAG, "entered onViewCreated");

        rvTodayPlans.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTodayPlans.setLayoutManager(layoutManager);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                queryPlans();
            }
        });
    }

    private void queryPlans() {
        Log.i(TAG, "Querying posts");
        ParseQuery<Plan> query = ParseQuery.getQuery(Plan.class);
        query.include(Plan.KEY_AUTHOR);
        query.whereEqualTo(Plan.KEY_AUTHOR, ParseUser.getCurrentUser());
        Calendar calendarStandard = Calendar.getInstance();
        calendarStandard.setTimeInMillis(calendar.getDate());
        Date date = calendarStandard.getTime();



        query.whereEqualTo(Plan.KEY_DATE, date);
        Log.d(TAG, "date " + date);
        query.setLimit(20);
        query.addDescendingOrder("date"); // TODO: fix order of results
        query.findInBackground(new FindCallback<Plan>() {
            @Override
            public void done(List<Plan> plans, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "issue with getting plans");
                    return;
                }

                if (plans.isEmpty()) {
                    Toast.makeText(getContext(), "No plans for today", Toast.LENGTH_SHORT).show();
                }

                for (Plan p: plans) {
                    Log.d(TAG, p.getPlanName());
                }
                todayPlans.addAll(plans);
                adapter.notifyDataSetChanged();
            }
        });
    }

}