package de.buw.se;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HomePage extends Application {

    private AddToCart cart = new AddToCart();

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Home Page");

        // Main root layout
        HBox root = new HBox();
        root.setPrefSize(640, 400);
        root.setSpacing(20);
        root.setPadding(new Insets(20));
        
        // Side form for creating an account
        VBox sideForm = new VBox();
        sideForm.setPrefSize(298, 400);
        sideForm.setStyle("-fx-background-color: #606060;");
        sideForm.setAlignment(Pos.CENTER);
        sideForm.setSpacing(20);
        sideForm.setPadding(new Insets(20));

        Label welcomeLabel1 = new Label("Welcome to");
        welcomeLabel1.setTextFill(Color.WHITE);
        welcomeLabel1.setFont(new Font(13));

        Label welcomeLabel2 = new Label("Electronic Gadget Store");
        welcomeLabel2.setTextFill(Color.WHITE);
        welcomeLabel2.setFont(new Font(18));

        Button createNewAccountButton = new Button("Create New Account");
        createNewAccountButton.setPrefSize(199, 30);
        createNewAccountButton.setOnAction(event -> displayUserForm(primaryStage));

        sideForm.getChildren().addAll(welcomeLabel1, welcomeLabel2, createNewAccountButton);

        // Login form
        VBox loginForm = new VBox();
        loginForm.setPrefSize(300, 400);
        loginForm.setSpacing(20);
        loginForm.setPadding(new Insets(20));
        loginForm.setAlignment(Pos.CENTER_LEFT);

        Label loginLabel = new Label("Login Account");
        loginLabel.setTextFill(Color.valueOf("#2653d9"));
        loginLabel.setFont(new Font(20));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password?");
        forgotPasswordLink.setOnAction(event -> displayResetPasswordForm(primaryStage));

        Button loginButton = new Button("Login");
        loginButton.setPrefSize(220, 30);
        loginButton.setOnAction(event -> {
            String userName = usernameField.getText();
            String password = passwordField.getText();

            if (userName.isEmpty() || password.isEmpty()) {
                showAlert(AlertType.ERROR, "Validation Error", "Please fill in both username and password.");
                return;
            }

            boolean isValidUser = false;
            try {
                isValidUser = DataStoreSql.validateUser(userName, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (isValidUser) {
                displayProductForm(primaryStage, userName);
            } else {
                showAlert(AlertType.ERROR, "Error Message", "Incorrect Username/Password");
            }
        });

        loginForm.getChildren().addAll(loginLabel, usernameField, passwordField, forgotPasswordLink, loginButton);

        // Add sideForm and loginForm to root
        root.getChildren().addAll(sideForm, loginForm);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayProductForm(Stage primaryStage, String username) {
        ProductPage productPage = new ProductPage(username, cart);
        productPage.start(new Stage());
        primaryStage.close();
    }

    private void displayUserForm(Stage primaryStage) {
        NewUserForm newUserForm = new NewUserForm();
        newUserForm.start(new Stage());
    }

    private void displayResetPasswordForm(Stage primaryStage) {
        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        resetPasswordForm.start(new Stage());
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
