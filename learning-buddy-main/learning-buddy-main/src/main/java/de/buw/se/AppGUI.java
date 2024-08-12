package de.buw.se;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Optional;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.effect.ColorAdjust;

public class AppGUI extends Application {

    protected Stage primaryStage;
    protected final int INITIAL_LIVES = 3; // Define initial lives constant
    protected int lives = INITIAL_LIVES; // TO DO IMPLEMENT LIVES VISIBILITY TO USER

    @Override
    public void start(Stage primaryStage) {

        // TO DO: FIGURE OUT HOW TO TRIGGER DataBasePopulator WHEN NEEDED
        // RUN ONLY IF DB IS MISSING
        //DataBasePopulator.populateDatabase();
        //System.out.println("Database populated successfully.");
        // AVOID RUNNING IF DB EXISTS - RESULTS IN ROW DUPLICATES

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Learning Buddy");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        Label label = new Label("Welcome to Learning Buddy!");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button generalInfoButton = new Button("Read General Information");
        TextArea generalInfoTextArea = new TextArea();
        generalInfoTextArea.setEditable(false);
        generalInfoTextArea.setMaxHeight(Double.MAX_VALUE); // Allow text area to expand vertically
        ScrollPane scrollPane = new ScrollPane(generalInfoTextArea); // Wrap text area in a scroll pane
        scrollPane.setFitToWidth(true); // Allow horizontal scrolling if needed

        generalInfoButton.setOnAction(e -> readGeneralInformation(generalInfoTextArea));

        Button startQuizButton = new Button("Start Quiz");
        startQuizButton.setOnAction(e -> startQuiz());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> System.exit(0));

