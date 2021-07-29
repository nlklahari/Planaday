package com.example.planaday.networking;

import android.util.Log;

import com.example.planaday.models.BoredAPIEvent;
import com.example.planaday.models.PlanadayEvent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoredAPIRequests {

    private static final String TAG = BoredAPIRequests.class.getSimpleName();
    private static final BoredAPIInterface apiService = APIClients.getBoredAPIClient().create(BoredAPIInterface.class);
    private static List<BoredAPIEvent> boredAPIEvents = new ArrayList<>();

    /**
     *  Get a random activity based on the number of participants
     * @param participants
     */
    public static void getEventParticipants(String key, int participants, APIRequestResponseListener listener) {
        Call<BoredAPIEvent> call = apiService.getActivityParticipants(participants);

        call.enqueue(new Callback<BoredAPIEvent>() {
            @Override
            public void onResponse(Call<BoredAPIEvent> call, Response<BoredAPIEvent> response) {
                boredAPIEvents.add(response.body());
                listener.onComplete(key, getBoredAPIEvents());
            }

            @Override
            public void onFailure(Call<BoredAPIEvent> call, Throwable t) {
                Log.e(TAG + " getEventParticipants", "Something went wrong fetching data " + t);
            }
        });
    }

    public static List<PlanadayEvent> getBoredAPIEvents() {
        List<PlanadayEvent> planadayEvents = new ArrayList<>();
        for (int i = 0; i < boredAPIEvents.size(); i++) {
            BoredAPIEvent current = boredAPIEvents.get(i);
            PlanadayEvent temp = new PlanadayEvent();

            // Convert the BoredAPIEvent into a PlanadayEvent
            temp.setEventName(current.getActivity());
            temp.setDuration(1);
            if (current.getParticipants() > 1) {
                temp.setSetting("group");
            } else if (current.getParticipants() <= 1) {
                temp.setSetting("individual");
            }
            if (current.getAccessibility() > 0.5) {
                temp.setEnvironment("outdoor");
            } else {
                temp.setEnvironment("indoor");
            }
            String[] types = {current.getType()};
            // temp.setTypes(types); // TODO: types type incorrect
            temp.setLocation("Nearby");

            planadayEvents.add(temp);
        }
        return planadayEvents;
    }
}
