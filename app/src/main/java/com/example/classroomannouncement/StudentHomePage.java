package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classroomannouncement.Database.Entities.Announcement;
import com.example.classroomannouncement.adapters.AnnouncementAdapter;
import com.example.classroomannouncement.viewmodels.AnnouncementViewModel;

import java.util.ArrayList;

public class StudentHomePage extends AppCompatActivity {

    // Constants for intent extras
    public static final String EXTRA_FULL_NAME = "fullName";
    public static final String EXTRA_ROLE = "roleLabel";

    private AnnouncementViewModel announcementViewModel;
    private AnnouncementAdapter announcementAdapter;
    private TextView welcomeText, roleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        // Initialize views
        welcomeText = findViewById(R.id.studentWelcomeText);
        roleText = findViewById(R.id.roleText);
        RecyclerView announcementsRecyclerView = findViewById(R.id.announcementsRecyclerView);
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        ImageButton editProfileButton = findViewById(R.id.editProfileButton);

        // Get user data from intent with null checks
        String fullName = getIntent().getStringExtra(EXTRA_FULL_NAME);
        String role = getIntent().getStringExtra(EXTRA_ROLE);

        // Set default values if null
        if (fullName == null || fullName.isEmpty()) {
            fullName = getString(R.string.default_student_name);
        }

        if (role == null || role.isEmpty()) {
            role = getString(R.string.default_role);
        }

        // Set welcome message with full name
        welcomeText.setText(getString(R.string.welcome_message, fullName));
        roleText.setText(getString(R.string.logged_in_as, role));

        // Rest of your onCreate code remains the same...
        settingsButton.setOnClickListener(v -> {
            startActivity(new Intent(this, SettingsPage.class));
        });

        editProfileButton.setOnClickListener(v -> {
            startActivity(new Intent(this, EditProfilePage.class));
        });

        announcementsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        announcementAdapter = new AnnouncementAdapter(new ArrayList<>(), false);
        announcementsRecyclerView.setAdapter(announcementAdapter);

        announcementViewModel = new ViewModelProvider(this).get(AnnouncementViewModel.class);
        announcementViewModel.getAllAnnouncements().observe(this, announcements -> {
            announcementAdapter.setAnnouncements(announcements);
        });

        announcementAdapter.setOnItemClickListener(new AnnouncementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Announcement announcement) {
                Intent intent = new Intent(StudentHomePage.this, ViewAnnouncementPage.class);
                intent.putExtra("announcement_id", announcement.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Announcement announcement) {
                // Not needed for student view
            }
        });
    }
}