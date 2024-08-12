package de.buw.se;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javafx.scene.control.TableView;

public class ProductPageTest extends JavaFXTestSetup {

    private TableView<Product> tableView;

    // Sets up the test environment before each test case
    @BeforeEach
    public void setup() {
        tableView = new TableView<>(); // Initialize the TableView
    }

    // Test for loading data when the database is empty
    @Test
    public void testLoadProductDataEmptyDatabase() throws Exception {
        // Mock database connection and interactions
        try (MockedStatic<DataStoreSql> mockedDataStoreSql = Mockito.mockStatic(DataStoreSql.class)) {
            Connection mockConnection = mock(Connection.class);
            Statement mockStatement = mock(Statement.class);
            ResultSet mockResultSet = mock(ResultSet.class);

            mockedDataStoreSql.when(DataStoreSql::getConnection).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false); // Simulate an empty result set

            ProductPage productPage = new ProductPage("Test User", new AddToCart());
            productPage.tableView = this.tableView;

            // Load data into the TableView
            productPage.loadProductData(tableView);

            // Assert that the TableView is empty
            assertEquals(0, tableView.getItems().size(), "TableView should be empty when there are no products in the database");
        }
    }

    // Test for filtering with a non-existent category
    @Test
    public void testFilterWithNonExistentCategory() throws Exception {
        // Mock database connection and interactions
        try (MockedStatic<DataStoreSql> mockedDataStoreSql = Mockito.mockStatic(DataStoreSql.class)) {
            Connection mockConnection = mock(Connection.class);
            Statement mockStatement = mock(Statement.class);
            ResultSet mockResultSet = mock(ResultSet.class);

            mockedDataStoreSql.when(DataStoreSql::getConnection).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false); // Simulate an empty result set

            ProductPage productPage = new ProductPage("Test User", new AddToCart());
            productPage.tableView = this.tableView;

            // Invoke the filter method with a non-existent category
            productPage.filterProductsByCategory("NonExistentCategory");

            // Assert that the TableView is empty
            assertEquals(0, tableView.getItems().size(), "TableView should be empty as this category doesn't exist");
        }
    }
}
