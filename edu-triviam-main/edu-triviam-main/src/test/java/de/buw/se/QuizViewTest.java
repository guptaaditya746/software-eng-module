package de.buw.se;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizViewTest {
    private QuestionDatabase questionDatabase;
    private QuizView quizView;
    private QuizController quizController;

    @BeforeEach
    void setUp() {
        questionDatabase = new QuestionDatabase();
        quizView = new QuizView();
        quizController = new QuizController(questionDatabase, quizView);
    }

    private void simulateSystemIn(String input) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }
    
    @Test
    void testChooseInvalidTopic() {
        simulateSystemIn("10\n");  // Simulate invalid topic choice
        String topic = QuizView.chooseTopic();
        assertEquals("General Knowledge", topic, "Choosing an invalid topic should not default to 'General Knowledge'");
    }
    
    @Test
    void testChooseInvalidDifficulty() {
        simulateSystemIn("5\n");  // Simulate invalid difficulty choice
        String difficulty = QuizView.chooseDifficulty();
        assertEquals("Easy", difficulty, "Choosing an invalid difficulty should not default to 'Easy'");
    }
    
    @Test
    void testUseLifelineTwice() {
        questionDatabase.loadQuestions("General Knowledge", "Easy");
        List<Question> questions = questionDatabase.getQuestions();
        Question question = questions.get(0);
        
        quizController.useFiftyFiftyLifeline(question);
        assertThrows(IllegalStateException.class, () -> {
            quizController.useFiftyFiftyLifeline(question);
        }, "Using a lifeline more than once should throw an IllegalStateException");
    }
    
    @Test
    void testNullQuestionHandling() {
        Question nullQuestion = null;
        assertThrows(NullPointerException.class, () -> {
            quizController.askQuestion(nullQuestion, 3);
        }, "Asking a null question should throw a NullPointerException");
    }
    
    @Test
    void testNullOptionHandling() {
        // Create a Question object with null options for testing
        String[] options = new String[]{null, null, null, null};
        Question questionWithNullOptions = new Question("Sample?", options, "d", "Hint");

        simulateSystemIn("d\n");  // Simulate an answer input
        assertThrows(NullPointerException.class, () -> {
            quizController.askQuestion(questionWithNullOptions, 3);
        }, "Asking a question with null options should throw a NullPointerException");
    }
}
