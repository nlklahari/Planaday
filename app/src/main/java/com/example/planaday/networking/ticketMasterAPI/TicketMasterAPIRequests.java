package com.example.planaday.networking.ticketMasterAPI;

import android.util.Log;

import com.example.planaday.models.PlanadayEvent;
import com.example.planaday.models.TicketMasterAPIEvent.TicketMasterAPIEvent;
import com.example.planaday.networking.APIClients;
import com.example.planaday.networking.listeners.APIRequestResponseListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketMasterAPIRequests {
    private static final String TAG = TicketMasterAPIRequests.class.getSimpleName();
    private static String API_KEY = "rQsRUoEN1fAAZSICGNH2OFKBmqYDKlkz";
    private static final TicketMasterAPIInterface apiService = APIClients.getTicketMasterAPI().create(TicketMasterAPIInterface.class);
    private static List<TicketMasterAPIEvent> ticketMasterAPIEvents = new ArrayList<>();

    public static void getEvents(String key, String keyword, int maxDistance, Date date, String setting, APIRequestResponseListener listener) {
        Call<TicketMasterAPIEvent> call = apiService.getEvents(keyword, API_KEY,
                maxDistance, "miles", "en-us", date, date, "us");

        call.enqueue(new Callback<TicketMasterAPIEvent>() {
            @Override
            public void onResponse(Call<TicketMasterAPIEvent> call, Response<TicketMasterAPIEvent> response) {
                Log.d(TAG, "entered ticketmaster api requests and got a response");

                if (response.body() != null && response.body().getPage().getTotalElements() != 0) {
                    ticketMasterAPIEvents.add(response.body());
                    listener.onComplete(key, getTicketMasterAPIEvents(setting));
                    ticketMasterAPIEvents.clear();
                } else {
                    listener.onComplete(key, null);
                }
            }

            @Override
            public void onFailure(Call<TicketMasterAPIEvent> call, Throwable t) {

            }
        });
    }

    public static List<PlanadayEvent> getTicketMasterAPIEvents(String setting) {
        List<PlanadayEvent> planadayEvents = new ArrayList<>();
        for (int i = 0; i < ticketMasterAPIEvents.size(); i++) {
            TicketMasterAPIEvent current = ticketMasterAPIEvents.get(i);
            PlanadayEvent temp = new PlanadayEvent();

            // Convert the GooglePlacesAPIEvent into a PlanadayEvent
            temp.setEventName(current.getTMEvents().get(i).getName());
            temp.setDuration(1);
            temp.setSetting(setting);
            temp.setEnvironment("indoor");
            //temp.setLocation(current.getTMEvents().get(i).getVenues().get(0).getVenueName());

            // Add it to array of planadayEvents
            planadayEvents.add(temp);
        }
        return planadayEvents;
    }
}
