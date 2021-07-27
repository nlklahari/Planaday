package com.example.planaday.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planaday.R;
import com.example.planaday.activities.PlanDetailsActivity;
import com.example.planaday.models.Plan;
import com.google.android.material.snackbar.Snackbar;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

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

    /**
     * Deletes the Plan from the list at the given position
     * @param position
     */
    public void deleteItem(int position) {
        recentlyDeletedItem = savedPlans.get(position);
        recentlyDeletePosition = position;
        savedPlans.remove(position);
        notifyItemRemoved(position);
        recentlyDeletedItem.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                showUndoSnackbar();
            }
        });
    }

    /**
     * Snackbar to undo most recent deletion of a Plan object
     */
    private void showUndoSnackbar() {
        Snackbar snackbar = Snackbar.make(view, "Undo",
                Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", v -> undoDelete());
        snackbar.show();
    }

    /**
     * Undoes the most recent deletion of a Plan object
     */
    private void undoDelete() {
        savedPlans.add(recentlyDeletePosition,
                recentlyDeletedItem);
        recentlyDeletedItem.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(context, "Plan restored", Toast.LENGTH_SHORT).show();
            }
        });
        notifyItemInserted(recentlyDeletePosition);
    }

    /**
     *
     */
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
                    Intent intent = new Intent(context, PlanDetailsActivity.class);
                    intent.putExtra("plan", savedPlans.get(getAbsoluteAdapterPosition()));
                    context.startActivity(intent);
                    // TODO: go to plan details activity
                }
            });
        }

        /**
         *
         * @param plan
         */
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
