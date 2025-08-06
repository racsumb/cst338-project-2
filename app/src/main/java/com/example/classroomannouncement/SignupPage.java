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

public class SignupPage extends AppCompatActivity {

    private EditText fullNameEditText, signupEmailEditText, signupPasswordEditText;
    private Button signupButton;
    private TextView goToLoginLink;
    private UserRepo userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        // Initialize views
        fullNameEditText = findViewById(R.id.fullNameEditText);
        signupEmailEditText = findViewById(R.id.signupEmailEditText);
        signupPasswordEditText = findViewById(R.id.signupPasswordEditText);
        signupButton = findViewById(R.id.signupButton);
        goToLoginLink = findViewById(R.id.goToLoginLink);

        userRepo = new UserRepo(this);

        signupButton.setOnClickListener(v -> {
            // Get input values
            String name = fullNameEditText.getText().toString().trim();
            String email = signupEmailEditText.getText().toString().trim();
            String password = signupPasswordEditText.getText().toString().trim();

            // Validate inputs
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if email exists
            if (userRepo.getUserByEmail(email) != null) {
                Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create and register new user
            User newUser = new User(name, email, password, false);
            userRepo.registerUser(newUser);

            // Go to StudentHomePage with user data
            Intent intent = new Intent(SignupPage.this, StudentHomePage.class);
            intent.putExtra("fullName", name);  // Pass the name directly from input
            intent.putExtra("email", email);
            intent.putExtra("roleLabel", "Student");
            startActivity(intent);
            finish();  // Close signup screen
        });

        goToLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(SignupPage.this, MainActivity.class));
            finish();
        });
    }
}