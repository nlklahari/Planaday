package com.example.planaday.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planaday.R;
import com.example.planaday.fragments.SavedPlansFragment;
import com.example.planaday.models.Plan;
import com.example.planaday.models.PlanadayEvent;
import com.google.android.material.snackbar.Snackbar;
import com.parse.ParseQuery;

import java.util.List;

public class SavedPlansAdapter extends RecyclerView.Adapter<SavedPlansAdapter.ViewHolder> {
    private Context context;
    private List<Plan> savedPlans;
    private Plan recentlyDeletedItem;
    private int recentlyDeletePosition;

    private View view;

    public SavedPlansAdapter(Context context, List<Plan> savedPlans) {
        this.context = context;
        this.savedPlans = savedPlans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plan plan = savedPlans.get(position);
        holder.bind(plan);
    }

    @Override
    public int getItemCount() {
        return savedPlans.size();
    }

    public void deleteItem(int position) {
        recentlyDeletedItem = savedPlans.get(position);
        recentlyDeletePosition = position;
        savedPlans.remove(position);
        notifyItemRemoved(position);
        // TODO: Delete the deleted item from the database
        showUndoSnackbar();
    }

    private void showUndoSnackbar() {
        Snackbar snackbar = Snackbar.make(view, "Undo",
                Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete() {
        savedPlans.add(recentlyDeletePosition,
                recentlyDeletedItem);
        notifyItemInserted(recentlyDeletePosition);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPlanName;
        private TextView tvPlanTime;
        private TextView tvPlanPrice;
        private TextView tvPlanDuration;
        private TextView tvPlanDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPlanName = itemView.findViewById(R.id.tvPlanName);
            tvPlanTime = itemView.findViewById(R.id.tvPlanTime);
            tvPlanPrice = itemView.findViewById(R.id.tvPlanPrice);
            tvPlanDuration = itemView.findViewById(R.id.tvPlanDuration);
            tvPlanDate = itemView.findViewById(R.id.tvPlanDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: go to plan details activity
                }
            });
        }

        public void bind(Plan plan) {
            Log.i("SavedPlansAdapter", "Binding entered");
            tvPlanName.setText(plan.getPlanName());
            tvPlanTime.setText(plan.getStringTime());
            tvPlanPrice.setText("$" + plan.getPrice());
            tvPlanDuration.setText(plan.getDuration() + " hrs");
            tvPlanDate.setText(plan.getPlanDate().toString());
        }
    }
}
