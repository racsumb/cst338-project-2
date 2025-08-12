package com.example.classroomannouncement.Database.Entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Room entity for the "announcements" table.
 *
 * Fields:
 *  - id         : auto-generated primary key
 *  - title      : short headline shown in lists
 *  - body       : full text shown on details page
 *  - createdAt  : time saved (epoch millis) so we can sort newest first
 *
 * NOTE:
 *  - Room requires a PUBLIC NO-ARG CONSTRUCTOR.
 *  - We also provide a convenient (title, body) constructor that
 *    auto-sets createdAt to "now" so new posts show at the top.
 */
@Entity(tableName = "announcements")
public class Announcement {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String body;
    private long createdAt;

    /** Required by Room (must exist and be public). */
    public Announcement() { }

    /** App/tests helper: build with title + body; timestamp is now. */
    @Ignore
    public Announcement(String title, String body) {
        this.title = title;                         // save the title
        this.body = body;                           // save the body text
        this.createdAt = System.currentTimeMillis();// set "now" for sorting/display
    }

    /** Optional: if you ever need a custom timestamp. */
    @Ignore
    public Announcement(String title, String body, long createdAt) {
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
    }

    // ---- Standard getters/setters used by Room + UI ----

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    /** Full text/content. */
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    /** When it was created (epoch millis). */
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    // ---- Back-compat helpers some screens use ----

    /** Older code calls getContent(); map it to body. */
    public String getContent() { return body; }

    /** Pretty date string for the UI. */
    public String getFormattedDate() {
        Date date = new Date(createdAt);
        return new SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())
                .format(date);
    }
}