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

import com.daimajia.androidanimations.library.attention.PulseAnimator;
import com.daimajia.androidanimations.library.attention.RubberBandAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutLeftAnimator;
import com.daimajia.androidanimations.library.sliders.SlideInUpAnimator;
import com.example.planaday.R;
import com.example.planaday.adapters.CalendarPlansAdapter;
import com.example.planaday.fragments.widgets.DatePickerFragment;
import com.example.planaday.models.Plan;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.tabs.TabLayout;
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
public class  CalendarFragment extends Fragment {

    private static final String TAG = CalendarFragment.class.getSimpleName();

    private CalendarView calendar;

    private RecyclerView rvTodayPlans;
    private List<Plan> todayPlans;
    private CalendarPlansAdapter adapter;

    private BottomAppBar navBar;
    private TabLayout tabLayout;

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
        navBar = getActivity().findViewById(R.id.bottomAppBar);
        tabLayout = getActivity().findViewById(R.id.tabLayout);

        navBar.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);

        todayPlans = new ArrayList<>();
        adapter = new CalendarPlansAdapter(getContext(), getActivity(), todayPlans);

        rvTodayPlans.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTodayPlans.setLayoutManager(layoutManager);

        Calendar c = Calendar.getInstance();
        int year = c.get(android.icu.util.Calendar.YEAR);
        int month = c.get(android.icu.util.Calendar.MONTH);
        int day = c.get(android.icu.util.Calendar.DAY_OF_MONTH);
        Log.d(TAG, year + " " + month + " " + day);

        queryPlans(year, month, day);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                todayPlans.clear();
                adapter.notifyDataSetChanged();
                queryPlans(year, month, dayOfMonth);
            }
        });
    }

    private void queryPlans(int year, int month, int dayOfMonth) {
        Log.i(TAG, "Querying posts");
        ParseQuery<Plan> query = ParseQuery.getQuery(Plan.class);
        query.include(Plan.KEY_AUTHOR);
        query.whereEqualTo(Plan.KEY_AUTHOR, ParseUser.getCurrentUser());

        String selectedDateString = "";
        if (month < 10) {
            selectedDateString = "0";
        }
        selectedDateString += (month + 1) + "/";
        if (dayOfMonth < 10) {
            selectedDateString+= "0";
        }
        selectedDateString += dayOfMonth + "/" + year;

        query.whereEqualTo(Plan.KEY_DATE_STRING, selectedDateString);
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
                    Log.d(TAG, "showing empty plans");
                    Toast.makeText(getContext(), "No plans for today", Toast.LENGTH_SHORT).show();
                    return;
                }
                SlideInUpAnimator animator = new SlideInUpAnimator();
                animator.prepare(rvTodayPlans);
                animator.animate();
                todayPlans.addAll(plans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        navBar.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
    }
}