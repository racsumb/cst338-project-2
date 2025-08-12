package com.example.classroomannouncement;

import org.junit.Test;
import static org.junit.Assert.*;

public class SettingsPageUnitTests {

    @Test
    public void testWelcomeMessage_withValidName() {
        String result = SettingsPage.getWelcomeMessage("Taylor");
        assertEquals("Welcome, Taylor", result);
    }

    @Test
    public void testWelcomeMessage_withNullName() {
        String result = SettingsPage.getWelcomeMessage(null);
        assertEquals("Welcome, User", result);
    }

    @Test
    public void testRoleLabel_forAdmin() {
        String result = SettingsPage.getRoleLabel(true);
        assertEquals("Admin", result);
    }

    @Test
    public void testRoleLabel_forStudent() {
        String result = SettingsPage.getRoleLabel(false);
        assertEquals("Student", result);
    }
}
