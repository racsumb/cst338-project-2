package com.example.classroomannouncement.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.classroomannouncement.Database.Entities.Announcement;
import com.example.classroomannouncement.Database.AnnouncementRepository;
import java.util.List;

public class AnnouncementViewModel extends AndroidViewModel {
    private final AnnouncementRepository repository;
    private final LiveData<List<Announcement>> allAnnouncements;

    public AnnouncementViewModel(@NonNull Application application) {
        super(application);
        repository = new AnnouncementRepository(application);
        allAnnouncements = repository.getAllAnnouncements();
    }

    public LiveData<List<Announcement>> getAllAnnouncements() {
        return allAnnouncements;
    }

    public LiveData<Announcement> getAnnouncementById(int id) {
        return repository.getAnnouncementById(id);
    }

    public void insert(Announcement announcement) {
        repository.insert(announcement);
    }

    public void delete(Announcement announcement) {
        repository.delete(announcement);
    }
}