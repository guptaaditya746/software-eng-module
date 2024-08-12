package de.buw.se.context;

import static org.junit.jupiter.api.Assertions.*;

import de.buw.se.model.User;
import org.junit.jupiter.api.*;


class SessionContextTest {

    @BeforeEach 
    void setUp() {
        // Reset the instance before each test
        // This uses reflection to reset the singleton instance (only for testing)
        try {
            java.lang.reflect.Field instance = SessionContext.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            fail("Failed to reset the singleton instance.");
        }
    }

    @Test
    void testSingletonInstance() {
        SessionContext instance1 = SessionContext.getInstance(); 
        SessionContext instance2 = SessionContext.getInstance();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertNotSame(instance1, instance2); //because both of them are not the same
    }

    @Test
    void testSetCurrentUser() {
        SessionContext context = SessionContext.getInstance();
        User user = new User(1, "test@example.com", "testUser", "password123");

        context.setCurrentUser(user);
        assertEquals(user, context.getCurrentUser());
    }

    @Test
    void testGetCurrentUser() {
        SessionContext context = SessionContext.getInstance();
        User user = new User(2, "another@example.com", "anotherUser", "password456");

        context.setCurrentUser(user);
        assertEquals(user, context.getCurrentUser());
    }
}