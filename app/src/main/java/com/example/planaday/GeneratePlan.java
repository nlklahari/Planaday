package com.example.planaday;

import com.example.planaday.models.PlanadayEvent;
import com.example.planaday.networking.BoredAPIRequests;

public class GeneratePlan /*implements BoredAPIListener*/ {
    private PlanadayEvent[] events;

    public GeneratePlan(String setting) {
        if (setting.equals("group")) {
            groupEvents();
        }
    }

    private void groupEvents() {
        for (int i = 2; i < 6; i++) {
            BoredAPIRequests.getEventParticipants(i /*, this */);
        }
    }

    // listener
    /* done() */

}
