package com.example.classroomannouncement.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.classroomannouncement.Database.Entities.Course;

import java.util.List;

/**
 * Data Access Object (DAO) for interacting with the "courses" table in the database.
 * This interface defines all the database operations related to courses.
 */
@Dao
public interface CourseDAO {

    /**
     * Inserts a new course into the "courses" table.
     * @param course The course object to be inserted.
     */
    @Insert
    void insert(Course course);

    /**
     * Updates an existing course in the "courses" table.
     * @param course The course object containing updated information.
     */
    @Update
    void update(Course course);

    /**
     * Deletes a specific course from the "courses" table.
     * @param course The course object to be removed.
     */
    @Delete
    void delete(Course course);

    /**
     * Retrieves all courses from the "courses" table.
     * @return A list of all Course objects.
     */
    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();
}