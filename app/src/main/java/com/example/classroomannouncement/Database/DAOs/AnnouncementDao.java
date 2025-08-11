package com.example.classroomannouncement.Database.DAOs;

import androidx.lifecycle.LiveData;    // <- NEW import
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.classroomannouncement.Database.Entities.Announcement;

import java.util.List;

/**
 * DAO for the "announcements" table.
 * Includes both LiveData (for app UI) and synchronous methods (for unit tests).
 */
@Dao
public interface AnnouncementDao {

    // ----- Mutations -----
    @Insert
    long insert(Announcement a);

    @Update
    int update(Announcement a);

    @Delete
    int delete(Announcement a);

    // ----- Queries used by the APP (LiveData) -----

    /** Live list for UI/repository */
    @Query("SELECT * FROM announcements ORDER BY createdAt DESC")
    LiveData<List<Announcement>> getAllAnnouncements();     // <-- matches repository call

    /** Live single row by id for UI/repository */
    @Query("SELECT * FROM announcements WHERE id = :id LIMIT 1")
    LiveData<Announcement> getAnnouncementById(int id);     // <-- matches repository call

    // ----- Queries used by TESTS (sync) -----

    /** Synchronous list for unit tests */
    @Query("SELECT * FROM announcements")
    List<Announcement> getAllAnnouncementsSync();

    /** Handy sync lookup by title for tests */
    @Query("SELECT * FROM announcements WHERE title = :title LIMIT 1")
    Announcement getByTitle(String title);
}