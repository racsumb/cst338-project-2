package com.example.classroomannouncement.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.classroomannouncement.Database.Entities.User;

import java.util.List;

/**
 * This file is like a list of commands we can ask the database to do
 * for the "users" table.
 */
@Dao
public interface UserDAO {

    /**
     * Add a new user to the database.
     * Example: When someone signs up, we use this to save them.
     */
    @Insert
    void insert(User user);

    /**
     * Look for a user with the same email and password.
     * Example: Used when someone tries to log in.
     * LIMIT 1 means just get the first match.
     */
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User getUser(String email, String password);

    /**
     * Look for a user by email only.
     * Example: To check if someone already signed up with this email.
     */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    /**
     * Get all users in the database.
     * Usually for testing or debugging, not for normal app use.
     */
    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @androidx.room.Update
    void update(User user);

}
