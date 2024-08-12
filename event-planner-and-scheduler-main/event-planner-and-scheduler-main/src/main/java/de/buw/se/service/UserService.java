package de.buw.se.service;

import de.buw.se.db.UserDao;
import de.buw.se.model.User;
import java.sql.SQLException;

public class UserService {
    UserDao userDao; 

    public UserService() {
        this.userDao = new UserDao();
    }
  
    /**
     * Registers a new user in the system
     */
    public boolean registerUser(String email, String username, String password) {
    	
        try {
            // if (!email.endsWith("@example.com")) {
            // System.out.println("Registration failed: Email must end with @example.com");
            // return false;
            // }
            User user = new User(0, email, username, password); // ID is auto-generated in the DB
            boolean success = userDao.addUser(user);
            if (success) {
                System.out.println("User registered successfully.");
                return true;
            } else
                System.out.println("Username or Email is already taken!!");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Authenticates a user based on username and password
     * 
     * @return
     */
    public LoginResult loginUser(String username, String password) {
        try {
            User retrievedUser = userDao.getUserByUsername(username);
            if (retrievedUser != null && retrievedUser.getPassword().equals(password)) {
                System.out.println("User logged in successfully.");
                System.out.println("ID: " + retrievedUser.getId());
                System.out.println("Email: " + retrievedUser.getEmail());
                System.out.println("Username: " + retrievedUser.getUsername());
                // System.out.println("Password: " + retrievedUser.getPassword());
                return new LoginResult(true, retrievedUser);
            }
            System.out.println("Login failed: Incorrect username or password.");
            return new LoginResult(false, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new LoginResult(false, null);
        }
    }
}
