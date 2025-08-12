package com.example.classroomannouncement.Database;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.classroomannouncement.Database.DAOs.UserDAO;
import com.example.classroomannouncement.Database.Entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Simple CRUD tests for the User table using an in-memory Room DB.
 * Each test focuses on exactly one operation: insert, update, or delete.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE) // don't load the real app manifest in unit tests
public class UserDBTest {

    // In-memory database used only for tests (wiped between runs)
    private UserDB db;

    // DAO under test
    private UserDAO userDAO;

    @Before
    public void setUp() {
        // 1) Get a test Context from Robolectric
        Context context = ApplicationProvider.getApplicationContext();

        // 2) Build an in-memory DB so tests are fast and isolated
        db = Room.inMemoryDatabaseBuilder(context, UserDB.class)
                .allowMainThreadQueries() // OK for unit tests
                .build();

        // 3) Grab the DAO we’re testing
        userDAO = db.userDao();
    }

    @After
    public void tearDown() {
        // Close DB after each test
        if (db != null) db.close();
    }

    /**
     * INSERT: inserting a user should allow reading it back by email.
     */
    @Test
    public void insert_and_getUserByEmail_returnsSameUser() {
        // Arrange: make a user
        User u = new User("Jane Doe", "jane@example.com", "secret123", false);

        // Act: insert into DB
        userDAO.insert(u);

        // Assert: fetch by email and verify fields
        User fromDb = userDAO.getUserByEmail("jane@example.com");
        assertNotNull("User should be found after insert", fromDb);

        // If your fields are public:
        assertEquals("jane@example.com", fromDb.email);
        assertEquals("Jane Doe", fromDb.fullName);

        // If you made fields private with getters instead, use:
        // assertEquals("jane@example.com", fromDb.getEmail());
        // assertEquals("Jane Doe", fromDb.getFullName());
    }

    /**
     * UPDATE: changing a user's password should update exactly one row,
     * and the new password should be readable afterwards.
     */
    @Test
    public void updatePassword_updatesExactlyOneRow() {
        // Arrange: insert a user
        User u = new User("John", "john@example.com", "abcdef", false);
        userDAO.insert(u);

        // Act: update password using DAO helper
        int rows = userDAO.updatePassword("john@example.com", "newpass123");

        // Assert: one row changed and value persisted
        assertEquals(1, rows);
        User updated = userDAO.getUserByEmail("john@example.com");
        assertNotNull(updated);
        assertEquals("newpass123", updated.password); // or updated.getPassword()
    }

    /**
     * DELETE: deleting a user by email should remove it from the table.
     */
    @Test
    public void deleteByEmail_removesRow() {
        // Arrange: insert a user
        User u = new User("Alice", "alice@example.com", "qwerty", false);
        userDAO.insert(u);
        assertNotNull(userDAO.getUserByEmail("alice@example.com"));

        // Act: delete by email using DAO helper
        int rows = userDAO.deleteByEmail("alice@example.com");

        // Assert: exactly one row deleted and user no longer found
        assertEquals(1, rows);
        assertNull(userDAO.getUserByEmail("alice@example.com"));
    }
}