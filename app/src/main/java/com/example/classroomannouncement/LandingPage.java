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

public class LandingPage extends AppCompatActivity {

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

        // Find buttons by their ID
        Button goToSettingsButton = findViewById(R.id.goToSettingsButton);
        Button goToCreateAnnouncementButton = findViewById(R.id.goToCreateAnnouncementButton);
        Button goToViewAnnouncementButton = findViewById(R.id.goToViewAnnouncementButton);
        Button backToMainButton = findViewById(R.id.backToMainButton);

        // Set OnClickListener for the Settings button
        goToSettingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPage.this, SettingsPage.class);
            startActivity(intent);
        });

        // Set OnClickListener for the Create Announcement button
        goToCreateAnnouncementButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPage.this, CreateAnnouncementPage.class);
            startActivity(intent);
        });

        // Set OnClickListener for the View Announcement button
        goToViewAnnouncementButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPage.this, ViewAnnouncementPage.class);
            startActivity(intent);
        });

        // Set OnClickListener for the Back to Main button
        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPage.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
