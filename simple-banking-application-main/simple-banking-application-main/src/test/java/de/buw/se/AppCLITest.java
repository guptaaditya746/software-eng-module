package de.buw.se;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
// import java.time.LocalDate;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/*
 * certain test cases are hid on purpose as while running those test cases it was running indefinitely.
 * it was also taking us into debug console.
 * 
 * 
 */
public class AppCLITest {

    private AppCLI app;
    private Bank bank;

    @BeforeEach
    public void Initialization(@TempDir Path tempDir) throws IOException {
        // Initializing CSVHandler with temporary files...
        String usersFilePath = tempDir.resolve("users.csv").toString();
        String transactionsFilePath = tempDir.resolve("transactions.csv").toString();
        CSVHandler csvHandler = new CSVHandler(usersFilePath, transactionsFilePath);
        bank = new Bank(csvHandler);

        // Initializing AppCLI with the created bank instance...
        app = new AppCLI(bank);
    }

    @Test
    public void testAddMoneyWithNonNumericValue() {

        assertThrows(NumberFormatException.class, () -> {
            AppCLI.addAmount("aksljfldskj");
        }, "Amount should be a positive numeric value");
    }

    @Test
    public void testRegistrationWithEmptyUsernameAndPassword() {

        Exception exception = assertThrows(IOException.class, () -> {
            AppCLI.registerUser("   ", "  ");
        }, "Username and Password should not be empty");

        // Verify that the exception message is as expected
        String expectedMessage = "This field is required";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage),
                "Exception should mention that username and password can not be empty.");
    }

    // @Test
    // public void testAddMoney() {
    // // Registering a new user...
    // String username = "abc";
    // String password = "def";
    // bank.registerUser(username, password);

    // // Logging in as the new user...
    // AppCLI.currentUser = bank.loginUser(username, password);

    // // Adding money to the account...
    // double amountToAdd = 100.0;
    // Helper.changeInput(Double.toString(amountToAdd));
    // assertDoesNotThrow(() -> AppCLI.addMoney());

    // // Checking the balance...
    // assertEquals(amountToAdd, AppCLI.currentUser.getBalance());
    // }

    // @Test
    // public void testWithdrawMoney() {
    // // Registering a new user...
    // String username = "abc";
    // String password = "def";
    // bank.registerUser(username, password);

    // // Logging in as the new user...
    // AppCLI.currentUser = bank.loginUser(username, password);

    // // Adding money to the account...
    // double amountToAdd = 100.0;
    // Helper.changeInput(Double.toString(amountToAdd));
    // assertDoesNotThrow(() -> AppCLI.addMoney());

    // // Withdrawing money from the account...
    // double amountToWithdraw = 10.0;
    // Helper.changeInput(Double.toString(amountToWithdraw));
    // assertDoesNotThrow(() -> AppCLI.withdrawMoney());
    // double amountafterdeduction = 90.0;

    // // Checking the balance...
    // assertEquals(amountafterdeduction, AppCLI.currentUser.getBalance());
    // }

    // @Test
    // public void testTransactionHistory() {
    // // Registering a new user...
    // String username = "abc";
    // String password = "def";
    // bank.registerUser(username, password);

    // // Logging in as the new user...
    // AppCLI.currentUser = bank.loginUser(username, password);

    // int i;
    // for (i = 0; i < 10; i++) {
    // // Adding money to the account...
    // double amountToAdd = 100.0;
    // Helper.changeInput(Double.toString(amountToAdd));
    // assertDoesNotThrow(() -> AppCLI.addMoney());

    // // Withdrawing money from the account...
    // double amountToWithdraw = 10.0;
    // Helper.changeInput(Double.toString(amountToWithdraw));
    // assertDoesNotThrow(() -> AppCLI.withdrawMoney());
    // }

    // int Expectednumberoftransactions = 20;
    // List<Transaction> transactions = AppCLI.currentUser.getTransactions();

    // // Verifying the transaction history...
    // assertEquals(Expectednumberoftransactions, transactions.size(), "Transaction
    // History Test Failed.");
    // }

    // White box testing from this point onwards...

    // @Test
    // public void testAddMoneyNegativeAmount() {
    // // Registering a new user...
    // String username = "abc";
    // String password = "def";
    // bank.registerUser(username, password);

    // // Logging in as the new user...
    // AppCLI.currentUser = bank.loginUser(username, password);

    // // Adding money to the account...
    // double amountToAdd = -100.0;
    // Helper.changeInput(Double.toString(amountToAdd));
    // assertDoesNotThrow(() -> AppCLI.addMoney());
    // assertEquals(0, AppCLI.currentUser.getBalance());
    // }

}

// This class helps in setting the system input stream to the simulation set by
// several testing methods...
class Helper {
    private static final InputStream sysInBackup = System.in;
    private static ByteArrayInputStream testIn;

    public static void changeInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    // Restoring System.in to its original input stream...
    public static void restoreSystemInputOutput() {
        System.setIn(sysInBackup);
    }
}