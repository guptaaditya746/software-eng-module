package de.buw.se.backend.connection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseManagerTest {

    @Test
    public void testGetConnection() {
        Connection connection = null;
        String driver = "org.h2.Driver"; // Correct driver for H2 database
        try {
            connection = DatabaseManager.getConnection(driver);
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        } catch (SQLException | ClassNotFoundException e) {
            fail("SQLException or ClassNotFoundException should not have been thrown");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Ignore the exception on closing
                }
            }
        }
    }

    @Test
    public void testInvalidConnection() {
        String driver = "invalid.Driver"; // Invalid driver class name
        assertThrows(ClassNotFoundException.class, () -> {
            DatabaseManager.getConnection(driver);
        });
    }
}
