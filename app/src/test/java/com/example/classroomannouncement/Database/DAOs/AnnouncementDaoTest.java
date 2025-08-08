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
 * Minimal insert / update / delete test for the announcements table.
 * - Uses in-memory Room DB
 * - Uses only your existing DAO methods: insert(), update(), delete(),
 *   and getAllAnnouncementsSync() (no LiveData helpers).
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AnnouncementDaoTest {

    private UserDB db;
    private AnnouncementDao dao;

    @Before
    public void setUp() {
        Context ctx = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(ctx, UserDB.class)
                .allowMainThreadQueries() // OK for tests
                .build();
        dao = db.announcementDao();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insert_update_delete_roundTrip() {
        long now = System.currentTimeMillis();

        // INSERT
        Announcement a = new Announcement("Welcome", "Class starts Monday!", now);
        dao.insert(a);

        List<Announcement> all = dao.getAllAnnouncementsSync();
        assertEquals(1, all.size());
        Announcement saved = all.get(0);
        assertEquals("Welcome", saved.getTitle());
        assertEquals("Class starts Monday!", saved.getBody());

        // UPDATE
        saved.setTitle("Updated Title");
        saved.setBody("Class starts Tuesday!");
        dao.update(saved);

        List<Announcement> afterUpdate = dao.getAllAnnouncementsSync();
        assertEquals(1, afterUpdate.size());
        assertEquals("Updated Title", afterUpdate.get(0).getTitle());
        assertEquals("Class starts Tuesday!", afterUpdate.get(0).getBody());

        // DELETE
        dao.delete(afterUpdate.get(0));
        assertTrue(dao.getAllAnnouncementsSync().isEmpty());
    }
}