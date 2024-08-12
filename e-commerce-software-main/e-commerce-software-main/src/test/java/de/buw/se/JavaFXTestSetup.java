package de.buw.se;

import org.junit.jupiter.api.BeforeAll;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

public abstract class JavaFXTestSetup {
    @BeforeAll
    static void initJavaFX() {
        new JFXPanel(); // Initializes the JavaFX environment
        Platform.runLater(() -> {
        }); // Runs an empty task to complete the initialization
    }
}
