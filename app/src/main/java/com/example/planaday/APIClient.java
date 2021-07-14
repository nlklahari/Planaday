package com.example.planaday;

import android.content.Context;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import okhttp3.Headers;

public class APIClient {
    public static final String REST_URL = "";
    public static final String REST_CONSUMER_KEY = "GFUVQP5ETS4Q6U2ULKX3";
    public static final String REST_CONSUMER_SECRET = "";
    public static final String REST_CALLBACK_URL = "https://www.boredapi.com/api/activity?";

    public APIClient(Context context) {

    }

    public static void getEventParticipants(int numOfParticipants) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("participants", numOfParticipants);

        client.get(REST_CALLBACK_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                // Access a JSON array response with `json.jsonArray`
                Log.d("DEBUG ARRAY", json.jsonObject.toString());
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

    }


}
