package com.example.classroomannouncement.Database.DAOs;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.classroomannouncement.Database.Entities.Announcement;
import com.example.classroomannouncement.Database.UserDB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

/**
 * Three independent tests for the announcements table:
 * 1) insert
 * 2) update
 * 3) delete
 *
 * Uses an in-memory Room DB (no emulator, no disk IO).
 * Sticks to your existing DAO methods and entity getters/setters.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AnnouncementDaoTest {

    private UserDB db;            // in-memory database instance used only for tests
    private AnnouncementDao dao;  // DAO under test

    @Before
    public void setUp() {
        // Get a test Context from Robolectric
        Context ctx = ApplicationProvider.getApplicationContext();

        // Build an in-memory database (safe for unit tests)
        db = Room.inMemoryDatabaseBuilder(ctx, UserDB.class)
                .allowMainThreadQueries() // fine for tests
                .build();

        // Grab the DAO from the test DB
        dao = db.announcementDao();
    }

    @After
    public void tearDown() {
        // Always close the DB after each test to free resources
        db.close();
    }

    /**
     * INSERT: Insert one announcement and verify it exists.
     */
    @Test
    public void insert_insertsAnnouncement() {
        long now = System.currentTimeMillis();

        // Insert
        Announcement a = new Announcement("Welcome", "Class starts Monday!", now);
        dao.insert(a);

        // Verify
        List<Announcement> all = dao.getAllAnnouncementsSync();
        assertEquals(1, all.size());
        Announcement saved = all.get(0);
        assertEquals("Welcome", saved.getTitle());
        assertEquals("Class starts Monday!", saved.getBody());
    }

    /**
     * UPDATE: Insert, modify title/body, call update(), verify changes persisted.
     */
    @Test
    public void update_updatesAnnouncementFields() {
        long now = System.currentTimeMillis();

        // Arrange: insert one row first
        Announcement a = new Announcement("Welcome", "Class starts Monday!", now);
        dao.insert(a);

        // Act: update fields and persist
        List<Announcement> before = dao.getAllAnnouncementsSync();
        Announcement toUpdate = before.get(0);
        toUpdate.setTitle("Updated Title");
        toUpdate.setBody("Class starts Tuesday!");
        dao.update(toUpdate);

        // Assert: read back and confirm
        List<Announcement> after = dao.getAllAnnouncementsSync();
        assertEquals(1, after.size());
        assertEquals("Updated Title", after.get(0).getTitle());
        assertEquals("Class starts Tuesday!", after.get(0).getBody());
    }

    /**
     * DELETE: Insert, delete the same row, verify the table is empty.
     */
    @Test
    public void delete_deletesAnnouncement() {
        long now = System.currentTimeMillis();

        // Arrange: insert one row first
        Announcement a = new Announcement("Welcome", "Class starts Monday!", now);
        dao.insert(a);

        // Act: delete it
        Announcement saved = dao.getAllAnnouncementsSync().get(0);
        dao.delete(saved);

        // Assert: nothing remains
        assertTrue(dao.getAllAnnouncementsSync().isEmpty());
    }
}