package com.example.classroomannouncement.Database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Announcement entity stored in the Room table "announcements".
 * Fields:
 *  - id: auto-generated row id
 *  - title: short headline
 *  - body: full text/content
 *  - createdAt: epoch millis when the announcement was created
 *
 * NOTE:
 *  - We provide a public no-arg constructor (required by Room).
 *  - We also provide two helper getters used by the UI:
 *      getContent() -> returns body  (so older code keeps working)
 *      getFormattedDate() -> returns a nicely formatted createdAt string
 */
@Entity(tableName = "announcements")
public class Announcement {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String body;
    private long createdAt;

    /** Required by Room (public no-arg constructor). */
    public Announcement() { }

    /** Convenience constructor used by app/tests. */
    @Ignore
    public Announcement(String title, String body, long createdAt) {
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
    }

    public Announcement(String title, String content) {
    }

    // ---- Standard getters/setters Room & UI can use ----
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    /** Full text of the announcement. */
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    /** When it was created (epoch millis). */
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    // ---- Backward-compat helpers expected by your screens/adapters ----

    /** Some screens call getContent(); map it to the body field. */
    public String getContent() {
        return body;
    }

    /** UI helper: format createdAt for display. */
    public String getFormattedDate() {
        Date date = new Date(createdAt);
        SimpleDateFormat fmt = new SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault());
        return fmt.format(date);
    }
}