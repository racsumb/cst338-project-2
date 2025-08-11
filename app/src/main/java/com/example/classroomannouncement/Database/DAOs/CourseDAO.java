package com.example.classroomannouncement.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.classroomannouncement.Database.Entities.Course;

import java.util.List;

/**
 * DAO for the Course entity stored in table "courses".
 */
@Dao
public interface CourseDAO {

    /** Insert a single course; returns new rowId */
    @Insert
    long insert(Course c);

    /** Update a single course; returns rows updated */
    @Update
    int update(Course c);

    /** Delete a single course; returns rows deleted */
    @Delete
    int delete(Course c);

    /**
     * Used by the APP UI (matches CourseActivity):
     * returns all courses synchronously.
     */
    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();          // <-- CourseActivity uses this

    /**
     * Used by tests (if you kept earlier test naming).
     * You can keep or remove this if not needed.
     */
    @Query("SELECT * FROM courses")
    List<Course> getAllCoursesSync();

    /** Helper used by tests */
    @Query("SELECT * FROM courses WHERE courseName = :name LIMIT 1")
    Course getCourseByName(String name);

}