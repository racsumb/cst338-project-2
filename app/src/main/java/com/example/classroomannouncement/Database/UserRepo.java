package com.example.classroomannouncement.Database;

import android.content.Context;

import androidx.room.Room;

import com.example.classroomannouncement.Database.Entities.User;

/**
 * This class is a helper that talks to the Room database.
 *
 * Activities (like Login or Signup screens) will use this class instead of talking directly to the DAO.
 * It makes our code simpler and cleaner.
 */
public class UserRepo {

    // This is the database object
    private UserDB db;

    // This is how we run commands for the "users" table
    private UserDAO userDAO;

    /**
     * Constructor for UserRepo.
     *
     * We give it the app context (from an Activity),
     * and it builds a database and gets the UserDAO.
     */
    public UserRepo(Context context) {
        // Build the database (creates or opens "classroom_database")
        db = Room.databaseBuilder(
                        context.getApplicationContext(),
                        UserDB.class,
                        "classroom_database" // name of the database file
                )
                .allowMainThreadQueries() // run queries on main thread (simpler for now)
                .build();

        // Get the DAO from the database
        userDAO = db.userDao();
    }

    /**
     * Save a new user into the database.
     */
    public void registerUser(User user) {
        userDAO.insert(user);
    }

    /**
     * Find a user that matches email and password.
     * If found, returns that User. If not, returns null.
     */
    public User loginUser(String email, String password) {
        return userDAO.getUser(email, password);
    }

    /**
     * Find a user just by email.
     * If an account already exists with this email, we will get that User back.
     * If none, it returns null.
     */
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
}
