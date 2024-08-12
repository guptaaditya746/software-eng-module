package de.buw.se;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

class NewUserFormTest extends JavaFXTestSetup {

    @Test // When Different entries in password and reEnterPassword Checking
    void PasswordMismatch() {
        boolean result = NewUserForm.createUser("username", "password", "differentPassword");
        assertFalse(result, "User creation should fail if passwords do not match.");
    }

    @Test //Checking When  Username is empty 
    void EmptyUsername() {
        boolean result = NewUserForm.createUser("", "password", "password");
        assertFalse(result, "User creation should fail if username is empty.");
    }

    @Test //Checking When Password is empty
    void EmptyPassword() {
        boolean result = NewUserForm.createUser("username", "", "");
        assertFalse(result, "User creation should fail if password is empty.");
    }

    @Test //Checking When Username includes only spaces
    void UsernameOnlySpaces() {
        boolean result = NewUserForm.createUser(" ", "password", "password");
        assertFalse(result, "User creation should fail if username contains only spaces.");
    }

    @Test // Checking When Password includes only spaces
    void PasswordOnlySpaces() {
        boolean result = NewUserForm.createUser("username", " ", " ");
        assertFalse(result, "User creation should fail if password contains only spaces.");
    }
}
