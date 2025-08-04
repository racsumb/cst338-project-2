package com.example.classroomannouncement.Database;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.classroomannouncement.Database.Entities.Announcement;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnnouncementRepository {
    private final AnnouncementDao announcementDao;
    private final ExecutorService executorService;

    public AnnouncementRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.announcementDao = db.announcementDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Announcement>> getAllAnnouncements() {
        return announcementDao.getAllAnnouncements();
    }

    public LiveData<Announcement> getAnnouncementById(int id) {
        return announcementDao.getAnnouncementById(id);
    }

    public void insert(Announcement announcement) {
        executorService.execute(() -> announcementDao.insert(announcement));
    }

    public void delete(Announcement announcement) {
        executorService.execute(() -> announcementDao.delete(announcement));
    }
}