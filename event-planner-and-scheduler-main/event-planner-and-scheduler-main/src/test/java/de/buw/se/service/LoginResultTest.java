package de.buw.se.service;

import de.buw.se.model.User;
import de.buw.se.service.LoginResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginResultTest {

    @Test
    public void testLoginResultSuccess() {
        User user = new User(1, "test@example.com", "testuser", "password");
        LoginResult loginResult = new LoginResult(true, user);

        assertTrue(loginResult.isSuccess());
        assertEquals(user, loginResult.getUserData());
    }
 
    @Test
    public void testLoginResultFailure() {
        LoginResult loginResult = new LoginResult(false, null);

        assertFalse(loginResult.isSuccess());
        assertNull(loginResult.getUserData());
        
    }
}