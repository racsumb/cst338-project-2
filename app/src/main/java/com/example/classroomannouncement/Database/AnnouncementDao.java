package com.example.classroomannouncement.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.classroomannouncement.Database.Entities.Announcement;
import java.util.List;

@Dao
public interface AnnouncementDao {
    @Query("SELECT * FROM announcements ORDER BY createdAt DESC")
    LiveData<List<Announcement>> getAllAnnouncements();

    @Query("SELECT * FROM announcements WHERE id = :id")
    LiveData<Announcement> getAnnouncementById(int id);

    @Insert
    void insert(Announcement announcement);

    @Delete
    void delete(Announcement announcement);
}