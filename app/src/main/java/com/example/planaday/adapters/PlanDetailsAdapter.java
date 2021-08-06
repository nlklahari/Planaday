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
import com.example.planaday.models.PlanadayEvent;

import java.util.List;

public class PlanDetailsAdapter extends RecyclerView.Adapter<PlanDetailsAdapter.ViewHolder> {

    private static final String TAG = PlanDetailsAdapter.class.getSimpleName();
    private Context context;
    private List<PlanadayEvent> events;

    public PlanDetailsAdapter(Context context, List<PlanadayEvent> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanadayEvent event = events.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvEventName;
        private TextView tvEventLocation;
        private TextView tvEventDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvEventLocation = itemView.findViewById(R.id.tvEventLocation);
            tvEventDuration = itemView.findViewById(R.id.tvEventDuration);
        }

        public void bind(PlanadayEvent event) {
            Log.i(TAG, "binding");
            tvEventName.setText(event.getEventName());
            tvEventLocation.setText(event.getLocation());
            tvEventDuration.setText(event.getDuration() + " hrs");
        }
    }
}
