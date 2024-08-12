package de.buw.se.frontend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignInUITest {

    private SignInUI signInUI = new SignInUI(null);

    @Test
    public void testIsValidEmail() {
        // Pass cases
        assertTrue(signInUI.isValidEmail("test@example.com"));
        assertTrue(signInUI.isValidEmail("user.name+tag+sorting@example.com"));
        assertTrue(signInUI.isValidEmail("user123@example.co.uk"));

        // Fail cases
        assertFalse(signInUI.isValidEmail("plainaddress"));
        assertFalse(signInUI.isValidEmail("user@.com"));
        assertFalse(signInUI.isValidEmail("user@com"));
        assertFalse(signInUI.isValidEmail("user@domain@domain.com"));
        assertFalse(signInUI.isValidEmail(null));
    }

    @Test
    public void testIsValidInput() {
        // Pass cases
        assertTrue(signInUI.isValidInput("Hello"));
        assertTrue(signInUI.isValidInput("  Valid input  "));

        // Fail cases
        assertFalse(signInUI.isValidInput(""));
        assertFalse(signInUI.isValidInput("     "));
        assertFalse(signInUI.isValidInput(null));
    }

    @Test
    public void testIsValidUsername() {
        // Pass cases
        assertTrue(signInUI.isValidUsername("user123"));
        assertTrue(signInUI.isValidUsername("username"));

        // Fail cases
        assertFalse(signInUI.isValidUsername("user@name"));
        assertFalse(signInUI.isValidUsername("user name"));
        assertFalse(signInUI.isValidUsername("user.name"));
        assertFalse(signInUI.isValidUsername(null));
    }

    @Test
    public void testIsValidUsernameInput() {
        // Pass cases
        assertTrue(signInUI.isValidUsernameInput("validuser"));
        assertTrue(signInUI.isValidUsernameInput("user123"));
        assertTrue(signInUI.isValidUsernameInput("test@example.com"));

        // Fail cases
        assertFalse(signInUI.isValidUsernameInput("invalid user"));
        assertFalse(signInUI.isValidUsernameInput("user@name"));
        assertFalse(signInUI.isValidUsernameInput("user@domain@domain.com"));
        assertFalse(signInUI.isValidUsernameInput(""));
        assertFalse(signInUI.isValidUsernameInput("     "));
        assertFalse(signInUI.isValidUsernameInput(null));
    }

    @Test
    public void testIsValidPassword() {
        // Pass cases
        assertTrue(signInUI.isValidPassword("abcdef"));
        assertTrue(signInUI.isValidPassword("123456"));
        assertTrue(signInUI.isValidPassword("password123"));

        // Fail cases
        assertFalse(signInUI.isValidPassword("abcde"));
        assertFalse(signInUI.isValidPassword(""));
        assertFalse(signInUI.isValidPassword(null));
    }

    @Test
    public void testIsValidUsername_Story() {

        String username = "OnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglightOnnglig";

        // Pass cases
        assertFalse(signInUI.isValidUsername(username));
    }

    @Test
    public void testIsValidPassword_Story() {

        String password = "Once upon a time, in a faraway kingdom, there lived a young girl named Cinderella. She was a kind, gentle, and beautiful child with the most radiant smile. Cinderella had a heart full of love, but Unfortunately, fate hadn't been kind to Cinderella. Her mother had passed away, leaving her alone with her selfish stepmother.\n" +
                "\n" +
                "The stepmother had two daughters of her own, and she was terribly jealous of Cinderella's beauty and kind heart. She treated Cinderella unfairly, making her do all the housework and dress in rags while her own daughters wore fine clothes and lived in luxury.\n" +
                "\n" +
                "Despite the cruelty she faced, Cinderella remained kind and gentle, never letting her stepmother's unkindness change who she was. She would often seek solace in the garden, talking to the birds and the animals, who had become her dearest friends.\n" +
                "\n" +
                "One day, the kingdom received exciting news. The prince was hosting a grand ball at the palace, and every maiden in the land was invited. Cinderella's stepsisters were overjoyed and spent weeks preparing their gowns and practicing their dance moves.\n" +
                "\n" +
                "Cinderella watched her stepsisters with a wistful smile, hoping she might be able to attend the ball and experience a bit of the magic she had heard about in her mother's stories. But her stepmother was adamant that Cinderella would stay home and continue with her chores.\n" +
                "\n" +
                "The night of the ball arrived, and Cinderella was left alone in her tattered clothes while her stepsisters and stepmother departed for the palace. She sat by the fireplace, tears in her eyes, wishing for just a taste of the magic her mother had told her about.\n" +
                "\n" +
                "Suddenly, a soft, melodious voice filled the room. It was her fairy godmother, a beautiful figure surrounded by a soft, glowing light.";

        // Pass cases
        assertFalse(signInUI.isValidPassword(password));
    }

}
