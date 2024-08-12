package de.buw.se.db;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.*;

import de.buw.se.model.Event;

class EventDaoTest {

    private EventDao eventDao;

    @BeforeEach
    
    void setUp() throws SQLException {
        eventDao = new EventDao();
    }

    @AfterEach  
    void tearDown() throws SQLException {
        // Clean up test data or close resources after each test if necessary
    }

    @Test
    void testAddEvent() throws SQLException {
        // Create a test event
        Event event = new Event(1, 0, "Test Event", Timestamp.valueOf(LocalDateTime.now().plusDays(1)), "10:00", "Test description");

        // Add the event
        eventDao.addEvent(event);

        // Retrieve the event to check if it was added successfully
        List<Event> events = eventDao.getEventsByUserId(1);
        assertFalse(events.isEmpty(), "No events retrieved");
        Event retrievedEvent = events.get(0);

        // Verify the retrieved event matches the added event
        assertEquals(event.getUserId(), retrievedEvent.getUserId(), "User IDs should match");
        assertEquals(event.getEventName(), retrievedEvent.getEventName(), "Event names should match");
        assertEquals(event.getEventDate(), retrievedEvent.getEventDate(), "Event dates should much match");
        assertEquals(event.getEventTime(), retrievedEvent.getEventTime(), "Event times should much match");
        assertEquals(event.getDescription(), retrievedEvent.getDescription(), "Event descriptions should  match");
    }

    @Test
    void testGetEventsByUserId() throws SQLException {
        // Create and add multiple test events for a user
        Event event1 = new Event(1, 0, "Event 1", Timestamp.valueOf(LocalDateTime.now().plusDays(1)), "12:00", "Description 1");
        Event event2 = new Event(1, 0, "Event 2", Timestamp.valueOf(LocalDateTime.now().plusDays(2)), "14:00", "Description 2");
        Event event3 = new Event(2, 0, "Event 3", Timestamp.valueOf(LocalDateTime.now().plusDays(1)), "15:00", "Description 3");

        eventDao.addEvent(event1);
        eventDao.addEvent(event2);
        eventDao.addEvent(event3);

        // Retrieve events for user 1
        List<Event> eventsForUser1 = eventDao.getEventsByUserId(1);
        assertNotEquals(2, eventsForUser1.size(), "Incorrect number of events retrieved for user 1");

        // Retrieve events for user 2
        List<Event> eventsForUser2 = eventDao.getEventsByUserId(2);
        assertNotEquals(1, eventsForUser2.size(), "Incorrect number of events retrieved for user 2");
    }
}