 package de.buw.se;

 import java.nio.file.Files;
 import java.nio.file.Paths;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Collections;
 import java.util.HashSet;
 import java.util.Set;
 import java.util.Scanner;

 public class AppCLI {
     private static Scanner scanner = new Scanner(System.in);
     private static int lives = 3;

     public static void main(String[] args)
     {
         // TO DO: FIGURE OUT HOW TO TRIGGER DataBasePopulator WHEN NEEDED
         // RUN ONLY IF DB IS MISSING
         //DataBasePopulator.populateDatabase();
         //System.out.println("Database populated successfully.");
         // AVOID RUNNING IF DB EXISTS - RESULTS IN ROW DUPLICATES
         System.out.println("Welcome to Learning Buddy!");
         mainMenu();
     }

     static void mainMenu() {
        
         while (true) {
             System.out.println("\nMain Menu:");
             System.out.println("1. Read General Information");
             System.out.println("2. Start Quiz");
             System.out.println("3. Exit");
             System.out.print("\nChoose a menu option by ID: ");
            
             int choice = scanner.nextInt();
             scanner.nextLine();
             switch (choice) {
                 case 1:
                     readGeneralInformation();
                     break;
                 case 2:
                     Category selected_category = selectCategory();
                     showCategoryInformation(selected_category);
                     String selected_difficulty = selectDifficulty(selected_category.category_id);
                     runQuiz(selected_category.category_id, selected_difficulty);
                     break;
                 case 3:
                     System.out.println("Goodbye!");
                     System.exit(0);
                 default:
                     System.out.println("\nInvalid choice, please try again.");
             }
         }
     }

     static void readGeneralInformation() {
         String file_path = "src/main/java/de/buw/se/GeneralInformation.txt";
         try {
             String general_information = new String(Files.readAllBytes(Paths.get(file_path)));
             System.out.println("\nGeneral Information:");
             System.out.println(general_information);
         }
         catch (IOException e) {
             System.out.println("Error reading general information: " + e.getMessage());
             System.exit(1);
         }
     }

     static Category selectCategory() {
         List<Category> categories = DataStoreSql.readCategories();
         Category selected_category = null;

         System.out.println("\nCategories:");
         for (Category category : categories) {
             System.out.println(category.category_id + ". " + category.name);
         }

         while (selected_category == null) {
             System.out.print("\nSelect a category by ID: ");
             int selected_category_id = scanner.nextInt();
             scanner.nextLine();

             if (selected_category_id > 0 && selected_category_id <= categories.size()) {
                 selected_category = categories.get(selected_category_id - 1);
             } else {
                 System.out.println("\nInvalid category ID, please try again.");
             }
         }
         return selected_category;
     }

     static void showCategoryInformation(Category category) {
         System.out.println("\nCategory: " + category.name);
         System.out.println("\nInformation: " + category.study_information);
     }

     static String selectDifficulty(int category_id) {
         List<Question> category_questions = DataStoreSql.readQuestionsbyCategory(category_id);
         Set<String> difficulties_set = new HashSet<>();
         String selected_difficulty = null;

         System.out.println("\nSelect Quiz Difficulty:");
         for (Question question : category_questions) {
             if (difficulties_set.add(question.difficulty)) {
                 System.out.println(difficulties_set.size() + ". " + question.difficulty);
             }
         }

         List<String> difficulties_list = new ArrayList<>(difficulties_set);

         while (selected_difficulty == null) {
             System.out.print("\nSelect difficulty by ID: ");
             int difficulty_choice = scanner.nextInt();
             scanner.nextLine();

             if (difficulty_choice > 0 && difficulty_choice <= difficulties_list.size()) {
                 selected_difficulty = difficulties_list.get(difficulty_choice - 1);
             } else {
                 System.out.println("\nInvalid difficulty choice, please try again.");
             }
         }
         return selected_difficulty;
     }

     static void runQuiz(int category_id, String difficulty) {
         System.out.println("\nDifficulty: " + difficulty);
         List<Question> question_list = prepareQuestionList(category_id, difficulty);

         while (lives > 0 && !question_list.isEmpty()) {
             Question question = question_list.remove(0);
             askQuestion(question);
         }
        
         System.out.println("\nQuiz Over!");
         if (lives > 0) {
             System.out.println("Congratulations, you finished the quiz!");
         } else {
             System.out.println("You've run out of lives. Better luck next time!");
         }
         lives = 3; // reset for next Quiz loop
     }

     static List<Question> prepareQuestionList(int category_id, String difficulty) {
         List<Question> category_questions = DataStoreSql.readQuestionsbyCategory(category_id);
         List<Question> selected_questions = new ArrayList<>();

         for (Question question : category_questions) {
             if (question.difficulty.equals(difficulty)) {
                 selected_questions.add(question);
             }
         }
         Collections.shuffle(selected_questions);
         return selected_questions;
     }

     static void askQuestion(Question question) {
         String selected_answer = null;

         System.out.println("\nQuestion: " + question.question_text);
         for (int i = 0; i < question.answers.length; i++) {
             System.out.println((i + 1) + ". " + question.answers[i]);
         }

         while (selected_answer == null) {
             System.out.print("\nSelect answer by ID: ");
             int answer_choice = scanner.nextInt();
             scanner.nextLine();

             if (answer_choice > 0 && answer_choice <= question.answers.length) {
                 selected_answer = question.answers[answer_choice - 1];
             } else {
                 System.out.println("\nInvalid answer choice, please try again.");
             }
         }

         if (selected_answer.equals(question.right_answer)) {
             System.out.println("Correct!");
         }
         else {
             System.out.println("\nIncorrect. The right answer is: " + question.right_answer);
             lives--;
             System.out.println("Lives remaining: " + lives);
         }
     }
 }