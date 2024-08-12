package de.buw.se.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class DatabaseManagerTest {
	

    @Test
    void testGetConnection() {
        try (Connection conn = DatabaseManager.getConnection()) {
            assertNotNull(conn, "The connection should not be null.");
            assertFalse(conn.isClosed(), "The connection should be open.");
        } catch (SQLException e) {
            fail("An SQLException was thrown: " + e.getMessage());
        } 
    }

    @Test
    void testInitializeDatabase() {
        try (Connection conn = DatabaseManager.getConnection();
             var stmt = conn.createStatement()) {
            var rs = stmt.executeQuery("SELECT 1 FROM DUAL");
            assertTrue(rs.next(), "Initialization script should have run and made a successful query possible.");
        } catch (SQLException e) {
            fail("An SQLException was thrown: " + e.getMessage());
        }
    }
}