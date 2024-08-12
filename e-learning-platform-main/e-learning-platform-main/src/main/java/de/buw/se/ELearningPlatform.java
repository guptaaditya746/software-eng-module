package de.buw.se;

import java.sql.SQLException;

import de.buw.se.frontend.AuthenticatedUI;
import de.buw.se.frontend.SignInUI;
import de.buw.se.frontend.SignUpUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ELearningPlatform extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showSignInUI();
    }

    // Method to show sign-in UI
    public void showSignInUI() {
        SignInUI signInUI = new SignInUI(this);
        Scene scene = new Scene(signInUI.getPane(), 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign In");
        primaryStage.show();
    }

    // Method to show register UI
    public void showRegisterUI() {
        SignUpUI registerUI = new SignUpUI(this);
        Scene scene = new Scene(registerUI.getPane(), 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign Up");
        primaryStage.show();
    }

    // Method to show authenticated UI
    public void showAuthenticatedUI(String username) throws SQLException, ClassNotFoundException {
        AuthenticatedUI authenticatedUI = new AuthenticatedUI(this, username);
        Scene scene = new Scene(authenticatedUI.getTabPane(), 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle(username);
        primaryStage.show();
    }

    // Method to update the primary stage title
    public void updateTitle(String newTitle) {
        primaryStage.setTitle(newTitle);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
