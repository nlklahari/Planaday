package com.example.planaday.networking.ticketMasterAPI;

import com.example.planaday.models.TicketMasterAPIEvent.TicketMasterAPIEvent;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TicketMasterAPIInterface {
    String EVENTS_URL = "/discovery/v2/events?";

    @GET(EVENTS_URL)
    Call<TicketMasterAPIEvent> getEvents(@Query("keyword") String keyword, @Query("apikey") String key,
                                         @Query("radius") int radius, @Query("unit") String unit,
                                         @Query("locale") String locale, @Query("startDateTime") Date startDateTime,
                                         @Query("endDateTime") Date endDateTime,
                                         @Query("preferredCountry") String preferredCountry);
}
