package com.example.classroomannouncement.Database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a Course table in the database.
 * This is your 3rd table to meet the persistence requirement.
 */
@Entity(tableName = "courses")
public class Course {

    // Unique ID for each course (auto-generated)
    @PrimaryKey(autoGenerate = true)
    public int id;

    // The name of the course (e.g., "Math 101")
    public String courseName;

    /**
     * Constructor to create a course object.
     * @param courseName Name of the course.
     */
    public Course(String courseName) {
        this.courseName = courseName;
    }
}