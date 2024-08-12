package de.buw.se.frontend.tabs;

import de.buw.se.ELearningPlatform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

public class LogoutTab {

    private final ELearningPlatform app;

    public LogoutTab(ELearningPlatform app) {
        this.app = app;
    }

    public VBox createLogoutPane() {
        VBox logoutLayout = new VBox(20);
        logoutLayout.setAlignment(Pos.CENTER);

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: linear-gradient(to bottom, #ff7e5f, #feb47b);"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 16px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 10 20;"
                + "-fx-background-radius: 5;"
                + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.5, 0, 0);");

        logoutButton.setOnAction(event -> {
            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to logout?");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");

            confirmAlert.getButtonTypes().setAll(yesButton, noButton);

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == yesButton) {
                    app.showSignInUI();
                }
            });
        });

        logoutLayout.getChildren().add(logoutButton);

        return logoutLayout;
    }
}
