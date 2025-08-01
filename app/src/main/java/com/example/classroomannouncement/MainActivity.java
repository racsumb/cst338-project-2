package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.classroomannouncement.Database.UserRepo;
import com.example.classroomannouncement.Database.Entities.User;

/**
 * This is the Login screen.
 * Users type email and password to log in.
 */
public class MainActivity extends AppCompatActivity {

    // Boxes for typing email and password
    private EditText emailEditText, passwordEditText;

    // Button for login
    private Button loginButton;

    // Text link to go to Signup screen
    private TextView goToSignupLink;

    // Database helper class
    private UserRepo userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Make sure the screen adjusts for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Connect screen elements to code
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        goToSignupLink = findViewById(R.id.goToSignupLink);

        // Make a new UserRepo so we can check users in the database
        userRepo = new UserRepo(this);

        /**
         * When Login button is clicked:
         * 1. Get email and password typed by the user
         * 2. If boxes are empty, show a message
         * 3. Ask the database if a user with that email & password exists
         * 4. If found, show success and go to LandingPage
         * 5. If not found, show "Invalid email or password"
         */
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Check for empty input
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                // Ask the database if there is a user with these credentials
                User user = userRepo.loginUser(email, password);

                if (user != null) {
                    // Login success
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LandingPage.class));
                } else {
                    // No matching user
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * When "Don't have an account? Sign Up" text is clicked:
         * Open the SignupPage screen.
         */
        goToSignupLink.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SignupPage.class));
        });
    }
}