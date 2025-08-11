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
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * This screen lets you add a new course and see all courses.
 */
public class CourseActivity extends AppCompatActivity {

    private EditText courseNameEditText;
    private Button addCourseButton;
    private ListView coursesListView;
    private UserDB db;
    private final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // Link UI elements to code
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        courseNameEditText = findViewById(R.id.courseNameEditText);
        addCourseButton = findViewById(R.id.addCourseButton);
        coursesListView = findViewById(R.id.coursesListView);

        db = Room.databaseBuilder(getApplicationContext(),
                        UserDB.class, "classroom_database")
                .fallbackToDestructiveMigration()
                .build();

        // Show all courses when we open the page
        loadCourses();

        // Add a new course when button is clicked
        addCourseButton.setOnClickListener(v -> {
            String name = courseNameEditText.getText().toString().trim();
            if (!name.isEmpty()) {
                databaseWriteExecutor.execute(() -> {
                    db.courseDao().insert(new Course(name));
                    runOnUiThread(() -> {
                        courseNameEditText.setText("");
                        Toast.makeText(CourseActivity.this, "Course added!", Toast.LENGTH_SHORT).show();
                        loadCourses();
                    });
                });
            } else {
                Toast.makeText(this, "Please enter a course name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Load all courses from the DB and show in ListView
    private void loadCourses() {
        databaseWriteExecutor.execute(() -> {
            List<Course> courses = db.courseDao().getAllCourses();
            List<String> courseNames = courses.stream()
                    .map(course -> course.courseName)
                    .collect(Collectors.toList());

            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        courseNames
                );
                coursesListView.setAdapter(adapter);
            });
        });
    }
}