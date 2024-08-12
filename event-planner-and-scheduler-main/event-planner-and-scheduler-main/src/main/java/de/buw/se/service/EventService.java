package de.buw.se.service;

import de.buw.se.db.DatabaseManager;
import de.buw.se.db.EventDao;
import de.buw.se.model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EventService { 
    private EventDao eventDao;

    public EventService() throws SQLException {
        this.eventDao = new EventDao();
    }

    /**  
     * Adds a new event for a user
     */
    public boolean addEvent(int userId, String eventName, java.sql.Timestamp eventDate, String eventTime,
            String description) {
        try {
            Event event = new Event(0, userId, eventName, eventDate, eventTime, description); // ID is auto-generated
            eventDao.addEvent(event);
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all events for a specific user
     */
    public List<Event> getAllEventsForUser(int userId) {
        List<Event> events = new ArrayList<>();
        try {
            events = eventDao.getEventsByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    /*
     * public boolean updateEvent(int eventId, String eventName, Timestamp
     * eventDate) {
     * // Logic to update an event in the database
     * }
     * 
     * public boolean deleteEvent(int eventId) {
     * // Logic to delete an event from the database
     * }
     */

     public boolean updateEvent(int eventId, String eventName, Timestamp eventDate, String eventTime, String description) {
        String sql = "UPDATE Events SET eventName = ?, eventDate = ?, eventTime = ?, description = ? WHERE eventId = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, eventName);
            pstmt.setTimestamp(2, eventDate);
            pstmt.setString(3, eventTime);
            pstmt.setString(4, description);
            pstmt.setInt(5, eventId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM Events WHERE eventId = ?";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
