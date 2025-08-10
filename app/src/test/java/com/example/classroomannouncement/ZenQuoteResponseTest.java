package com.example.classroomannouncement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

// unit test for zen quote API response
public class ZenQuoteResponseTest {

    private ZenQuoteResponse quote;

    @Before
    public void setUp() {
        // create a mock JSON string that represents our API response
        String jsonString = "[{\"q\":\"The best way to predict the future is to create it.\",\"a\":\"Peter Drucker\"}]";
        Gson gson = new Gson();
        // API returns a list so define the type for Gson to parse into.
        Type listType = new TypeToken<List<ZenQuoteResponse>>(){}.getType();
        List<ZenQuoteResponse> quoteList = gson.fromJson(jsonString, listType);
        // get the list of quotes
        quote = quoteList.get(0);
    }

    // test verifies that getter on quote text works
    @Test
    public void getQuoteText_returnsCorrectQuote() {
        // call quote getter method
        String actualQuote = quote.getQuoteText();
        // ensure value matches mock
        assertEquals("The best way to predict the future is to create it.", actualQuote);
    }

    // test verifies that getter on quote quthor works
    @Test
    public void getAuthor_returnsCorrectAuthor() {
        // call author getter method
        String actualAuthor = quote.getAuthor();
        // ensure value matches mock
        assertEquals("Peter Drucker", actualAuthor);
    }
}