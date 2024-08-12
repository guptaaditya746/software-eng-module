package de.buw.se.service;

import de.buw.se.db.UserDao;
import de.buw.se.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        // Injecting a stubbed UserDao
        userService.userDao = new UserDao();
    }

    

     @Test
    void testLoginUser_Failure_WrongPassword() {
        userService.registerUser("test@example.com", "testuser", "password123");
        LoginResult loginResult = userService.loginUser("testuser", "wrongpassword");
        
        assertFalse(loginResult.isSuccess());
        assertNull(loginResult.getUserData());
    }

    @Test
    void testLoginUser_Failure_UserNotFound() {
        LoginResult loginResult = userService.loginUser("nonexistentuser", "password123");
        assertFalse(loginResult.isSuccess());
        assertNull(loginResult.getUserData());
    }
}