package de.buw.se;

// Importing necessary classes for making assertions
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import javafx.scene.image.Image;

class ProductTest extends JavaFXTestSetup {

    @Test
    void testItemNameProperty() {
        // Test to ensure that the item name property is correctly set and retrievable
        Product product = new Product("Laptop", 2021, 1000.0, 800.0, new Image("file:test-image.jpg"), "Electronics");
        assertEquals("Laptop", product.itemNameProperty().get());
    }

    @Test
    void testYearOfPurchaseProperty() {
        // Test to ensure that the year of purchase property is correctly set and
        // retrievable
        Product product = new Product("Laptop", 2021, 1000.0, 800.0, new Image("file:test-image.jpg"), "Electronics");
        assertEquals(2021, product.yearOfPurchaseProperty().get());
    }

    @Test
    void testActualPriceProperty() {
        // Test to ensure that the actual price property is correctly set and
        // retrievable
        Product product = new Product("Laptop", 2021, 1000.0, 800.0, new Image("file:test-image.jpg"), "Electronics");
        assertEquals(1000.0, product.actualPriceProperty().get());
    }

    @Test
    void testSellingPriceProperty() {
        // Test to ensure that the selling price property is correctly set and
        // retrievable
        Product product = new Product("Laptop", 2021, 1000.0, 800.0, new Image("file:test-image.jpg"), "Electronics");
        assertEquals(800.0, product.sellingPriceProperty().get());
    }

    @Test
    void testCategoryProperty() {
        // Test to ensure that the category property is correctly set and retrievable
        Product product = new Product("Laptop", 2021, 1000.0, 800.0, new Image("file:test-image.jpg"), "Electronics");
        assertEquals("Electronics", product.categoryProperty().get());
    }

    @Test
    void testImageProperty() {
        // Test to ensure that the image property is correctly set and retrievable
        Image image = new Image("file:test-image.jpg");
        Product product = new Product("Laptop", 2021, 1000.0, 800.0, image, "Electronics");
        assertEquals(image, product.imageProperty().get());
    }
}
