package com.example.classroomannouncement.Database.Repo;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.classroomannouncement.Database.DAOs.AnnouncementDao;
import com.example.classroomannouncement.Database.Entities.Announcement;
import com.example.classroomannouncement.Database.UserDB;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository used by the UI/ViewModels to read/write Announcements.
 * IMPORTANT: Uses UserDB (the single database for the whole app).
 */
public class AnnouncementRepository {

    // Door to the announcements table
    private final AnnouncementDao dao;

    // Simple background thread so inserts/deletes don't block the UI
    private final ExecutorService io = Executors.newSingleThreadExecutor();

    public AnnouncementRepository(Application app) {
        // Build ONE Room database named "classroom_database"
        // (this must match wherever else you open the DB)
        UserDB db = Room.databaseBuilder(
                        app.getApplicationContext(),
                        UserDB.class,
                        "classroom_database"
                )
                .fallbackToDestructiveMigration() // ok for class projects
                .build();

        // Get the announcements DAO from that single DB
        dao = db.announcementDao();
    }

    // Live list used by screens (Landing page etc.)
    public LiveData<List<Announcement>> getAllAnnouncements() {
        return dao.getAllAnnouncements();
    }

    // Live single row by id (detail screen)
    public LiveData<Announcement> getAnnouncementById(int id) {
        return dao.getAnnouncementById(id);
    }

    // Insert on a background thread
    public void insert(Announcement a) {
        io.execute(() -> dao.insert(a));
    }

    // Delete on a background thread
    public void delete(Announcement a) {
        io.execute(() -> dao.delete(a));
    }

    // Optional: update on a background thread
    public void update(Announcement a) {
        io.execute(() -> dao.update(a));
    }
}