package com.example.planaday;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import java.util.List;

import okhttp3.Headers;

public class APIClient {

    public static final String BOREDAPI_REST_CALLBACK_URL = "https://www.boredapi.com/api/activity?";
    public static final String GOOGLEAPI_REST_CALLBACK_URL = "hhttps://maps.googleapis.com/maps/api/place/findplacefromtext/json?";

    private AsyncHttpClient client;

    public APIClient() {
        client = new AsyncHttpClient();
    }

    // Get a random activity based on the number of participants
    public void getEventParticipants(int numOfParticipants) {
        RequestParams params = new RequestParams();
        params.put("participants", numOfParticipants);

        client.get(BOREDAPI_REST_CALLBACK_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("getEventParticipants", json.jsonObject.toString());
                // TODO something here to store the event
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("getEventParticipants", "Something went wrong fetching info from Bored API with participants query param " + response);
            }
        });
    }

    public void getEventKeywordsANDDistance(List<String> inputs) {
        RequestParams params = new RequestParams();

        client.get(GOOGLEAPI_REST_CALLBACK_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("getEventKeywordsANDDistance", json.jsonObject.toString());
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("getEventKeywordsANDDistance", "Something went wrong fetching info from Bored API with participants query param " + response);
            }
        });
    }

}
