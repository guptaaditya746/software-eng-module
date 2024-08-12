package de.buw.se;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    @Test
    public void testQuestionConstructorAndGetters() {
        Integer questionId = 1;
        Integer categoryId = 101;
        String difficulty = "Easy";
        String questionText = "What is the capital of France?";
        String[] answers = {"Paris", "London", "Berlin", "Madrid"};
        String rightAnswer = "Paris";

        Question question = new Question(questionId, categoryId, difficulty, questionText, answers, rightAnswer);

        assertEquals(questionId, question.getQuestionId());
        assertEquals(categoryId, question.getCategoryId());
        assertEquals(difficulty, question.getDifficulty());
        assertEquals(questionText, question.getQuestion());
        assertArrayEquals(answers, question.getAnswers());
        assertEquals(rightAnswer, question.getRightAnswer());
    }

    @Test
    public void testQuestionWithDifferentDifficulty() {
        Integer questionId = 2;
        Integer categoryId = 102;
        String difficulty = "Hard";
        String questionText = "What is the square root of 256?";
        String[] answers = {"14", "15", "16", "17"};
        String rightAnswer = "16";

        Question question = new Question(questionId, categoryId, difficulty, questionText, answers, rightAnswer);

        assertEquals(questionId, question.getQuestionId());
        assertEquals(categoryId, question.getCategoryId());
        assertEquals(difficulty, question.getDifficulty());
        assertEquals(questionText, question.getQuestion());
        assertArrayEquals(answers, question.getAnswers());
        assertEquals(rightAnswer, question.getRightAnswer());
    }

    @Test
    public void testQuestionWithNullValues() {
        Integer questionId = null;
        Integer categoryId = null;
        String difficulty = null;
        String questionText = null;
        String[] answers = null;
        String rightAnswer = null;

        Question question = new Question(questionId, categoryId, difficulty, questionText, answers, rightAnswer);

        assertNull(question.getQuestionId());
        assertNull(question.getCategoryId());
        assertNull(question.getDifficulty());
        assertNull(question.getQuestion());
        assertNull(question.getAnswers());
        assertNull(question.getRightAnswer());
    }
}
