package com.example.classroomannouncement.Database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class defines a User for our database.
 * Room will create a table called "users" based on this class.
 */
@Entity(tableName = "users")
public class User {

    /**
     * id: A unique number for each user (auto-generated)
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * fullName: The user's full name
     */
    public String fullName;

    /**
     * email: The user's email (used to log in)
     */
    public String email;

    /**
     * password: The user's password
     */
    public String password;

    /**
     * isAdmin: true if the user is a teacher/admin, false if a student
     */
    public boolean isAdmin;

    // Constructor
    public User(String fullName, String email, String password, boolean isAdmin) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return fullName;
    }

}
