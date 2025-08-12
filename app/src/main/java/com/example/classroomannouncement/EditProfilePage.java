package com.example.classroomannouncement;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.classroomannouncement.Database.Entities.User;
import com.example.classroomannouncement.Database.Repo.UserRepo;

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

        Log.d("EditProfilePage", "onCreate called");

        String userEmail = getIntent().getStringExtra("userEmail");
        Log.d("EditProfilePage", "Received email: " + userEmail);

        if (userEmail == null) {
            Log.e("EditProfilePage", "No user email provided");
            Toast.makeText(this, "Error: No user email provided", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no email is passed
            return;
        }
        Log.d("EditProfilePage", "Received email: " + userEmail);



        // Link input fields and button to layout components
        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        saveButton = findViewById(R.id.saveButton);

        // Set up helper class for DB operations
        userRepo = new UserRepo(this);

        // Get user data passed from SettingsPage
       // String userEmail = getIntent().getStringExtra("userEmail");
        currentUser = userRepo.getUserByEmail(userEmail);

        // Fill the input boxes with current values
        nameEditText.setText(currentUser.getName());
        passwordEditText.setText(currentUser.getPassword());


        // Save updated info when button is clicked
        saveButton.setOnClickListener(v -> {
            String newName = nameEditText.getText().toString().trim();
            String newPassword = passwordEditText.getText().toString().trim();

            if (newName.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Please enter both name and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update user object
            currentUser.setName(newName);
            currentUser.setPassword(newPassword);


            // Save changes to database
            userRepo.updateUser(currentUser);

            Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
            finish(); // Close and return to SettingsPage
        });
    }

    // Utility methods for unit tests
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
    public static boolean isPasswordChangeAllowed(String oldPassword, String newPassword) {
        return oldPassword != null && newPassword != null && !newPassword.equals(oldPassword);
    }

}
