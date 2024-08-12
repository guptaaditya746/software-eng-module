package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.buw.se.model.Event;
import de.buw.se.model.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

class EventManagementSystemTest {

    private static EventManagementSystem ems;
    private static Stage primaryStage;

    // Wrapper to start JavaFX thread for testing purposes
    public static class TestApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            // Do nothing
        } 
    }

    @BeforeAll
    public static void initJFX() throws InterruptedException {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(TestApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        Thread.sleep(500); // Give time for JavaFX to start

        // Initialize ems after JavaFX is started
        ems = new EventManagementSystem();
    }

    @AfterAll
    public static void teardownJFX() {
        Platform.exit();
    }

    @Test
    void testUserConstructorAndGetters() {
        User user = new User(1, "test@example.com", "testuser", "password");

        assertEquals(1, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    void testEventConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        Event event = new Event(1, 1, "Test Event", timestamp, "10:00", "Description");

        assertEquals(1, event.getEventId());
        assertEquals(1, event.getUserId());
        assertEquals("Test Event", event.getEventName());
        assertEquals(timestamp, event.getEventDate());
        assertEquals("10:00", event.getEventTime());
        assertEquals("Description", event.getDescription());
    }

    @Test
    void testEventManagementSystemInitialization() {
        assertNotNull(ems, "EventManagementSystem should be instantiated correctly");
    }

    @Test
    void testShowRegisterUI() {
        Platform.runLater(() -> {
            ems.showRegisterUI();
            assertEquals("Sign Up", primaryStage.getTitle());
        });
    }

    @Test
    void testShowSignInUI() {
        Platform.runLater(() -> {
            ems.showSignInUI();
            assertEquals("Sign In", primaryStage.getTitle());
        });
    }

    @Test
    void testShowAuthenticatedUI() {
        Platform.runLater(() -> {
            try {
                ems.showAuthenticatedUI("testuser");
                assertEquals("Event Management System", primaryStage.getTitle());
            } catch (SQLException e) {
                fail("showAuthenticatedUI should not throw SQLException");
            }
        });
    }
}