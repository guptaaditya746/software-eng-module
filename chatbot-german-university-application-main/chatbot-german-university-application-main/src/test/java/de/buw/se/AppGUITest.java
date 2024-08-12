package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

class AppGUITest {

    private static final String[] GREETINGS = {
            "Hi there! Ready to explore the universities?",
            "Hello! Let's find the perfect university for you!",
            "Greetings! Excited to discover German universities with you!"
    };

    @Test
void testGetRandomGreeting() {
    AppGUI app = new AppGUI();

    // Set to collect all possible greetings for verification
    Set<String> greetingSet = new HashSet<>(Arrays.asList(GREETINGS));

    // Get a greeting
    String greeting = app.getRandomGreeting();

    // Print or log the expected and actual values
    System.out.println("Expected greetings: " + greetingSet);
    System.out.println("Actual greeting: " + greeting);

    // Assert that the greeting is one of the expected greetings
    assertTrue(greetingSet.contains(greeting), "Unexpected greeting: " + greeting);
}


    @RepeatedTest(10) // Repeat the test multiple times for better randomness verification
    void testGetRandomGreetingRepeated() {
        AppGUI app = new AppGUI();

        // Set to collect all possible greetings for verification
        Set<String> greetingSet = new HashSet<>(Arrays.asList(GREETINGS));

        // Get a greeting
        String greeting = app.getRandomGreeting();

        // Assert that the greeting is one of the expected greetings
        assertTrue(greetingSet.contains(greeting), "Unexpected greeting: " + greeting);
    }

    @Test
    void testAskQuestion() {
        AppGUI app = new AppGUI();

        // Set up initial state
        app.currentQuestionIndex = 0;
        app.chatArea = new VBox(); // Mocking the chat area

        // Call askQuestion() to simulate asking the first question
        app.askQuestion();

        // Assert that the chat area now contains the correct question text
        assertEquals("Chatbot: What is your field of interest?", ((Text) app.chatArea.getChildren().get(0)).getText());
    }

    

    @Test
    void testAskQuestion_MoreQuestions() {
        AppGUI appGUI = new AppGUI();
        appGUI.currentQuestionIndex = 0;
        appGUI.chatArea = new VBox();
    
        appGUI.askQuestion();
    
        Text questionText = (Text) appGUI.chatArea.getChildren().get(0);
        assertEquals("Chatbot: What is your field of interest?", questionText.getText());
        assertEquals(0, appGUI.currentQuestionIndex);
    }

    @Test
    void testAskQuestion_NoMoreQuestions() {
    AppGUI appGUI = new AppGUI();
    appGUI.currentQuestionIndex = AppGUI.questions.length;
    appGUI.chatArea = new VBox();

    appGUI.askQuestion();

    Text recommendationText = (Text) appGUI.chatArea.getChildren().get(0);
    assertTrue(recommendationText.getText().startsWith("Chatbot: Based on your preferences,"));
}

@Test
void testValidateInput_ValidFieldOfInterest() {
    AppGUI appGUI = new AppGUI();
    appGUI.currentQuestionIndex = 0;

    boolean isValidInput = appGUI.validateInput("computer science");

    assertTrue(isValidInput);
}

@Test
void testGetRandomGreeting_returnsValidGreeting() {
    AppGUI appGUI = new AppGUI();
    String greeting = appGUI.getRandomGreeting();
    List<String> greetingList = Arrays.asList(AppGUI.GREETINGS);
    assertTrue(greetingList.contains(greeting));
}

@Test
public void testGetRandomGreeting_returnsDifferentGreetings() {
    AppGUI appGUI = new AppGUI();
    String firstGreeting = appGUI.getRandomGreeting();
    String secondGreeting = appGUI.getRandomGreeting();
    assertNotEquals(firstGreeting, secondGreeting);
}

@Test
public void testGetRandomGreeting_returnsNonEmptyString() {
    AppGUI appGUI = new AppGUI();
    String greeting = appGUI.getRandomGreeting();
    assertFalse(greeting.isEmpty());
}
@Test
public void testGetRandomGreeting_returnsNonNullString() {
    AppGUI appGUI = new AppGUI();
    String greeting = appGUI.getRandomGreeting();
    assertNotNull(greeting);
}

@Test
public void testGetRandomGreeting_returnsUniqueGreetings() {
    AppGUI appGUI = new AppGUI();
    String[] greetings = new String[AppGUI.GREETINGS.length];
    for (int i = 0; i < greetings.length; i++) {
        greetings[i] = appGUI.getRandomGreeting();
    }
    assertArrayEquals(AppGUI.GREETINGS, greetings);
}


@Test
    public void testValidCity_ValidInput() {
        
       AppGUI appGUI = new AppGUI();
        appGUI.currentQuestionIndex = 2;// Given
        String validCity = "munich";

        // When
        boolean isValidInput = appGUI.validateInput(validCity);

        // Then
        assertTrue(isValidInput, "Valid city should be accepted");
    }


    @Test
    public void testValidCity_InvalidInput_CaseSensitive() {
        AppGUI appGUI = new AppGUI();
        appGUI.currentQuestionIndex = 2;
        String invalidCity = "MUNICH";

        // When
        boolean isValidInput = appGUI.validateInput(invalidCity);

        // Then
        assertFalse(isValidInput, "Invalid city (case sensitive) should be rejected");
    }

    @Test
    public void reset_conversation() {
        AppGUI appGUI = new AppGUI();
        assertEquals(0, appGUI.currentQuestionIndex);
    }
}
