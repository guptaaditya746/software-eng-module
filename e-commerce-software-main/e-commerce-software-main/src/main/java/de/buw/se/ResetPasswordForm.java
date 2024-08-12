package de.buw.se;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResetPasswordForm extends Application {

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Reset Password");

        VBox root = new VBox();
        root.setPrefSize(300, 200);

        AnchorPane formPane = new AnchorPane();

        Label resetPasswordLabel = new Label("Reset Password");
        resetPasswordLabel.setLayoutX(80);
        resetPasswordLabel.setLayoutY(20);
        resetPasswordLabel.setFont(new Font(20));

        TextField usernameField = new TextField();
        usernameField.setLayoutX(50);
        usernameField.setLayoutY(60);
        usernameField.setPrefSize(200, 30);
        usernameField.setPromptText("Username");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setLayoutX(50);
        newPasswordField.setLayoutY(100);
        newPasswordField.setPrefSize(200, 30);
        newPasswordField.setPromptText("New Password");

        Button resetButton = new Button("Reset Password");
        resetButton.setLayoutX(90);
        resetButton.setLayoutY(140);
        resetButton.setPrefSize(120, 30);

        resetButton.setOnAction(event -> {
            String username = usernameField.getText();
            String newPassword = newPasswordField.getText();
            boolean isReset = false;
            try {
                isReset = resetPassword(username, newPassword);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(isReset ? AlertType.INFORMATION : AlertType.ERROR);
            alert.setTitle(isReset ? "Success" : "Error");
            alert.setHeaderText(null);
            alert.setContentText(isReset ? "Password reset successful!" : "Username not found!");
            alert.showAndWait();

            if (isReset) {
                primaryStage.close();
            }
        });

        formPane.getChildren().addAll(resetPasswordLabel, usernameField, newPasswordField, resetButton);
        root.getChildren().add(formPane);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    protected boolean resetPassword(String username, String newPassword) throws SQLException {
        // Update the password in the database
        String updateQuery = "UPDATE Users SET PASSWORD = ? WHERE USERNAME = ?";
        try (Connection conn = DataStoreSql.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            return pstmt.executeUpdate() > 0; // Return true if at least one row is affected (password reset)
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
