package de.buw.se;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    @Test
    public void testEventCreationAndModification() {
        // Creating an instance of the Event class
        String initialProgramName = "Initial Program";
        String initialPassword = "InitialPassword123";
        Event event = new Event(initialProgramName, initialPassword);

        
        //Verifying the initial state of the Event object
        assertEquals(initialProgramName, event.getProgramName()); 
        assertEquals(initialPassword, event.getPassword()); 

        //Modifying the state of the Event object
        String newProgramName = "Updated Program";
        String newPassword = "UpdatedPassword567";
        event.setProgramName(newProgramName);
        event.setPassword(newPassword);

        //Verifying the modified state of the Event object
        assertEquals(newProgramName, event.getProgramName()); 
        assertEquals(newPassword, event.getPassword()); 

        //Verifying the toString() method output
        String expectedString = "Program: " + newProgramName + ", Password: " + newPassword;
        assertEquals(expectedString, event.toString());
    }
}
