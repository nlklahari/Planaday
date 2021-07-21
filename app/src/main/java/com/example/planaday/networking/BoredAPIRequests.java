package com.example.planaday.networking;

import android.util.Log;

import com.example.planaday.models.BoredAPIEvent;
import com.example.planaday.models.PlanadayEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoredAPIRequests {

    private static final String TAG = BoredAPIRequests.class.getSimpleName();
    private static final BoredAPIInterface apiService = APIClients.getBoredAPIClient().create(BoredAPIInterface.class);

    /**
     *  Get a random activity based on the number of participants
     * @param participants
     */
    public static void getEventParticipants(int participants) {
        Call<BoredAPIEvent> call = apiService.getActivityParticipants(participants);
        call.enqueue(new Callback<BoredAPIEvent>() {
            @Override
            public void onResponse(Call<BoredAPIEvent> call, Response<BoredAPIEvent> response) {
                Log.d(TAG, response.body().getActivity());
                PlanadayEvent event = new PlanadayEvent();
                event.setEventName(response.body().getActivity());
                event.setDuration(1);
                event.setPrice(0);
                // TODO something here to store the event
            }

            @Override
            public void onFailure(Call<BoredAPIEvent> call, Throwable t) {
                Log.e(TAG + "getEventParticipants", "Something went wrong fetching data " + t);
            }
        });
    }
}
