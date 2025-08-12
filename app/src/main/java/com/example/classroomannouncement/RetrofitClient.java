package com.example.classroomannouncement;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// setup the retrofit client to get it ready to retrieve from zen quote
// tbh, this could likely be a generic wrapper that takes a url -- but good enough for now
public class RetrofitClient {

    private static Retrofit retrofit = null;
    // base url for zen quote api
    private static final String BASE_URL = "https://zenquotes.io/api/";

    public static Retrofit getClient() {
        if (retrofit == null || !retrofit.baseUrl().toString().equals(BASE_URL)) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}