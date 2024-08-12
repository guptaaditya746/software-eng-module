package de.buw.se.cli;

import java.util.Map;
import java.util.Scanner;

public class UserAuthenticator {
    private final Map<String, String> users;

    public UserAuthenticator(Map<String, String> users) {
        this.users = users;
    }

    public void login(Scanner scanner, String UPLOAD_FOLDER) {
        // login logic
        // Ask for username and password
        String[] credentials = askForCredentials(scanner);

        // Check if the entered credentials are valid
        boolean isValidUser = validateCredentials(credentials[0], credentials[1]);

        if (isValidUser) {
            System.out.println("Login successful!");
            // Ask the user to select upload or download
            FileManager fileManager = new FileManager();
            fileManager.selectOption(scanner, UPLOAD_FOLDER);

        } else {
            System.out.println("Invalid username or password. Login failed.");
            System.out.println("................................");
            System.out.println("Do you want to recover your password? (yes/no)");
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("yes")) {
                recoverPassword(scanner);
                login(scanner, UPLOAD_FOLDER);
            }
        }
    }

    public void recoverPassword(Scanner scanner) {
        System.out.println("Please enter your username to recover the password:");
        String username = scanner.nextLine().trim();
        if (users.containsKey(username)) {
            System.out.println("Your password is: " + users.get(username));
            System.out.println("................................");
        } else {
            System.out.println("Username not found.");
            System.out.println("................................");
        }
    }

    private String[] askForCredentials(Scanner scanner) {
        String[] credentials = new String[2];

        System.out.println("................................");
        // Get username
        System.out.println("Please enter your username:");
        credentials[0] = scanner.nextLine().trim();

        // Get password
        System.out.println("Please enter your password:");
        credentials[1] = scanner.nextLine().trim();
        System.out.println("................................");
        return credentials;
    }

    private boolean validateCredentials(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}