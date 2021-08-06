package com.example.planaday.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.planaday.R;
import com.example.planaday.adapters.PlanDetailsAdapter;
import com.example.planaday.models.Plan;
import com.example.planaday.models.PlanadayEvent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class PlanDetailsActivity extends AppCompatActivity {

    private static final String TAG = "PlanDetailsActivity";
    private TextView tvPlanName;
    private TextView tvPlanDate;
    private TextView tvPlanTime;
    private TextView tvPlanDuration;

    private RecyclerView rvEvents;
    private PlanDetailsAdapter adapter;

    private List<PlanadayEvent> selectedEvents;

    private FloatingActionButton fabShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);

        tvPlanName = findViewById(R.id.tvPlanName);
        tvPlanDate = findViewById(R.id.tvPlanDate);
        tvPlanTime = findViewById(R.id.tvPlanTime);
        tvPlanDuration = findViewById(R.id.tvPlanDuration);
        fabShare = findViewById(R.id.fabShare);

        rvEvents = findViewById(R.id.rvEvents);

        Plan plan = getIntent().getExtras().getParcelable("plan");

        tvPlanName.setText(plan.getPlanName());
        tvPlanDate.setText(plan.getPlanDateString());
        tvPlanTime.setText(plan.getStringTime());
        tvPlanDuration.setText(plan.getDuration() + " hrs");

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String planDetailsToSend = plan.getPlanName()
                        + " on " + plan.getPlanDateString() + " from " + plan.getStringTime() + " \nEvents: ";
                List<PlanadayEvent> events;
                try {
                    events = plan.getEvents();
                    for (int i = 0; i < events.size(); i++) {
                        planDetailsToSend += events.get(i).getEventName() +", ";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TITLE, plan.getUser().getUsername() + " is sharing a plan created using Planday! ");
                sendIntent.putExtra(Intent.EXTRA_TEXT, planDetailsToSend.substring(0,planDetailsToSend.length() - 1));
                sendIntent.setType("text/plain");


                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        selectedEvents = new ArrayList<>();

        try {
            selectedEvents.addAll(plan.getEvents());
        } catch (JSONException e) {
            Log.e(TAG, "Error getting selected events for plan " + e);
        }

        adapter = new PlanDetailsAdapter(this, selectedEvents);
        rvEvents.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvEvents.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}