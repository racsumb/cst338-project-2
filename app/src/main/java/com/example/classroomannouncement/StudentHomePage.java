package com.example.classroomannouncement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        // Find the text views in the layout
        TextView welcomeText = findViewById(R.id.studentWelcomeText);
        TextView roleText = findViewById(R.id.roleText);

        // Get the role label from the login screen
        String role = getIntent().getStringExtra("roleLabel");

        // Set the texts
        welcomeText.setText("Welcome to the Announcement Board!");
        roleText.setText("Logged in as: " + role);
    }
}