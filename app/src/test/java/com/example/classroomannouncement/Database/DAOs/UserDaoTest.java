package com.example.classroomannouncement.Database.DAOs;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.classroomannouncement.Database.UserDB;
import com.example.classroomannouncement.Database.Entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * UserDAO tests: one test each for insert, update, and delete.
 * Uses an in-memory Room DB so nothing is written to disk.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class UserDaoTest {

    // In-memory database reference
    private UserDB db;

    // DAO we are testing
    private UserDAO userDao;

    @Before
    public void setUp() {
        // Get a Context from Robolectric (no emulator needed)
        Context ctx = ApplicationProvider.getApplicationContext();

        // Build an in-memory database instance for tests
        db = Room.inMemoryDatabaseBuilder(ctx, UserDB.class)
                .allowMainThreadQueries()  // OK for tests
                .build();

        // Grab the DAO
        userDao = db.userDao();
    }

    @After
    public void tearDown() {
        // Close the database after each test
        db.close();
    }

    /**
     * INSERT test:
     * Insert a user and verify we can fetch it by email.
     */
    @Test
    public void insert_insertsUser() {
        User u = new User("Alice", "alice@example.com", "secret123", false);
        userDao.insert(u);

        User fromDb = userDao.getUserByEmail("alice@example.com");
        assertNotNull("User should be found after insert", fromDb);
    }

    /**
     * UPDATE test:
     * Insert a user, update the password by email, verify login works with the new password.
     */
    @Test
    public void update_updatesPassword() {
        // Insert
        User u = new User("Bob", "bob@example.com", "oldpass", false);
        userDao.insert(u);

        // Update just the password
        int rows = userDao.updatePassword("bob@example.com", "newpass");
        assertEquals("Exactly one row should be updated", 1, rows);

        // Verify old password no longer works, new one does
        assertNull("Old password should not match",
                userDao.getUser("bob@example.com", "oldpass"));

        assertNotNull("New password should match",
                userDao.getUser("bob@example.com", "newpass"));
    }

    /**
     * DELETE test:
     * Insert a user, delete by email, verify it no longer exists.
     */
    @Test
    public void delete_deletesUserByEmail() {
        // Insert
        User u = new User("Cara", "cara@example.com", "pw123456", false);
        userDao.insert(u);

        // Delete
        int rows = userDao.deleteByEmail("cara@example.com");
        assertEquals("Exactly one row should be deleted", 1, rows);

        // Verify gone
        assertNull("User should not be found after delete",
                userDao.getUserByEmail("cara@example.com"));
    }
}