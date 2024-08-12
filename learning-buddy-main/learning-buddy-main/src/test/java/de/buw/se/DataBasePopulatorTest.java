package de.buw.se;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataBasePopulatorTest {

    @BeforeAll
    public static void setUpClass() {
        try {
            // Clean the database and set up tables
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:./src/main/resources/books", "", "");
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS Questions");
            statement.executeUpdate("DROP TABLE IF EXISTS Categories");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Categories"
                    + "(CategoryID INT PRIMARY KEY AUTO_INCREMENT, "
                    + "Name VARCHAR(255) NOT NULL, "
                    + "StudyInformation CLOB)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Questions"
                    + "(QuestionID INT PRIMARY KEY AUTO_INCREMENT, "
                    + "CategoryID INT NOT NULL, "
                    + "Difficulty VARCHAR(255) NOT NULL, "
                    + "Question CLOB NOT NULL, "
                    + "Answers VARCHAR(1023) ARRAY NOT NULL, "
                    + "RightAnswer VARCHAR(1023) NOT NULL, "
                    + "FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID))");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Setup failed: " + e.getMessage());
        }
    }

    @Test
    public void testPopulateDatabase() {
        DataBasePopulator.populateDatabase();

        // Verify Categories
        List<Category> categories = DataStoreSql.readCategories();
        assertEquals(5, categories.size(), "There should be 5 categories");

        // Verify Questions for Chemistry
        List<Question> chemistryQuestions = DataStoreSql.readQuestionsbyCategory(1);
        assertEquals(6, chemistryQuestions.size(), "There should be 6 chemistry questions");

        // Verify Questions for History
        List<Question> historyQuestions = DataStoreSql.readQuestionsbyCategory(2);
        assertEquals(6, historyQuestions.size(), "There should be 6 history questions");

        // Verify Questions for Mathematics
        List<Question> mathematicsQuestions = DataStoreSql.readQuestionsbyCategory(3);
        assertEquals(6, mathematicsQuestions.size(), "There should be 6 mathematics questions");

        // Verify Questions for Physics
        List<Question> physicsQuestions = DataStoreSql.readQuestionsbyCategory(4);
        assertEquals(6, physicsQuestions.size(), "There should be 6 physics questions");

        // Verify Questions for Biology
        List<Question> biologyQuestions = DataStoreSql.readQuestionsbyCategory(5);
        assertEquals(6, biologyQuestions.size(), "There should be 6 biology questions");
    }

    @Test
    public void testAddCategoryFromFile() {
        String categoryName = "Geography";
        String filePath = "src/main/resources/Geography.txt";
        try {
            Files.write(Paths.get(filePath), "Study of the Earth''s physical features and human activity.".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Setup failed: " + e.getMessage());
        }

        DataBasePopulator.addCategoryFromFile(categoryName, filePath);

        List<Category> categories = DataStoreSql.readCategories();
        boolean categoryExists = categories.stream()
                .anyMatch(category -> category.getName().equals(categoryName));

        assertTrue(categoryExists, "The category should be added from the file");

        try {
            Files.delete(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            fail("Cleanup failed: " + e.getMessage());
        }
    }
}
