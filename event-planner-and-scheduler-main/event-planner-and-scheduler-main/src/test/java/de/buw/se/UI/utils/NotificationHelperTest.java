package de.buw.se.UI.utils;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationHelperTest {

    // Helper class to initialize JavaFX
    public static class TestApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            // No need to show the primary stage
        }
    }

    @BeforeAll
    public static void initJFX() throws InterruptedException {
    	
        // Initialize JavaFX
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(TestApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        Thread.sleep(500); // Wait for JavaFX to initialize
    }

    @Test
    void testShowNotification() throws InterruptedException {
        final String notificationMessage = "Test Notification";

        // Run the notification display on the JavaFX application thread
        Platform.runLater(() -> {
            try {
                NotificationHelper.showNotification(notificationMessage);
            } catch (Throwable t) {
                fail("A throwable occurred while showing notification: " + t.getMessage());
            }
        });

        // Wait for the notification to be displayed
        Thread.sleep(1000); // Adjust timing as necessary

        // Check if the notification is shown
        assertTrue(isNotificationShown(), "Notification should be shown");

        // Verify the content of the notification
        Platform.runLater(() -> {
            Stage stage = getNotificationStage();
            assertNotNull(stage);

            StackPane root = (StackPane) stage.getScene().getRoot();
            assertNotNull(root);

            Label label = (Label) root.getChildren().get(0);
            assertNotNull(label);

            assertEquals(notificationMessage, label.getText(), "Notification message should match");

            // Verify UI styling
            String labelStyle = label.getStyle();
            assertTrue(labelStyle.contains("-fx-background-color: #4CAF50;"), "Background color should be #4CAF50");
            assertTrue(labelStyle.contains("-fx-text-fill: white;"), "Text color should be white");
        });

        // Wait for the notification to fade out
        Thread.sleep(3000); // Adjust timing as necessary

        // Check if the notification is hidden
        assertFalse(isNotificationShown(), "Notification should be hidden after fading out");
    }

    // Helper method to check if notification is shown
    private boolean isNotificationShown() {
        for (Window window : Stage.getWindows()) {
            if (window instanceof Stage) {
                Stage stage = (Stage) window;
                if (stage.isShowing() && stage.getScene() != null && !stage.getScene().getRoot().getChildrenUnmodifiable().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    // Helper method to get the notification stage
    private Stage getNotificationStage() {
        for (Window window : Stage.getWindows()) {
            if (window instanceof Stage) {
                Stage stage = (Stage) window;
                if (stage.isShowing() && stage.getScene() != null && !stage.getScene().getRoot().getChildrenUnmodifiable().isEmpty()) {
                    return stage;
                }
            }
        }
        return null;
    }
}