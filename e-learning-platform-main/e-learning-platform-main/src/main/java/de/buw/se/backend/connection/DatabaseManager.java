package de.buw.se.backend.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:h2:./src/main/resources/eLearningDB";

    public static Connection getConnection(String driver) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(DB_URL, "bd", ""); // Empty "" is the password
    }
}
