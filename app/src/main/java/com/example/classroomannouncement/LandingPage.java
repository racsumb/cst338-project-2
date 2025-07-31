package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classroomannouncement.adapters.AnnouncementAdapter;
import com.example.classroomannouncement.database.entities.Announcement;
import com.example.classroomannouncement.viewmodels.AnnouncementViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LandingPage extends AppCompatActivity {

    private AnnouncementViewModel announcementViewModel;
    private AnnouncementAdapter announcementAdapter;
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get user role from intent
        isAdmin = getIntent().getBooleanExtra("isAdmin", true);

        // Initialize RecyclerView
        RecyclerView announcementsRecyclerView = findViewById(R.id.announcementsRecyclerView);
        announcementsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        announcementAdapter = new AnnouncementAdapter(new ArrayList<>(), isAdmin);
        announcementsRecyclerView.setAdapter(announcementAdapter);

        // Initialize ViewModel
        announcementViewModel = new ViewModelProvider(this).get(AnnouncementViewModel.class);
        announcementViewModel.getAllAnnouncements().observe(this, announcements -> {
            announcementAdapter.setAnnouncements(announcements);
        });

        // Set click listener for announcements
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

        // Find buttons by their ID
        Button goToSettingsButton = findViewById(R.id.goToSettingsButton);
        FloatingActionButton createAnnouncementButton = findViewById(R.id.goToCreateAnnouncementButton);

        // Set OnClickListener for the Settings button
        goToSettingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPage.this, SettingsPage.class);
            startActivity(intent);
        });

        // Set OnClickListener for the Create Announcement button
        createAnnouncementButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPage.this, CreateAnnouncementPage.class);
            startActivity(intent);
        });

        // Show create button only for admin
        createAnnouncementButton.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
    }
}