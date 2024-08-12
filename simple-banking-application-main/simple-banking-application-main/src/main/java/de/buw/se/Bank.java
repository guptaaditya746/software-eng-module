package de.buw.se;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, User> users;
    private CSVHandler csvHandler;
    
    // Existing constructor
    public Bank() {
        this(new CSVHandler());
    }

    // New constructor for dependency injection
    public Bank(CSVHandler csvHandler) {
        users = new HashMap<>();
        this.csvHandler = csvHandler;
        loadUsers();
    }

    private void loadUsers() {
        try { // Handle the exception
            users = csvHandler.loadUsers();
        } catch (IOException e) {
            e.printStackTrace();
            users = new HashMap<>();
        }
    }
    
    // Registering User if the username does not contain in the csv file
    public boolean registerUser(String username, String password) {
        if (!users.containsKey(username)) {
            User newUser = new User(username, password);
            users.put(username, newUser);
            csvHandler.saveUser(newUser);
            return true;
        }
        return false;
    }

    // User will login if the username is not null and the password must be equal to the password in the csv file
    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public void resetPassword(String username, String newPassword) {
        User user = users.get(username);
        if (user != null) {
            user.setPassword(newPassword);
            saveUser(user); // Save the updated user data
        }
    }
    
    public void saveUser(User user) {
        csvHandler.updateUser(user);
    }
    
    // Method to change the user's password
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            csvHandler.updateUser(user); // Save the updated user
            return true;
        }
        return false;
    }
}
