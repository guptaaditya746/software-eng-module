import de.buw.se.ShoppingCartPage;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.assertions.api.Assertions.assertThat;

public class ShoppingCartPageTest extends ApplicationTest {

    private TextField searchField;
    private Button searchButton;
    private Accordion productAccordion;
    private CheckBox ayurvedaCheckBox;
    private CheckBox homeopathyCheckBox;
    private CheckBox homePreparedCheckBox;
    private CheckBox naturopathyCheckBox;
    private CheckBox chineseMedicineCheckBox;
    private Button logoutButton;

    @Override
    public void start(Stage stage) {
        new ShoppingCartPage().start(stage);
    }

    @BeforeEach
    public void setUp() {
        searchField = lookup("#searchField").query();
        searchButton = lookup("#searchButton").query();
        productAccordion = lookup("#productAccordion").query();
        ayurvedaCheckBox = lookup("#ayurvedaCheckBox").query();
        homeopathyCheckBox = lookup("#homeopathyCheckBox").query();
        homePreparedCheckBox = lookup("#homePreparedCheckBox").query();
        naturopathyCheckBox = lookup("#naturopathyCheckBox").query();
        chineseMedicineCheckBox = lookup("#chineseMedicineCheckBox").query();
        logoutButton = lookup("#logoutButton").query();
    }

    @Test
    public void testSearchProducts() {
        clickOn(searchField).write("herbal");
        clickOn(searchButton);
        assertThat(productAccordion.getPanes()).hasSize(4);
    }

    @Test
    public void testFilterProductsByCategory() {
        clickOn(ayurvedaCheckBox);
        assertThat(productAccordion.getPanes()).hasSize(2);
        clickOn(ayurvedaCheckBox);
        clickOn(naturopathyCheckBox);
        assertThat(productAccordion.getPanes()).hasSize(2);
        clickOn(naturopathyCheckBox);
        assertThat(productAccordion.getPanes()).hasSize(0);
    }

    @Test
    public void testMultipleFilters() {
        clickOn(ayurvedaCheckBox);
        clickOn(homeopathyCheckBox);
        assertThat(productAccordion.getPanes()).hasSize(4);
    }

    @Test
    public void testClearSearch() {
        clickOn(searchField).write("herbal");
        clickOn(searchButton);
        assertThat(productAccordion.getPanes()).hasSize(4);
        doubleClickOn(searchField).eraseText(6);
        clickOn(searchButton);
        assertThat(productAccordion.getPanes()).hasSize(0);
    }

    @Test
    public void testLogoutButton() {
        clickOn(logoutButton);
        assertTrue(lookup("#usernameInput").tryQuery().isPresent(), "Username field should be present after logout");
        assertTrue(lookup("#passwordInput").tryQuery().isPresent(), "Password field should be present after logout");
    }
}
