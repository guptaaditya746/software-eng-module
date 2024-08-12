import de.buw.se.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductTest {

    Product product;

    @BeforeEach
     void setUp() {
        product = new Product("Herbal Tea", "Ayurveda",
                "/Images/herbal_tea.jpg", "A soothing herbal tea blend.", 5.99);
    }

    @Test
    void testConstructorInitialization() {
        assertEquals("Herbal Tea", product.getName());
        assertEquals("Ayurveda", product.getCategory());
        assertEquals("/Images/herbal_tea.jpg", product.getImagePath());
        assertEquals("A soothing herbal tea blend.", product.getDescription());
        assertEquals(5.99, product.getPrice(), 0.01);
    }

    @Test
    void testGetName() {
        assertEquals("Herbal Tea", product.getName());
    }

    @Test
    void testSetName() {
        product.setName("Green Tea");
        assertEquals("Green Tea", product.getName());
    }

    @Test
    void testGetCategory() {
        assertEquals("Ayurveda", product.getCategory());
    }

    @Test
    void testSetCategory() {
        product.setCategory("Tea");
        assertEquals("Tea", product.getCategory());
    }

    @Test
    void testGetImagePath() {
        assertEquals("/Images/herbal_tea.jpg", product.getImagePath());
    }

    @Test
    void testSetImagePath() {
        product.setImagePath("/Images/green_tea.jpg");
        assertEquals("/Images/green_tea.jpg", product.getImagePath());
    }

    @Test
    void testGetDescription() {
        assertEquals("A soothing herbal tea blend.", product.getDescription());
    }

    @Test
    void testSetDescription() {
        product.setDescription("Refreshing green tea.");
        assertEquals("Refreshing green tea.", product.getDescription());
    }

    @Test
    void testGetPrice() {
        assertEquals(5.99, product.getPrice(), 0.01);
    }

    @Test
    void testSetPrice() {
        product.setPrice(4.99);
        assertEquals(4.99, product.getPrice(), 0.01);
    }

    @Test
    void testNegativePrice() {
        product.setPrice(-5.0);
        assertEquals(-5.0, product.getPrice(), 0.01);
    }

    @Test
    void testEmptyName() {
        product.setName("");
        assertEquals("", product.getName());
    }

    @Test
    void testEmptyCategory() {
        product.setCategory("");
        assertEquals("", product.getCategory());
    }

    @Test
    void testEmptyDescription() {
        product.setDescription("");
        assertEquals("", product.getDescription());
    }

    @Test
    void testNullImagePath() {
        product.setImagePath(null);
        assertEquals(null, product.getImagePath());
    }

    @Test
    void testMinimumPrice() {
        product.setPrice(Double.MIN_VALUE);
        assertEquals(Double.MIN_VALUE, product.getPrice(), 0.01);
    }

    @Test
    void testMaximumPrice() {
        product.setPrice(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, product.getPrice(), 0.01);
    }

    @Test
    void testPerformanceOfPriceCalculation() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000000; i++) {
            product.setPrice(product.getPrice() * 1.01);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        assertTrue(duration < 100); // Assert that the operation completes in less than 100 milliseconds
    }
}
