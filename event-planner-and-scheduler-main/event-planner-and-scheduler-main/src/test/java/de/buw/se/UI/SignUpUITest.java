package de.buw.se.UI;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SignUpUITest {

    @Test
    void testIsValidUsername() {
        // Test valid username
        assertTrue(SignUpUI.isValidUsername("username123"));

        // Test invalid username (contains special characters)
        assertFalse(SignUpUI.isValidUsername("user@name"));

        // Test invalid username (contains spaces)
        assertFalse(SignUpUI.isValidUsername("user name"));
        
    }

    @Test
    void testIsValidEmail() {
        // Test valid email
        assertTrue(SignUpUI.isValidEmail("example@example.com"));

        // Test invalid email (missing @)
        assertFalse(SignUpUI.isValidEmail("exampleexample.com"));

        // Test invalid email (missing domain)
        assertFalse(SignUpUI.isValidEmail("example@.com"));

        // Test invalid email (missing username)
        assertFalse(SignUpUI.isValidEmail("@example.com"));
    }

    @Test
    void testIsValidPassword() {
        // Test valid password
        assertTrue(SignUpUI.isValidPassword("password123"));

        // Test invalid password (less than 6 characters)
        assertFalse(SignUpUI.isValidPassword("pass"));

        // Test invalid password (contains spaces)
        assertFalse(SignUpUI.isValidPassword("pass word"));

        // Test invalid password (contains special characters)
        assertFalse(SignUpUI.isValidPassword("pass@word"));
    }

    // Add more test methods as needed...
}