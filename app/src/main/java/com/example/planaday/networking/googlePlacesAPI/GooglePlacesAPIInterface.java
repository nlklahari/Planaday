package com.example.planaday.networking.googlePlacesAPI;

import com.example.planaday.models.GooglePlacesAPIEvent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesAPIInterface {

    String FINDPLACE_URL = "maps/api/place/findplacefromtext/json?";

    @GET(FINDPLACE_URL)
    Call<GooglePlacesAPIEvent> getPlaces(@Query("input") String input, @Query("inputtype") String inputType,
                                         @Query("fields") String fields, @Query("locationbias") String latLong,
                                         @Query("key") String key);
}
