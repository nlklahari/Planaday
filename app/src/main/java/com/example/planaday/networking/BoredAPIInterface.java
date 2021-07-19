package com.example.planaday.networking;

import com.example.planaday.models.BoredAPIEvent;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BoredAPIInterface {

    @GET("/api/activity")
    Call<BoredAPIEvent> getRandomActivity();

    @GET("/api/activity?")
    Call<BoredAPIEvent> getActivityParticipants(@Query("participants") int participants);

    @GET("/api/activity?")
    Call<BoredAPIEvent> getActivityType(@Query("type") String type);

    @GET("/api/activity?")
    Call<BoredAPIEvent> getActivityPrice(@Query("price") int price);

}
