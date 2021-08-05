package com.example.planaday.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.planaday.R;
import com.example.planaday.adapters.ExploreAdapter;
import com.example.planaday.models.Plan;
import com.example.planaday.models.PlanadayEvent;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {

    private RecyclerView rvSuggestedEvents;
    private List<PlanadayEvent> suggestedEvents;
    private ExploreAdapter adapter;

    public ExploreFragment() {
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
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSuggestedEvents = view.findViewById(R.id.rvSuggestedEvents);
        suggestedEvents = new ArrayList<>();
        adapter = new ExploreAdapter(getContext(), suggestedEvents);
        rvSuggestedEvents.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvSuggestedEvents.setLayoutManager(layoutManager);

        queryEvents();
    }

    private void queryEvents() {
        Log.i("ExploreFragment", "Querying events");
        ParseQuery<PlanadayEvent> query = ParseQuery.getQuery(PlanadayEvent.class);
        query.setLimit(20);

        query.findInBackground(new FindCallback<PlanadayEvent>() {
            @Override
            public void done(List<PlanadayEvent> suggestEvents, ParseException e) {
                if (e != null) {
                    Log.e("ExploreFragment", "issue with getting plans");
                    return;
                }
                suggestedEvents.addAll(suggestEvents);
                adapter.notifyDataSetChanged();
            }
        });
    }
}