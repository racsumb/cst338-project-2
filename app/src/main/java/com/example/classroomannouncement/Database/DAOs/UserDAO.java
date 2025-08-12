package com.example.classroomannouncement.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.classroomannouncement.Database.Entities.User;

import java.util.List;

/**
 * Room DAO for the "users" table.
 * Every abstract method has exactly one Room annotation.
 */
@Dao
public interface UserDAO {

    /** Insert a new user row. */
    @Insert
    void insert(User user);

    /** Find a user by email + password (used at login). */
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User getUser(String email, String password);

    /** Find a user by email only. */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    /** Get all users (handy for tests/debug). */
    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    /** Update an existing user row. */
    @Update
    void update(User user);

    /** Delete a user row. */
    @Delete
    void delete(User user);

    /** Update only the password for a given email. Returns rows changed. */
    @Query("UPDATE users SET password = :newPassword WHERE email = :email")
    int updatePassword(String email, String newPassword);

    /** Delete by email. Returns rows removed. */
    @Query("DELETE FROM users WHERE email = :email")
    int deleteByEmail(String email);
}
