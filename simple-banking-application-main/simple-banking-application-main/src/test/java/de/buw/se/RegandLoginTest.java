package de.buw.se;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegandLoginTest {

    private Bank bank;
    private Path tempUsersFile;
    private Path tempTransactionsFile;

    @BeforeEach
    public void Initialization() throws IOException {
        // Creating temporary files for users and transactions...
        tempUsersFile = Files.createTempFile("users", ".csv");
        tempTransactionsFile = Files.createTempFile("transactions", ".csv");

        // Initializing the Bank class...
        bank = new Bank(new CSVHandler(tempUsersFile.toString(), tempTransactionsFile.toString()));

        // Ensuring the temp files start empty...
        Files.write(tempUsersFile, List.of());
        Files.write(tempTransactionsFile, List.of());
    }

    @AfterEach
    public void delTempFiles() throws IOException {
        // Deleting the temporary files after the test...
        Files.deleteIfExists(tempUsersFile);
        Files.deleteIfExists(tempTransactionsFile);
    }

    @Test
    public void testRegisterAndLoginSuccess() {
        // Registering a new user...
        boolean registrationResult = bank.registerUser("abc", "def");
        assertTrue(registrationResult, "Reg Test Failed");

        // Trying to login with the sample credentials...
        User result = bank.loginUser("abc", "def");
        assertNotNull(result, "Log In Test Failed");
        assertEquals("abc", result.getUsername(), "Log In Test Failed");
    }

    @Test
    public void testRegisterUsernameTaken() {
        // Registering a user with the same user name...
        bank.registerUser("abc", "def");

        // Attempting to register a new user with the same user name...
        boolean registrationResult = bank.registerUser("abc", "ghi");

        // Verifying that registration fails due to the user name being taken...
        assertFalse(registrationResult, "Same Username test failed.");
    }


    @Test
    public void testLoginFailureInvalidPassword() {
        // Registering a new user...
        bank.registerUser("abc", "def");

        // Try to login with an incorrect password...
        User result = bank.loginUser("abc", "ghi");
        assertNull(result, "Invalid Password Test Failed.");
    }

    @Test
    public void testLoginFailureNonexistentUser() {
        // Trying to login with a non-existent user...
        User result = bank.loginUser("abc", "def");
        assertNull(result, "NonexistentUser Test Failed");
    }
    
    // White Box Testing from this point onwards...
    
    @Test
    public void testLoadUsersIOException() {
        // Testing while assuming users file does not exist...
        CSVHandler csvHandler = new CSVHandler("null.csv", "transactions.csv");
        // This test fails if the loadUsers() method does not throw an exception when a non existent file is called in it...
        assertThrows(IOException.class, csvHandler::loadUsers);
    }
    
    @Test
    public void testLoadTransactionsIOException() {
    	// Testing while assuming transactions file does not exist...
        CSVHandler csvHandler = new CSVHandler("users.csv", "null.csv");
        // This test fails if the loadTransactions() method does not throw an exception when a non existent file is called in it...
        assertThrows(IOException.class, () -> csvHandler.loadTransactions(new HashMap<>()));
    }
}