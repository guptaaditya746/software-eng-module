package de.buw.se.db;

import de.buw.se.model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDao {
    private Connection connection;

    public EventDao() throws SQLException {
        connection = DatabaseManager.getConnection();
        createTableIfNotExists();
    }    

    private void createTableIfNotExists() throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet tables = metadata.getTables(null, null, "EVENTS", null);
        
        if (!tables.next()) {
            // Table does not exist, create it
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE EVENTS (" +
                    "eventId INT PRIMARY KEY AUTO_INCREMENT, " +
                    "eventName VARCHAR(255), " +
                    "eventDate DATE, " +
                    "eventTime TIME, " +
                    "description VARCHAR(1000), " +
                    "userId VARCHAR(50))");
            statement.close();
        }
    }

    /**
     * Adds an event to the database
     */
    public void addEvent(Event event) throws SQLException {
        String sql = "INSERT INTO Events (userId, eventName, eventDate, eventTime, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, event.getUserId());
            pstmt.setString(2, event.getEventName());
            pstmt.setTimestamp(3, event.getEventDate());
            pstmt.setString(4, event.getEventTime());
            pstmt.setString(5, event.getDescription());
            pstmt.executeUpdate();
        }
    }

    /**
     * Retrieves all events for a given user
     */
    public List<Event> getEventsByUserId(int userId) throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM Events WHERE userId = ?";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int eventId = rs.getInt("eventId");
                    String eventName = rs.getString("eventName");
                    Timestamp eventDate = rs.getTimestamp("eventDate");
                    String eventTime = rs.getString("eventTime");
                    String description = rs.getString("description");

                    Event event = new Event(eventId, userId, eventName, eventDate, eventTime, description);
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return events;
    }
}