        vbox.getChildren().addAll(label, generalInfoButton, generalInfoTextArea, startQuizButton, exitButton);
        // TO DO CLASSES CSS ARE NOT APPLIED
        Scene scene = new Scene(vbox, 600, 400);
        // scene.getStylesheets().add(getClass().getResource("src/resources/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    protected void readGeneralInformation(TextArea textArea) {
        String file_path = "src/main/resources/GeneralInformation.txt";
        String general_information = null;
        try {
            general_information = new String(Files.readAllBytes(Paths.get(file_path)));
        } catch (IOException e) {
            System.err.println("Error reading general information: " + e.getMessage());
        }
        textArea.setText(general_information);
    }

    protected void startQuiz() {
        lives = INITIAL_LIVES; // Reset lives at the start of each quiz
        Category selected_category = selectCategory();
        if (selected_category != null) {
            showCategoryInformation(selected_category);
            String selectedDifficulty = selectDifficulty(selected_category.category_id);
            if (selectedDifficulty != null) {
                runQuiz(selected_category.category_id, selectedDifficulty);
            }
        }
    }

    protected Category selectCategory() {
        List<Category> categories = DataStoreSql.readCategories();
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.category_id + ". " + category.name);
        }

        // Creating the Choice Dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Select Category");
        dialog.setHeaderText("Select a Category");

        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType quitButtonType = new ButtonType("Quit", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, quitButtonType);

        // Create the ChoiceBox
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(categoryNames);

        // Set the content of the dialog
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().add(new Label("Choose your category:"));
        vbox.getChildren().add(choiceBox);
        dialog.getDialogPane().setContent(vbox);

        // Convert the result to a selected category when the confirm button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return choiceBox.getValue();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String selectedName = result.get();
            for (Category category : categories) {
                if ((category.category_id + ". " + category.name).equals(selectedName)) {
                    return category;
                }
            }
        }
        return null;
    }

    protected String selectDifficulty(int category_id) {
        List<Question> category_questions = DataStoreSql.readQuestionsbyCategory(category_id);
        Set<String> difficulties_set = new HashSet<>();
        for (Question question : category_questions) {
            difficulties_set.add(question.difficulty);
        }
        List<String> difficulties_list = new ArrayList<>(difficulties_set);

        // Creating the Choice Dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Select Difficulty");
        dialog.setHeaderText("Select Quiz Difficulty");

        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType quitButtonType = new ButtonType("Quit", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, quitButtonType);

        // Create the ChoiceBox
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(difficulties_list);

        // Set the content of the dialog
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().add(new Label("Choose your difficulty:"));
        vbox.getChildren().add(choiceBox);
        dialog.getDialogPane().setContent(vbox);

        // Convert the result to the selected difficulty when the confirm button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return choiceBox.getValue();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    protected void showCategoryInformation(Category category) {
        // Create a scrollable text area to display category information
        TextArea categoryInfoTextArea = new TextArea(category.study_information);
        categoryInfoTextArea.setEditable(false);
        categoryInfoTextArea.setWrapText(true); // Enable text wrapping
        categoryInfoTextArea.setMaxWidth(Double.MAX_VALUE);
        categoryInfoTextArea.setMaxHeight(Double.MAX_VALUE);

        // Place the text area in a scroll pane
        ScrollPane scrollPane = new ScrollPane(categoryInfoTextArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Create a stack pane to hold the scroll pane
        StackPane stackPane = new StackPane(scrollPane);

        // Create the alert and set its content to the stack pane
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Category Information");
        alert.setHeaderText(category.name);
        alert.getDialogPane().setContent(stackPane);
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }

    protected void runQuiz(int category_id, String difficulty) {
        List<Question> question_list = prepareQuestionList(category_id, difficulty);
        boolean quitSelected = false; // Flag to track if quit was selected

        while (lives > 0 && !question_list.isEmpty() && !quitSelected) {
            Question question = question_list.remove(0);
            quitSelected = !askQuestion(question); // Update flag if quit was selected
        }

        // Show the quiz end message only if quit was not selected
        if (!quitSelected) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quiz Over");
            if (lives > 0) {
                alert.setHeaderText("Congratulations!");
                alert.setContentText("You have successfully completed the quiz.");
            } else {
                alert.setHeaderText("Game Over");
                alert.setContentText("You have run out of lives.");
            }
            alert.showAndWait();
        }
    }

    protected List<Question> prepareQuestionList(int category_id, String difficulty) {
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

    protected boolean askQuestion(Question question) {
        Alert question_alert = new Alert(Alert.AlertType.CONFIRMATION);
        question_alert.setTitle("Quiz Question");
        question_alert.setHeaderText(question.question_text);

        List<ButtonType> buttons = new ArrayList<>();
        for (int i = 0; i < question.answers.length; i++) {
            buttons.add(new ButtonType(question.answers[i]));
        }
        buttons.add(new ButtonType("Quit", ButtonBar.ButtonData.CANCEL_CLOSE));
        question_alert.getButtonTypes().setAll(buttons);

        Optional<ButtonType> result = question_alert.showAndWait();
        if (result.isPresent() && result.get().getText().equals("Quit")) {
            return false; // Return false if "Quit" is selected
        }
        String selected_answer = result.map(ButtonType::getText).orElse("");

        if (selected_answer.equals(question.right_answer)) {
            Alert correctAlert = new Alert(Alert.AlertType.INFORMATION);
            correctAlert.setTitle("Correct Answer");
            correctAlert.setHeaderText(null);
            correctAlert.setContentText("Correct!");
            applyGlowEffect(correctAlert, -0.4); // Apply green glow effect
            correctAlert.showAndWait();
        } else {
            Alert incorrect_alert = new Alert(Alert.AlertType.ERROR);
            incorrect_alert.setTitle("Incorrect Answer");
            incorrect_alert.setHeaderText(null);
            incorrect_alert.setContentText("Incorrect. The right answer is: " + question.right_answer);
            applyGlowEffect(incorrect_alert, -1); // Apply red glow effect
            incorrect_alert.showAndWait();
            lives--;
        }
        return true; // Return true if the user did not select "Quit"
    }

    protected void applyGlowEffect(Alert alert, double hue) {
        // TO DO GLOW EFFECT WORKS ONLY FOR BUTTON, THE CROSS MARK GETS INCORRECT COLOR
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(hue);
        alert.getDialogPane().setEffect(colorAdjust);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(colorAdjust.brightnessProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(colorAdjust.brightnessProperty(), 0.3)));
        timeline.setAutoReverse(true);
        timeline.setCycleCount(2);
        timeline.setOnFinished(event -> alert.getDialogPane().setEffect(null));
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}