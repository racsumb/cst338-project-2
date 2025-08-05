package com.example.classroomannouncement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.classroomannouncement.Database.Entities.Announcement;
import com.example.classroomannouncement.viewmodels.AnnouncementViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class ViewAnnouncementPage extends AppCompatActivity {

    private TextView announcementTitle, announcementContent, announcementDate;
    private AnnouncementViewModel announcementViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_announcement_page);

        // Initialize views
        announcementTitle = findViewById(R.id.announcementTitle);
        announcementContent = findViewById(R.id.announcementContent);
        announcementDate = findViewById(R.id.announcementDate);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        // set the click listener for the back arrow on the toolbar
        toolbar.setNavigationOnClickListener(v -> finish());

        // Get announcement ID from intent
        int announcementId = getIntent().getIntExtra("announcement_id", -1);
        if (announcementId == -1) {
            // close page if ID is non-existent
            finish();
            return;
        }

        // Initialize ViewModel
        announcementViewModel = new ViewModelProvider(this).get(AnnouncementViewModel.class);

        // Get the announcement by ID, set properties
        announcementViewModel.getAnnouncementById(announcementId).observe(this, announcement -> {
            if (announcement != null) {
                announcementTitle.setText(announcement.getTitle());
                announcementContent.setText(announcement.getContent());
                announcementDate.setText(announcement.getFormattedDate());
            }
        });
    }

    public static Intent newIntent(android.content.Context context, int announcementId) {
        Intent intent = new Intent(context, ViewAnnouncementPage.class);
        intent.putExtra("announcement_id", announcementId);
        return intent;
    }
}
