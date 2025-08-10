package com.example.classroomannouncement;

import com.google.gson.annotations.SerializedName;

// class models the response from zen quote API
public class ZenQuoteResponse {

    @SerializedName("q")
    private String quoteText;

    @SerializedName("a")
    private String author;

    public String getQuoteText() {
        return quoteText;
    }

    public String getAuthor() {
        return author;
    }
}