import de.buw.se.AppGUI;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppGUITest extends ApplicationTest {

    private TextField usernameInput;
    private PasswordField passwordInput;
    private Button loginButton;
    private Button registerButton;
    private Button forgotPasswordButton;
    private Label promptLabel;

    private static final String TEMP_CSV_FILE_PATH = "src/test/resources/temp_test.csv";

    @Override
    public void start(Stage stage) throws IOException {
        AppGUI appGUI = new AppGUI();
        appGUI.start(stage);

        // Initialize UI components for testing
        WaitForAsyncUtils.waitForFxEvents();
        usernameInput = lookup("#usernameInput").query();
        passwordInput = lookup("#passwordInput").query();
        loginButton = lookup("#loginButton").query();
        registerButton = lookup("#registerButton").query();
        forgotPasswordButton = lookup("#forgotPasswordButton").query();
        promptLabel = lookup("#promptLabel").query();
    }

    @BeforeEach
    void setUp() throws IOException {
        // Prepare test data before each test
        prepareTestData("test1", "12345", "987654321", "tester123@example.com", TEMP_CSV_FILE_PATH);
    }

    @Test
    public void testUIComponentsExistence() {
        assertNotNull(usernameInput, "Username input should not be null");
        assertNotNull(passwordInput, "Password input should not be null");
        assertNotNull(loginButton, "Login button should not be null");
        assertNotNull(registerButton, "Register button should not be null");
        assertNotNull(forgotPasswordButton, "Forgot Password button should not be null");
        assertNotNull(promptLabel, "Prompt label should not be null");
    }

    @Test
    public void testLoginSuccessful() {
        clickOn(usernameInput).write("praveen1@gmail.com");
        clickOn(passwordInput).write("123456");
        clickOn(loginButton);

        // Delay for the promptLabel to update
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("", promptLabel.getText(), "Prompt label should be empty on successful login");
    }

    @Test
    public void testEmptySpaceLogin() {
        clickOn(usernameInput).write("");
        clickOn(passwordInput).write("");
        clickOn(loginButton);
        assertEquals("User Not Found , Enter valid credentials", promptLabel.getText(),
                "Prompt label should display error message on login failure");
    }

    @Test
    public void testLoginFailure() {
        clickOn(usernameInput).write("invalid@example.com");
        clickOn(passwordInput).write("wrongpassword");
        clickOn(loginButton);
        assertEquals("User Not Found , Enter valid credentials", promptLabel.getText(),
                "Prompt label should display error message on login failure");
    }

    @Test
    public void testRedirectToRegisterPage() {
        clickOn(registerButton);
        System.out.println("Redirecting to Register Page...");
    }

    @Test
    public void testRedirectToForgotPasswordPage() {
        clickOn(forgotPasswordButton);
        System.out.println("Redirecting to Forgot Password Page...");
    }

    private void prepareTestData(String username, String password, String phoneNumber, String email, String filePath) throws IOException {
        // Write test data to the temporary CSV file
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(String.format("%s,%s,%s,%s%n", username, password, phoneNumber, email));
        }
    }
}