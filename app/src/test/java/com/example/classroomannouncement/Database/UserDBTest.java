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
 * Local unit tests for Room UserDB (runs on JVM with Robolectric).
 */
@RunWith(RobolectricTestRunner.class)
// We aren't using the app manifest in unit tests
@Config(manifest = Config.NONE)
public class UserDBTest {

    private UserDB db;
    private UserDAO userDAO;

    @Before
    public void setUp() {
        // Get a Context from Robolectric (this is what fixed your NPE)
        Context context = ApplicationProvider.getApplicationContext();

        // In-memory Room DB so tests are fast and isolated
        db = Room.inMemoryDatabaseBuilder(context, UserDB.class)
                .allowMainThreadQueries() // okay in tests
                .build();

        userDAO = db.userDao();
    }

    @After
    public void tearDown() {
        if (db != null) db.close();
    }

    @Test
    public void insert_and_getUserByEmail_returnsSameUser() {
        User u = new User("Jane Doe", "jane@example.com", "secret123", false);
        userDAO.insert(u);

        User fromDb = userDAO.getUserByEmail("jane@example.com");
        assertNotNull(fromDb);
        assertEquals("jane@example.com", fromDb.email);
        assertEquals("Jane Doe", fromDb.fullName);
    }

    @Test
    public void getUser_withWrongPassword_returnsNull() {
        User u = new User("John", "john@example.com", "abcdef", false);
        userDAO.insert(u);

        // wrong password -> should be null
        User result = userDAO.getUser("john@example.com", "WRONG");
        assertNull(result);
    }
}