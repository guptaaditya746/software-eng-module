package  de.buw.se;

import java.util.Scanner;
import java.util.List;

public class QuizView {
    private static Scanner scanner;
    private boolean hasHintLifeline; // Flag to track if hint lifeline is available
    
    public QuizView() {
        scanner = new Scanner(System.in);
    }
    
    public QuizView(Scanner scanner) {
        QuizView.scanner = scanner;
    }

    public void displayWelcomeMessage() {
        System.out.println("Welcome to EduTrivium Quiz Game!");
        System.out.println("Let's test your knowledge.\n");
    }
    public static String chooseTopic() {
    	Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a subject:");
        System.out.println("1. General Knowledge");
        System.out.println("2. Geography");
        System.out.println("3. Maths");
        System.out.println("4. Literature");
        System.out.println("5. Science");
        System.out.print("Enter your choice (1-5): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                return "General Knowledge";
            case 2:
                return "Geography";
            case 3:
                return "Maths";
            case 4:
                return "Literature";
            case 5:
                return "Science";
            default:
                return "General Knowledge"; // Default choice
        }
    }
    public static String chooseDifficulty() {
    	Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a difficulty level:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        System.out.print("Enter your choice (1-3): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                return "Easy";
            case 2:
                return "Medium";
            case 3:
                return "Hard";
            default:
                return "Easy"; // Default choice
        }
    }

    public static void displayQuestion(Question question) {
        System.out.println(question.getText());
        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println(options[i]);
        }
    }

    public static String getUserAnswer() {
        while (true) {
            System.out.print("\nEnter your answer (a/b/c/d): ");
            String inputAnswer = scanner.nextLine().toLowerCase();
            if (inputAnswer.equals("a") || inputAnswer.equals("b") || inputAnswer.equals("c") || inputAnswer.equals("d")) {
                return inputAnswer;
            } else {
                System.out.println("\nInvalid input. Enter options a/b/c/d");
            }
        }
    }

    public static void displayResult(boolean isCorrect, String correctOption) {
        if (isCorrect) {
            System.out.println("Correct Answer!");
        } else {
            System.out.println("Incorrect Answer!");
            System.out.println("Correct Answer: " + correctOption);
        }
    }

    public void displayEndOfGameMessage(int score) {
        System.out.println("\nEnd of Quiz.");
        System.out.println("Your Score: " + score);
    }
    
   

    public void setHintLifeline(boolean hasHintLifeline) {
        this.hasHintLifeline = hasHintLifeline;
    }

    public boolean provideLifeline() {
        // Implement logic to prompt the user whether they want to use the lifeline
        // You can use any user interface element such as a dialog box or command line input
        // Return true if the user wants to use the lifeline, false otherwise
        // For example:
        System.out.println("Do you want to use the 50-50 lifeline? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toLowerCase();
        return input.equals("yes");
    }


    public void displayQuestionWithLifeline(Question question, int correctIndex, int wrongIndex1, int wrongIndex2) {
        // Display the question with two wrong options removed
        // Also, inform the user about the restricted options
        String[] options = question.getOptions();
        System.out.println("Question: " + question.getQuestion());

        for (int i = 0; i < options.length; i++) {
            if (i == correctIndex) {
                System.out.println(options[i]);
            } else if (i == wrongIndex1 || i == wrongIndex2) {
                System.out.println((char) ('a'+i) + ". [Option Removed]");
            } else {
                System.out.println(options[i]);
            }
        }
    }


    public int chooseLifelineOption() {
        // Display lifeline options to the user and prompt for their choice
    	if(hasHintLifeline) {
    		System.out.println("Choose a lifeline option:");
            System.out.println("1. 50-50 Lifeline");
            System.out.println("2. Remove One Wrong Option Lifeline");
            System.out.println("3. Hint Lifeline");
            System.out.println("4. No Lifeline");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice (1-5)");
    		
    		
    	} else {
    		System.out.println("Choose a lifeline option:");
            System.out.println("1. 50-50 Lifeline");
            System.out.println("2. Remove One Wrong Option Lifeline");
            System.out.println("3. No lifeline");
            System.out.println("4. Back to Main menu");
            System.out.print("Enter your choice (1, 2, 3 or 4)");
    		
    	}
        

        // Read user input
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if(hasHintLifeline) {
                	if (choice == 1 || choice == 2 || choice == 3 || choice == 4 || choice == 5) {
                        break; // Exit the loop if the input is valid
                    } else {
                        System.out.print("Invalid input. Please enter a valid number: ");
                    }
                } else {
                	if (choice == 1 || choice == 2 || choice == 3 || choice == 4) {
                        break; // Exit the loop if the input is valid
                    } else {
                        System.out.print("Invalid input. Please enter a valid number: ");
                    }
                	
                }
                
            } else {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next(); // Consume invalid input
            }
        }

        return choice;
    }
    
    public void displayReviewQuestions(List<Question> reviewQuestions){
        System.out.println("Let us review the incorrectly answered questions:");
        for (Question question : reviewQuestions) {
            System.out.println(question.getQuestion());
            System.out.println("Correct Answer: " + question.getCorrectOption());
            System.out.println();
        }


    }
    public static void reattemptConfirmation(List<Question> reviewQuestions) {
        while (true) {
            System.out.println("Do you want to reattempt the questions you reviewed?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Enter your choice (1/2): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                displayreattemptQuiz(reviewQuestions);
                break;
            } else if (choice == 2) {
                System.out.println("\nOkay! See you soon!");
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid option (1/2).");
            }
        }
    }
    public static void displayreattemptQuiz(List<Question> reviewQuestions) {
    	int correct=0;
    	for (Question question : reviewQuestions) {
    		displayQuestion(question);
    		String choice=getUserAnswer();
    		boolean isCorrect = checkAnswer(choice, question.getCorrectOption());
    		if(isCorrect) {
    			correct++;
    		}
    		displayResult(isCorrect,question.getCorrectOption());
    		
    	}
    	if(correct==3) {
    		System.out.println("\nWell done! You have got them all correct this time!");
    		
    	}else {
    		System.out.println("\nPractice makes perfect!");
    	}
    }
    
    private static boolean checkAnswer(String userAnswer, String correctOption) {
        System.out.println(correctOption.substring(0, 1));
        return userAnswer.equals(correctOption.substring(0, 1));
    }
    
    public void displayHint(String hint) {
        System.out.println("Hint: " + hint);
    }
}
