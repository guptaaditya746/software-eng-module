import de.buw.se.ForgotPasswordPage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import static org.junit.jupiter.api.Assertions.*;

public class ForgotPasswordPageTest extends ApplicationTest {

    private ForgotPasswordPage forgotPasswordPage;
    private static final String CSV_FILE_PATH = "src/test/resources/temp_test.csv"; // Test file path

    @Override
    public void start(Stage stage) {
        forgotPasswordPage = new ForgotPasswordPage();
        ForgotPasswordPage.setCSVFilePath(CSV_FILE_PATH); // Set the test CSV file path
        forgotPasswordPage.start(stage);
    }

    @BeforeEach
    void setUp() throws IOException {
        // Prepare test data before each test
        prepareTestData("test1", "12345", "7654321", "tester@example.com");
    }

    @Test
    void testUpdatePasswordSuccessful() throws IOException {
        String email = "tester@example.com";
        String newPassword = "newPassword123";

        // Simulate updating password
        interact(() -> {
            forgotPasswordPage.updatePassword(email, newPassword, new Label());
        });

        // Assert that password was updated successfully by checking the CSV file
        assertTrue(isPasswordUpdated(email, newPassword), "Password update was successful");
    }

    @Test
    void testEmailInput() {
        FxRobot robot = new FxRobot();
        TextField emailInput = robot.lookup("#emailInput").queryAs(TextField.class);
        assertNotNull(emailInput);
        String testEmail = "test@example.com";
        robot.clickOn(emailInput).write(testEmail);
        assertEquals(testEmail, emailInput.getText());
    }

    @Test
    void testNewPasswordInput() {
        FxRobot robot = new FxRobot();
        PasswordField newPasswordInput = robot.lookup("#newPasswordInput").queryAs(PasswordField.class);
        assertNotNull(newPasswordInput);
        String testNewPassword = "newPassword123";
        robot.clickOn(newPasswordInput).write(testNewPassword);
        assertEquals(testNewPassword, newPasswordInput.getText());
    }

    @Test
    void testConfirmPasswordInput() {
        FxRobot robot = new FxRobot();
        PasswordField confirmPasswordInput = robot.lookup("#confirmPasswordInput").queryAs(PasswordField.class);
        assertNotNull(confirmPasswordInput);
        String testConfirmPassword = "newPassword123";
        robot.clickOn(confirmPasswordInput).write(testConfirmPassword);
        assertEquals(testConfirmPassword, confirmPasswordInput.getText());
    }

    private void prepareTestData(String name, String password, String phoneNumber, String mail) throws IOException {
        // Write test data to the test CSV file
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE_PATH))) {
            String[] header = {"name", "password", "phonenumber", "mailinput"};
            writer.writeNext(header);
            String[] data = {name, password, phoneNumber, mail};
            writer.writeNext(data);
        }
    }

    private boolean isPasswordUpdated(String email, String newPassword) {
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length >= 4 && nextLine[3].equals(email) && nextLine[1].equals(newPassword)) {
                    return true; // Password updated successfully
                }
            }
        } catch (IOException | com.opencsv.exceptions.CsvValidationException e) {
            e.printStackTrace();
        }
        return false; // Password not updated or not found
    }
}