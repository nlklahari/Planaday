package com.example.planaday.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planaday.models.Plan;
import com.example.planaday.models.PlanadayEvent;

import java.util.ArrayList;
import java.util.List;

public class PlanDetailsAdapter extends RecyclerView.Adapter<PlanDetailsAdapter.ViewHolder> {

    private List<PlanadayEvent> events;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        events = new ArrayList<>();
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanadayEvent event = events.get(position);

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind() {

        }
    }
}
