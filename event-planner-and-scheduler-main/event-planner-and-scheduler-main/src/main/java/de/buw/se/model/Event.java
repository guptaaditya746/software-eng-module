package de.buw.se.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import java.time.LocalDateTime;

import java.sql.Timestamp;

public class Event { 
    private SimpleIntegerProperty eventId;
    private SimpleIntegerProperty userId;
    private SimpleStringProperty eventName; 
    private SimpleObjectProperty<Timestamp> eventDate;
    private SimpleStringProperty eventTime;
    private SimpleStringProperty description; 

    // Constructor
    public Event(int eventId, int userId, String eventName, Timestamp eventDate, String eventTime, String description) {
        this.eventId = new SimpleIntegerProperty(eventId);
        this.userId = new SimpleIntegerProperty(userId);
        this.eventName = new SimpleStringProperty(eventName);

        // Check if eventDate is in the past
        if (eventDate.toLocalDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event date cannot be in the past");
        }
        
        this.eventDate = new SimpleObjectProperty<>(eventDate);
        this.eventTime = new SimpleStringProperty(eventTime);
        this.description = new SimpleStringProperty(description);
    }

    // Getters
    public int getEventId() {
        return eventId.get();
    }

    public int getUserId() {
        return userId.get();
    }

    public String getEventName() {
        return eventName.get();
    }

    public Timestamp getEventDate() {
        return eventDate.get();
    }

    public String getEventTime() {
        return eventTime.get();
    }

    public String getDescription() {
        return description.get();
    }

    // JavaFX properties
    public IntegerProperty eventIdProperty() {
        return eventId;
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public StringProperty eventNameProperty() {
        return eventName;
    }

    public SimpleObjectProperty<Timestamp> eventDateProperty() {
        return eventDate;
    }

    public StringProperty eventTimeProperty() {
        return eventTime;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

	public void setEventId(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setEventName(String string) {
		// TODO Auto-generated method stub
		
	}
}
