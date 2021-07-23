package com.example.planaday.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.planaday.R;
import com.example.planaday.models.Plan;

public class PlanDetailsActivity extends AppCompatActivity {

    private TextView tvPlanName;
    private TextView tvPlanDate;
    private TextView tvPlanTime;
    private TextView tvPlanPrice;
    private TextView tvPlanDuration;

    private RecyclerView rvEvents;

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
        // plan date
        // plan time
        tvPlanPrice.setText("$" + plan.getPrice());
        tvPlanDuration.setText(plan.getDuration() + "hrs");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}