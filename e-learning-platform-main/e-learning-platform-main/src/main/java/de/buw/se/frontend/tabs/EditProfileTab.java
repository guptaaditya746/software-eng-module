package de.buw.se.frontend.tabs;

import java.sql.SQLException;

import de.buw.se.ELearningPlatform;
import de.buw.se.backend.context.SessionContext;
import de.buw.se.backend.model.User;
import de.buw.se.backend.service.LoginResult;
import de.buw.se.backend.service.UserService;
import de.buw.se.frontend.utils.AlertUtils;
import de.buw.se.frontend.utils.CursorUtils;
import de.buw.se.frontend.utils.NotificationHelper;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EditProfileTab {

    private final User currentUser;
    private final UserService userService;
    private final ELearningPlatform app;

    public EditProfileTab(User currentUser, UserService userService, ELearningPlatform app) {
        this.currentUser = currentUser;
        this.userService = new UserService();
        this.app = app;
    }

    public VBox createEditProfilePane() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        // Create input fields
        TextField emailField = new TextField(currentUser.getEmail());
        TextField nameField = new TextField(currentUser.getFullName());
        PasswordField passwordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();

        // Create labels
        Label emailLabel = new Label("Email:");
        Label nameLabel = new Label("Name:");
        Label passwordLabel = new Label("Password:");
        Label confirmPasswordLabel = new Label("Confirm Password:");

        // Create buttons
        Button saveButton = new Button("Save");
        CursorUtils.setPointerCursor(saveButton);
        Text feedback = new Text();

        // Add validation and submit action
        saveButton.setOnAction((ActionEvent event) -> {
            String newEmail = emailField.getText();
            String newName = nameField.getText();
            String newPassword = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            boolean emailChanged = !newEmail.equals(currentUser.getEmail()) && !newEmail.isEmpty();
            boolean nameChanged = !newName.equals(currentUser.getFullName()) && !newName.isEmpty();
            boolean passwordChanged = !newPassword.isEmpty() && newPassword.equals(confirmPassword)
                    && !newPassword.equals(currentUser.getPassword());

            if (newEmail.isEmpty() || newName.isEmpty()) {
                AlertUtils.showAlert("No changes detected", "Error");
                return;
            }

            if (!emailChanged && !nameChanged && !passwordChanged) {
                AlertUtils.showAlert("No changes detected.", "Error");
                return;
            }

            if (passwordChanged && !newPassword.equals(confirmPassword)) {
                feedback.setText("Passwords do not match.");
                return;
            }
            String updatedPassword = newPassword.isEmpty() ? currentUser.getPassword() : newPassword;
            int userId = currentUser.getId();
            String username = currentUser.getUsername();
            try {
                LoginResult loginResult = userService.editProfile(userId, newEmail, username, newName, updatedPassword);

                if (loginResult.isSuccess()) {
                    User userData = loginResult.getUserData();
                    SessionContext.getInstance().setCurrentUser(userData);
                    NotificationHelper.showNotification("Profile updated successfully!!");
                    resetFields(passwordField, confirmPasswordField);
                    app.updateTitle(newName);
                    app.showAuthenticatedUI(newName);
                } else {
                    AlertUtils.showAlert("Email is already taken", "Error");
                }
            } catch (SQLException | ClassNotFoundException e) {
                AlertUtils.showAlert("Error: " + e.getMessage(), "Error");
            }
        });

        // Create a grid pane for layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(emailLabel, 0, 0);
        gridPane.add(emailField, 1, 0);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(confirmPasswordLabel, 0, 3);
        gridPane.add(confirmPasswordField, 1, 3);
        gridPane.add(saveButton, 1, 4);
        gridPane.add(feedback, 1, 5);

        vbox.getChildren().add(gridPane);
        return vbox;
    }

    private void resetFields(TextField passwordField, TextField confirmPasswordField) {
        passwordField.clear();
        confirmPasswordField.clear();
    }
}
