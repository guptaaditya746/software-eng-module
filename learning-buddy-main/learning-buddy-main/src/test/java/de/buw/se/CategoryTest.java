package de.buw.se;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void testGettersAndSetters() {
        // Create a Category object
        Category category = new Category(1, "Science", "Study of natural phenomena");

        // Test getters
        assertEquals("Science", category.getName());
        assertEquals("Study of natural phenomena", category.getStudy_information());
        assertEquals(1, category.getCategory_id());

        // Test setters with new values
        category.setName("Mathematics");
        category.setStudy_information("Study of numbers and shapes");

        // Verify using getters
        assertEquals("Mathematics", category.getName());
        assertEquals("Study of numbers and shapes", category.getStudy_information());

        // Additional setter test: Set new values again
        category.setName("Biology");
        category.setStudy_information("Study of living organisms");
        assertEquals("Biology", category.getName());
        assertEquals("Study of living organisms", category.getStudy_information());

        // Additional test: Object identity after setters
        Category originalCategory = category;
        category.setName("Physics");
        category.setStudy_information("Study of matter and energy");
        assertSame(originalCategory, category);
    }

    @Test
    public void testEmptyStringValidationDuringInitialization() {
        // Attempt to initialize with empty strings
        try {
            Category categoryWithEmptyStrings = new Category(2, "", "");
            fail("Expected IllegalArgumentException was not thrown for empty strings during initialization");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }
    }

    @Test
    public void testEmptyStringValidationAfterInitialization() {
        // Create a Category object with initial values
        Category category = new Category(2, "History", "Study of past events");

        // Attempt to set empty strings using setters after initialization
        try {
            category.setName("");
            fail("Expected IllegalArgumentException was not thrown for setting an empty name");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }

        try {
            category.setStudy_information("");
            fail("Expected IllegalArgumentException was not thrown for setting empty study information");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }

        // Verify that getters return the initial values
        assertEquals("History", category.getName());
        assertEquals("Study of past events", category.getStudy_information());
    }

    @Test
    public void testNullValueValidationDuringInitialization() {
        // Attempt to initialize with null values
        try {
            Category categoryWithNullValues = new Category(3, null, null);
            fail("Expected IllegalArgumentException was not thrown for null values during initialization");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }
    }

    @Test
    public void testNullValueValidationAfterInitialization() {
        // Create a Category object with initial values
        Category category = new Category(3, "Literature", "Study of literary works");

        // Attempt to set null values using setters after initialization
        try {
            category.setName(null);
            fail("Expected IllegalArgumentException was not thrown for setting a null name");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }

        try {
            category.setStudy_information(null);
            fail("Expected IllegalArgumentException was not thrown for setting null study information");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }

        // Verify that getters return the initial values
        assertEquals("Literature", category.getName());
        assertEquals("Study of literary works", category.getStudy_information());
    }

    @Test
    public void testSetNameToEmptyStringAfterSettingValidValue() {
        // Create a Category object with initial valid name
        Category category = new Category(4, "Art", "Study of visual arts");

        // Set a valid name first
        category.setName("Painting");
        assertEquals("Painting", category.getName());

        // Attempt to set name to empty string
        try {
            category.setName("");
            fail("Expected IllegalArgumentException was not thrown for setting name to empty string after setting a valid name");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }

        // Verify that the name remains as the valid value set previously
        assertEquals("Painting", category.getName());
    }

    @Test
    public void testSetStudyInformationToNullAfterSettingValidValue() {
        // Create a Category object with initial valid study information
        Category category = new Category(5, "Music", "Study of sound and rhythm");

        // Set a valid study information first
        category.setStudy_information("Study of musical compositions");
        assertEquals("Study of musical compositions", category.getStudy_information());

        // Attempt to set study information to null
        try {
            category.setStudy_information(null);
            fail("Expected IllegalArgumentException was not thrown for setting study information to null after setting a valid value");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }

        // Verify that the study information remains as the valid value set previously
        assertEquals("Study of musical compositions", category.getStudy_information());
    }

    @Test
    public void testSettersWithWhitespaceStrings() {
        // Create a Category object with initial values
        Category category = new Category(6, "Language", "Study of communication");

        // Attempt to set whitespace strings using setters
        try {
            category.setName("   ");
            fail("Expected IllegalArgumentException was not thrown for setting name to whitespace string");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }

        try {
            category.setStudy_information("   ");
            fail("Expected IllegalArgumentException was not thrown for setting study information to whitespace string");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }

        // Verify that getters return the initial values
        assertEquals("Language", category.getName());
        assertEquals("Study of communication", category.getStudy_information());
    }

    @Test
    public void testSetStudyInformationToEmptyStringAfterSettingValidValue() {
        // Create a Category object with initial valid study information
        Category category = new Category(7, "Chemistry", "Study of substances and their interactions");

        // Set a valid study information first
        category.setStudy_information("Study of chemical reactions");
        assertEquals("Study of chemical reactions", category.getStudy_information());

        // Attempt to set study information to empty string
        try {
            category.setStudy_information("");
            fail("Expected IllegalArgumentException was not thrown for setting study information to empty string after setting a valid value");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }

        // Verify that the study information remains as the valid value set previously
        assertEquals("Study of chemical reactions", category.getStudy_information());
    }

    @Test
    public void testSetNameToNullAfterSettingValidValue() {
        // Create a Category object with initial valid name
        Category category = new Category(8, "Psychology", "Study of mind and behavior");

        // Set a valid name first
        category.setName("Behavioral Psychology");
        assertEquals("Behavioral Psychology", category.getName());

        // Attempt to set name to null
        try {
            category.setName(null);
            fail("Expected IllegalArgumentException was not thrown for setting name to null after setting a valid name");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }

        // Verify that the name remains as the valid value set previously
        assertEquals("Behavioral Psychology", category.getName());
    }
}

