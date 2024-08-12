package de.buw.se;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DataStoreSqlTest {
    private static Connection connection;

    @BeforeAll
    public static void setUpClass() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./src/main/resources/books", "", "");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Setup failed: " + e.getMessage());
        }
    }

    @BeforeEach
    public void setUp() {
        try {
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
                    + "Answers VARCHAR(1023) NOT NULL, "
                    + "RightAnswer VARCHAR(1023) NOT NULL, "
                    + "FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID))");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Setup failed: " + e.getMessage());
        }
    }

    @AfterAll
    public static void tearDownClass() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Teardown failed: " + e.getMessage());
        }
    }

    @Test
    public void testAddCategory() {
        String categoryName = "Science";
        String studyInfo = "Study of natural phenomena";

        DataStoreSql.addCategory(categoryName, studyInfo);

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Categories WHERE Name = '" + categoryName + "'");
            assertTrue(rs.next(), "Category should exist");
            assertEquals(categoryName, rs.getString("Name"), "Category name should match");
            assertEquals(studyInfo, rs.getString("StudyInformation"), "Study information should match");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database query failed: " + e.getMessage());
        }
    }

    @Test
    public void testAddQuestion() {
        DataStoreSql.addCategory("Science", "Study of natural phenomena");
        List<Category> categories = DataStoreSql.readCategories();
        int categoryId = categories.get(0).getCategory_id();

        String difficulty = "Medium";
        String questionText = "What is the speed of light?";
        String[] answers = {"299,792,458 m/s", "150,000,000 m/s", "300,000,000 m/s", "450,000,000 m/s"};
        String rightAnswer = "299,792,458 m/s";

        DataStoreSql.addQuestion(categoryId, difficulty, questionText, answers, rightAnswer);

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Questions WHERE Question = '" + questionText + "'");
            assertTrue(rs.next(), "Question should exist");
            assertEquals(categoryId, rs.getInt("CategoryID"), "Category ID should match");
            assertEquals(difficulty, rs.getString("Difficulty"), "Difficulty should match");
            assertEquals(questionText, rs.getString("Question"), "Question text should match");
            String[] storedAnswers = rs.getString("Answers").split(", ");
            assertArrayEquals(answers, storedAnswers, "Answers should match");
            assertEquals(rightAnswer, rs.getString("RightAnswer"), "Right answer should match");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database query failed: " + e.getMessage());
        }
    }

    @Test
    public void testReadCategories() {
        DataStoreSql.addCategory("History", "Study of past events");
        DataStoreSql.addCategory("Mathematics", "Study of numbers and shapes");

        List<Category> categories = DataStoreSql.readCategories();
        assertTrue(categories.size() >= 2, "There should be at least two categories");

        Category category1 = categories.get(categories.size() - 2);  // Assuming categories are appended
        assertEquals("History", category1.getName(), "First category name should match");
        assertEquals("Study of past events", category1.getStudy_information(), "First category study information should match");

        Category category2 = categories.get(categories.size() - 1);  // Assuming categories are appended
        assertEquals("Mathematics", category2.getName(), "Second category name should match");
        assertEquals("Study of numbers and shapes", category2.getStudy_information(), "Second category study information should match");
    }

    @Test
    public void testReadQuestionsByCategory() {
        DataStoreSql.addCategory("Science", "Study of natural phenomena");
        List<Category> categories = DataStoreSql.readCategories();
        int categoryId = categories.get(0).getCategory_id();

        String difficulty = "Easy";
        String questionText = "What is 2 + 2?";
        String[] answers = {"3", "4", "5", "6"};
        String rightAnswer = "4";

        DataStoreSql.addQuestion(categoryId, difficulty, questionText, answers, rightAnswer);

        List<Question> questions = DataStoreSql.readQuestionsbyCategory(categoryId);
        assertTrue(questions.size() >= 1, "There should be at least one question");

        Question question = questions.get(questions.size() - 1);  // Assuming questions are appended
        assertEquals(categoryId, question.getCategoryId(), "Category ID should match");
        assertEquals(difficulty, question.getDifficulty(), "Difficulty should match");
        assertEquals(questionText, question.getQuestion(), "Question text should match");
        assertArrayEquals(answers, question.getAnswers(), "Answers should match");  // Fails due to incorrect splitting of answers in main class
        assertEquals(rightAnswer, question.getRightAnswer(), "Right answer should match");
    }
}
