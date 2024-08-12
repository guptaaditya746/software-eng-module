import de.buw.se.RegisterPage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
class RegisterPageTest {

    RegisterPage registerPage;
    Stage primaryStage;
    String tempFilePath = "src/test/resources/temp_test.csv";

    @Start
    void start(Stage stage) throws Exception {
        primaryStage = stage;
        registerPage = new RegisterPage();

        // Access protected field via reflection
        Field filePathField = RegisterPage.class.getDeclaredField("filePath");
        filePathField.setAccessible(true);
        filePathField.set(registerPage, tempFilePath);

        registerPage.start(primaryStage);
    }

    @BeforeEach
    void setUp() throws IOException {
        // Ensure the test CSV file is empty before each test
        try (FileWriter writer = new FileWriter(tempFilePath)) {
            writer.append("name,password,phonenumber,mailinput\n");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        // Ensure all JavaFX windows are closed after each test
        FxToolkit.cleanupStages();
    }

    @Test
    void testIsValidEmail() throws Exception {
        Method isValidEmailMethod = RegisterPage.class.getDeclaredMethod("isValidEmail", String.class);
        isValidEmailMethod.setAccessible(true);

        assertTrue((boolean) isValidEmailMethod.invoke(registerPage, "test@example.com"));
        assertFalse((boolean) isValidEmailMethod.invoke(registerPage, "testexample.com"));
        assertFalse((boolean) isValidEmailMethod.invoke(registerPage, "test@.com"));
        assertFalse((boolean) isValidEmailMethod.invoke(registerPage, "@example.com"));
    }

    @Test
    void testIsEmailRegistered() throws Exception {
        Method isEmailRegisteredMethod = RegisterPage.class.getDeclaredMethod("isEmailRegistered", String.class);
        isEmailRegisteredMethod.setAccessible(true);

        try (FileWriter writer = new FileWriter(tempFilePath, true)) {
            writer.append("user1,password1,1234567890,user1@example.com\n");
            writer.append("user2,password2,0987654321,user2@example.com\n");
        }

        assertTrue((boolean) isEmailRegisteredMethod.invoke(registerPage, "user1@example.com"));
        assertFalse((boolean) isEmailRegisteredMethod.invoke(registerPage, "nonexistent@example.com"));
    }

    @Test
    void testIsPhoneNumberRegistered() throws Exception {
        Method isPhoneNumberRegisteredMethod = RegisterPage.class.getDeclaredMethod("isPhoneNumberRegistered", String.class);
        isPhoneNumberRegisteredMethod.setAccessible(true);

        try (FileWriter writer = new FileWriter(tempFilePath, true)) {
            writer.append("user1,password1,1234567890,user1@example.com\n");
            writer.append("user2,password2,0987654321,user2@example.com\n");
        }

        assertTrue((boolean) isPhoneNumberRegisteredMethod.invoke(registerPage, "1234567890"));
        assertFalse((boolean) isPhoneNumberRegisteredMethod.invoke(registerPage, "1111111111"));
    }

    @Test
    void testWriteCSV() throws IOException {
        RegisterPage.writeCSV("user3", "password3", "5555555555", "user3@example.com");

        try (BufferedReader reader = new BufferedReader(new FileReader(tempFilePath))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("user3,password3,5555555555,user3@example.com")) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testRegisterWithValidInput(FxRobot robot) {
        robot.clickOn("#usernameInput").write("newuser");
        robot.clickOn("#passwordInput").write("newpassword");
        robot.clickOn("#phoneInput").write("1234567890");
        robot.clickOn("#mailInput").write("newuser@example.com");
        robot.clickOn("#registerButton");

        // Verify CSV file content
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFilePath))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("newuser,newpassword,1234567890,newuser@example.com")) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRegisterWithExistingEmail(FxRobot robot) throws IOException {
        try (FileWriter writer = new FileWriter(tempFilePath, true)) {
            writer.append("user1,password1,1234567890,user1@example.com\n");
        }

        robot.clickOn("#usernameInput").write("newuser");
        robot.clickOn("#passwordInput").write("newpassword");
        robot.clickOn("#phoneInput").write("0987654321");
        robot.clickOn("#mailInput").write("user1@example.com");
        robot.clickOn("#registerButton");

        // Wait for UI update (showing alert) before capturing the Alert instance
        Platform.runLater(() -> {
            // Assuming you have a way to capture the Alert instance in your RegisterPage class
            Alert alert = registerPage.showEmailRegisteredAlert();

            // Assert the contentText of the Alert
            assertEquals("E-Mail is already registered.", alert.getContentText());
        });
    }

    @Test
    void testRegisterWithExistingPhoneNumber(FxRobot robot) throws IOException {
        try (FileWriter writer = new FileWriter(tempFilePath, true)) {
            writer.append("user1,password1,1234567890,user1@example.com\n");
        }

        robot.clickOn("#usernameInput").write("newuser");
        robot.clickOn("#passwordInput").write("newpassword");
        robot.clickOn("#phoneInput").write("1234567890");
        robot.clickOn("#mailInput").write("newuser@example.com");
        robot.clickOn("#registerButton");

        // Wait for UI update (showing alert) before capturing the Alert instance
        Platform.runLater(() -> {
            // Assuming you have a way to capture the Alert instance in your RegisterPage class
            Alert alert = registerPage.showPhoneNumberRegisteredAlert();

            // Assert the contentText of the Alert
            assertEquals("Phonenumber is already registered.", alert.getContentText());
        });
    }

    @Test
    void testRegisterWithBothExisting(FxRobot robot) throws IOException {
        try (FileWriter writer = new FileWriter(tempFilePath, true)) {
            writer.append("user1,password1,1234567890,user1@example.com\n");
        }

        robot.clickOn("#usernameInput").write("newuser");
        robot.clickOn("#passwordInput").write("newpassword");
        robot.clickOn("#phoneInput").write("1234567890");
        robot.clickOn("#mailInput").write("user1@example.com");
        robot.clickOn("#registerButton");

        // Wait for UI update (showing alert) before capturing the Alert instance
        Platform.runLater(() -> {
            // Assuming you have a way to capture the Alert instance in your RegisterPage class
            Alert alert = registerPage.showBothRegisteredAlert();

            // Assert the contentText of the Alert
            assertEquals("E-Mail and Phonenumber are already registered.", alert.getContentText());
        });
    }

    @Test
    void testRegisterWithEmptyFields(FxRobot robot) {
        robot.clickOn("#usernameInput").write("");
        robot.clickOn("#passwordInput").write("");
        robot.clickOn("#phoneInput").write("");
        robot.clickOn("#mailInput").write("");
        robot.clickOn("#registerButton");

        // Verify the promptLabel content
        verifyThat("#promptLabel", hasText("Please fill in all fields."));
    }

    @Test
    void testRegisterWithInvalidEmail(FxRobot robot) {
        robot.clickOn("#usernameInput").write("newuser");
        robot.clickOn("#passwordInput").write("newpassword");
        robot.clickOn("#phoneInput").write("1234567890");
        robot.clickOn("#mailInput").write("invalidemail");
        robot.clickOn("#registerButton");

        // Verify the promptLabel content
        verifyThat("#promptLabel", hasText("Please enter a valid email address."));
    }
}
