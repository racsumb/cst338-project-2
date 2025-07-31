package com.example.classroomannouncement.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.classroomannouncement.Database.Entities.User;

/**
 * This class is like the main database file.
 *
 * 1. We tell Room which tables (entities) our database will have.
 * 2. Room will build the database using this information.
 * 3. Other parts of our app will talk to the database through this class.
 */
@Database(
        entities = {User.class}, // list all tables we want, right now only User
        version = 1              // start at version 1 for the first database version
)
public abstract class UserDB extends RoomDatabase {

    /**
     * This is how we get access to the UserDAO (the list of commands for the User table).
     *
     * Example:
     * AppDatabase.database.userDao().insert(newUser)
     */
    public abstract UserDAO userDao();
}
