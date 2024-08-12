package de.buw.se.UI;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SignInUITest {

    private SignInUI signInUI;

    @BeforeEach
    void setUp() {
        signInUI = new SignInUI(null); // Passing null as EventManagementSystem since it's not needed for this test
    }

    
    @Test
    void testIsValidInput() {
        assertTrue(signInUI.isValidInput("validInput"), "Non-empty input should return true");
        assertFalse(signInUI.isValidInput(""), "Empty input should return false");
    }

    
    @Test
    void testIsValidUsernameInput() {
        assertTrue(signInUI.isValidUsernameInput("test@example.com"), "Valid email should return true");
        assertTrue(signInUI.isValidUsernameInput("validUsername123"), "Valid username should return true");
        assertFalse(signInUI.isValidUsernameInput("invalid username"), "Invalid username should return false");
        assertFalse(signInUI.isValidUsernameInput("invalid-email"), "Invalid email should return false");
    }
}