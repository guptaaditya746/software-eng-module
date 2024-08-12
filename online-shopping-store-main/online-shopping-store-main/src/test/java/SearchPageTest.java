
import de.buw.se.SearchPage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SearchPageTest extends ApplicationTest {

    private TextField searchField;
    private Button searchButton;
    private Label consoleLabel;

    @Override
    public void start(Stage stage) {
        // Start your JavaFX application
        SearchPage searchPage = new SearchPage();
        searchPage.start(stage);

        // Initialize UI components for testing
        WaitForAsyncUtils.waitForFxEvents(); // Ensure that JavaFX is fully loaded
        searchField = lookup("#searchField").query();
        searchButton = lookup("#searchButton").query();
        consoleLabel = lookup("#consoleLabel").query();
    }

    @Test
    public void testUIComponentsExistence() {
        assertNotNull(searchField, "Search field should not be null");
        assertNotNull(searchButton, "Search button should not be null");
        assertNotNull(consoleLabel, "Console label should not be null");
    }

    @Test
    public void testSearchButtonAction() {
        // Simulate user interaction: enter text and click the search button
        clickOn(searchField).write("Test Query");
        clickOn(searchButton);

        // Verify expected behavior
        assertEquals("Search Query: Test Query", consoleLabel.getText(),
                "Console label should display the correct search query");
    }

    @Test
    public void testEmptySearchInput() {
        clickOn(searchButton);

        // Verify that console label remains empty or unchanged
        assertEquals("", consoleLabel.getText(),
                "Console label should remain empty when search input is empty");
    }

    @Test
    public void testSpecialCharactersInSearchInput() {
        clickOn(searchField).write("Special #@$ Characters");
        clickOn(searchButton);

        // Verify expected behavior
        assertEquals("Search Query: Special #@$ Characters", consoleLabel.getText(),
                "Console label should display the correct search query with special characters");
    }

    @Test
    public void testMultipleSearches() {
        // First search
        clickOn(searchField).write("herbal");
        clickOn(searchButton);
        assertEquals("Search Query: herbal", consoleLabel.getText(),
                "Console label should display the correct first search query");

        // Second search
        clickOn(searchField).eraseText(6).write("remedy");
        clickOn(searchButton);
        assertEquals("Search Query: remedy", consoleLabel.getText(),
                "Console label should display the correct second search query");
    }

}
