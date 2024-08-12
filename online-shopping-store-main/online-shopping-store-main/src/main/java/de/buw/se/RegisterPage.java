package de.buw.se;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.*;

public class RegisterPage extends Application {

    protected Stage primaryStage;
    protected Label promptLabel;

    protected static String filePath = "src/main/resources/Onlineshoppingstore.csv";

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Welcome to registerPage");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 30));
        grid.setVgap(10);
        grid.setHgap(10);

        Button backButton = new Button("<");
        backButton.setId("backButton");
        GridPane.setConstraints(backButton, 1, 0);
        backButton.setOnAction(e -> redirectToHomePage());

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 1, 1);
        TextField usernameInput = new TextField();
        usernameInput.setId("usernameInput");
        usernameInput.setPromptText("Enter your username");
        GridPane.setConstraints(usernameInput, 2, 1);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 1, 2);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setId("passwordInput");
        passwordInput.setPromptText("Enter your password");
        GridPane.setConstraints(passwordInput, 2, 2);

        Label phoneLabel = new Label("Phonenumber:");
        GridPane.setConstraints(phoneLabel, 1, 3);
        TextField phoneInput = new TextField();
        phoneInput.setId("phoneInput");
        phoneInput.setPromptText("Enter your PhoneNumber");
        GridPane.setConstraints(phoneInput, 2, 3);

        Label mailLabel = new Label("Mail:");
        GridPane.setConstraints(mailLabel, 1, 4);

        TextField mailInput = new TextField();
        mailInput.setId("mailInput");
        mailInput.setPromptText("Enter your mail");
        GridPane.setConstraints(mailInput, 2, 4);

        promptLabel = new Label("");
        promptLabel.setId("promptLabel");
        GridPane.setConstraints(promptLabel, 2, 5);

        Button loginButton = new Button("Register");
        loginButton.setId("registerButton");
        GridPane.setConstraints(loginButton, 2, 6);
        loginButton.setOnAction(e -> {


            if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty() ||
                    phoneInput.getText().isEmpty() || mailInput.getText().isEmpty()) {
                promptLabel.setText("Please fill in all fields.");
            } else if (!isValidEmail(mailInput.getText())) {
                promptLabel.setText("Please enter a valid email address.");
            } else if (isEmailRegistered(mailInput.getText()) && isPhoneNumberRegistered(phoneInput.getText())) {
                showBothRegisteredAlert();
            } else if (isEmailRegistered(mailInput.getText())) {
                showEmailRegisteredAlert();
            } else if (isPhoneNumberRegistered(phoneInput.getText())) {
                showPhoneNumberRegisteredAlert();
            } else {
                RegisterPage.writeCSV(usernameInput.getText(), passwordInput.getText(),
                        phoneInput.getText(), mailInput.getText());
                redirectToHomePage();
            }
        });

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordInput, passwordLabel,
                phoneInput, phoneLabel, mailInput, mailLabel, loginButton,
                backButton, promptLabel);

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void redirectToRegisterPage() {
        RegisterPage home = new RegisterPage();
        home.start(primaryStage);
    }

    protected void redirectToHomePage() {
        AppGUI homePage = new AppGUI();
        homePage.start(primaryStage);
    }

    public static void writeCSV(String name, String password, String phoneNumber, String mail) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            File file = new File(filePath);
            if (file.length() == 0) {
                writer.append("name,password,phonenumber,mailinput\n");
            }
            writer.append(name);
            writer.append(',');
            writer.append(password);
            writer.append(',');
            writer.append(phoneNumber);
            writer.append(',');
            writer.append(mail);
            writer.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean isValidEmail(String email) {
        // regresssion for email validation to validate the mailing id . 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    protected boolean isEmailRegistered(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length > 3 && fields[3].equals(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    protected boolean isPhoneNumberRegistered(String phoneNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length > 2 && fields[2].equals(phoneNumber)) {
                    return true;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Alert showPhoneNumberRegisteredAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registration Error");
        alert.setHeaderText(null);
        alert.setContentText("Phonenumber is already registered.");
        alert.showAndWait();
        return alert;
    }

    public Alert showEmailRegisteredAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registration Error");
        alert.setHeaderText(null);
        alert.setContentText("E-Mail is already registered.");
        alert.showAndWait();
        return alert;
    }

    public Alert showBothRegisteredAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registration Error");
        alert.setHeaderText(null);
        alert.setContentText("E-Mail and Phonenumber are already registered.");
        alert.showAndWait();
        return alert;
    }


    public static void main(String[] args) {
        launch(args);
    }

    private Alert displayedAlert; // Store the currently displayed Alert

    // Method to show alert
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registration Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        this.displayedAlert = alert; // Store the displayed alert instance
    }
}
