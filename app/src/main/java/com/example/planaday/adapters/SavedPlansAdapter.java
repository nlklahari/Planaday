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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.fading_exits.FadeOutLeftAnimator;
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
    private FragmentActivity parentActivity;
    private List<Plan> savedPlans;
    private Plan recentlyDeletedItem;
    private int recentlyDeletePosition;

    private View view;

    private TextView tvDisplayTextWhenNoSavedPlans;

    public SavedPlansAdapter(Context context, FragmentActivity parentActivity, List<Plan> savedPlans) {
        this.context = context;
        this.parentActivity = parentActivity;
        this.savedPlans = savedPlans;

        tvDisplayTextWhenNoSavedPlans = parentActivity.findViewById(R.id.tvDisplayTextWhenNoSavedPlans);
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
                if (savedPlans.isEmpty()) {
                    tvDisplayTextWhenNoSavedPlans.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * Snackbar to undo most recent deletion of a Plan object
     */
    private void showUndoSnackbar() {
        Snackbar snackbar = Snackbar.make(parentActivity.findViewById(android.R.id.content), "Undo",
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
        tvDisplayTextWhenNoSavedPlans.setVisibility(View.GONE);
    }

    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPlanName;
        private TextView tvPlanTime;

        private TextView tvPlanDuration;
        private TextView tvPlanDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPlanName = itemView.findViewById(R.id.tvPlanName);
            tvPlanTime = itemView.findViewById(R.id.tvPlanTime);
            tvPlanDuration = itemView.findViewById(R.id.tvPlanDuration);
            tvPlanDate = itemView.findViewById(R.id.tvPlanDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlanDetailsActivity.class);
                    intent.putExtra("plan", savedPlans.get(getAbsoluteAdapterPosition()));
                    context.startActivity(intent);
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
            tvPlanDuration.setText(plan.getDuration() + " hrs");
            tvPlanDate.setText(plan.getPlanDateString());
        }
    }
}
