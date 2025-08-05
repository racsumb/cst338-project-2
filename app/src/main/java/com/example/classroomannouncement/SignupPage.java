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

import java.util.concurrent.Future;

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
         * 3. Check if the email is already used (now happens in the background)
         * 4. If email is new, create a user in the database and go back to Login
         */
        signupButton.setOnClickListener(v -> {
            // Get what the user typed in the boxes
            String name = fullNameEditText.getText().toString().trim();
            String email = signupEmailEditText.getText().toString().trim();
            String password = signupPasswordEditText.getText().toString().trim();
            // 1. Check if any box is empty
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            // 2. Check if password is too short
            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            // 3. Check if the email already exists in the database
            Future<User> futureUser = userRepo.getUserByEmail(email);
            try {
                User existingUser = futureUser.get(); // Waits for the background check to finish
                if (existingUser != null) {
                    // If a user with this email is already found
                    Toast.makeText(this, "Email already registered!", Toast.LENGTH_SHORT).show();
                } else {
                    // 4. If no user found, make a new user object
                    User newUser = new User(name, email, password, false); // false for non-admin
                    // Save the new user into the database
                    userRepo.registerUser(newUser);
                    // Show a message that signup worked
                    Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show();
                    // Go back to the Login screen
                    startActivity(new Intent(SignupPage.this, MainActivity.class));
                }
            } catch (Exception e) {
                Toast.makeText(this, "An issue occurred during signup", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        /**
         * When "Already have an account? Login" text is clicked:
         * Just go back to the Login screen.
         */
        goToLoginLink.setOnClickListener(v -> {
            finish(); // Closes this screen and returns to the previous one (MainActivity)
        });
    }
}
