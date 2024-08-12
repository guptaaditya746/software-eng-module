package de.buw.se;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;

public class DataStoreSql {
  private static final String FILE_NAME = "src/main/resources/books";

  private static Statement getSqlStatement() { // create Category and Question table
    try {
      Class.forName("org.h2.Driver");
      Connection connection = DriverManager.getConnection("jdbc:h2:./" + FILE_NAME, "", "");
  
      Statement statement = connection.createStatement();

      String createCategoriesTableQuery = "CREATE TABLE IF NOT EXISTS Categories"
          + "(CategoryID INT PRIMARY KEY AUTO_INCREMENT, "
          + "Name VARCHAR(255) NOT NULL, "
          + "StudyInformation CLOB)";      
          
      String createQuestionsTableQuery = "CREATE TABLE IF NOT EXISTS Questions"
          + "(QuestionID INT PRIMARY KEY AUTO_INCREMENT, "
          + "CategoryID INT NOT NULL, "
          + "Difficulty VARCHAR(255) NOT NULL, "
          + "Question CLOB NOT NULL, "
          + "Answers VARCHAR(1023) ARRAY NOT NULL, "
          + "RightAnswer VARCHAR(1023) NOT NULL, "
          + "FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID))";

      statement.executeUpdate(createCategoriesTableQuery);
      statement.executeUpdate(createQuestionsTableQuery);
      return statement;
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }

public static void addCategory(String category_name, String category_study_information) {
  try {
      Statement statement = getSqlStatement();
      String addCategoryQuery = String.format("INSERT INTO Categories (Name, StudyInformation) VALUES('%s', '%s')", 
                                              category_name, category_study_information);
      statement.executeUpdate(addCategoryQuery);
    } catch (SQLException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

public static void addQuestion(int question_category_id, String difficulty, String question, String[] answers, String right_answer) { // create Question
  try {
      Statement statement = getSqlStatement();

      String answers_string = String.join(", ", answers);
      String addQuestionQuery = String.format("INSERT INTO Questions (CategoryID, Difficulty, Question, Answers, RightAnswer) VALUES ('%d', '%s', '%s', '%s', '%s')",
                                question_category_id, difficulty, question, answers_string, right_answer);
    
      statement.executeUpdate(addQuestionQuery);
    } catch (SQLException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public static List<Category> readCategories() {
    List<Category> categories = new ArrayList<>();
    try {
      Statement statement = getSqlStatement();
      ResultSet selectRS = statement.executeQuery("SELECT * FROM Categories");
      while (selectRS.next()) {
        Integer category_id = selectRS.getInt("CategoryID");
        String name = selectRS.getString("Name");
        String study_information = selectRS.getString("StudyInformation");
        Category category = new Category(category_id, name, study_information);
        categories.add(category);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return categories;
  }

public static List<Question> readQuestionsbyCategory(int question_category_id) {
    List<Question> questions = new ArrayList<>();
    try {
      Statement statement = getSqlStatement();
      ResultSet selectRS = statement.executeQuery("SELECT * FROM Questions WHERE CategoryID = " + question_category_id);
      while (selectRS.next()) {
        Integer question_id = selectRS.getInt("QuestionID");
        Integer category_id = selectRS.getInt("CategoryID");
        String difficulty = selectRS.getString("Difficulty");
        String question_text = selectRS.getString("Question");

        // TO DO FIGURE OUT HOW TO OPERATE ARRAY TYPE PROPERLY
        String answers_string = selectRS.getString("Answers");
        answers_string = answers_string.substring(1, answers_string.length() - 1);
        String[] answers = answers_string.split(", ");
        // CURRENTLY EVENTHOUGH TABLE IS DEFINED WITH Answers VARCHAR(1023) ARRAY NOT NULL
        // ANSWERS ARRAY IS CONVERTED TO LONG STRING WITH ", " DELIMITER

        String right_answer = selectRS.getString("RightAnswer");
        Question question = new Question(question_id, category_id, difficulty, question_text, answers, right_answer);
        questions.add(question);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return questions;
  }
}  
