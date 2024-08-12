package de.buw.se.backend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.buw.se.backend.connection.DatabaseManager;
import de.buw.se.backend.model.User;

public class UserDao {

    private static String driver = "org.h2.Driver";
    /**
     * Adds a user to the database
     *
     * @param user
     * @return
     * @throws java.sql.SQLException
     */
    public boolean addUser(User user) throws SQLException, ClassNotFoundException {
        // Check if the email is already in use
        createUsersTableIfNotExists();

        if (isEmailUnique(user.getEmail(), -1) && isUsernameUnique(user.getUsername())) {
            String insertUserSql = "INSERT INTO Users (email, username, fullName, password) VALUES (?, ?, ?, ?)";

            try (Connection conn = DatabaseManager.getConnection(driver); PreparedStatement pstmt = conn.prepareStatement(insertUserSql)) {

                // Insert user data
                pstmt.setString(1, user.getEmail());
                pstmt.setString(2, user.getUsername());
                pstmt.setString(3, user.getFullName());
                pstmt.setString(4, user.getPassword());
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
    private void createUsersTableIfNotExists() throws SQLException, ClassNotFoundException {
        String createUserTableSql = "CREATE TABLE IF NOT EXISTS Users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "email VARCHAR(100) NOT NULL, "
                + "username VARCHAR(50) NOT NULL, "
                + "fullName VARCHAR(100) NOT NULL, "
                + "password VARCHAR(100) NOT NULL)";

        try (Connection conn = DatabaseManager.getConnection(driver); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createUserTableSql);
        }
    }

    /**
     * Checks if the email is unique in the database
     */
    private boolean isEmailUnique(String email, int userId) throws SQLException, ClassNotFoundException {
        String sql;
        if (userId != -1) {
            sql = "SELECT COUNT(*) FROM Users WHERE email = ? AND id != ?";
        } else {
            sql = "SELECT COUNT(*) FROM Users WHERE email = ?";
        }

        try (Connection conn = DatabaseManager.getConnection(driver); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            if (userId != -1) {
                pstmt.setInt(2, userId);
            }
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
    private boolean isUsernameUnique(String username) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection(driver); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
     *
     * @param usernameOrEmail
     * @return
     * @throws java.sql.SQLException
     */
    public User getUserByUsername(String usernameOrEmail) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Users WHERE username = ? OR email = ?";
        try (Connection conn = DatabaseManager.getConnection(driver); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usernameOrEmail);
            pstmt.setString(2, usernameOrEmail);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("username"),
                            rs.getString("fullName"),
                            rs.getString("password"));
                }
            }
        }
        return null;
    }

    /**
     * Updates a user's profile in the database
     *
     * @param user The user with updated details
     * @return true if the update was successful, false otherwise
     * @throws java.sql.SQLException
     */
    public boolean editProfile(User user) throws SQLException, ClassNotFoundException {
        // SQL statement to update user details
        String updateProfileSql = "UPDATE Users SET email = ?, username = ?, fullName = ?, password = ? WHERE id = ?";
        if (isEmailUnique(user.getEmail(), user.getId())) {
            try (Connection conn = DatabaseManager.getConnection(driver); PreparedStatement pstmt = conn.prepareStatement(updateProfileSql)) {
                // Set parameters for the update statement
                pstmt.setString(1, user.getEmail());
                pstmt.setString(2, user.getUsername());
                pstmt.setString(3, user.getFullName());
                pstmt.setString(4, user.getPassword());
                pstmt.setInt(5, user.getId());

                // Execute the update statement
                int affectedRows = pstmt.executeUpdate();

                // Check if any row was affected
                return affectedRows > 0;
            }
        }
        return false;
    }
}
