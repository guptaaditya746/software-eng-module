package de.buw.se;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class passwordEngineTest {
    
    @Test
    public void testPasswordLength() {
        int length = 10;
        String password = generatePassword(length, true, true, true, true);
        assertEquals(length, password.length(), "Password length should be " + length);
    }

    @Test
    public void testIncludeLowerCase() {
        String password = generatePassword(10, true, false, false, false);
        assertTrue(password.chars().anyMatch(Character::isLowerCase), "Password should include at least one lowercase letter");
    }

    @Test
    public void testIncludeUpperCase() {
        String password = generatePassword(10, false, true, false, false);
        assertTrue(password.chars().anyMatch(Character::isUpperCase), "Password should include at least one uppercase letter");
    }

    @Test
    public void testIncludeNumbers() {
        String password = generatePassword(10, false, false, true, false);
        assertTrue(password.chars().anyMatch(Character::isDigit), "Password should include at least one number");
    }

    @Test
    public void testIncludeSpecialCharacters() {
        String password = generatePassword(10, false, false, false, true);
        String specialChars = "!@#$%^&*()_+[]{}|;:'><>?";
        assertTrue(password.chars().anyMatch(ch -> specialChars.indexOf(ch) >= 0), "Password should include at least one special character");
    }

    @Test
    public void testIncludeAllCharacterTypes() {
        String password = generatePassword(10, true, true, true, true);
        assertTrue(password.chars().anyMatch(Character::isLowerCase), "Password should include at least one lowercase letter");
        assertTrue(password.chars().anyMatch(Character::isUpperCase), "Password should include at least one uppercase letter");
        assertTrue(password.chars().anyMatch(Character::isDigit), "Password should include at least one number");
        String specialChars = "!@#$%^&*()_+[]{}|;:'><>?";
        assertTrue(password.chars().anyMatch(ch -> specialChars.indexOf(ch) >= 0), "Password should include at least one special character");
    }

    @Test
    public void testNoCharacterTypesSelected() {
        String password = generatePassword(10, false, false, false, false);
        assertEquals("", password, "Password should be an empty string if no character types are selected");
    }

    // Utility method to generate a random number for testing purposes.
    // Used maths package in java
    private int generateRandomNumber(int bound) {
        return (int) (Math.random() * bound);
    }

    // Placeholder for the actual generatePassword method.
    // In actual testing, this should call the method from the class where it is defined.
    private String generatePassword(int length, boolean includeLowerCase, boolean includeUpperCase, boolean includeNumbers, boolean includeSpecialCharacters) {
        // This should call the method from your class.
        return passwordEngine.generatePassword(length, includeLowerCase, includeUpperCase, includeNumbers, includeSpecialCharacters);
    }


}
