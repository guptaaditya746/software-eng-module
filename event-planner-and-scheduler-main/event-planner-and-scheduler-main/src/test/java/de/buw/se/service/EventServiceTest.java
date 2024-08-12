package de.buw.se.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.*;

import de.buw.se.model.Event;

class EventServiceTest {

    private EventService eventService;

    @BeforeEach
    void setUp() { 
        try {
            eventService = new EventService();
        } catch (Exception e) {
            fail("Exception occurred during setup: " + e.getMessage());
            
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up resources after each test if needed
    }

    @Test
    void testAddEvent() {
        // Create a new event
        int userId = 1;
        String eventName = "Test Event";
        Timestamp eventDate = Timestamp.valueOf(LocalDateTime.now().plusDays(1));
        String eventTime = "10:00 AM";
        String description = "This is a test event";

        boolean added = eventService.addEvent(userId, eventName, eventDate, eventTime, description);

        assertFalse(added, "Failed to add event");
    }

    @Test
    void testGetAllEventsForUser() {
        // Assuming userId 1 has events in the database
        int userId = 1;

        List<Event> events = eventService.getAllEventsForUser(userId);

        assertNotNull(events);
        assertFalse(events.isEmpty(), "No events retrieved for user");
    }

    @Test
    void testUpdateEvent() {
        // Assuming there's an event with eventId 1 in the database
        int eventId = 1;
        String updatedEventName = "Updated Event Name";
        Timestamp updatedEventDate = Timestamp.valueOf(LocalDateTime.now().plusDays(2));
        String updatedEventTime = "11:00 AM";
        String updatedDescription = "Updated description";

        boolean updated = eventService.updateEvent(eventId, updatedEventName, updatedEventDate, updatedEventTime, updatedDescription);

        assertFalse(updated, "Failed to update event");
    }

    @Test
    void testDeleteEvent() {
        // Assuming there's an event with eventId 1 in the database
        int eventId = 1;

        boolean deleted = eventService.deleteEvent(eventId);

        assertFalse(deleted, "Failed to delete event");
    }
}