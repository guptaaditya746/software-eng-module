package de.buw.se.UI;

import java.sql.SQLException;

import de.buw.se.EventManagementSystem;
import de.buw.se.context.SessionContext;
import de.buw.se.model.User;
import de.buw.se.service.LoginResult;
import de.buw.se.service.UserService;
import javafx.geometry.HPos;
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

//Improve feature
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

 
public class SignInUI {

    private EventManagementSystem app; 
    private UserService userService;

    public SignInUI(EventManagementSystem app) {
        this.app = app;
        this.userService = new UserService();
    }

    public Pane getPane() {
        GridPane signInPane = new GridPane();
        
        signInPane.setPadding(new Insets(20));
        signInPane.setVgap(10);
        signInPane.setHgap(10);
        // Set background color to signInPane
        signInPane.setStyle("-fx-background-color: #319164;");
        // Insert image        signInPane.setAlignment(Pos.CENTER);
        Image image = new Image("EM Sign up Logo.png");
        ImageView imageView = new ImageView(image);
        // Set the size of the ImageView
        imageView.setFitWidth(200); // Adjust width as needed
        imageView.setFitHeight(200); // Adjust height as needed

        // Labels
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        TextField signInUsernameField = new TextField();
        signInUsernameField.setPromptText("Enter your email or username");
        PasswordField signInPasswordField = new PasswordField();
        signInPasswordField.setPromptText("Enter your password");
        Label passwordNote = new Label("(Password must be at least 6 characters long)");
        passwordNote.setFont(Font.font(10));
        passwordNote.setTextFill(Color.RED);

        Button signInButton = new Button("Sign In");
        signInButton.setStyle("-fx-background-color: #898121; -fx-text-fill: white; -fx-font-weight: bold;");
        Button goToRegisterButton = new Button("Register");
        goToRegisterButton.setStyle("-fx-background-color: #0A6847; -fx-text-fill: white; -fx-font-weight: bold;");

        signInPane.add(imageView, 0, 0, 2, 1);//Edit the position of the image
        GridPane.setHalignment(imageView, HPos.RIGHT);// Add image to the grid pane
        signInPane.add(usernameLabel, 0, 1);
        signInPane.add(signInUsernameField, 1, 1);
        signInPane.add(passwordLabel, 0, 2);
        signInPane.add(signInPasswordField, 1, 2);
        signInPane.add(passwordNote, 1, 3);

        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(signInButton, goToRegisterButton);
        signInPane.add(buttonsBox, 1, 4);


        // Event handlers
        signInButton.setOnAction(event -> {
            String emailOrUsername = signInUsernameField.getText();
            String password = signInPasswordField.getText();

            if (isValidUsernameInput(emailOrUsername) && isValidPassword(password)) {
                LoginResult loginResult = userService.loginUser(emailOrUsername, password);
                if (loginResult.isSuccess()) {
                    User userData = loginResult.getUserData();
                    SessionContext.getInstance().setCurrentUser(userData);
                    try {
                        app.showAuthenticatedUI("admin");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid email or password");
                    alert.showAndWait();
                }
            } else {
                // Show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email or password");
                alert.showAndWait();
            }
        });

        goToRegisterButton.setOnAction(event -> {
            app.showRegisterUI();
        });

        return signInPane;
    }

    protected boolean isValidEmail(String email) {
        // Basic email validation
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    protected boolean isValidInput(String input) {
        // Check if input is not null and not empty
        return input != null && !input.trim().isEmpty();
    }

    protected boolean isValidUsername(String username) {
        // Alphanumeric validation for username
        return username != null && username.matches("[a-zA-Z0-9]+");
    }

    public boolean isValidUsernameInput(String input) {
        return isValidInput(input) && (isValidEmail(input) || isValidUsername(input));
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}