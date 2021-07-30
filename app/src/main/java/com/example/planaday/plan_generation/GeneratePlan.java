package com.example.planaday.plan_generation;

import android.util.Log;

import com.example.planaday.models.Plan;
import com.example.planaday.models.PlanadayEvent;
import com.example.planaday.networking.listeners.APIRequestResponseListener;
import com.example.planaday.networking.listeners.APIRequestsCompleteListener;
import com.example.planaday.networking.boredAPI.BoredAPIRequests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratePlan implements APIRequestResponseListener {
    private static final String TAG = GeneratePlan.class.getSimpleName();
    /**
     * List of events that fit user preferences
     */
    private List<PlanadayEvent> validEvents;

    /**
     * Maps every call to an endpoint to a boolean value indicating completion status of api call
     */
    Map<String, Boolean> apiCallComplete;

    /**
     * Generated plan for current user preferences
     */
    private Plan plan;

    /**
     * Listener to identify when all api calls are completed
     */
    private APIRequestsCompleteListener listener;

    /**
     * Generates a new plan with current user preferences
     * @param listener to identify when all api calls are completed
     * @param setting of the plan (i.e individual or group)
     */
    public GeneratePlan(APIRequestsCompleteListener listener,
                        String planName, String planDate,
                        String planStartTime, String planEndTime,  String setting) {

        validEvents = new ArrayList<>();
        apiCallComplete = new HashMap<>();
        this.plan = new Plan();
        this.listener = listener;

        plan.setPlanName(planName);
        plan.setDuration(5);
        plan.setPlanDateString(planDate);

        if (setting.equals("group")) {
            Log.i(TAG, "Group events selected");
            groupEvents();
        } else {
            Log.i(TAG, "Individual events selected");
            individualEvents();
        }
    }

    /**
     * Gets events that are in a group setting
     */
    private void groupEvents() {
        for (int i = 2; i < 6; i++) {
            String key = "groupEvents" + i;
            apiCallComplete.put(key, false);
            BoredAPIRequests.getEventParticipants(key, i, this);

        }
    }

    /**
     * Gets events that are in an individual setting
     */
    private void individualEvents() {
        String key = "individualEvents";
        apiCallComplete.put(key, false);
        BoredAPIRequests.getEventParticipants(key, 1, this);
        // TODO: Make call to GoogleAPI
    }

    @Override
    public void onComplete(String key, List<PlanadayEvent> planadayEvents) {
        validEvents.addAll(planadayEvents);
        apiCallComplete.replace(key, true);
        List<PlanadayEvent> selectedEvents = new ArrayList<>();
        if (!apiCallComplete.containsValue(false)) {
            int totalDuration = 0;
            for (int i = 0; i < validEvents.size(); i++) {
                // parse through events list and create a plan
                if (plan.getDuration() < totalDuration) {
                    Log.i("GeneratePlan", "Adding events and duration: " + totalDuration);
                    selectedEvents.add(validEvents.get(i));
                }
                totalDuration += validEvents.get(i).getDuration();
            }
            plan.setEvents(selectedEvents);
            listener.onComplete();
        }
    }

    public Plan getPlan() {
        return plan;
    }

}
