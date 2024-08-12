package de.buw.se.backend.context;
import de.buw.se.backend.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SessionContextTest {

    @Test
    public void testGetInstance() {
        SessionContext instance1 = SessionContext.getInstance();
        assertNotNull(instance1);
        
        SessionContext instance2 = SessionContext.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testSetCurrentUser() {
        SessionContext sessionContext = SessionContext.getInstance();
        User user = new User(1, "user@example.com", "username", "User FullName", "password123");
        sessionContext.setCurrentUser(user);
        
        assertEquals(user, sessionContext.getCurrentUser());
    }

    @Test
    public void testGetCurrentUser() {
        SessionContext sessionContext = SessionContext.getInstance();
        User user = new User(1, "user@example.com", "username", "User FullName", "password123");
        sessionContext.setCurrentUser(user);
        
        User retrievedUser = sessionContext.getCurrentUser();
        assertNotNull(retrievedUser);
        assertEquals(user.getId(), retrievedUser.getId());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
        assertEquals(user.getUsername(), retrievedUser.getUsername());
        assertEquals(user.getFullName(), retrievedUser.getFullName());
        assertEquals(user.getPassword(), retrievedUser.getPassword());
    }

    @Test
    public void testSingletonBehavior() {
        SessionContext instance1 = SessionContext.getInstance();
        SessionContext instance2 = SessionContext.getInstance();
        
        assertSame(instance1, instance2);
        
        User user1 = new User(1, "user1@example.com", "user1", "User One", "password1");
        instance1.setCurrentUser(user1);
        
        User user2 = new User(2, "user2@example.com", "user2", "User Two", "password2");
        instance2.setCurrentUser(user2);
        
        assertSame(instance1.getCurrentUser(), instance2.getCurrentUser());
        assertEquals(user2, instance1.getCurrentUser());
    }
}
