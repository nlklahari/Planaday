package com.example.planaday.networking;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesAPIInterface {

    String FINDPLACE_URL = "maps/api/place/findplacefromtext/json?";
    String API_KEY = "AIzaSyCsl4NwvF6zLQkXoKgcPpwiKbkHXJTKyZg"; // TODO: Make secure

    @GET(FINDPLACE_URL + "input={input}&inputtype=textquery&key=" + API_KEY)
    Call<ResponseBody> getPlaces(@Query("fields") List<String> names);
}
