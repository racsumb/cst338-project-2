package com.example.classroomannouncement;

import com.example.classroomannouncement.Database.Entities.User;

// Interface for mocking UserRepo behavior in tests
public interface IUserRepo {
    User getUserByEmail(String email);
}