package com.example.classroomannouncement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.classroomannouncement.Database.Entities.User;
import com.example.classroomannouncement.Database.UserRepo;

/**
 * This screen lets the user update their profile info.
 */
public class EditProfilePage extends AppCompatActivity {

    private EditText nameEditText, passwordEditText;
    private Button saveButton;

    private User currentUser;
    private UserRepo userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile); // Connect layout XML file

        // Link input fields and button to layout components
        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        saveButton = findViewById(R.id.saveButton);

        // Set up helper class for DB operations
        userRepo = new UserRepo(this);

        // Get user data passed from SettingsPage
        currentUser = (User) getIntent().getSerializableExtra("user");

        // Fill the input boxes with current values
        nameEditText.setText(currentUser.fullName);
        passwordEditText.setText(currentUser.password);

        // Save updated info when button is clicked
        saveButton.setOnClickListener(v -> {
            String newName = nameEditText.getText().toString().trim();
            String newPassword = passwordEditText.getText().toString().trim();

            if (newName.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Please enter both name and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update user object
            currentUser.fullName = newName;
            currentUser.password = newPassword;

            // Save changes to database
            userRepo.updateUser(currentUser);

            Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
            finish(); // Close and return to SettingsPage
        });
    }
}
