package com.example.planaday.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.planaday.R;
import com.example.planaday.adapters.PlanDetailsAdapter;
import com.example.planaday.models.Plan;
import com.example.planaday.models.PlanadayEvent;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class PlanDetailsActivity extends AppCompatActivity {

    private static final String TAG = "PlanDetailsActivity";
    private TextView tvPlanName;
    private TextView tvPlanDate;
    private TextView tvPlanTime;
    private TextView tvPlanPrice;
    private TextView tvPlanDuration;

    private RecyclerView rvEvents;
    private PlanDetailsAdapter adapter;

    private List<PlanadayEvent> selectedEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);

        tvPlanName = findViewById(R.id.tvPlanName);
        tvPlanDate = findViewById(R.id.tvPlanDate);
        tvPlanTime = findViewById(R.id.tvPlanTime);
        tvPlanPrice = findViewById(R.id.tvPlanPrice);
        tvPlanDuration = findViewById(R.id.tvPlanDuration);

        rvEvents = findViewById(R.id.rvEvents);

        Plan plan = getIntent().getExtras().getParcelable("plan");

        tvPlanName.setText(plan.getPlanName());
        tvPlanDate.setText(plan.getPlanDateString());
        // plan time
        tvPlanPrice.setText("$ " + plan.getPrice());
        tvPlanDuration.setText(plan.getDuration() + " hrs");

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