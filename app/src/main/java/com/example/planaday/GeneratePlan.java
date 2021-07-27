package com.example.planaday;

import android.util.Log;

import com.example.planaday.models.Plan;
import com.example.planaday.models.PlanadayEvent;
import com.example.planaday.networking.APIRequestResponseListener;
import com.example.planaday.networking.APIRequestsCompleteListener;
import com.example.planaday.networking.BoredAPIRequests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratePlan implements APIRequestResponseListener {
    private List<PlanadayEvent> validEvents;
    Map<String, Boolean> apiCallComplete;
    private Plan plan;
    private APIRequestsCompleteListener listener;

    public GeneratePlan(APIRequestsCompleteListener listener, String setting) {
        validEvents = new ArrayList<>();
        apiCallComplete = new HashMap<>();
        this.plan = new Plan();
        this.listener = listener;
        if (setting.equals("group")) {
            groupEvents();
        }

    }

    private void groupEvents() {
        for (int i = 2; i < 6; i++) {
            String key = "groupEvents" + i;
            apiCallComplete.put(key, false);
            BoredAPIRequests.getEventParticipants(key, i, this);
        }
    }

    @Override
    public void onComplete(String key, List<PlanadayEvent> planadayEvents) {
        validEvents.addAll(planadayEvents);
        apiCallComplete.replace(key, true);
        if (!apiCallComplete.containsValue(false)) {
            int totalDuration = 0;
            for (int i = 0; i < validEvents.size(); i++) {
                // parse through events list and create a plan
                if (plan.getDuration() < totalDuration) {
                    Log.i("GeneratePlan", "Adding events");
                    plan.addEvent(validEvents.get(i));
                }
                totalDuration += validEvents.get(i).getDuration();
            }
            listener.onComplete();
        }
    }

    public Plan getPlan() {
        return plan;
    }

}
