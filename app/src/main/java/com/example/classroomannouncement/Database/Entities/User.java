package com.example.classroomannouncement.Database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class defines a User for our Room database.
 * Room will automatically turn this into a "users" table.
 */
@Entity(tableName = "users")
public class User {

    /**
     * id: A unique number for each user (automatically created)
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * fullName: The user's full name (e.g., "Jane Doe")
     */
    public String fullName;

    /**
     * email: The user's email address, used to log in
     */
    public String email;

    /**
     * password: The user's password, used to log in
     */
    public String password;

    /**
     * isAdmin: true if the user is a teacher/admin, false if they are a student
     */
    public boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }


    /**
     * Constructor: Creates a new user with name, email, password, and admin status
     *
     * @param fullName The user's full name
     * @param email The user's email address
     * @param password The user's login password
     * @param isAdmin Whether the user is an admin (true) or student (false)
     */
    public User(String fullName, String email, String password, boolean isAdmin) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    /**
     * Returns the user's full name.
     *
     * @return full name
     */
    public String getName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.fullName = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Returns whether the user is an admin or not.
     *
     * @return true if admin, false if student
     */
//    public boolean isAdmin() {
//    }
    public String getEmail() {
        return email;
    }
}