package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.classroomannouncement.Database.UserRepo;
import com.example.classroomannouncement.Database.Entities.User;

/**
 * This is the Signup screen.
 * Users type their name, email, and password to create an account.
 */
public class SignupPage extends AppCompatActivity {

    // Text boxes for typing full name, email, and password
    private EditText fullNameEditText, signupEmailEditText, signupPasswordEditText;

    // Button to confirm signup
    private Button signupButton;

    // Text link to go back to Login screen
    private TextView goToLoginLink;

    // Our helper class to talk to the database
    private UserRepo userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        // Connect screen elements to code
        fullNameEditText = findViewById(R.id.fullNameEditText);
        signupEmailEditText = findViewById(R.id.signupEmailEditText);
        signupPasswordEditText = findViewById(R.id.signupPasswordEditText);
        signupButton = findViewById(R.id.signupButton);
        goToLoginLink = findViewById(R.id.goToLoginLink);

        // Make a new UserRepo so we can save users into the database
        userRepo = new UserRepo(this);

        /**
         * When the Sign Up button is clicked:
         * 1. Get the name, email, and password typed by the user
         * 2. If any field is empty, show a message
         * 3. Check if the email is already used
         * 4. If email is new, create a user in the database and go back to Login
         */
        signupButton.setOnClickListener(v -> {
            // Get what the user typed in the boxes
            String name = fullNameEditText.getText().toString().trim();
            String email = signupEmailEditText.getText().toString().trim();
            String password = signupPasswordEditText.getText().toString().trim();

            // 1. Check if any box is empty
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                // Show a message on the screen
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();

                // 2. Check if password is too short (less than 6 characters)
            } else if (password.length() < 6) {
                // Tell the user to make the password longer
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();

            } else {
                // 3. Check if the email already exists in the database
                User existingUser = userRepo.getUserByEmail(email);

                // If a user with this email is already found
                if (existingUser != null) {
                    // Show a message saying the email is already used
                    Toast.makeText(this, "Email already registered!", Toast.LENGTH_SHORT).show();

                } else {
                    // 4. If no user found, make a new user object with the info we typed
                    User newUser = new User(name, email, password, false);

                    // Save the new user into the database
                    userRepo.registerUser(newUser);

                    // Show a message that signup worked
                    Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show();

                    // Go back to the Login screen
                    startActivity(new Intent(SignupPage.this, MainActivity.class));
                }
            }
        });
        /**
         * When "Already have an account? Login" text is clicked:
         * Just go back to the Login screen.
         */
        goToLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(SignupPage.this, MainActivity.class));
        });
    }
}