package com.example.planaday.networking;

import android.util.Log;

import com.codepath.asynchttpclient.RequestParams;

import java.util.List;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClients {

    public static final String BOREDAPI_REST_CALLBACK_URL = "https://www.boredapi.com/";
    public static final String GOOGLEAPI_REST_CALLBACK_URL = "hhttps://maps.googleapis.com/";

    private static Retrofit retrofit = null;

    public static Retrofit getBoredAPIClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BOREDAPI_REST_CALLBACK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    public static Retrofit getGoogleAPIClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(GOOGLEAPI_REST_CALLBACK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

}
