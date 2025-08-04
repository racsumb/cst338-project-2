package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.classroomannouncement.Database.Entities.Announcement;
import com.example.classroomannouncement.viewmodels.AnnouncementViewModel;

public class ViewAnnouncementPage extends AppCompatActivity {

    private TextView announcementTitle, announcementContent, announcementDate;
    private Button backButton;
    private AnnouncementViewModel announcementViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_announcement_page);

        // Initialize views
        announcementTitle = findViewById(R.id.announcementTitle);
        announcementContent = findViewById(R.id.announcementContent);
        announcementDate = findViewById(R.id.announcementDate);
        backButton = findViewById(R.id.backButton);

        // Get announcement ID from intent
        int announcementId = getIntent().getIntExtra("announcement_id", -1);

        // Initialize ViewModel
        announcementViewModel = new ViewModelProvider(this).get(AnnouncementViewModel.class);

        // Observe the specific announcement
        announcementViewModel.getAllAnnouncements().observe(this, announcements -> {
            if (announcements != null) {
                for (Announcement announcement : announcements) {
                    if (announcement.getId() == announcementId) {
                        announcementTitle.setText(announcement.getTitle());
                        announcementContent.setText(announcement.getContent());
                        announcementDate.setText(announcement.getFormattedDate());
                        break;
                    }
                }
            }
        });

        backButton.setOnClickListener(v -> finish());
    }

    public static Intent newIntent(android.content.Context context, int announcementId) {
        Intent intent = new Intent(context, ViewAnnouncementPage.class);
        intent.putExtra("announcement_id", announcementId);
        return intent;
    }
}