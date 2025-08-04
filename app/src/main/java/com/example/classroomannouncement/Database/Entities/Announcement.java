package com.example.classroomannouncement.Database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.classroomannouncement.Database.Converters;
import java.util.Date;

@Entity(tableName = "announcements")
public class Announcement {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String content;

    @TypeConverters(Converters.class)
    private Date createdAt;

    public Announcement(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = new Date(); // Sets to current time by default
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Helper method for formatted date display
    public String getFormattedDate() {
        return android.text.format.DateFormat.format("MMM dd, yyyy", createdAt).toString();
    }
}