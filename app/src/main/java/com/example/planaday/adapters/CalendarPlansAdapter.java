package com.example.planaday.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planaday.R;
import com.example.planaday.activities.PlanDetailsActivity;
import com.example.planaday.models.Plan;

import java.util.List;


public class CalendarPlansAdapter extends SavedPlansAdapter {
    public CalendarPlansAdapter(Context context, FragmentActivity parentActivity, List<Plan> savedPlans) {
        super(context, parentActivity, savedPlans);
    }

//    private Context context;
//    private List<Plan> todayPlans;
//
//    public CalendarPlansAdapter(Context context, List<Plan> todayPlans) {
//        this.context = context;
//        this.todayPlans = todayPlans;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return todayPlans.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView tvPlanName;
//        private TextView tvPlanTime;
//        private TextView tvPlanPrice;
//        private TextView tvPlanDuration;
//        private TextView tvPlanDate;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            tvPlanName = itemView.findViewById(R.id.tvPlanName);
//            tvPlanTime = itemView.findViewById(R.id.tvPlanTime);
//            tvPlanPrice = itemView.findViewById(R.id.tvPlanPrice);
//            tvPlanDuration = itemView.findViewById(R.id.tvPlanDuration);
//            tvPlanDate = itemView.findViewById(R.id.tvPlanDate);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, PlanDetailsActivity.class);
//                    intent.putExtra("plan", todayPlans.get(getAbsoluteAdapterPosition()));
//                    context.startActivity(intent);
//                }
//            });
//        }
//
//        /**
//         * @param plan
//         */
//        public void bind(Plan plan) {
//            Log.i("SavedPlansAdapter", "Binding entered");
//            tvPlanName.setText(plan.getPlanName());
//            tvPlanTime.setText(plan.getStringTime());
//            tvPlanPrice.setText("$" + plan.getPrice());
//            tvPlanDuration.setText(plan.getDuration() + " hrs");
//            tvPlanDate.setText(plan.getPlanDate().toString());
//        }
//    }
}
