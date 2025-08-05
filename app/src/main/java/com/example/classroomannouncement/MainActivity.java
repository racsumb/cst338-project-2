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

import java.util.concurrent.Future;
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

        // Makes the screen adjust for the top/bottom bars on newer devices
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Connect the screen inputs to code
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        goToSignupLink = findViewById(R.id.goToSignupLink);

        // Set up the helper to talk to the database
        userRepo = new UserRepo(this);

        /**
         * When Login button is clicked:
         * 1. Get the typed email and password
         * 2. Check if either is empty
         * 3. Ask the database if a user exists (now happens in the background)
         * 4. If found, go to the correct screen (admin or student)
         * 5. If not found, show error
         */
        loginButton.setOnClickListener(v -> {
            // Step 1: Get email and password from input boxes
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            // Step 2: Make sure user filled both boxes
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }
            // Step 3: Ask the database if this user exists. This returns a Future.
            Future<User> futureUser = userRepo.loginUser(email, password);
            try {
                // .get() waits for the background task to finish and returns the User
                User user = futureUser.get();

                if (user != null) {
                    // Step 4: Login worked
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                    Intent intent;

                    if (user.isAdmin) {
                        // Admins go to LandingPage
                        intent = new Intent(MainActivity.this, LandingPage.class);
                        intent.putExtra("isAdmin", true);
                    } else {
                        // Regular users go to StudentHomePage
                        intent = new Intent(MainActivity.this, LandingPage.class);
                        intent.putExtra("isAdmin", false);
                    }
                    startActivity(intent);
                    finish(); // Close the login screen

                } else {
                    // Step 5: Login failed
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // This catches errors if the background task fails
                Toast.makeText(this, "An issue occurred", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        /**
         * If user clicks the sign-up text:
         * Take them to the Signup screen.
         */
        goToSignupLink.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SignupPage.class));
        });
    }
}
