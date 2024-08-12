package de.buw.se.frontend;

import de.buw.se.ELearningPlatform;
import de.buw.se.backend.service.UserService;
import de.buw.se.frontend.utils.CursorUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SignUpUI {

    private final ELearningPlatform app;
    private final UserService userService;

    public SignUpUI(ELearningPlatform app) {
        this.app = app;
        this.userService = new UserService();
    }

    public Pane getPane() {
        GridPane registerPane = new GridPane();
        registerPane.setPadding(new Insets(20));
        registerPane.setVgap(10);
        registerPane.setHgap(10);

        // Labels
        Label usernameLabel = new Label("Username:");
        Label emailLabel = new Label("Email:");
        Label nameLabel = new Label("Name:");
        Label passwordLabel = new Label("Password:");
        Label confirmPasswordLabel = new Label("Confirm Password:");

        TextField registerUsernameField = new TextField();
        registerUsernameField.setPromptText("Enter your username");
        Label usernameNote = new Label("(Alphanumeric characters only)");
        usernameNote.setFont(Font.font(10));
        usernameNote.setTextFill(Color.GRAY);

        TextField registerEmailField = new TextField();
        registerEmailField.setPromptText("Enter your email");

        TextField registerNameField = new TextField();
        registerNameField.setPromptText("Enter your Full Name");

        PasswordField registerPasswordField = new PasswordField();
        registerPasswordField.setPromptText("Enter your password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");
        Label passwordNote = new Label("(Password must be at least 6 characters long)");
        passwordNote.setFont(Font.font(10));
        passwordNote.setTextFill(Color.GRAY);

        Button registerButton = new Button("Sign Up");
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        CursorUtils.setPointerCursor(registerButton);
        Button backToSignInButton = new Button("Back to Sign In");
        backToSignInButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        CursorUtils.setPointerCursor(backToSignInButton);

        registerPane.add(usernameLabel, 0, 0);
        registerPane.add(registerUsernameField, 1, 0);
        registerPane.add(usernameNote, 1, 1);
        registerPane.add(emailLabel, 0, 2);
        registerPane.add(registerEmailField, 1, 2);
        registerPane.add(nameLabel, 0, 3);
        registerPane.add(registerNameField, 1, 3);
        registerPane.add(passwordLabel, 0, 4);
        registerPane.add(registerPasswordField, 1, 4);
        registerPane.add(confirmPasswordLabel, 0, 5);
        registerPane.add(confirmPasswordField, 1, 5);
        registerPane.add(passwordNote, 1, 6);

        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(registerButton, backToSignInButton);
        registerPane.add(buttonsBox, 1, 7);

        // Event handlers
        registerButton.setOnAction(event -> {
            String username = registerUsernameField.getText();
            String email = registerEmailField.getText();
            String name = registerNameField.getText();
            String password = registerPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Perform validation
            if (!isValidUsername(username)) {
                showAlert("Invalid username. Username should only contain alphanumeric characters.", "Error");
            } else if (username.length() < 2 )  {
                showAlert("Username should be at least 2 characters.", "Error");
            }
            else if (!isValidEmail(email)) {
                showAlert("Invalid email address.", "Error");
            } else if (name.isEmpty()) {
                showAlert("Name is a required field", "Error");
            } else if (!isValidPassword(password)) {
                showAlert("Password must be at least 6 characters long.", "Error");
            } else if (!password.equals(confirmPassword)) {
                showAlert("Passwords do not match.", "Error");
            } else {
                boolean isUserRegistered = userService.registerUser(email, username, name, password);
                if (isUserRegistered) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registration successful!");
                    alert.setHeaderText(null);
                    alert.setContentText("Registration successful!");
                    alert.showAndWait();
                    clearFields(registerEmailField, registerUsernameField, registerNameField, registerPasswordField, confirmPasswordField);
                    app.showSignInUI();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Registration failed!");
                    alert.setHeaderText(null);
                    alert.setContentText("Email or Username is already taken!!");
                    alert.showAndWait();
                }
            }
        });

        backToSignInButton.setOnAction(event -> {
            app.showSignInUI();
        });

        return registerPane;
    }

    private void clearFields(TextField registerEmailField, TextField registerUsernameField, TextField registerNameField,
            TextField registerPasswordField, TextField confirmPasswordField) {
        registerEmailField.clear();
        registerUsernameField.clear();
        registerNameField.clear();
        registerPasswordField.clear();
        confirmPasswordField.clear();
    }

    private boolean isValidUsername(String username) {
        return username != null && username.matches("[a-zA-Z0-9]+");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    private void showAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
