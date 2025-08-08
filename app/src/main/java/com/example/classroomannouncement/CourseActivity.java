package com.example.classroomannouncement;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.classroomannouncement.Database.UserDB;
import com.example.classroomannouncement.Database.Entities.Course;

import java.util.List;

/**
 * This screen lets you add a new course and see all courses.
 */
public class CourseActivity extends AppCompatActivity {

    private EditText courseNameEditText; // Box for typing course name
    private Button addCourseButton;      // Button to save the course
    private ListView coursesListView;    // List to show all courses
    private UserDB db;                   // Database connection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // Link UI elements to code
        courseNameEditText = findViewById(R.id.courseNameEditText);
        addCourseButton = findViewById(R.id.addCourseButton);
        coursesListView = findViewById(R.id.coursesListView);

        // Build the database
        db = Room.databaseBuilder(getApplicationContext(),
                        UserDB.class, "UserDB")
                .allowMainThreadQueries() // Only for quick testing!
                .fallbackToDestructiveMigration()
                .build();

        // Show all courses when we open the page
        loadCourses();

        // Add a new course when button is clicked
        addCourseButton.setOnClickListener(v -> {
            String name = courseNameEditText.getText().toString().trim();
            if (!name.isEmpty()) {
                db.courseDao().insert(new Course(name)); // Save to DB
                courseNameEditText.setText("");          // Clear input
                Toast.makeText(this, "Course added!", Toast.LENGTH_SHORT).show();
                loadCourses();                           // Refresh list
            } else {
                Toast.makeText(this, "Please enter a course name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Load all courses from the DB and show in ListView
    private void loadCourses() {
        List<Course> courses = db.courseDao().getAllCourses();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                // Convert Course objects to just their names
                courses.stream().map(c -> c.courseName).toArray(String[]::new)
        );
        coursesListView.setAdapter(adapter);
    }
}