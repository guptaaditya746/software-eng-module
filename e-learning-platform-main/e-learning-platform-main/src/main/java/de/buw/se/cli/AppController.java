// AppController.java
package de.buw.se.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AppController {
    private static final String UPLOAD_FOLDER = "uploads/";
    private final Map<String, String> users;

    public AppController() {
        users = new HashMap<>();
        // Add some sample users to the map
        users.put("user1", "password1");
        users.put("user2", "password2");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        LoginManager loginManager = new LoginManager(users, UPLOAD_FOLDER);
        loginManager.login(scanner);
        scanner.close();
    }
}
