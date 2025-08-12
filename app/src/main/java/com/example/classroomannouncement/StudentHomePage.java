package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classroomannouncement.Database.Entities.Announcement;
import com.example.classroomannouncement.viewmodels.AnnouncementViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import adapters.AnnouncementAdapter;

public class StudentHomePage extends AppCompatActivity {

    public static final String EXTRA_FULL_NAME = "fullName";
    public static final String EXTRA_ROLE = "roleLabel";
    public static final String EXTRA_EMAIL = "userEmail";
  
    private AnnouncementViewModel announcementViewModel;
    private AnnouncementAdapter announcementAdapter;
    private TextView welcomeText, roleText, quoteTextView;
    private String userEmail;

    private final String[] quotes = {
            "The beautiful thing about learning is that no one can take it away from you. – B.B. King",
            "Education is the passport to the future. – Malcolm X",
            "An investment in knowledge pays the best interest. – Benjamin Franklin",
            "Learning never exhausts the mind. – Leonardo da Vinci",
            "The mind is not a vessel to be filled but a fire to be kindled. – Plutarch"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        welcomeText = findViewById(R.id.studentWelcomeText);
        roleText = findViewById(R.id.roleText);
        quoteTextView = findViewById(R.id.quoteTextView);
        RecyclerView announcementsRecyclerView = findViewById(R.id.announcementsRecyclerView);
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        ImageButton editProfileButton = findViewById(R.id.editProfileButton);
        FloatingActionButton refreshQuoteButton = findViewById(R.id.refreshQuoteButton);

        // Intent extras
        String fullName = getIntent().getStringExtra(EXTRA_FULL_NAME);
        String role = getIntent().getStringExtra(EXTRA_ROLE);

        userEmail = getIntent().getStringExtra(EXTRA_EMAIL);

        if (fullName == null || fullName.isEmpty()) {
            fullName = getString(R.string.default_student_name);
        }
        if (role == null || role.isEmpty()) {
            role = getString(R.string.default_role);
        }

        welcomeText.setText(getString(R.string.welcome_message, fullName));
        roleText.setText(getString(R.string.logged_in_as, role));

        // Settings button
        settingsButton.setOnClickListener(v -> {
            // Create the intent to open EditProfilePage
            Intent intent = new Intent(this, SettingsPage.class);
            // Add the user's email as an extra
            intent.putExtra("userEmail", userEmail);
            // Start the activity
            startActivity(intent);
        });

        editProfileButton.setOnClickListener(v -> {
            // Create the intent to open EditProfilePage
            Intent intent = new Intent(this, EditProfilePage.class);
            // Add the user's email as an extra
            intent.putExtra("userEmail", userEmail);
            // Start the activity
            startActivity(intent);
        });

        // Announcements
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

        // Load initial quote
        loadRandomQuote();

        // Refresh quote with animation
        refreshQuoteButton.setOnClickListener(v -> {
            RotateAnimation rotate = new RotateAnimation(
                    0, 360,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f
            );
            rotate.setDuration(500);
            refreshQuoteButton.startAnimation(rotate);

            loadRandomQuote();
        });
    }

    private void loadRandomQuote() {
        Random random = new Random();
        String quote = quotes[random.nextInt(quotes.length)];
        quoteTextView.setText(quote);
    }
}
