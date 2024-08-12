package de.buw.se.UI.utils;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class NotificationHelper {

    public static void showNotification(String message) {
        Label label = new Label(message);
        label.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px;");
        label.setWrapText(true);

        StackPane root = new StackPane(label);
        root.setAlignment(Pos.CENTER); 
        root.setPrefSize(300, 100);

        Scene scene = new Scene(root);  
        scene.setFill(Color.TRANSPARENT);
        

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(javafx.stage.StageStyle.TRANSPARENT);

        stage.show();

        // Fade out after 2 seconds
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), root);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.play();

        // Start a separate thread to pause the execution for 2 seconds
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Close the stage after 2 seconds
            Platform.runLater(stage::close);
        }).start();
    }
}
