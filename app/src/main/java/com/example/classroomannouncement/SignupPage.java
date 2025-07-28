package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupPage extends AppCompatActivity {

    private EditText fullNameEditText, signupEmailEditText, signupPasswordEditText;
    private Button signupButton;
    private TextView goToLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        signupEmailEditText = findViewById(R.id.signupEmailEditText);
        signupPasswordEditText = findViewById(R.id.signupPasswordEditText);
        signupButton = findViewById(R.id.signupButton);
        goToLoginLink = findViewById(R.id.goToLoginLink);

        // Sign up button logic (placeholder)
        signupButton.setOnClickListener(v -> {
            String name = fullNameEditText.getText().toString().trim();
            String email = signupEmailEditText.getText().toString().trim();
            String password = signupPasswordEditText.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Placeholder – will save to DB later
                Toast.makeText(this, "Signup successful (placeholder)", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupPage.this, MainActivity.class));
            }
        });

        // Navigate back to Login
        goToLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(SignupPage.this, MainActivity.class));
        });
    }
}
