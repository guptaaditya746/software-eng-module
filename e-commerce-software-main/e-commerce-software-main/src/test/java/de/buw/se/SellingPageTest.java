package de.buw.se;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SellingPageTest extends JavaFXTestSetup {

    private SellingPage sellingPage;
    private ProductPage productPage;
    private TableView<Product> tableView;

    private TextField itemNameField;
    private ComboBox<String> categoryComboBox;
    private TextField yearOfPurchaseField;
    private TextField actualPriceField;
    private TextField sellingPriceField;
    private TextArea commentsArea;

    @BeforeEach
    public void setUp() throws Exception {

        // Initialize SellingPage and ProductPage instances
        sellingPage = new SellingPage();
        productPage = new ProductPage();
        tableView = new TableView<>();

        // Create fields
        itemNameField = new TextField();
        categoryComboBox = new ComboBox<>();
        yearOfPurchaseField = new TextField();
        actualPriceField = new TextField();
        sellingPriceField = new TextField();
        commentsArea = new TextArea();

        // Set private fields using reflection for SellingPage
        setPrivateField(sellingPage, "itemNameField", itemNameField);
        setPrivateField(sellingPage, "categoryComboBox", categoryComboBox);
        setPrivateField(sellingPage, "yearOfPurchaseField", yearOfPurchaseField);
        setPrivateField(sellingPage, "actualPriceField", actualPriceField);
        setPrivateField(sellingPage, "sellingPriceField", sellingPriceField);
        setPrivateField(sellingPage, "commentsArea", commentsArea);

        // Set private fields using reflection for ProductPage
        setPrivateField(productPage, "tableView", tableView);
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private void callPrivateMethod(Object target, String methodName, Class<?>[] parameterTypes, Object... args)
            throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        method.invoke(target, args);
    }

    @Test
    public void testNoImageProductNotAdded() {
        // Mock DataStoreSql and its methods
        try (MockedStatic<DataStoreSql> mockedStatic = mockStatic(DataStoreSql.class)) {
            Connection mockConnection = mock(Connection.class);
            Statement mockStatement = mock(Statement.class);
            ResultSet mockResultSet = mock(ResultSet.class);

            mockedStatic.when(DataStoreSql::getConnection).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false); // No product should be returned

            // Set field values
            itemNameField.setText("Test Item");
            categoryComboBox.setValue("Laptop");
            yearOfPurchaseField.setText("2020");
            actualPriceField.setText("1000.0");
            sellingPriceField.setText("800.0");
            commentsArea.setText("Good condition");

            // Call handleSubmitButtonAction method
            Platform.runLater(() -> {
                try {
                    callPrivateMethod(sellingPage, "handleSubmitButtonAction", new Class<?>[] { Stage.class },
                            new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Exception during handleSubmitButtonAction: " + e.getMessage());
                }
            });

            // Wait for JavaFX operations to complete
            waitForFxEvents();

            // Verify that addProduct was not called with a null image
            mockedStatic.verify(() -> DataStoreSql.addProduct(anyString(), anyString(), anyInt(), anyDouble(),
                    anyDouble(), isNull(), anyString()), never());

            // Load data into ProductPage
            Platform.runLater(() -> {
                try {
                    callPrivateMethod(productPage, "loadProductData", new Class<?>[] { TableView.class }, tableView);
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Exception during loadProductData: " + e.getMessage());
                }
            });

            // Wait for JavaFX operations to complete
            waitForFxEvents();

            // Verify that no products are loaded into the table
            if (!tableView.getItems().isEmpty()) {
                fail("Product without image was loaded into the table");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred during test execution: " + e.getMessage());
        }
    }

    private void waitForFxEvents() {
        try {
            // Create a barrier for JavaFX events to complete
            final Object barrier = new Object();
            Platform.runLater(() -> {
                synchronized (barrier) {
                    barrier.notify();
                }
            });
            synchronized (barrier) {
                barrier.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNegativeSellingPrice() {
        // Mock DataStoreSql and its methods
        try (MockedStatic<DataStoreSql> mockedStatic = mockStatic(DataStoreSql.class)) {
            Connection mockConnection = mock(Connection.class);
            Statement mockStatement = mock(Statement.class);
            ResultSet mockResultSet = mock(ResultSet.class);

            mockedStatic.when(DataStoreSql::getConnection).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false); // No product should be returned

            // Set field values
            itemNameField.setText("Test Item");
            categoryComboBox.setValue("Laptop");
            yearOfPurchaseField.setText("2020");
            actualPriceField.setText("1000.0");
            sellingPriceField.setText("-800.0"); // Negative selling price
            commentsArea.setText("Good condition");

            // Call handleSubmitButtonAction method
            Platform.runLater(() -> {
                try {
                    callPrivateMethod(sellingPage, "handleSubmitButtonAction", new Class<?>[] { Stage.class },
                            new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Exception during handleSubmitButtonAction: " + e.getMessage());
                }
            });

            // Wait for JavaFX operations to complete
            waitForFxEvents();

            // Verify that addProduct was not called with negative selling price
            mockedStatic.verify(() -> DataStoreSql.addProduct(anyString(), anyString(), anyInt(), anyDouble(),
                    eq(-800.0), any(), anyString()), never());

            // Load data into ProductPage
            Platform.runLater(() -> {
                try {
                    callPrivateMethod(productPage, "loadProductData", new Class<?>[] { TableView.class }, tableView);
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Exception during loadProductData: " + e.getMessage());
                }
            });

            // Wait for JavaFX operations to complete
            waitForFxEvents();

            // Verify that no products are loaded into the table
            if (!tableView.getItems().isEmpty()) {
                fail("Product with negative selling price was loaded into the table");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred during test execution: " + e.getMessage());
        }
    }

    @Test
    public void testNegativeActualPrice() {
        // Mock DataStoreSql and its methods
        try (MockedStatic<DataStoreSql> mockedStatic = mockStatic(DataStoreSql.class)) {
            Connection mockConnection = mock(Connection.class);
            Statement mockStatement = mock(Statement.class);
            ResultSet mockResultSet = mock(ResultSet.class);

            mockedStatic.when(DataStoreSql::getConnection).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false); // No product should be returned

            // Set field values
            itemNameField.setText("Test Item");
            categoryComboBox.setValue("Laptop");
            yearOfPurchaseField.setText("2020");
            actualPriceField.setText("-1000.0"); // Negative actual price
            sellingPriceField.setText("800.0");
            commentsArea.setText("Good condition");

            // Call handleSubmitButtonAction method
            Platform.runLater(() -> {
                try {
                    callPrivateMethod(sellingPage, "handleSubmitButtonAction", new Class<?>[] { Stage.class },
                            new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Exception during handleSubmitButtonAction: " + e.getMessage());
                }
            });

            // Wait for JavaFX operations to complete
            waitForFxEvents();

            // Verify that addProduct was not called with negative actual price
            mockedStatic.verify(() -> DataStoreSql.addProduct(anyString(), anyString(), anyInt(), eq(-1000.0),
                    anyDouble(), any(), anyString()), never());

            // Load data into ProductPage
            Platform.runLater(() -> {
                try {
                    callPrivateMethod(productPage, "loadProductData", new Class<?>[] { TableView.class }, tableView);
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Exception during loadProductData: " + e.getMessage());
                }
            });

            // Wait for JavaFX operations to complete
            waitForFxEvents();

            // Verify that no products are loaded into the table
            if (!tableView.getItems().isEmpty()) {
                fail("Product with negative actual price was loaded into the table");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred during test execution: " + e.getMessage());
        }
    }

    @Test
    public void testFutureYearOfPurchase() {
        // Mock DataStoreSql and its methods
        try (MockedStatic<DataStoreSql> mockedStatic = mockStatic(DataStoreSql.class)) {
            Connection mockConnection = mock(Connection.class);
            Statement mockStatement = mock(Statement.class);
            ResultSet mockResultSet = mock(ResultSet.class);

            mockedStatic.when(DataStoreSql::getConnection).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false); // No product should be returned

            // Set field values
            itemNameField.setText("Test Item");
            categoryComboBox.setValue("Laptop");
            yearOfPurchaseField.setText("2030"); // Future year
            actualPriceField.setText("1000.0");
            sellingPriceField.setText("800.0");
            commentsArea.setText("Good condition");

            // Call handleSubmitButtonAction method
            Platform.runLater(() -> {
                try {
                    callPrivateMethod(sellingPage, "handleSubmitButtonAction", new Class<?>[] { Stage.class },
                            new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Exception during handleSubmitButtonAction: " + e.getMessage());
                }
            });

            // Wait for JavaFX operations to complete
            waitForFxEvents();

            // Verify that addProduct was not called with future year of purchase
            mockedStatic.verify(() -> DataStoreSql.addProduct(anyString(), anyString(), eq(2030), anyDouble(),
                    anyDouble(), any(), anyString()), never());

            // Load data into ProductPage
            Platform.runLater(() -> {
                try {
                    callPrivateMethod(productPage, "loadProductData", new Class<?>[] { TableView.class }, tableView);
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Exception during loadProductData: " + e.getMessage());
                }
            });

            // Wait for JavaFX operations to complete
            waitForFxEvents();

            // Verify that no products are loaded into the table
            if (!tableView.getItems().isEmpty()) {
                fail("Product with future year of purchase was loaded into the table");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred during test execution: " + e.getMessage());
        }
    }

}
