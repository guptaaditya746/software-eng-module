package de.buw.se;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AppGUI extends Application {

    protected Map<String, String> userResponses = new HashMap<>();
    public int indexer = 0;
    public int cindex = 0;

    protected static final String[] GREETINGS = {
            "Hi there! Ready to explore the universities?",
            "Hello! Let's find the perfect university for you!",
            "Greetings! Excited to discover German universities with you!"
    };
    protected Text questionText;
    protected VBox chatArea;
    protected TextField userInputField;

    protected static String[] questions = {
            "What is your field of interest?",
            "Which program are you interested in?",
            "Which city would you prefer to study in?"
    };

    public int currentQuestionIndex = 0;
    public int ind = 0;
    public boolean expectingYesNo = false;

    private List<String> fieldOfInterests = Arrays.asList(
            "computer science", "engineering", "business administration",
            "biology", "psychology", "economics"
    );

    private List<String> programs = Arrays.asList(
            "master", "master of science", "msc", "master of engineering", "meng",
            "master of business administration", "mba", "master of arts", "ma",
            "master of psychology", "master of economics"
    );

    private List<String> cities = Arrays.asList(
            "munich", "heidelberg", "berlin", "freiburg", "stuttgart",
            "frankfurt", "weimar"
    );

    @Override // first method
    public void start(Stage primaryStage) {
        String randomGreeting = getRandomGreeting();

        chatArea = new VBox(10);
        chatArea.setStyle("-fx-background-color: #F0F0F0; -fx-padding: 10px;");
        Text greetingText = new Text("Chatbot: " + randomGreeting);
        greetingText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        greetingText.setFill(Color.BLACK);
        chatArea.getChildren().add(greetingText);

        userInputField = new TextField();
        userInputField.setPromptText("Type your message...");
        userInputField.setPrefWidth(400);
        userInputField.setOnAction(e -> processInput());
        userInputField.setStyle("-fx-font-size: 14px;");

        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-font-size: 14px;");
        sendButton.setOnAction(e -> processInput());

        HBox chatInputBox = new HBox(10);
        chatInputBox.getChildren().addAll(userInputField, sendButton);
        chatInputBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane root = new BorderPane();
        root.setCenter(chatArea);
        root.setBottom(chatInputBox);
        BorderPane.setMargin(chatInputBox, new Insets(10));

        Scene scene = new Scene(root, 500, 600);
        primaryStage.setTitle("German University Finder");
        primaryStage.setScene(scene);
        primaryStage.show();

        askQuestion();
    }

    protected String getRandomGreeting() {
        Random random = new Random();
        int index = ind;
        ind = ind + 1;
        if (ind == 3) {
            ind = 0;
        }
        return GREETINGS[index];
    }

    protected void askQuestion() {
        if (currentQuestionIndex < questions.length) {
            String question = questions[currentQuestionIndex];
            Text questionText = new Text("Chatbot: " + question);
            questionText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            questionText.setFill(Color.BLACK);
            chatArea.getChildren().add(questionText);
        } else {
            generateRecommendation();
        }
    }

    protected void generateRecommendation() {
        String fieldOfInterest = userResponses.get(questions[0]);
        String program = userResponses.get(questions[1]);
        String city = userResponses.get(questions[2]);

        List<University> universities = new ArrayList<>();
        try {
            CSVDataReader csvDataReader = new CSVDataReader("src/main/resources/universities.csv");
            universities = csvDataReader.readCSVData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> recommendations = new ArrayList<>();
        for (University university : universities) {
            if (university.getFieldOfInterest().equalsIgnoreCase(fieldOfInterest)
                    && university.getProgram().equalsIgnoreCase(program)
                    && university.getCity().equalsIgnoreCase(city)) {
                recommendations.add(university.getName() + ",  " + "Requirements: (" + university.getGmatorGRERequirement() + " " + university.getcgpaRequirement() + ")");
            }
        }

        StringBuilder recommendationMessage = new StringBuilder();
        if (recommendations.isEmpty()) {
            recommendationMessage.append("Based on your preferences, no universities found matching your criteria.");
        } else {
            recommendationMessage.append("Based on your preferences, we recommend the following universities:\n");
            for (int i = 0; i < recommendations.size(); i++) {
                recommendationMessage.append(i + 1).append(". ").append(recommendations.get(i)).append("\n");
            }
        }

        Text recommendationText = new Text("Chatbot: " + recommendationMessage.toString());
        recommendationText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        recommendationText.setFill(Color.BLACK);
        chatArea.getChildren().add(recommendationText);

        resetConversation();
    }

    protected void resetConversation() {
        currentQuestionIndex = 0;
        userResponses.clear();
        expectingYesNo = true;

        Text continueText = new Text("Chatbot: Would you like to start another search? (yes/no)");
        continueText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        continueText.setFill(Color.BLACK);
        chatArea.getChildren().add(continueText);
    }

    protected void processInput() {
        String input = userInputField.getText().trim().toLowerCase();

        if (expectingYesNo) {
            handleYesNoResponse(input);
        } else {
            if (validateInput(input)) {
                Text userMessage = new Text("You: " + input);
                userMessage.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                userMessage.setFill(Color.BLUE);
                chatArea.getChildren().add(userMessage);

                userResponses.put(questions[currentQuestionIndex], input);

                userInputField.clear();
                currentQuestionIndex++;
                askQuestion();
            }
        }
    }

    protected void handleYesNoResponse(String input) {
        if (input.equals("yes")) {
            expectingYesNo = false;
             // Clear the input field
            userInputField.clear();
            askQuestion();
        } else if (input.equals("no")) {
            Text goodbyeText = new Text("Chatbot: Goodbye! Have a great day!");
            goodbyeText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            goodbyeText.setFill(Color.BLACK);
            chatArea.getChildren().add(goodbyeText);
            userInputField.clear(); // Clear the input field
            userInputField.setDisable(true);
        } else {
            showAlert("Invalid Input", "Please enter 'yes' or 'no'.");
            userInputField.clear(); // Clear the input field
        }

    }


    protected boolean validateInput(String input) {
        boolean isValidInput = false;

        switch (currentQuestionIndex) {
            case 0:
                if (fieldOfInterests.contains(input)) {
                    isValidInput = true;
                } else {
                    showAlert("Invalid Input", "Please enter a valid field of interest.");
                }
                break;
            case 1:
                if (programs.contains(input)) {
                    isValidInput = true;
                } else {
                    showAlert("Invalid Input", "Please enter a valid program.");
                }
                break;
            case 2:
                if (cities.contains(input)) {
                    isValidInput = true;
                } else {
                    return false;
                }
                break;
        }

        return isValidInput;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
