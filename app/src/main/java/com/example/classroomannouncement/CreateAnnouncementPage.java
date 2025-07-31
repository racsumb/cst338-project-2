package com.example.classroomannouncement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.classroomannouncement.database.entities.Announcement;
import com.example.classroomannouncement.repositories.AnnouncementRepository;

import java.util.Date;

public class CreateAnnouncementPage extends AppCompatActivity {

    // declare UI elements
    private EditText announcementTitleInput;
    private EditText announcementBodyInput;
    private Button createAnnouncementButton;
    private Button cancelButton; // New cancel button

    // declare announcementRepository
    private AnnouncementRepository announcementRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_announcement_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initialize the announcement repository
        announcementRepository = new AnnouncementRepository(getApplication());
        // initialize UI elements
        announcementTitleInput = findViewById(R.id.announcementTitleInput);
        announcementBodyInput = findViewById(R.id.announcementBodyInput);
        createAnnouncementButton = findViewById(R.id.createAnnouncementButton);
        cancelButton = findViewById(R.id.cancelButton); // Initialize cancel button

        // OnClick Listener for create announcement button
        createAnnouncementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get text from inputs
                String title = announcementTitleInput.getText().toString().trim();
                String content = announcementBodyInput.getText().toString().trim();
                // validate content is there
                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(CreateAnnouncementPage.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    // create a new announcement object
                    Announcement announcement = new Announcement(title, content);
                    // insert the announcement into the database
                    announcementRepository.insert(announcement);
                    Toast.makeText(CreateAnnouncementPage.this, "Announcement Created!", Toast.LENGTH_SHORT).show();
                    // finish the activity and return to the previous screen
                    finish();
                }
            }
        });

        // Set OnClickListener for the cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish the activity and return to previous screen
                finish();
            }
        });
    }
}
