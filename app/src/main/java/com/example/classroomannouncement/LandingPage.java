package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classroomannouncement.Database.Entities.Announcement;
import com.example.classroomannouncement.viewmodels.AnnouncementViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adapters.AnnouncementAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingPage extends AppCompatActivity {

    private AnnouncementViewModel announcementViewModel;
    private AnnouncementAdapter announcementAdapter;
    private boolean isAdmin;
    private TextView quoteTextView;
    private ZenQuoteApiService apiService;
    private String userEmail;
    public static final String EXTRA_USER_EMAIL = "userEmail";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing_page);

        String currentUserEmail = getIntent().getStringExtra("userEmail");
        Log.d("LandingPage", "Received email: " + currentUserEmail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });





        // Get user role from intent
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        userEmail = getIntent().getStringExtra(EXTRA_USER_EMAIL);


        // RecyclerView setup
        RecyclerView announcementsRecyclerView = findViewById(R.id.announcementsRecyclerView);
        announcementsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        announcementAdapter = new AnnouncementAdapter(new ArrayList<>(), isAdmin);
        announcementsRecyclerView.setAdapter(announcementAdapter);

        // ViewModel setup
        announcementViewModel = new ViewModelProvider(this).get(AnnouncementViewModel.class);
        announcementViewModel.getAllAnnouncements().observe(this, announcements -> {
            announcementAdapter.setAnnouncements(announcements);
        });

        // Item click listeners
        announcementAdapter.setOnItemClickListener(new AnnouncementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Announcement announcement) {
                Intent intent = new Intent(LandingPage.this, ViewAnnouncementPage.class);
                intent.putExtra("announcement_id", announcement.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Announcement announcement) {
                announcementViewModel.delete(announcement);
            }
        });

        // Quote TextView
        quoteTextView = findViewById(R.id.quoteTextView);

        // Retrofit setup for quotes
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://zenquotes.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ZenQuoteApiService.class);

        // Fetch initial quote
        fetchQuote();

        // FAB for adding announcements
        FloatingActionButton createAnnouncementButton = findViewById(R.id.goToCreateAnnouncementButton);
        createAnnouncementButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPage.this, CreateAnnouncementPage.class);
            intent.putExtra("isAdmin", true);
            intent.putExtra("roleLabel", "Admin");
            intent.putExtra("userEmail", currentUserEmail);
            startActivity(intent);
        });
        createAnnouncementButton.setVisibility(isAdmin ? View.VISIBLE : View.GONE);

        // FAB for creating courses
        FloatingActionButton createCourseButton = findViewById(R.id.goToCreateCourseButton);
        createCourseButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPage.this, CourseActivity.class);
            intent.putExtra("isAdmin", true);
            intent.putExtra("roleLabel", "Admin");
            intent.putExtra("userEmail", currentUserEmail);
            startActivity(intent);
        });
        createCourseButton.setVisibility(isAdmin ? View.VISIBLE : View.GONE);

        // FAB for refreshing quote
        FloatingActionButton refreshQuoteButton = findViewById(R.id.refreshQuoteButton);
        refreshQuoteButton.setOnClickListener(v -> fetchQuote());


        // Settings Button
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsPage.class);
            intent.putExtra("userEmail", userEmail);
            startActivity(intent);
        });

        ImageButton editProfileButton = findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditProfilePage.class);
            intent.putExtra("userEmail", userEmail);
            startActivity(intent);        });
    }

    private void fetchQuote() {
        quoteTextView.setText("Loading quote...");
        apiService.getRandomQuote().enqueue(new Callback<List<ZenQuoteResponse>>() {
            @Override
            public void onResponse(Call<List<ZenQuoteResponse>> call, Response<List<ZenQuoteResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    ZenQuoteResponse quote = response.body().get(0);
                    quoteTextView.setText("\"" + quote.getQuoteText() + "\" — " + quote.getAuthor());
                } else {
                    quoteTextView.setText("Couldn't load quote.");
                }
            }

            @Override
            public void onFailure(Call<List<ZenQuoteResponse>> call, Throwable t) {
                quoteTextView.setText("Error loading quote.");
                Toast.makeText(LandingPage.this, "Failed to load quote", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
