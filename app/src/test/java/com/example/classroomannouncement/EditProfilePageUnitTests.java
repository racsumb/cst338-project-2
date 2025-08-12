package com.example.classroomannouncement;

import org.junit.Test;
import static org.junit.Assert.*;

public class EditProfilePageUnitTests {

    @Test
    public void testValidName_withNonEmptyName() {
        assertTrue(EditProfilePage.isValidName("Taylor"));
    }

    @Test
    public void testValidName_withEmptyName() {
        assertFalse(EditProfilePage.isValidName(""));
    }

    @Test
    public void testPasswordChangeAllowed_withDifferentPasswords() {
        assertTrue(EditProfilePage.isPasswordChangeAllowed("oldPass123", "newPass456"));
    }

    @Test
    public void testPasswordChangeAllowed_withSamePasswords() {
        assertFalse(EditProfilePage.isPasswordChangeAllowed("samePass", "samePass"));
    }
}
