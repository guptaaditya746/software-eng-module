package de.buw.se.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

class EventTest {

    @Test
    void testEventConstructorAndGetters() {
        // Test data 
        int eventId = 1;
        int userId = 100;
        String eventName = "Birthday Party";
        Timestamp eventDate = Timestamp.valueOf("2023-07-15 18:00:00");
        String eventTime = "18:00";
        String description = "A celebration of life!";

        // Create an Event instance
        Event event = new Event(eventId, userId, eventName, eventDate, eventTime, description);

        // Test getters
        assertEquals(eventId, event.getEventId());
        
        assertEquals(userId, event.getUserId());
        assertEquals(eventName, event.getEventName());
        assertEquals(eventDate, event.getEventDate());
        assertEquals(eventTime, event.getEventTime());
        assertEquals(description, event.getDescription());
    }

    @Test
    void testEventCannotBeCreatedInThePast() {
        // Current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Attempt to create an event with time in the past
        LocalDateTime eventDateTime = currentDateTime.minusHours(1); // One hour ago

        // Test data
        int eventId = 2;
        int userId = 200;
        String eventName = "Conference";
        Timestamp eventDate = Timestamp.valueOf(eventDateTime);
        String eventTime = eventDateTime.toLocalTime().toString();
        String description = "Annual Tech Conference";

        // Try to create an event in the past
        assertThrows(IllegalArgumentException.class, () -> {
            new Event(eventId, userId, eventName, eventDate, eventTime, description);
        });
    }
    @Test
    void testEventPropertySetters() {
        // Test data
        Event event = new Event(3, 300, "Meeting", Timestamp.valueOf("2023-09-10 14:00:00"), "14:00", "Project meeting");

        // Modify properties using setters
        event.eventIdProperty().set(4);
        event.userIdProperty().set(400);
        event.eventNameProperty().set("Workshop");
        event.eventDateProperty().set(Timestamp.valueOf("2023-10-12 10:00:00"));
        event.eventTimeProperty().set("10:00");
        event.descriptionProperty().set("Technical Workshop");

        // Verify changes
        assertEquals(4, event.getEventId());
        assertEquals(400, event.getUserId());
        assertEquals("Workshop", event.getEventName());
        assertEquals(Timestamp.valueOf("2023-10-12 10:00:00"), event.getEventDate());
        assertEquals("10:00", event.getEventTime());
        assertEquals("Technical Workshop", event.getDescription());
    }

    @Test
    void testEventEdgeCases() {
        // Test edge cases with null values
        Event event = new Event(5, 500, null, null, null, null);

        // Verify that properties are set to null
        assertNull(event.getEventName());
        assertNull(event.getEventDate());
        assertNull(event.getEventTime());
        assertNull(event.getDescription());
    }

    @Test
    void testEventEquality() {
        // Create two events with the same data
        int eventId = 6;
        int userId = 600;
        String eventName = "Concert";
        Timestamp eventDate = Timestamp.valueOf("2023-11-15 19:00:00");
        String eventTime = "19:00";
        String description = "Live concert event";

        Event event1 = new Event(eventId, userId, eventName, eventDate, eventTime, description);
        Event event2 = new Event(eventId, userId, eventName, eventDate, eventTime, description);

        // Verify equality based on getters
        assertEquals(event1.getEventId(), event2.getEventId());
        assertEquals(event1.getUserId(), event2.getUserId());
        assertEquals(event1.getEventName(), event2.getEventName());
        assertEquals(event1.getEventDate(), event2.getEventDate());
        assertEquals(event1.getEventTime(), event2.getEventTime());
        assertEquals(event1.getDescription(), event2.getDescription());
    }
}