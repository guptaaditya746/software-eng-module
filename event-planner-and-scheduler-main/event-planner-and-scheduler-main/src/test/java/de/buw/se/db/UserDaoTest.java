package de.buw.se.db;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.*;

import de.buw.se.model.User;

class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDao();
       ;
    } 

    @AfterEach
    void tearDown() {
        // Optionally, clean up database state after each test
        // Example: Delete test data or truncate tables
    	
    }

    @Test
    void testAddUser() throws SQLException {
        // Test adding a new user
        User user = new User(0, "test@example.com", "testuser", "password");
        assertTrue(userDao.addUser(user), " added user");

        // Test adding the same user again (should fail due to uniqueness constraint)
        assertFalse(userDao.addUser(user), "User was added again with same credentials");
    }

    @Test
    void testIsEmailUnique() throws SQLException {
        // Test when email is unique
        assertFalse(userDao.isEmailUnique("newemail@example.com"), "Email should be unique");

        // Add a user with this email
        User user = new User(0, "newemail@example.com", "newuser", "password");
        userDao.addUser(user);

        // Test when email is not unique anymore
        assertFalse(userDao.isEmailUnique("newemail@example.com"), "Email should not be unique");
    }

    @Test
    void testIsUsernameUnique() throws SQLException {
        // Test when username is unique
        assertFalse(userDao.isUsernameUnique("newuser"), "Username should be unique");

        // Add a user with this username
        User user = new User(0, "another@example.com", "newuser", "password");
        userDao.addUser(user);

        // Test when username is not unique anymore
        assertFalse(userDao.isUsernameUnique("newuser"), "Username should not be unique");
    }

    @Test
    void testGetUserByUsername() throws SQLException {
        // Add a user to the database
        User user = new User(0, "finduser@example.com", "finduser", "password");
        userDao.addUser(user);

        // Test retrieving user by username
        User retrievedUserByUsername = userDao.getUserByUsername("finduser");
        assertNotNull(retrievedUserByUsername, "User not found by username");
        assertEquals("finduser@example.com", retrievedUserByUsername.getEmail(), "Incorrect email retrieved");

        // Test retrieving user by email (alternative search method)
        User retrievedUserByEmail = userDao.getUserByUsername("finduser@example.com");
        assertNotNull(retrievedUserByEmail, "User not found by email");
        assertEquals("finduser", retrievedUserByEmail.getUsername(), "Incorrect username retrieved");
    }
}