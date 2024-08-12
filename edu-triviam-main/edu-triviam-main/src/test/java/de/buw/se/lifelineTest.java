package de.buw.se;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class lifelineTest {
    private QuizView quizView;
    private QuestionDatabase questionDatabase;
    private QuizController quizController;

    @BeforeEach
    public void setUp() {
        quizView = new QuizView();
        questionDatabase = new QuestionDatabase();
        quizController = new QuizController(questionDatabase, quizView);
    }

    private void simulateSystemIn(String input) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }

    @Test
    public void testChooseTopic() {
        simulateSystemIn("1\n");  // Simulating user input for "General Knowledge"
        String topic = QuizView.chooseTopic();
        assertEquals("General Knowledge", topic);

        simulateSystemIn("2\n");  // Simulating user input for "Geography"
        topic = QuizView.chooseTopic();
        assertEquals("Geography", topic);

        simulateSystemIn("3\n");  // Simulating user input for "Maths"
        topic = QuizView.chooseTopic();
        assertEquals("Maths", topic);

        simulateSystemIn("4\n");  // Simulating user input for "Literature"
        topic = QuizView.chooseTopic();
        assertEquals("Literature", topic);

        simulateSystemIn("5\n");  // Simulating user input for "Science"
        topic = QuizView.chooseTopic();
        assertEquals("Science", topic);
    }

    @Test
    public void testChooseDifficulty() {
        simulateSystemIn("1\n");  // Simulating user input for "Easy"
        String difficulty = QuizView.chooseDifficulty();
        assertEquals("Easy", difficulty);

        simulateSystemIn("2\n");  // Simulating user input for "Medium"
        difficulty = QuizView.chooseDifficulty();
        assertEquals("Medium", difficulty);

        simulateSystemIn("3\n");  // Simulating user input for "Hard"
        difficulty = QuizView.chooseDifficulty();
        assertEquals("Hard", difficulty);
    }

    @Test
    public void testFiftyFiftyLifeline() {
        Question question = new Question(
            "What is the capital of France?",
            new String[]{"a. London", "b. Paris", "c. Rome", "d. Berlin"},
            "b. Paris"
        );

        quizController.useFiftyFiftyLifeline(question);
        String[] options = question.getOptions();
        int nullCount = 0;

        for (String option : options) {
            if (option == null) {
                nullCount++;
            }
        }

        assertEquals(2, nullCount);
    }
    
    @Test
    public void testRemoveOneWrongOptionLifeline() {
        Question question = new Question(
            "What is the largest planet in our solar system?",
            new String[]{"a. Earth", "b. Mars", "c. Jupiter", "d. Saturn"},
            "c. Jupiter"
        );

        quizController.useRemoveOneWrongOptionLifeline(question);
        String[] options = question.getOptions();
        int nullCount = 0;

        for (String option : options) {
            if (option == null) {
                nullCount++;
            }
        }

        assertEquals(1, nullCount);
    }

    @Test
    public void testHintLifeline() {
        Question question = new Question(
            "Who was the first President of the United States?",
            new String[]{"a. Abraham Lincoln", "b. Thomas Jefferson", "c. George Washington", "d. John Adams"},
            "c. George Washington",
            "He is known as the 'Father of His Country'."
        );

        quizController.useHintLifeline(question);
        String hint = question.getHint();
        assertNotNull(hint);
        assertEquals("He is known as the 'Father of His Country'.", hint);
    }

    @Test
    public void testQuestionDatabase() {
        questionDatabase.loadQuestions("General Knowledge", "Easy");
        List<Question> questions = questionDatabase.getQuestions();
        assertEquals(8, questions.size());

        Question firstQuestion = questions.get(0);
        assertEquals("What is the largest planet in our solar system?", firstQuestion.getText());
        assertEquals("c. Jupiter", firstQuestion.getCorrectOption());
    }
}
