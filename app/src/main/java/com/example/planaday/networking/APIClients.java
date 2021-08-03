package com.example.planaday.networking;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClients {

    // Callback URLs
    public static final String BOREDAPI_REST_CALLBACK_URL = "https://www.boredapi.com/";
    public static final String GOOGLEAPI_REST_CALLBACK_URL = "https://maps.googleapis.com/";
    public static final String TICKETMASTERAPI_REST_CALLBACK_URL = "https://app.ticketmaster.com/discovery/v2//";

    private static Retrofit retrofit = null;

    /**
     * Bored API Client
     * @return
     */
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

    /**
     * Google API Client
     * @return
     */
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

    /**
     * TicketMaster API Client
     * @return
     */
    public static Retrofit getTicketMasterAPI() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(TICKETMASTERAPI_REST_CALLBACK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

}
