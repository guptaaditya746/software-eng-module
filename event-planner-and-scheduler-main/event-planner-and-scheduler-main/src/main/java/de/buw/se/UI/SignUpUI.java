package de.buw.se.UI;

import de.buw.se.EventManagementSystem;
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


public class SignUpUI { 
 
    private EventManagementSystem app;
    private UserService userService;

    public SignUpUI(EventManagementSystem app) {
        this.app = app;
        this.userService = new UserService();
    }

    public Pane getPane() { 
        GridPane registerPane = new GridPane();
        
        registerPane.setPadding(new Insets(10, 10, 10, 10));
        registerPane.setVgap(10);
        registerPane.setHgap(10);

        // Set background color to registerPane
        registerPane.setStyle("-fx-background-color: #319164;");
        // Insert image        signInPane.setAlignment(Pos.CENTER);
        Image image = new Image("EM Register Logo.png");
        ImageView imageView = new ImageView(image);
        // Set the size of the ImageView
        imageView.setFitWidth(210); // Adjust width as needed
        imageView.setFitHeight(235); // Adjust height as needed

        // Labels
        Label usernameLabel = new Label("Username:");
        Label emailLabel = new Label("Email:");
        Label passwordLabel = new Label("Password:");
        Label confirmPasswordLabel = new Label("Confirm Password:");

        TextField registerUsernameField = new TextField();
        registerUsernameField.setPromptText("Enter your username");
        Label usernameNote = new Label("  (Alphanumeric characters only)");
        usernameNote.setFont(Font.font(10));
        usernameNote.setTextFill(Color.GRAY);

        TextField registerEmailField = new TextField();
        registerEmailField.setPromptText("Enter your email");
        PasswordField registerPasswordField = new PasswordField();
        registerPasswordField.setPromptText("Enter your password");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");
        Label passwordNote = new Label("(Password must be at least 6 characters long)");
        passwordNote.setFont(Font.font(10));
        passwordNote.setTextFill(Color.RED);

        Button registerButton = new Button("Sign Up");
        registerButton.setStyle("-fx-background-color: #C40C0C; -fx-text-fill: white; -fx-font-weight: bold;");
        Button backToSignInButton = new Button("Back to Sign In");
        backToSignInButton.setStyle("-fx-background-color: #FF8A08; -fx-text-fill: white; -fx-font-weight: bold;");

        registerPane.add(imageView, 0, 0, 2, 1);//Edit the position of the image
        GridPane.setHalignment(imageView, HPos.RIGHT);
        imageView.setStyle("-fx-background-color: transparent;");
        registerPane.add(usernameLabel, 0, 1);
        registerPane.add(registerUsernameField, 1, 1);
        registerPane.add(usernameNote, 1, 3);
        registerPane.add(emailLabel, 0, 3);
        registerPane.add(registerEmailField, 1, 3);        registerPane.add(passwordLabel, 0, 4);
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
            String password = registerPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Perform validation
            if (!isValidUsername(username)) {
                showAlert("Invalid username. Username should only contain alphanumeric characters.", "Error");
            } else if (!isValidEmail(email)) {
                showAlert("Invalid email address.", "Error");
            } else if (!isValidPassword(password)) {
                showAlert("Password must be at least 6 characters long.", "Error");
            } else if (!password.equals(confirmPassword)) {
                showAlert("Passwords do not match.", "Error");
            } else {
                // System.out.println("email" + email + "<<< username >>> " + username + "<<<<
                // password >>>> " + password);
                boolean success = userService.registerUser(email, username, password);
                if (success) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registration successful!");
                    alert.setHeaderText(null);
                    alert.setContentText("Registration successful!");
                    alert.showAndWait();
                    clearFields(registerEmailField, registerUsernameField, registerPasswordField, confirmPasswordField);
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

    private void clearFields(TextField registerEmailField, TextField registerUsernameField,
            TextField registerPasswordField, TextField confirmPasswordField) {
        registerEmailField.clear();
        registerUsernameField.clear();
        registerPasswordField.clear();
        confirmPasswordField.clear();
    }

    protected static boolean isValidUsername(String username) {
        return username != null && username.matches("[a-zA-Z0-9]+");
    }

    protected static boolean isValidEmail(String email) {
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6 && password.matches("[a-zA-Z0-9]+");
    }

    

    private void showAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
