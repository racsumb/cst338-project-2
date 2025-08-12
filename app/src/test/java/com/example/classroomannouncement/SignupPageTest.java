package com.example.classroomannouncement;

import com.example.classroomannouncement.Database.Entities.User;
import com.example.classroomannouncement.IUserRepo;
import org.junit.Test;
import static org.junit.Assert.*;

// Unit tests for SignupPage.validateSignupInput()
public class SignupPageTest {

    // A fake version of UserRepo that works in unit tests
    static class FakeUserRepo implements IUserRepo {
        private User dummyUser;

        // Set a user to return when getUserByEmail is called
        public void setDummyUser(User user) {
            this.dummyUser = user;
        }

        @Override
        public User getUserByEmail(String email) {
            if (dummyUser != null && dummyUser.getEmail().equals(email)) {
                return dummyUser;
            }
            return null;
        }
    }

    @Test
    public void testEmptyFields() {
        FakeUserRepo repo = new FakeUserRepo();
        String result = SignupPage.validateSignupInput("", "", "", repo);
        assertEquals("Please fill all fields", result);
    }

    @Test
    public void testShortPassword() {
        FakeUserRepo repo = new FakeUserRepo();
        String result = SignupPage.validateSignupInput("Jane", "jane@example.com", "123", repo);
        assertEquals("Password must be at least 6 characters", result);
    }

    @Test
    public void testEmailAlreadyRegistered() {
        FakeUserRepo repo = new FakeUserRepo();
        repo.setDummyUser(new User("Jane", "jane@example.com", "123456", false));
        String result = SignupPage.validateSignupInput("Jane", "jane@example.com", "abcdef", repo);
        assertEquals("Email already registered!", result);
    }

    @Test
    public void testValidSignup() {
        FakeUserRepo repo = new FakeUserRepo();
        String result = SignupPage.validateSignupInput("John", "john@example.com", "abcdef", repo);
        assertEquals("Signup successful!", result);
    }
}