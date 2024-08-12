package de.buw.se.db;

import de.buw.se.model.User;
import java.sql.*;
 
public class UserDao { 
    /**
     * Adds a user to the database
     */ 
    public boolean addUser(User user) throws SQLException {
        // Check if the email is already in use
        createUsersTableIfNotExists();

        if (isEmailUnique(user.getEmail()) && isUsernameUnique(user.getUsername())) {
            String insertUserSql = "INSERT INTO Users (email, username, password) VALUES (?, ?, ?)";

            try (Connection conn = DatabaseManager.getConnection();
                    Statement stmt = conn.createStatement();
                    PreparedStatement pstmt = conn.prepareStatement(insertUserSql)) {
            	

                // Insert user data
                pstmt.setString(1, user.getEmail());
                pstmt.setString(2, user.getUsername());
                pstmt.setString(3, user.getPassword());
                pstmt.executeUpdate();
                return true;
            }
        } else {
            return false;
        }

    }

    /**
     * Create the Users table if it doesn't exist
     */
    protected void createUsersTableIfNotExists() throws SQLException {
        String createUserTableSql = "CREATE TABLE IF NOT EXISTS Users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "email VARCHAR(100) NOT NULL, " +
                "username VARCHAR(50) NOT NULL, " +
                "password VARCHAR(100) NOT NULL)";

        try (Connection conn = DatabaseManager.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createUserTableSql);
        }
    }

    /**
     * Checks if the email is unique in the database
     */
    boolean isEmailUnique(String email) throws SQLException {
        System.out.println("isunique... " + email);
        String sql = "SELECT COUNT(*) FROM Users WHERE email = ?";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0; // If count is 0, email is unique
                }
            }
        }
        // In case of error, consider email as not unique
        return false;
    }

    /**
     * Checks if the username is unique in the database
     */
    boolean isUsernameUnique(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0; // If count is 0, username is unique
                }
            }
        }
        // In case of error, consider username as not unique
        return false;
    }

    /**
     * Retrieves a user by username
     */
    public User getUserByUsername(String usernameOrEmail) throws SQLException {
        String sql = "SELECT * FROM Users WHERE username = ? OR email = ?";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usernameOrEmail);
            pstmt.setString(2, usernameOrEmail);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("username"),
                            rs.getString("password"));
                }
            }
        }
        return null;
    }
}