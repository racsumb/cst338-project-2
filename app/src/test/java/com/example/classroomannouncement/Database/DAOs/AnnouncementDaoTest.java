package com.example.classroomannouncement.Database;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;

import com.example.classroomannouncement.Database.DAOs.AnnouncementDao;
import com.example.classroomannouncement.Database.Entities.Announcement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

/**
 * Simple, separate tests for insert, update, and delete on the Announcement table.
 * Runs entirely on the JVM using Robolectric, so no emulator or device is needed.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AnnouncementDaoTest {

    // In-memory database so changes are not persisted to disk
    private UserDB db;

    // Data Access Object (DAO) for the Announcement table
    private AnnouncementDao announcementDao;

    @Before
    public void setUp() {
        // Get a plain Context from Robolectric
        Context ctx = RuntimeEnvironment.getApplication();

        // Build an in-memory Room database for testing
        db = Room.inMemoryDatabaseBuilder(ctx, UserDB.class)
                .allowMainThreadQueries() // main thread OK for tests
                .build();

        // Get the DAO we will be testing
        announcementDao = db.announcementDao();
    }

    @After
    public void tearDown() {
        // Close the database after each test
        db.close();
    }

    @Test
    public void insert_insertsOneRow() {
        // Create an announcement row
        long now = System.currentTimeMillis();
        Announcement a = new Announcement("Welcome", "Class starts Monday!", now);

        // Insert the announcement
        announcementDao.insert(a);

        // Get all announcements back from the DB
        List<Announcement> all = announcementDao.getAllAnnouncementsSync();

        // Verify there is exactly one announcement
        assertEquals(1, all.size());

        // Verify the title and body match what we inserted
        Announcement saved = all.get(0);
        assertEquals("Welcome", saved.getTitle());
        assertEquals("Class starts Monday!", saved.getBody());
    }

    @Test
    public void update_updatesRow() {
        // Insert an initial announcement
        long now = System.currentTimeMillis();
        Announcement a = new Announcement("Welcome", "Class starts Monday!", now);
        announcementDao.insert(a);

        // Get the saved announcement from the DB
        Announcement toUpdate = announcementDao.getAllAnnouncementsSync().get(0);

        // Change its title and body
        toUpdate.setTitle("Updated Title");
        toUpdate.setBody("Class starts Tuesday!");

        // Update the row in the database
        announcementDao.update(toUpdate);

        // Fetch all announcements again
        List<Announcement> after = announcementDao.getAllAnnouncementsSync();

        // Verify the changes took effect
        assertEquals(1, after.size());
        assertEquals("Updated Title", after.get(0).getTitle());
        assertEquals("Class starts Tuesday!", after.get(0).getBody());
    }

    @Test
    public void delete_removesRow() {
        // Insert a row we plan to delete
        long now = System.currentTimeMillis();
        Announcement a = new Announcement("ToRemove", "Body", now);
        announcementDao.insert(a);

        // Get the saved announcement
        Announcement saved = announcementDao.getAllAnnouncementsSync().get(0);

        // Delete it
        announcementDao.delete(saved);

        // Verify the table is now empty
        assertTrue(announcementDao.getAllAnnouncementsSync().isEmpty());
    }
}