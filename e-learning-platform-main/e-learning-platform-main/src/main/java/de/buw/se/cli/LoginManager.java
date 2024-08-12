// LoginManager.java
package de.buw.se.cli;

import java.util.Map;
import java.util.Scanner;

public class LoginManager {
    private final Map<String, String> users;
    private final String UPLOAD_FOLDER;

    public LoginManager(Map<String, String> users, String UPLOAD_FOLDER) {
        this.users = users;
        this.UPLOAD_FOLDER = UPLOAD_FOLDER;
    }

    public void login(Scanner scanner) {
        UserAuthenticator userAuthenticator = new UserAuthenticator(users);
        userAuthenticator.login(scanner, UPLOAD_FOLDER);
    }
}