package de.buw.se;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class QuizController {
    private QuestionDatabase questionDatabase;
    private QuizView quizView;
    private int score;
    private List<Question> reviewQuestions;
    private List<Question> currentQuestions; // Track current questions for testing purposes
    private boolean hasHintLifeline;

    public QuizController(QuestionDatabase questionDatabase, QuizView quizView) {
        this.questionDatabase = questionDatabase;
        this.quizView = quizView;
        score = 0;
        reviewQuestions = new ArrayList<>();
        this.currentQuestions = new ArrayList<>(); // Initialize current questions list
    }
    

    public void startQuiz() {
    	Scanner scanner = new Scanner(System.in);
        quizView.displayWelcomeMessage();
        
        String topic = QuizView.chooseTopic();
        String difficulty = QuizView.chooseDifficulty();
        
        hasHintLifeline = difficulty.equals("Hard");
        quizView.setHintLifeline(hasHintLifeline); 
        
        questionDatabase.loadQuestions(topic, difficulty);
        List<Question> questions = new ArrayList<>(questionDatabase.getQuestions());
        //Collections.shuffle(questions); // Shuffle the questions

        int lives = 3; // Initialize lives to 3
        for (Question question : questions) {
            if (lives == 0) {
                System.out.println("Sorry you are out of lives. The quiz ends here.\n\n \t\tGAME OVER");
                break; // Exit the loop if lives are exhausted
            }
            lives = askQuestion(question, lives);
        }

        // Display the end-of-game message
        quizView.displayEndOfGameMessage(score);
        if (!reviewQuestions.isEmpty()) {
            quizView.displayReviewQuestions(reviewQuestions);
            QuizView.reattemptConfirmation(reviewQuestions);
            
        }
    }
    
    public void answerCurrentQuestion(String answer) {
        if (currentQuestions != null && !currentQuestions.isEmpty()) {
            Question currentQuestion = currentQuestions.get(0); // Assuming the current question is the first in the list
            boolean isCorrect = checkAnswer(answer, currentQuestion.getCorrectOption());
            if (isCorrect) {
                score++;
            }
            QuizView.displayResult(isCorrect, currentQuestion.getCorrectOption());
            currentQuestions.remove(0); // Remove the answered question
        }
    }

    protected int askQuestion(Question question, int lives) {
        QuizView.displayQuestion(question);
        String userAnswer;
        String[] options=question.getOptions();
        if(options[0]==null && options[1]==null && options[2]==null && options[3]==null) {
        	throw new NullPointerException("Option Null");}
 
    
        // Ask the user to choose a lifeline option
        int lifelineChoice = quizView.chooseLifelineOption();
        if (hasHintLifeline) {
        	if (lifelineChoice == 5) {
                startQuiz(); // Signal to the caller to go back to the main menu
            }
        	if (lifelineChoice == 1) {
                // Use 50-50 lifeline
                useFiftyFiftyLifeline(question);
            } else if (lifelineChoice == 2) {
                // Use remove one wrong option lifeline
                useRemoveOneWrongOptionLifeline(question);
                 
            }else if (lifelineChoice == 3) {
            	useHintLifeline(question);
            }
        	
        } else {
        	if (lifelineChoice == 4) {
                startQuiz(); // Signal to the caller to go back to the main menu
            }
        // Handle the chosen lifeline
        if (lifelineChoice == 1) {
            // Use 50-50 lifeline
            useFiftyFiftyLifeline(question);
        } else if (lifelineChoice == 2) {
            // Use remove one wrong option lifeline
            useRemoveOneWrongOptionLifeline(question);
             
        }

        	
        }
        
        userAnswer = QuizView.getUserAnswer();

        boolean isCorrect = checkAnswer(userAnswer, question.getCorrectOption());
        if (!isCorrect) {
            lives--;
            System.out.println("Wrong answer! Lives remaining: " + lives);
            reviewQuestions.add(question);
        } else {
            System.out.println("CORRECT ANSWER!");
            score++;
        }
        return lives;
    }

    protected void useFiftyFiftyLifeline(Question question) {
        String[] options = question.getOptions();
        int correctIndex = getCorrectIndex(question, options);

        // Generate a list of indices excluding the correct index
        System.out.println(correctIndex);
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < options.length; i++) {
            if (i != correctIndex) {
                indices.add(i);
            }
        }

        // Shuffle the indices and select the first two
        Collections.shuffle(indices);
        int wrongIndex1 = indices.get(0);
        int wrongIndex2 = indices.get(1);

        // Remove two options (excluding the correct one)
        options[wrongIndex1] = null;
        options[wrongIndex2] = null;

        // Display the question with two options removed
        quizView.displayQuestionWithLifeline(question,correctIndex, wrongIndex1, wrongIndex2);
    }

    protected void useRemoveOneWrongOptionLifeline(Question question) {
        String[] options = question.getOptions();
        int correctIndex = getCorrectIndex(question, options);

        // Generate a list of indices excluding the correct index
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < options.length; i++) {
            if (i != correctIndex) {
                indices.add(i);
            }
        }

        // Shuffle the indices and select the first one
        Collections.shuffle(indices);
        int wrongIndex = indices.get(0);

        // Remove one option (excluding the correct one)
        options[wrongIndex] = null;

        // Display the question with one option removed
        quizView.displayQuestionWithLifeline(question, correctIndex, wrongIndex, wrongIndex);
    }
    
    protected void useHintLifeline(Question question) {
        String hint = question.getHint();
        quizView.displayHint(hint);
    }

    protected int getCorrectIndex(Question question, String[] options) {
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(question.getCorrectOption())) {
                return i;
            }
        }
        return -1; // Should never happen if the correct option is present
    }

    protected boolean checkAnswer(String userAnswer, String correctOption) {
        System.out.println(correctOption.substring(0, 1));
        return userAnswer.equals(correctOption.substring(0, 1));
    }
}

