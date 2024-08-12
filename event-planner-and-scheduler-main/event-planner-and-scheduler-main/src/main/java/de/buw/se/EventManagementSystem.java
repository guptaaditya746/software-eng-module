package de.buw.se;

import java.sql.SQLException;


import de.buw.se.UI.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EventManagementSystem extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        showSignInUI();  
    }

    // Method to show sign-in UI
    public void showSignInUI() {
        SignInUI signInUI = new SignInUI(this);
        Scene scene = new Scene(signInUI.getPane(), 370, 370);    // rtkm changed width length
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign In");
        primaryStage.show();
    }
 
    // Method to show register UI
    public void showRegisterUI() {
        SignUpUI registerUI = new SignUpUI(this);
        Scene scene = new Scene(registerUI.getPane(), 440, 470);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign Up");
        primaryStage.show();
    }

    // Method to show authenticated UI
    public void showAuthenticatedUI(String username) throws SQLException {
        AuthenticatedUI authenticatedUI = new AuthenticatedUI(this, username);
        Scene scene = new Scene(authenticatedUI.getTabPane(), 670, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
