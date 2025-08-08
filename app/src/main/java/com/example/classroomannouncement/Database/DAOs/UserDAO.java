package com.example.classroomannouncement.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.classroomannouncement.Database.Entities.User;

import java.util.List;

/**
 * Data Access Object (DAO) for interacting with the "users" table in the database.
 * This interface defines all the database operations related to users.
 */
@Dao
public interface UserDAO {

    /**
     * Inserts a new user into the "users" table.
     * @param user The user object to be inserted.
     */
    @Insert
    void insert(User user);

    /**
     * Updates an existing user in the "users" table.
     * @param user The user object containing updated information.
     */
    @Update
    void update(User user);

    /**
     * Deletes a specific user from the "users" table.
     * @param user The user object to be removed.
     */
    @Delete
    void delete(User user);

    /**
     * Retrieves a user that matches the provided email and password.
     * Used for login authentication.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The matching User object, or null if not found.
     */
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User getUser(String email, String password);

    /**
     * Retrieves a user that matches the provided email.
     * Used to check if an account with this email already exists.
     * @param email The email of the user.
     * @return The matching User object, or null if not found.
     */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    /**
     * Retrieves all users from the "users" table.
     * Typically used for testing or debugging.
     * @return A list of all User objects.
     */
    @Query("SELECT * FROM users")
    List<User> getAllUsers();
}
