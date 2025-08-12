// UserDB.java  (REPLACE your file with this version)

package com.example.classroomannouncement.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;   // <-- add this import

import com.example.classroomannouncement.Database.DAOs.AnnouncementDao;
import com.example.classroomannouncement.Database.DAOs.CourseDAO;
import com.example.classroomannouncement.Database.DAOs.UserDAO;
import com.example.classroomannouncement.Database.Entities.Announcement;
import com.example.classroomannouncement.Database.Entities.Course;
import com.example.classroomannouncement.Database.Entities.User;

/**
 * This is the ONE database for the whole app.
 * It includes all tables and the type converters we need.
 */
@Database(
        entities = { User.class, Course.class, Announcement.class }, // all tables here
        version = 7,               // bump when schema changes (pick a new number)
        exportSchema = false
)
@TypeConverters({ Converters.class })  // <-- we moved converters here from AppDatabase
public abstract class UserDB extends RoomDatabase {

    // These are the "doors" to each table in the database.
    public abstract UserDAO userDao();
    public abstract CourseDAO courseDao();
    public abstract AnnouncementDao announcementDao();
}


