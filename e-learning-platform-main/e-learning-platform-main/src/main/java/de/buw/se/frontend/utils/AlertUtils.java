package de.buw.se.frontend.utils;

import javafx.scene.control.Alert;

public class AlertUtils {

    public static void showAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
