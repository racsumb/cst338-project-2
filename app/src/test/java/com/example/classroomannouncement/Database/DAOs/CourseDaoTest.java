package com.example.classroomannouncement.Database.DAOs;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;

import com.example.classroomannouncement.Database.DAOs.CourseDAO;
import com.example.classroomannouncement.Database.Entities.Course;
import com.example.classroomannouncement.Database.UserDB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.RuntimeEnvironment;

/**
 * Simple, separate tests for insert, update, and delete on Course table.
 * Runs on JVM with Robolectric (no emulator needed).
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class CourseDaoTest {

    // In‑memory DB so nothing touches disk
    private UserDB db;
    // Data access object we’re testing
    private CourseDAO courseDAO;

    @Before
    public void setUp() {
        // Get a plain Context from Robolectric
        Context ctx = RuntimeEnvironment.getApplication();

        // Build in‑memory Room database
        db = Room.inMemoryDatabaseBuilder(ctx, UserDB.class)
                .allowMainThreadQueries()   // OK for tests
                .build();

        // Grab the DAO we’re testing
        courseDAO = db.courseDao();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insert_insertsOneRow() {
        // Create a row
        Course c = new Course("Math 101");
        // Insert it
        courseDAO.insert(c);

        // Fetch it back by some query you already have, e.g., getAll or by name
        Course fetched = courseDAO.getCourseByName("Math 101"); // add this query if needed
        assertNotNull(fetched);
        assertEquals("Math 101", fetched.courseName);
    }

    @Test
    public void update_updatesRow() {
        Course c = new Course("Temp");
        courseDAO.insert(c);

        // Re‑read to get the generated id (or return long from insert and set it)
        Course saved = courseDAO.getCourseByName("Temp");
        assertNotNull(saved);

        // Change a field, then update
        saved.courseName = "History 201";
        courseDAO.update(saved);

        // Verify the change
        Course updated = courseDAO.getCourseByName("History 201");
        assertNotNull(updated);
        assertEquals("History 201", updated.courseName);
        // Old value should be gone
        assertNull(courseDAO.getCourseByName("Temp"));
    }

    @Test
    public void delete_removesRow() {
        Course c = new Course("ToRemove");
        courseDAO.insert(c);

        Course saved = courseDAO.getCourseByName("ToRemove");
        assertNotNull(saved);

        // Delete it
        courseDAO.delete(saved);

        // Verify it’s gone
        assertNull(courseDAO.getCourseByName("ToRemove"));
    }
}