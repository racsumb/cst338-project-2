package com.example.classroomannouncement.Database.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update; // ✅ Needed for update

import com.example.classroomannouncement.Database.Entities.Announcement;
import java.util.List;

@Dao
public interface AnnouncementDao {

    /**
     * Get all announcements sorted by creation date (newest first).
     */
    @Query("SELECT * FROM announcements ORDER BY createdAt DESC")
    LiveData<List<Announcement>> getAllAnnouncements();

    /**
     * Get a single announcement by its ID.
     */
    @Query("SELECT * FROM announcements WHERE id = :id")
    LiveData<Announcement> getAnnouncementById(int id);

    /**
     * Insert a new announcement into the database.
     */
    @Insert
    void insert(Announcement announcement);

    /**
     * Delete an existing announcement from the database.
     */
    @Delete
    void delete(Announcement announcement);

    /**
     * Update an existing announcement in the database.
     */
    @Update
    void update(Announcement announcement); // ✅ Added
}