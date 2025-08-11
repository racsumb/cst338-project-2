package com.example.classroomannouncement;

// Import necessary Android and Java classes
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

// Import your Room entities and repository
import com.example.classroomannouncement.Database.Entities.User;
import com.example.classroomannouncement.Database.Repo.UserRepo;

public class SettingsPage extends AppCompatActivity {

    // Declare a UserRepo object to interact with the database
    private UserRepo userRepo;

    // Variable to hold the logged-in user's email
    private String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the parent class's onCreate method
        super.onCreate(savedInstanceState);

        // Set the UI layout associated with this activity
        setContentView(R.layout.activity_settings_page);

        // Retrieve the current user's email passed in via Intent
        currentUserEmail = getIntent().getStringExtra("userEmail");
        Log.d("SettingsPage", "Received email: " + currentUserEmail);


        // Initialize the repository to access user data from Room
        userRepo = new UserRepo(this);

        // Get references to UI elements
        TextView userNameTextView = findViewById(R.id.userNameTextView);
        Button editProfileButton = findViewById(R.id.editProfileButton);
        Button logoutButton = findViewById(R.id.logoutButton);
        Button backToMainButton = findViewById(R.id.backToMainButton);

        // Query the database for the current user's full data
        User currentUser = userRepo.getUserByEmail(currentUserEmail);

        // Update the welcome message with the user's name, if found
        if (currentUser != null) {
            userNameTextView.setText("Welcome, " + currentUser.getName());
        } else {
            userNameTextView.setText("Welcome, User"); // Fallback text
        }

        // Set up click listener to navigate to EditProfilePage
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsPage.this, EditProfilePage.class);
            intent.putExtra("userEmail", currentUserEmail); // Pass user email to next screen
            startActivity(intent);
        });

        // Set up click listener to go to LoginActivity and clear the back stack
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsPage.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Prevent back navigation
            startActivity(intent);
        });

        // Set up click listener to navigate back to MainActivity
        backToMainButton.setOnClickListener(v -> {
            boolean isAdmin = currentUser != null && currentUser.isAdmin();

            Intent intent = new Intent(SettingsPage.this, LandingPage.class);
            intent.putExtra("userEmail", currentUserEmail);
            intent.putExtra("isAdmin", isAdmin);
            startActivity(intent);

        });

    }

}
