package com.example.planaday.networking.googlePlacesAPI;

import android.util.Log;

import com.example.planaday.models.GooglePlacesAPIEvent;
import com.example.planaday.models.PlanadayEvent;
import com.example.planaday.networking.APIClients;
import com.example.planaday.networking.listeners.APIRequestResponseListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GooglePlacesAPIRequests {

    private static final String TAG = GooglePlacesAPIRequests.class.getSimpleName();
    private static String API_KEY = "AIzaSyCsl4NwvF6zLQkXoKgcPpwiKbkHXJTKyZg"; // TODO: Make secure
    private static final GooglePlacesAPIInterface apiService = APIClients.getGoogleAPIClient().create(GooglePlacesAPIInterface.class);
    private static List<GooglePlacesAPIEvent> googleAPIEvents = new ArrayList<>();

    public static void getPlaces(String key, String input, int maxDistance, String currentLocation, APIRequestResponseListener listener) {
        Call<GooglePlacesAPIEvent> call = apiService.getPlaces(input, "textquery",
                "name,formatted_address,types", "circle:"+maxDistance+"@"+currentLocation,
                API_KEY );

        call.enqueue(new Callback<GooglePlacesAPIEvent>() {
            @Override
            public void onResponse(Call<GooglePlacesAPIEvent> call, Response<GooglePlacesAPIEvent> response) {
                googleAPIEvents.add(response.body());
                listener.onComplete(key, getGoogleAPIEvents());
            }

            @Override
            public void onFailure(Call<GooglePlacesAPIEvent> call, Throwable t) {
                Log.e(TAG + " getEventParticipants", "Something went wrong fetching data " + t);
            }
        });
    }

    public static List<PlanadayEvent> getGoogleAPIEvents() {
        List<PlanadayEvent> planadayEvents = new ArrayList<>();
        for (int i = 0; i < googleAPIEvents.size(); i++) {
            GooglePlacesAPIEvent current = googleAPIEvents.get(i);
            PlanadayEvent temp = new PlanadayEvent();

            // Convert the GooglePlacesAPIEvent into a PlanadayEvent
            temp.setEventName(current.getCandidates().get(0).getName());
            Log.i(TAG, "name of google event: " + temp.getEventName());
            temp.setDuration(1);
            temp.setSetting("group");
            // TODO: cross reference with keywords associated with outdoors
            temp.setEnvironment("outdoor");
            //temp.setEnvironment("indoor");
            // String[] types = current.getCandidates().get(0).getTypes();
            // temp.setTypes(types);
            temp.setLocation(current.getCandidates().get(0).getFormattedAddress());

            // Add it to array of planadayEvents
            planadayEvents.add(temp);
        }
        return planadayEvents;
    }
}
