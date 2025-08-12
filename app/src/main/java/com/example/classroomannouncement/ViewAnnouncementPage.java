package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.classroomannouncement.Database.Entities.Announcement;
import com.example.classroomannouncement.viewmodels.AnnouncementViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ViewAnnouncementPage extends AppCompatActivity {

    private AnnouncementViewModel announcementViewModel;
    private MaterialToolbar toolbar;
    private TextView announcementTitle, announcementContent, announcementDate;
    private TextView inspirationalQuote;
    private int currentAnnouncementId;
    private boolean isAdmin;
    private static final String TAG = "ViewAnnouncementPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_announcement_page);

        // Get user role from intent
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);

        // Initialize views
        initViews();

        // Get announcement ID from intent
        currentAnnouncementId = getIntent().getIntExtra("announcement_id", -1);
        if (currentAnnouncementId == -1) {
            showErrorAndFinish("Invalid announcement");
            return;
        }

        // Initialize ViewModel
        announcementViewModel = new ViewModelProvider(this).get(AnnouncementViewModel.class);

        // Observe announcement data
        observeAnnouncement();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        announcementTitle = findViewById(R.id.announcementTitle);
        announcementContent = findViewById(R.id.announcementContent);
        announcementDate = findViewById(R.id.announcementDate);
        inspirationalQuote = findViewById(R.id.inspirationalQuote);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void observeAnnouncement() {
        announcementViewModel.getAnnouncementById(currentAnnouncementId).observe(this, announcement -> {
            if (announcement != null) {
                displayAnnouncement(announcement);
                fetchInspirationalQuote();
            } else {
                showErrorAndFinish("Announcement not found");
            }
        });
    }

    private void displayAnnouncement(Announcement announcement) {
        announcementTitle.setText(announcement.getTitle());
        announcementContent.setText(announcement.getContent());
        announcementDate.setText(announcement.getFormattedDate());
        toolbar.setTitle(announcement.getTitle());
    }

    private void fetchInspirationalQuote() {
        // defensive null check in case quote is malformed or wrong
        if (inspirationalQuote == null) {
            return;
        }

        ZenQuoteApiService apiService = RetrofitClient.getClient().create(ZenQuoteApiService.class);
        Call<List<ZenQuoteResponse>> call = apiService.getRandomQuote();

        call.enqueue(new Callback<List<ZenQuoteResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ZenQuoteResponse>> call, @NonNull Response<List<ZenQuoteResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    // if the api call is successful we prepare the content
                    ZenQuoteResponse quote = response.body().get(0);
                    String quoteContent = quote.getQuoteText();
                    String authorName = quote.getAuthor();

                    String formattedQuote = "<i>\"" + quoteContent + " - " + authorName + "\"</i>" +
                            "<br><br>- quote provided by zenquotes.io, retrieved via retrofit";

                    inspirationalQuote.setText(Html.fromHtml(formattedQuote, Html.FROM_HTML_MODE_LEGACY));
                    inspirationalQuote.setVisibility(View.VISIBLE);
                } else {
                    // if api call fails show a toast, proves we're at least trying to retrieve in case API is down :)
                    Toast.makeText(ViewAnnouncementPage.this, "Retrieval from quote API failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ZenQuoteResponse>> call, @NonNull Throwable t) {
                // network failure, show same toast
                Toast.makeText(ViewAnnouncementPage.this, "Retrieval from quote API failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isAdmin) {
            // TODO add some kind of admin option if needed
            // Remove this if you don't have a menu resource
            // getMenuInflater().inflate(R.menu.menu_announcement, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showErrorAndFinish(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    public static Intent newIntent(android.content.Context context, int announcementId, boolean isAdmin) {
        Intent intent = new Intent(context, ViewAnnouncementPage.class);
        intent.putExtra("announcement_id", announcementId);
        intent.putExtra("isAdmin", isAdmin);
        return intent;
    }
}