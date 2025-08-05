package com.example.classroomannouncement.Database;

import android.content.Context;
import com.example.classroomannouncement.Database.Entities.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class is a helper that talks to the Room database.
 *
 * Activities (like Login or Signup screens) will use this class instead of talking directly to the DAO.
 * It makes our code simpler and cleaner.
 */
public class UserRepo {
    // define the userDAO
    private final UserDAO userDAO;
    private final ExecutorService executorService;

    /**
     * Constructor for UserRepo.
     *
     * We give it the app context (from an Activity),
     * and it builds a database and gets the UserDAO.
     * It also creates a default admin user if one doesn't exist yet.
     */
    public UserRepo(Context context) {
        // get singleton instance of the main database
        AppDatabase db = AppDatabase.getDatabase(context.getApplicationContext());
        this.userDAO = db.userDao();
        this.executorService = Executors.newSingleThreadExecutor();
        // add default admin user on a background thread
        executorService.execute(() -> {
            User existingAdmin = userDAO.getUserByEmail("admin@example.com");
            if (existingAdmin == null) {
                User adminUser = new User("Admin", "admin@example.com", "admin123", true);
                userDAO.insert(adminUser);
            }
        });
    }

    /**
     * Save a new user into the database.
     */
    public void registerUser(User user) {
        executorService.execute(() -> userDAO.insert(user));
    }

    /**
     * Find a user that matches email and password.
     * If found, returns that User. If not, returns null.
     * Uses Future for async operations.
     */
    public Future<User> loginUser(String email, String password) {
        return executorService.submit(() -> userDAO.getUser(email, password));
    }

    /**
     * Find a user just by email.
     * If an account already exists with this email, we will get that User back.
     * If none, it returns null.
     * Uses Future for async operations.
     */
    public Future<User> getUserByEmail(String email) {
        return executorService.submit(() -> userDAO.getUserByEmail(email));
    }
}
