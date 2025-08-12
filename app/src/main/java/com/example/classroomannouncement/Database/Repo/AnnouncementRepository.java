package com.example.classroomannouncement.Database.Repo;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.classroomannouncement.Database.UserDB;                   // use UserDB
import com.example.classroomannouncement.Database.DAOs.AnnouncementDao;
import com.example.classroomannouncement.Database.Entities.Announcement;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Talks to the "announcements" table using the ONE shared database (UserDB).
 */
public class AnnouncementRepository {
    // The helper for the announcements table
    private final AnnouncementDao announcementDao;
    // Runs work off the main thread
    private final ExecutorService executorService;

    public AnnouncementRepository(Application application) {
        // Open the same database file the rest of the app uses
        UserDB db = Room.databaseBuilder(
                        application.getApplicationContext(),
                        UserDB.class,
                        "classroom_database"             // <-- same name
                )
                .fallbackToDestructiveMigration()         // <-- same setting
                .build();

        // Get the DAO from this shared database
        this.announcementDao = db.announcementDao();

        // One background thread for DB writes
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /** Live list for the UI */
    public LiveData<List<Announcement>> getAllAnnouncements() {
        return announcementDao.getAllAnnouncements();
    }

    /** Live single row by id */
    public LiveData<Announcement> getAnnouncementById(int id) {
        return announcementDao.getAnnouncementById(id);
    }

    /** Insert on background thread */
    public void insert(Announcement announcement) {
        executorService.execute(() -> announcementDao.insert(announcement));
    }

    /** Delete on background thread */
    public void delete(Announcement announcement) {
        executorService.execute(() -> announcementDao.delete(announcement));
    }
}