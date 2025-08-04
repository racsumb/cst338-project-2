package com.example.classroomannouncement.Database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import com.example.classroomannouncement.Database.Entities.Announcement;

@Database(
        entities = {Announcement.class},
        version = 1,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AnnouncementDao announcementDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "announcements_db"
                            )
                            .fallbackToDestructiveMigration() // optional for development
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}