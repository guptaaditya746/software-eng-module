package de.buw.se.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:h2:./src/main/resources/eventPlannerDB";

    static {
        try {
            Class.forName("org.h2.Driver");
            initializeDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error loading H2 driver or initializing database.");
            
            e.printStackTrace();
        } 
    }   
 
    private static void initializeDatabase() throws SQLException {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute("RUNSCRIPT FROM 'classpath:init.sql'");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, "sa", ""); // Empty "" is the password
    }
}