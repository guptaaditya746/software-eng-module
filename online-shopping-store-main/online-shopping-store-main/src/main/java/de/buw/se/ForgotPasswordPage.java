package de.buw.se;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ForgotPasswordPage extends Application {

    protected static String CSV_FILE_PATH = "src/main/resources/Onlineshoppingstore.csv"; // Default file path
    protected Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Forgot Password");

        // Create GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Add labels and fields
        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 0);
        TextField emailInput = new TextField();
        emailInput.setId("emailInput"); // Set ID for TestFX
        emailInput.setPromptText("Enter your email");
        GridPane.setConstraints(emailInput, 1, 0);

        Label newPasswordLabel = new Label("New Password:");
        GridPane.setConstraints(newPasswordLabel, 0, 1);
        PasswordField newPasswordInput = new PasswordField();
        newPasswordInput.setId("newPasswordInput"); // Set ID for TestFX
        newPasswordInput.setPromptText("Enter your new password");
        GridPane.setConstraints(newPasswordInput, 1, 1);

        Label confirmPasswordLabel = new Label("Confirm Password:");
        GridPane.setConstraints(confirmPasswordLabel, 0, 2);
        PasswordField confirmPasswordInput = new PasswordField();
        confirmPasswordInput.setId("confirmPasswordInput"); // Set ID for TestFX
        confirmPasswordInput.setPromptText("Confirm your new password");
        GridPane.setConstraints(confirmPasswordInput, 1, 2);

        Label promptLabel = new Label("");
        GridPane.setConstraints(promptLabel, 1, 4);

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 3);
        submitButton.setOnAction(e -> {
            if (newPasswordInput.getText().equals(confirmPasswordInput.getText())) {
                updatePassword(emailInput.getText(), newPasswordInput.getText(), promptLabel);
            } else {
                promptLabel.setText("Passwords do not match!");
            }
        });

        // Add Back button
        Button backButton = new Button("Back");
        GridPane.setConstraints(backButton, 0, 4);
        backButton.setOnAction(e -> redirectToHomePage());

        grid.getChildren().addAll(emailLabel, emailInput, newPasswordLabel, newPasswordInput, confirmPasswordLabel, confirmPasswordInput, submitButton, promptLabel, backButton);

        // Set scene
        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updatePassword(String email, String newPassword, Label promptLabel) {
        List<String[]> users = new ArrayList<>();
        boolean updated = false;

        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine != null && nextLine.length == 4) {
                    if (nextLine[3].equals(email)) {
                        nextLine[1] = newPassword; // Update password
                        updated = true;
                    }
                    users.add(nextLine);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        if (updated) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE_PATH))) {
                writer.writeAll(users);
                promptLabel.setText("");
                // Instead of showing an alert, provide direct feedback through promptLabel
                promptLabel.setText("Password updated successfully!");
                redirectToHomePage(); // Redirect logic if needed
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            promptLabel.setText("Email not found!");
        }
    }

    protected void redirectToHomePage() {
        AppGUI appGUI = new AppGUI();
        appGUI.start(primaryStage);
    }

    protected void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void setCSVFilePath(String path) {
        CSV_FILE_PATH = path;
    }

    public static String getCSVFilePath() {
        return CSV_FILE_PATH;
    }

    // Main method
    public static void main(String[] args) {
        launch(args);
    }
}
