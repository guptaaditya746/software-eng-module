package de.buw.se.backend.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;
    private int id;
    private String email;
    private String username;
    private String fullName;
    private String password;

    @BeforeEach
    public void setUp() {
        id = 1;
        email = "user@example.com";
        username = "username";
        fullName = "User FullName";
        password = "password123";
        user = new User(id, email, username, fullName, password);
    }

    @Test
    public void testConstructor() {
        assertNotNull(user);
    }

    @Test
    public void testGetId() {
        assertEquals(id, user.getId());
    }

    @Test
    public void testSetId() {
        int newId = 2;
        user.setId(newId);
        assertEquals(newId, user.getId());
    }

    @Test
    public void testGetEmail() {
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testSetEmail() {
        String newEmail = "newuser@example.com";
        user.setEmail(newEmail);
        assertEquals(newEmail, user.getEmail());
    }

    @Test
    public void testGetUsername() {
        assertEquals(username, user.getUsername());
    }

    @Test
    public void testSetUsername() {
        String newUsername = "newusername";
        user.setUsername(newUsername);
        assertEquals(newUsername, user.getUsername());
    }

    @Test
    public void testGetFullName() {
        assertEquals(fullName, user.getFullName());
    }

    @Test
    public void testSetFullName() {
        String newFullName = "New User FullName";
        user.setFullName(newFullName);
        assertEquals(newFullName, user.getFullName());
    }

    @Test
    public void testGetPassword() {
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testSetPassword() {
        String newPassword = "newpassword123";
        user.setPassword(newPassword);
        assertEquals(newPassword, user.getPassword());
    }
}
