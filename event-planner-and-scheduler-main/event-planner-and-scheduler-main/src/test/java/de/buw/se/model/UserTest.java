package de.buw.se.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UserTest {
 
    @Test
    void testUserConstructorAndGetters() {
        // Test data
        int id = 1;
        String email = "john.doe@example.com";
        String username = "johndoe";
        String password = "password123";

        // Create a User instance
        User user = new User(id, email, username, password);
        

        // Test getters
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

    @Test
    void testUserSetters() {
        // Create a User instance
        User user = new User(1, "john.doe@example.com", "johndoe", "password123");

        // Test setters
        user.setEmail("john.smith@example.com");
        assertEquals("john.smith@example.com", user.getEmail());

        user.setUsername("johnsmith");
        assertEquals("johnsmith", user.getUsername());

        user.setPassword("newpassword123");
        assertEquals("newpassword123", user.getPassword());
    }

    @Test
    void testUserEquality() {
        // Create two User instances with same attributes
        User user1 = new User(1, "john.doe@example.com", "johndoe", "password123");
        User user2 = new User(1, "john.doe@example.com", "johndoe", "password123");

        // Test equals method
        assertEquals(user1, user2);

        // Test hashCode method
       assertEquals(user1.hashCode(), user2.hashCode()); //it should be equal 
    }

    

}