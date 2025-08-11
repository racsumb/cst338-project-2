package com.example.classroomannouncement.Database;

import com.example.classroomannouncement.Database.DAOs.CourseDAO;
import com.example.classroomannouncement.Database.DAOs.UserDAO;
import com.example.classroomannouncement.Database.Entities.Course;
import com.example.classroomannouncement.Database.Entities.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * This class is like the main database file.
 *
 * 1. We tell Room which tables (entities) our database will have.
 * 2. Room will build the database using this information.
 * 3. Other parts of our app will talk to the database through this class.
 */
@Database(
        // add both user and course class to entities list
        entities = {User.class, Course.class},
        version = 2
)
public abstract class UserDB extends RoomDatabase {

    /**
     * This is how we get access to the UserDAO (the list of commands for the User table).
     *
     * Example:
     * AppDatabase.database.userDao().insert(newUser)
     */
    public abstract UserDAO userDao();

    /**
     * This is how we get access to the CourseDAO (the list of commands for the Course table).
     *
     * Example:
     * AppDatabase.database.courseDao().insert(newCourse)
     */
    public abstract CourseDAO courseDAO();
}
