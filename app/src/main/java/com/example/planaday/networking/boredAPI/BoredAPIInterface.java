package com.example.planaday.networking.boredAPI;

import com.example.planaday.models.BoredAPIEvent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BoredAPIInterface {

    @GET("/api/activity?")
    Call<BoredAPIEvent> getActivityParticipants(@Query("participants") int participants);

    @GET("/api/activity?")
    Call<BoredAPIEvent> getActivityType(@Query("type") String type);
}
