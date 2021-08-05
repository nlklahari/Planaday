package com.example.planaday.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planaday.R;
import com.example.planaday.models.PlanadayEvent;

import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> {

    private Context context;
    private List<PlanadayEvent> suggestedEvents;

    public ExploreAdapter(Context context, List<PlanadayEvent> suggestedEvents) {
        this.context = context;
        this.suggestedEvents = suggestedEvents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanadayEvent event = suggestedEvents.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return suggestedEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEventName;
        private TextView tvEventLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvEventLocation = itemView.findViewById(R.id.tvEventLocation);
        }

        public void bind(PlanadayEvent event) {
            tvEventName.setText(event.getEventName());
            tvEventLocation.setText(event.getLocation());
        }
    }
}
