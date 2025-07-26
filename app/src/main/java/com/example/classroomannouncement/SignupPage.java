package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SignupPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        // Find the button by its ID
        Button backToMainButton = findViewById(R.id.backToMainButton);

        // Set OnClickListener to go back to MainActivity
        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignupPage.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
