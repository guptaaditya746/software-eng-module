package de.buw.se;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import de.buw.se.Domain.User;
import de.buw.se.ShoppingCartPage;

public class AppGUI extends Application {

    private Stage primaryStage;
    private static final String CSV_FILE_PATH = "src/main/resources/Onlineshoppingstore.csv";

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Online Shopping Store");

       
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));

        grid.setVgap(10);
        grid.setHgap(10);

        Label usernameLabel = new Label("Mail ID:");
        GridPane.setConstraints(usernameLabel, 0, 0);

        TextField usernameInput = new TextField();
        usernameInput.setId("usernameInput");
        usernameInput.setPromptText("Enter your username");
        GridPane.setConstraints(usernameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        PasswordField passwordInput = new PasswordField();
        passwordInput.setId("passwordInput");
        passwordInput.setPromptText("Enter your password");

        GridPane.setConstraints(passwordInput, 1, 1);

        Label promptLabel = new Label("");
        promptLabel.setId("promptLabel");
        GridPane.setConstraints(promptLabel, 1, 3);

        Button loginButton = new Button("Login");
        loginButton.setId("loginButton");
        GridPane.setConstraints(loginButton, 1, 2);

        loginButton.setOnAction(e -> {
            List<User> users = this.saveUsersFromCSV();
            boolean found = false;
            System.out.println("Checking credentials...");

            for (User user : users) {
                System.out.println("Checking user: " + user.getMail());
                if (user.getMail().equals(usernameInput.getText()) && user.getPassword().equals(passwordInput.getText()))
                 {
                    System.out.println("User found, redirecting to shopping cart...");
                    redirectToShoppingCartPage();
                    found = true;

                    break;
                }
            }


            if (!found) {
                System.out.println("User not found , Enter valid credentials");
                promptLabel.setText("User Not Found , Enter valid credentials"); }
        });

        Button registerButton = new Button("Register");
        registerButton.setId("registerButton");

        GridPane.setConstraints(registerButton, 1, 4);
        registerButton.setOnAction(e -> {
            redirectToRegisterPage();
        });

        Button forgotPasswordButton = new Button("Forgot Password");
        forgotPasswordButton.setId("forgotPasswordButton");

        GridPane.setConstraints(forgotPasswordButton, 1, 5);

        forgotPasswordButton.setOnAction(e -> {

            redirectToForgotPasswordPage();
        });

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, registerButton, forgotPasswordButton, promptLabel);

       
        Scene scene = new Scene(grid, 300, 250); 

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public List<User> saveUsersFromCSV() {
        List<User> users = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine != null && nextLine.length == 4) {
                    User user = new User();
                    user.setName(nextLine[0]);
                    user.setPassword(nextLine[1]);
                    user.setPhoneNumber(nextLine[2]);
                    user.setMail(nextLine[3]);
                    users.add(user);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void redirectToRegisterPage() {

        RegisterPage registerPage = new RegisterPage();
        registerPage.start(primaryStage);
    }

    private void redirectToHomePage() {

        SearchPage homePage = new SearchPage();
        homePage.start(primaryStage);
    }


    private void redirectToForgotPasswordPage() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage();
        forgotPasswordPage.start(primaryStage);
    }

    private void redirectToShoppingCartPage() {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
        
        shoppingCartPage.start(primaryStage);
    }



    public static void main(String[] args) {
        launch(args);
    }
}



