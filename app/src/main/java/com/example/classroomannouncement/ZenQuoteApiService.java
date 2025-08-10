package com.example.classroomannouncement;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ZenQuoteApiService {
    @GET("random")
    // annoyingly zen quote api returns a list
    Call<List<ZenQuoteResponse>> getRandomQuote();
}