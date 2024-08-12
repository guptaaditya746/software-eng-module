package de.buw.se;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppGUITest extends ApplicationTest {

    @Mock
    private DataStoreSql mockDataStore;

    @InjectMocks
    private AppGUI app;

    @Override
    public void start(Stage stage) throws Exception {
        MockitoAnnotations.openMocks(this);
        app = new AppGUI();
        app.start(stage);
    }

    @BeforeEach
    public void setUp() throws Exception {
        ApplicationTest.launch(AppGUI.class);
        setUpDatabase();
    }

    @AfterEach
    public void afterEachTest() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testReadGeneralInformation() throws IOException {
        // Ensure the GeneralInformation.txt file exists and has content
        File file = new File("src/main/resources/GeneralInformation.txt");
        if (!file.exists()) {
            Files.write(Paths.get("src/main/resources/GeneralInformation.txt"), "General information content.".getBytes());
        }

        Platform.runLater(() -> {
            clickOn("Read General Information");
        });

        // Wait for the file content to be loaded and the TextArea to be updated
        WaitForAsyncUtils.waitForFxEvents();

        String content = lookup(".text-area").queryAs(TextArea.class).getText();
        assertFalse(content.isEmpty(), "General Information should be displayed");
    }

    @Test
    public void testStartQuizButton() {
        // Mock categories
        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(new Category(1, "Math", "Study Math"));
        mockCategories.add(new Category(2, "Science", "Study Science"));
        when(DataStoreSql.readCategories()).thenReturn(mockCategories);

        Platform.runLater(() -> {
            //clickOn("Start Quiz");
            Button quizButton = lookup("Start Quiz").query();
            quizButton.fire();
        });

        // Wait for the dialog to appear
        WaitForAsyncUtils.waitForFxEvents();

        // Check if category selection dialog is shown
        assertTrue(lookup(".dialog-pane").tryQuery().isPresent(), "Category selection dialog should be displayed");
    }


    @Test
    public void testShowCategoryInformation() {
        Category category = new Category(1, "Math", "Study Math");

        Platform.runLater(() -> {
            app.showCategoryInformation(category);
        });

        // Wait for the dialog to appear
        WaitForAsyncUtils.waitForFxEvents();

        // Check if the category information dialog is shown
        assertTrue(lookup(".dialog-pane").tryQuery().isPresent(), "Category information dialog should be displayed");
    }



    @Test
    public void testAskQuestionCorrect() {
        Question question = new Question(1, 1, "Easy", "Question 1", new String[]{"A", "B", "C", "D"}, "A");

        Platform.runLater(() -> {
            boolean result = app.askQuestion(question);
            assertTrue(result, "The user should continue the quiz after answering correctly");
        });

        // Wait for the dialog to appear and simulate user input
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("A");
    }

    @Test
    public void testAskQuestionIncorrect() {
        Question question = new Question(1, 1, "Easy", "Question 1", new String[]{"A", "B", "C", "D"}, "A");

        Platform.runLater(() -> {
            boolean result = app.askQuestion(question);
            assertTrue(result, "The user should continue the quiz after answering incorrectly");
        });

        // Wait for the dialog to appear and simulate user input
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("B");
    }

    @Test
    public void testAskQuestionQuit() {
        Question question = new Question(1, 1, "Easy", "Question 1", new String[]{"A", "B", "C", "D"}, "A");

        Platform.runLater(() -> {
            boolean result = app.askQuestion(question);
            assertFalse(result, "The user should quit the quiz");
        });

        // Wait for the dialog to appear and simulate user input
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("Quit");
    }

    private void setUpDatabase() throws Exception {
        // Set up the database state here if needed
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./src/main/resources/books", "", "");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Questions");
            statement.executeUpdate("DROP TABLE IF EXISTS Categories");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Categories"
                    + "(CategoryID INT PRIMARY KEY AUTO_INCREMENT, "
                    + "Name VARCHAR(255) NOT NULL, "
                    + "StudyInformation CLOB)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Questions"
                    + "(QuestionID INT PRIMARY KEY AUTO_INCREMENT, "
                    + "CategoryID INT NOT NULL, "
                    + "Difficulty VARCHAR(255) NOT NULL, "
                    + "Question CLOB NOT NULL, "
                    + "Answers VARCHAR(1023) NOT NULL, "
                    + "RightAnswer VARCHAR(1023) NOT NULL, "
                    + "FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID))");
        }
    }
}

