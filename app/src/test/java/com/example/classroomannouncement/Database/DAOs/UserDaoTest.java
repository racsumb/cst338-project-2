package com.example.classroomannouncement.Database.DAOs;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.classroomannouncement.Database.Entities.User;
import com.example.classroomannouncement.Database.UserDB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Unit tests for UserDAO using an in-memory Room database.
 * Verifies insert, update, and delete operations.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class UserDaoTest {

    private UserDB db;
    private UserDAO userDAO;

    @Before
    public void setUp() {
        Context ctx = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(ctx, UserDB.class)
                .allowMainThreadQueries()  // fine for tests
                .build();
        userDAO = db.userDao();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insert_and_getByEmail_returnsUser() {
        User u = new User("Alice", "alice@example.com", "pw12345", false);
        userDAO.insert(u);

        User fromDb = userDAO.getUserByEmail("alice@example.com");
        assertNotNull(fromDb);
        assertEquals("Alice", fromDb.fullName);
        assertEquals("alice@example.com", fromDb.email);
    }

    @Test
    public void updatePassword_changesRow() {
        User u = new User("Bob", "bob@example.com", "oldpass", false);
        userDAO.insert(u);

        int changed = userDAO.updatePassword("bob@example.com", "newpass");
        assertEquals(1, changed);

        // now login should succeed with new password and fail with old
        assertNull(userDAO.getUser("bob@example.com", "oldpass"));
        assertNotNull(userDAO.getUser("bob@example.com", "newpass"));
    }

    @Test
    public void deleteByEmail_removesRow() {
        User u = new User("Carol", "carol@example.com", "secret", false);
        userDAO.insert(u);

        int removed = userDAO.deleteByEmail("carol@example.com");
        assertEquals(1, removed);

        assertNull(userDAO.getUserByEmail("carol@example.com"));
    }
}