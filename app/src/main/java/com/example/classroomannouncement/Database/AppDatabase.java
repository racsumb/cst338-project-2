package com.example.classroomannouncement.Database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

// import announcement entity
import com.example.classroomannouncement.Database.Entities.Announcement;
// import user entity
import com.example.classroomannouncement.Database.Entities.User;

@Database(
        entities = {Announcement.class, User.class},
        version = 1,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    // add access to both DAOs
    public abstract AnnouncementDao announcementDao();
    public abstract UserDAO userDao();

    private static volatile AppDatabase INSTANCE;
    // use a single DB name
    private static final String DATABASE_NAME = "classroom_app.db";

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
