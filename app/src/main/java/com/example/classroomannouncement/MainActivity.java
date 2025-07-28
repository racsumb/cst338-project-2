package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, goToSignupButton, goToLandingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        goToSignupButton = findViewById(R.id.goToSignupButton);
        goToLandingButton = findViewById(R.id.goToLandingButton);

        // Login button action (placeholder logic for now)
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Replace with DB validation
                Toast.makeText(this, "Login successful (placeholder)", Toast.LENGTH_SHORT).show();

                // Move to landing page after "login"
                Intent intent = new Intent(MainActivity.this, LandingPage.class);
                startActivity(intent);
            }
        });

        // Navigate to Signup
        goToSignupButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignupPage.class);
            startActivity(intent);
        });

        // Navigate directly to Landing page
        goToLandingButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LandingPage.class);
            startActivity(intent);
        });
    }
}