package de.buw.se;

public class EduTriviumApp {
    public static void main(String[] args) {
        QuestionDatabase questionDatabase = new QuestionDatabase();
        QuizView quizView = new QuizView();
        QuizController quizController = new QuizController(questionDatabase, quizView);
        quizController.startQuiz();
    }
}