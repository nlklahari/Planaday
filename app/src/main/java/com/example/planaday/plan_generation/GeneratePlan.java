package com.example.planaday.plan_generation;

import android.app.TimePickerDialog;
import android.util.Log;

import com.example.planaday.fragments.widgets.TimePickerFragment;
import com.example.planaday.models.Plan;
import com.example.planaday.models.PlanadayEvent;
import com.example.planaday.networking.googlePlacesAPI.GooglePlacesAPIRequests;
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

    private String environment;
    private String setting;

    /**
     * Generates a new plan with current user preferences
     * @param listener to identify when all api calls are completed
     * @param setting of the plan (i.e individual or group)
     */
    public GeneratePlan(APIRequestsCompleteListener listener,
                        String planName, String planDate,
                        String planStartTime, String planEndTime,
                        int maxDistance, String currentLocation, List<String> keywords,
                        String environment, String setting) {

        validEvents = new ArrayList<>();
        apiCallComplete = new HashMap<>();
        this.plan = new Plan();
        this.listener = listener;
        this.environment = environment;
        this.setting = setting;

        plan.setPlanName(planName);
        plan.setDuration(TimePickerFragment.getDuration(planStartTime, planEndTime));
        plan.setPlanDateString(planDate);
        plan.setStringTime(planStartTime + " - " + planEndTime);

        if (setting.equals("group")) {
            Log.i(TAG, "Group events selected");
            groupEvents();
        } else {
            Log.i(TAG, "Individual events selected");
            individualEvents();
        }
        locationBasedEvents(maxDistance, currentLocation, keywords, setting);
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
    }

    /**
     * Get events from Google Places API using location bias
     * @param maxDistance
     * @param currentLocation
     * @param keywords
     */
    private void locationBasedEvents(int maxDistance, String currentLocation, List<String> keywords, String setting) {
        for (int i = 0 ; i < keywords.size(); i++) {
            Log.d(TAG, "entered location based events method");
            String key = "locationBasedEvents" + i;
            apiCallComplete.put(key, false);
            GooglePlacesAPIRequests.getPlaces(key, keywords.get(i), maxDistance, currentLocation, this, setting);
        }
    }

    @Override
    public void onComplete(String key, List<PlanadayEvent> planadayEvents) {
        if (planadayEvents != null) {
            validEvents.addAll(planadayEvents);
        }
        apiCallComplete.replace(key, true);
        List<PlanadayEvent> selectedEvents = new ArrayList<>();
        if (!apiCallComplete.containsValue(false)) {
            int totalDuration = 0;
            for (int i = 0; i < validEvents.size(); i++) {
                // parse through events list and create a plan
                if (validEvents.get(i).getEnvironment().equals(environment) && validEvents.get(i).getSetting().equals(setting)) {
                    Log.d(TAG, "entered outdoor + individual");
                } else {
                    Log.d(TAG, "name of event: " + validEvents.get(i).getEventName() + " setting: " + validEvents.get(i).getSetting() + " environment: " + validEvents.get(i).getEnvironment());
                }
                if (plan.getDuration() > totalDuration && validEvents.get(i).getEnvironment().equals(environment) && validEvents.get(i).getSetting().equals(setting)) {
                    Log.i("GeneratePlan", "Adding events and duration: " + totalDuration);
                    selectedEvents.add(validEvents.get(i));
                    totalDuration += validEvents.get(i).getDuration();
                }
            }
            plan.setEvents(selectedEvents);
            listener.onCompleteAllRequests();
        }
    }

    /**
     *
     * @return
     */
    public Plan getPlan() {
        return plan;
    }

}
