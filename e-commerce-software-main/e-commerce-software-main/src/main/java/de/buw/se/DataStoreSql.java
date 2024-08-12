package de.buw.se;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataStoreSql {

    private static final String JDBC_URL = "jdbc:h2:./src/main/resources/sourceDataBase"; // Persistent embedded database
    private static Connection testConnection;

    public static Connection getConnection() throws SQLException {
        if (testConnection != null) {
            return testConnection;
        }
        return DriverManager.getConnection(JDBC_URL);
    }

    public static void setTestConnection(Connection connection) {
        testConnection = connection;
    }

    public static void createTables() throws SQLException {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String createProductTableQuery = "CREATE TABLE IF NOT EXISTS Products ("
                    + "ID INT PRIMARY KEY AUTO_INCREMENT, "
                    + "ITEM_NAME VARCHAR(255), "
                    + "CATEGORY VARCHAR(255), "
                    + "YEAR_OF_PURCHASE INT, "
                    + "ACTUAL_PRICE DOUBLE, "
                    + "SELLING_PRICE DOUBLE, "
                    + "IMAGE BLOB, "
                    + "COMMENTS VARCHAR(255))";
            stmt.executeUpdate(createProductTableQuery);

            String createUserTableQuery = "CREATE TABLE IF NOT EXISTS Users ("
                    + "ID INT PRIMARY KEY AUTO_INCREMENT, "
                    + "USERNAME VARCHAR(255) UNIQUE, "
                    + "PASSWORD VARCHAR(255))";
            stmt.executeUpdate(createUserTableQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isUserExists(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE USERNAME = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public static boolean addUser(String username, String password) throws SQLException {
        if (isUserExists(username)) {
            return false;
        }

        String insertQuery = "INSERT INTO Users (USERNAME, PASSWORD) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            return pstmt.executeUpdate() > 0;
        }
    }

    public static boolean validateUser(String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE USERNAME = ? AND PASSWORD = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public static void addProduct(String itemName, String category, int yearOfPurchase, double actualPrice, double sellingPrice, File imageFile, String comments) throws SQLException, IOException {
        String insertQuery = "INSERT INTO Products (ITEM_NAME, CATEGORY, YEAR_OF_PURCHASE, ACTUAL_PRICE, SELLING_PRICE, IMAGE, COMMENTS) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, itemName);
            pstmt.setString(2, category);
            pstmt.setInt(3, yearOfPurchase);
            pstmt.setDouble(4, actualPrice);
            pstmt.setDouble(5, sellingPrice);

            try (FileInputStream fis = new FileInputStream(imageFile)) {
                pstmt.setBinaryStream(6, fis, (int) imageFile.length());
            }

            pstmt.setString(7, comments);
            pstmt.executeUpdate();
        }
    }

    public static void displayProducts() throws SQLException {
        String query = "SELECT ITEM_NAME, CATEGORY, YEAR_OF_PURCHASE, ACTUAL_PRICE, SELLING_PRICE, IMAGE, COMMENTS FROM Products";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String itemName = rs.getString("ITEM_NAME");
                String category = rs.getString("CATEGORY");
                int yearOfPurchase = rs.getInt("YEAR_OF_PURCHASE");
                double actualPrice = rs.getDouble("ACTUAL_PRICE");
                double sellingPrice = rs.getDouble("SELLING_PRICE");
                byte[] imageBytes = rs.getBytes("IMAGE");
                String comments = rs.getString("COMMENTS");

                // Handle the retrieved data (e.g., display on the product page)
                System.out.println("Item Name: " + itemName);
                System.out.println("Category: " + category);
                System.out.println("Year of Purchase: " + yearOfPurchase);
                System.out.println("Actual Price: " + actualPrice);
                System.out.println("Selling Price: " + sellingPrice);
                System.out.println("Image Size: " + (imageBytes != null ? imageBytes.length : 0));
                System.out.println("Comments: " + comments);
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        try {
            createTables();
            displayProducts(); // Display products on the console for testing
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
