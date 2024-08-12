package de.buw.se;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NewUserForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        AnchorPane signUpForm = new AnchorPane();
        signUpForm.setPrefSize(300.0, 400.0);

        Label titleLabel = new Label("New User Registration");
        titleLabel.setLayoutX(45.0);
        titleLabel.setLayoutY(122.0);
        titleLabel.setTextFill(javafx.scene.paint.Color.valueOf("#2653d9"));
        titleLabel.setFont(new Font(20.0));

        TextField usernameField = new TextField();
        usernameField.setLayoutX(45.0);
        usernameField.setLayoutY(163.0);
        usernameField.setPrefSize(220.0, 30.0);
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(45.0);
        passwordField.setLayoutY(206.0);
        passwordField.setPrefSize(220.0, 30.0);
        passwordField.setPromptText("Password");

        PasswordField reEnterPasswordField = new PasswordField();
        reEnterPasswordField.setLayoutX(45.0);
        reEnterPasswordField.setLayoutY(258.0);
        reEnterPasswordField.setPrefSize(220.0, 30.0);
        reEnterPasswordField.setPromptText("Re-Enter Password");

        Button createButton = new Button("Create");
        createButton.setLayoutX(45.0);
        createButton.setLayoutY(315.0);
        createButton.setMnemonicParsing(false);
        createButton.setPrefSize(220.0, 30.0);

        createButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String reEnterPassword = reEnterPasswordField.getText();

            if (!validateFields(username, password, reEnterPassword)) {
                return;
            }

            try {
                if (DataStoreSql.isUserExists(username)) {
                    showAlert(AlertType.ERROR, "Error", "Username already registered.");
                } else {
                    if (DataStoreSql.addUser(username, password)) {
                        showAlert(AlertType.CONFIRMATION, "Success", "User created successfully!");
                    } else {
                        showAlert(AlertType.ERROR, "Error", "Failed to create user. Please try again.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Database Error", "An error occurred while accessing the database.");
            }

            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
        });

        signUpForm.getChildren().addAll(titleLabel, usernameField, passwordField, reEnterPasswordField, createButton);

        Scene scene = new Scene(signUpForm, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("New User Form");
        primaryStage.show();
    }

    private boolean validateFields(String username, String password, String reEnterPassword) {
        if (username.isEmpty() || password.isEmpty() || reEnterPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Please fill in all fields.");
            return false;
        }

        if (username.contains(" ")) {
            showAlert(AlertType.ERROR, "Validation Error", "Username cannot contain spaces.");
            return false;
        }

        if (password.contains(" ")) {
            showAlert(AlertType.ERROR, "Validation Error", "Password cannot contain spaces.");
            return false;
        }

        if (!password.equals(reEnterPassword)) {
            showAlert(AlertType.ERROR, "Password Mismatch", "Passwords do not match.");
            return false;
        }

        return true;
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // This method was added for testing
    public static boolean createUser(String username, String password, String reEnterPassword) {
        return !username.isEmpty() && !password.isEmpty() && !reEnterPassword.isEmpty() && password.equals(reEnterPassword) && !username.contains(" ") && !password.contains(" ");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
