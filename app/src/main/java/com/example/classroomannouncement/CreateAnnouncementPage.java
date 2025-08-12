package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.classroomannouncement.Database.Entities.Announcement;
import com.example.classroomannouncement.Database.Entities.User;
import com.example.classroomannouncement.Database.Repo.AnnouncementRepository;
import com.example.classroomannouncement.Database.Repo.UserRepo;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class CreateAnnouncementPage extends AppCompatActivity {

    private TextInputEditText announcementTitleInput;
    private TextInputEditText announcementBodyInput;
    private AnnouncementRepository announcementRepository;
    private UserRepo userRepo;
    private String currentUserEmail;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement_page);

        // Initialize the repository
        announcementRepository = new AnnouncementRepository(getApplication());
        userRepo = new UserRepo(this);
        // Retrieve the current user's email passed in via Intent
        currentUserEmail = getIntent().getStringExtra("userEmail");
        // Get the current user to check their role
        currentUser = userRepo.getUserByEmail(currentUserEmail); // Initialize currentUser here

        // Initialize UI elements
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        announcementTitleInput = findViewById(R.id.announcementTitleInput);
        announcementBodyInput = findViewById(R.id.announcementBodyInput);

        // Set up the toolbar
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(CreateAnnouncementPage.this, LandingPage.class);
            intent.putExtra("isAdmin", true);
            intent.putExtra("roleLabel", "Admin");
            intent.putExtra("userEmail", currentUserEmail);
            startActivity(intent);
        });
    }

    // This method creates the "SAVE" button in the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_announcement_menu, menu);
        return true;
    }

    // This method handles clicks on the toolbar buttons
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveAnnouncement();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAnnouncement() {
        String title = announcementTitleInput.getText().toString().trim();
        String content = announcementBodyInput.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
        } else {
            Announcement announcement = new Announcement(title, content);
            announcementRepository.insert(announcement);
            Toast.makeText(this, "Announcement Posted!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreateAnnouncementPage.this, LandingPage.class);
            intent.putExtra("isAdmin", true);
            intent.putExtra("roleLabel", "Admin");
            intent.putExtra("userEmail", currentUserEmail);
            startActivity(intent);
        }
    }
}
