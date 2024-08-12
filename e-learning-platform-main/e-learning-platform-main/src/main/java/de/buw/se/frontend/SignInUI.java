package de.buw.se.frontend;

import java.sql.SQLException;

import de.buw.se.ELearningPlatform;
import de.buw.se.backend.context.SessionContext;
import de.buw.se.backend.model.User;
import de.buw.se.backend.service.LoginResult;
import de.buw.se.backend.service.UserService;
import de.buw.se.frontend.utils.AlertUtils;
import de.buw.se.frontend.utils.CursorUtils;
import de.buw.se.frontend.utils.NotificationHelper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class SignInUI {

    private final ELearningPlatform app;
    private final UserService userService;

    public SignInUI(ELearningPlatform app) {
        this.app = app;
        this.userService = new UserService();
    }

    public Pane getPane() {
        GridPane signInPane = new GridPane();
        signInPane.setPadding(new Insets(20));
        signInPane.setVgap(10);
        signInPane.setHgap(10);

        // Labels
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        TextField signInUsernameField = new TextField();
        signInUsernameField.setPromptText("Enter your email or username");
        PasswordField signInPasswordField = new PasswordField();
        signInPasswordField.setPromptText("Enter your password");
        Label passwordNote = new Label("(Password must be at least 6 characters long)");
        passwordNote.setFont(Font.font(10));
        passwordNote.setTextFill(Color.GRAY);

        Button signInButton = new Button("Sign In");
        signInButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        CursorUtils.setPointerCursor(signInButton);
        Button goToRegisterButton = new Button("Register");
        goToRegisterButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        CursorUtils.setPointerCursor(goToRegisterButton);

        signInPane.add(usernameLabel, 0, 0);
        signInPane.add(signInUsernameField, 1, 0);
        signInPane.add(passwordLabel, 0, 1);
        signInPane.add(signInPasswordField, 1, 1);
        signInPane.add(passwordNote, 1, 2);

        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(signInButton, goToRegisterButton);
        signInPane.add(buttonsBox, 1, 3);

        // Event handlers
        signInButton.setOnAction((var event) -> {
            String emailOrUsername = signInUsernameField.getText();
            String password = signInPasswordField.getText();

            if (emailOrUsername == null || emailOrUsername.isEmpty() || password == null || password.isEmpty()) {
                AlertUtils.showAlert( "Please enter all the fields correctly.", "Input Error");
            }

            if (isValidUsernameInput(emailOrUsername) && isValidPassword(password)) {
                LoginResult loginResult = userService.loginUser(emailOrUsername, password);
                if (loginResult.isSuccess()) {
                    User userData = loginResult.getUserData();
                    SessionContext.getInstance().setCurrentUser(userData);
                    String uName = userData.getFullName();
                    try {
                        showSuccessAndChangeLayout("Login Successful", uName);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    AlertUtils.showAlert("Invalid email or password", "Login Errror");
                }
            } else {
                // Show error message
                AlertUtils.showAlert("Invalid email or password", "Errror");

            }
        });

        goToRegisterButton.setOnAction(event -> {
            app.showRegisterUI();
        });

        return signInPane;
    }

    protected  boolean isValidLength(String input){
        if (input.length() < 1 || input.length() > 254) {
            return false;
        }
        return true;
    }


    protected boolean isValidEmail(String email) {
        // Basic email validation

        return  email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}") && isValidLength(email);
    }

    protected boolean isValidInput(String input) {
        // Check if input is not null and not empty
        return input != null && !input.trim().isEmpty() && isValidLength(input);
    }

    protected boolean isValidUsername(String username) {
        // Alphanumeric validation for username
        return username != null && username.matches("[a-zA-Z0-9]+") && isValidInput(username);
    }

    public boolean isValidUsernameInput(String input) {
        return isValidInput(input) && (isValidEmail(input) || isValidUsername(input)) && isValidLength(input);
    }

    protected boolean isValidPassword(String password) {
        return password != null && password.length() >= 6 && isValidLength(password);
    }

    protected void showSuccessAndChangeLayout(String message, String uName) throws SQLException, ClassNotFoundException {
        NotificationHelper.showNotification(message);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(0.1),
                e -> {
                    try {
                        app.showAuthenticatedUI(uName);
                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }));
        timeline.play();
    }
}
