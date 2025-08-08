package com.example.classroomannouncement.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.classroomannouncement.Database.Entities.Course;

import java.util.List;

/**
 * DAO (Data Access Object) for interacting with the Course table.
 */
@Dao
public interface CourseDAO {

    // Insert a new course
    @Insert
    void insert(Course course);

    // Get all courses
    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();

    // Delete a specific course
    @Delete
    void delete(Course course);
}