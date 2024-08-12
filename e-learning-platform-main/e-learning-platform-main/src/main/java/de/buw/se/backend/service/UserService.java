package de.buw.se.backend.service;

import java.sql.SQLException;

import de.buw.se.backend.db.UserDao;
import de.buw.se.backend.model.User;

public class UserService {

    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    /**
     * Registers a new user in the system
     *
     * @param email
     * @param username
     * @param fullName
     * @param password
     * @return
     */
    public boolean registerUser(String email, String username, String fullName, String password) {
        try {
            // if (!email.endsWith("@example.com")) {
            // System.out.println("Registration failed: Email must end with @example.com");
            // return false;
            // }
            User user = new User(0, email, username, fullName, password); // ID is auto-generated in the DB
            boolean success = userDao.addUser(user);
            if (success) {
                System.out.println("User registered successfully.");
                return true;
            } else {
                System.out.println("Username or Email is already taken!!");
            }
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Authenticates a user based on username and password
     *
     * @param username
     * @param password
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
                System.out.println("Full Name: " + retrievedUser.getFullName());
                // System.out.println("Password: " + retrievedUser.getPassword());
                return new LoginResult(true, retrievedUser);
            }
            System.out.println("Login failed: Incorrect username or password.");
            return new LoginResult(false, null);
        } catch (SQLException | ClassNotFoundException e) {
            return new LoginResult(false, null);
        }
    }

    /**
     * Registers a new user in the system
     *
     * @param id
     * @param username
     * @param email
     * @param fullName
     * @param password
     * @return
     */
    public LoginResult editProfile(int id, String email, String username, String fullName, String password) {
        try {
            User user = new User(id, email, username, fullName, password);
            boolean isSuccess = userDao.editProfile(user);
            if (isSuccess) {
                System.out.println("Profile Updated Successfully!!");
                User retrievedUser = userDao.getUserByUsername(username);

                if (retrievedUser != null) {
                    System.out.println("User logged in successfully.");
                    System.out.println("ID: " + retrievedUser.getId());
                    System.out.println("Email: " + retrievedUser.getEmail());
                    System.out.println("Username: " + retrievedUser.getUsername());
                    System.out.println("Full Name: " + retrievedUser.getFullName());
                    return new LoginResult(true, retrievedUser);
                }
                return new LoginResult(false, null);
            } else {
                System.out.println("Username or Email is already taken!!");
                return new LoginResult(false, null);
            }

        } catch (SQLException | ClassNotFoundException e) {
            return new LoginResult(false, null);
        }
    }
}
