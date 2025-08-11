package com.example.classroomannouncement.Database.Repo;

import android.content.Context;

import androidx.room.Room;

import com.example.classroomannouncement.Database.Entities.User;
import com.example.classroomannouncement.Database.DAOs.UserDAO;
import com.example.classroomannouncement.Database.DAOs.CourseDAO;
import com.example.classroomannouncement.Database.Entities.Course;
import com.example.classroomannouncement.Database.UserDB;

/**
 * UserRepo is like a helper between our Activities (screens) and the database.
 * Instead of our Activities talking to the database directly,
 * they call methods in this class to do the work.
 */
public class UserRepo {

    // This is our database object (the main "Room" database)
    private UserDB db;

    // This lets us talk to the "users" table
    private UserDAO userDAO;

    // This lets us talk to the "courses" table
    private CourseDAO courseDAO;

    /**
     * This constructor is called when we create a UserRepo object.
     * It sets up the database and makes sure some default data exists.
     */
    public UserRepo(Context context) {
        // Build (or open) the Room database named "classroom_database"
        db = Room.databaseBuilder(
                        context.getApplicationContext(), // use app context (not activity context)
                        UserDB.class,                     // the database class we made
                        "classroom_database"              // name of the database file
                )
                .fallbackToDestructiveMigration() // if versions don't match, reset DB (dev only)
                .allowMainThreadQueries()         // allow DB actions on main thread (simpler for now)
                .build();

        // Connect our DAOs to the database
        userDAO = db.userDao();
        courseDAO = db.courseDao();

        // ----------------------
        // Default Admin User
        // ----------------------
        // Check if an admin with this email exists
        User existingAdmin = userDAO.getUserByEmail("admin@example.com");

        // If not, create one so we always have an admin account
        if (existingAdmin == null) {
            User adminUser = new User("Admin", "admin@example.com", "admin123", true);
            userDAO.insert(adminUser);
        }

        // ----------------------
        // Default Course
        // ----------------------
        // Check if a "General" course exists
        Course allStudentsCourse = courseDAO.getCourseByName("General");

        // If not, create it so there's always at least one course
        if (allStudentsCourse == null) {
            Course newCourse = new Course("General");
            courseDAO.insert(newCourse);
        }
    }

    /**
     * Saves a new user to the database.
     * Example: when someone signs up.
     */
    public void registerUser(User user) {
        userDAO.insert(user);
    }

    /**
     * Checks the database for a user with the given email and password.
     * If found → returns that user.
     * If not found → returns null.
     */
    public User loginUser(String email, String password) {
        return userDAO.getUser(email, password);
    }

    /**
     * Gets a user from the database just by their email.
     * Useful to check if the email is already in use.
     */
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    /**
     * Updates an existing user's information in the database.
     */
    public void updateUser(User user) {
        userDAO.update(user);
    }
}