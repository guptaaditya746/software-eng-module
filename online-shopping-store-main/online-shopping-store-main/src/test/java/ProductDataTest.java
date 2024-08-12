import de.buw.se.ProductData;
import de.buw.se.ProductData.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDataTest {

    List<Product> products;

    @BeforeEach
    void setUp() {
        products = ProductData.loadProducts();
    }

    @Test
    void testLoadProducts() {
        // Check that the loaded products list is not null and contains expected number of products
        assertNotNull(products);
        assertEquals(10, products.size());

        // Test specific attributes of each product
        Product product1 = products.get(0);
        assertEquals("Herbal Tea", product1.getName());
        assertEquals("Ayurveda", product1.getCategory());
        assertEquals("/Images/herbal_tea.jpg", product1.getImagePath());
        assertEquals("A soothing herbal tea blend.", product1.getDescription());
        assertEquals(5.99, product1.getPrice(), 0.01);

        Product product2 = products.get(1);
        assertEquals("Ashwagandha Capsules", product2.getName());
        assertEquals("Ayurveda", product2.getCategory());
        assertEquals("/Images/ashwagandha_capsules.jpg", product2.getImagePath());
        assertEquals("Natural capsules to promote vitality.", product2.getDescription());
        assertEquals(9.99, product2.getPrice(), 0.01);

        Product product3 = products.get(2);
        assertEquals("Homeopathic Remedy A", product3.getName());
        assertEquals("Homeopathy", product3.getCategory());
        assertEquals("/Images/homeopathic_remedy_a.jpg", product3.getImagePath());
        assertEquals("Effective homeopathic remedy.", product3.getDescription());
        assertEquals(7.49, product3.getPrice(), 0.01);

        Product product4 = products.get(3);
        assertEquals("Homeopathic Remedy B", product4.getName());
        assertEquals("Homeopathy", product4.getCategory());
        assertEquals("/Images/homeopathic_remedy_b.jpg", product4.getImagePath());
        assertEquals("Another homeopathic remedy option.", product4.getDescription());
        assertEquals(6.99, product4.getPrice(), 0.01);

        Product product5 = products.get(4);
        assertEquals("Herbal Tincture", product5.getName());
        assertEquals("Home Prepared Medicine", product5.getCategory());
        assertEquals("/Images/herbal_tincture.jpg", product5.getImagePath());
        assertEquals("Homemade herbal tincture.", product5.getDescription());
        assertEquals(12.99, product5.getPrice(), 0.01);

        Product product6 = products.get(5);
        assertEquals("Handmade Balm", product6.getName());
        assertEquals("Home Prepared Medicine", product6.getCategory());
        assertEquals("/Images/handmade_balm.jpg", product6.getImagePath());
        assertEquals("All-natural healing balm.", product6.getDescription());
        assertEquals(8.99, product6.getPrice(), 0.01);

        Product product7 = products.get(6);
        assertEquals("Herbal Soap", product7.getName());
        assertEquals("Naturopathy", product7.getCategory());
        assertEquals("/Images/herbal_soap.jpg", product7.getImagePath());
        assertEquals("Gentle herbal soap bar.", product7.getDescription());
        assertEquals(4.99, product7.getPrice(), 0.01);

        Product product8 = products.get(7);
        assertEquals("Essential Oil", product8.getName());
        assertEquals("Naturopathy", product8.getCategory());
        assertEquals("/Images/essential_oil.jpg", product8.getImagePath());
        assertEquals("Pure essential oil for aromatherapy.", product8.getDescription());
        assertEquals(11.99, product8.getPrice(), 0.01);

        Product product9 = products.get(8);
        assertEquals("Ginseng Extract", product9.getName());
        assertEquals("Traditional Chinese Medicine", product9.getCategory());
        assertEquals("/Images/ginseng_extract.jpg", product9.getImagePath());
        assertEquals("High-quality ginseng extract.", product9.getDescription());
        assertEquals(14.99, product9.getPrice(), 0.01);

        Product product10 = products.get(9);
        assertEquals("Chinese Herbal Mix", product10.getName());
        assertEquals("Traditional Chinese Medicine", product10.getCategory());
        assertEquals("/Images/chinese_herbal_mix.jpg", product10.getImagePath());
        assertEquals("Traditional herbal mix for various ailments.", product10.getDescription());
        assertEquals(19.99, product10.getPrice(), 0.01);
    }

    @Test
    void testLoadProductsNotNull() {
        assertNotNull(products);
    }

    @Test
    void testLoadProductsSize() {
        assertEquals(10, products.size());
    }

    @Test
    void testProductCategoryCounts() {
        // Count products in each category
        long ayurvedaCount = products.stream().filter(p -> p.getCategory().equals("Ayurveda")).count();
        long homeopathyCount = products.stream().filter(p -> p.getCategory().equals("Homeopathy")).count();
        long homePreparedMedicineCount = products.stream().filter(p -> p.getCategory().equals("Home Prepared Medicine")).count();
        long naturopathyCount = products.stream().filter(p -> p.getCategory().equals("Naturopathy")).count();
        long traditionalChineseMedicineCount = products.stream().filter(p -> p.getCategory().equals("Traditional Chinese Medicine")).count();

        assertEquals(2, ayurvedaCount);
        assertEquals(2, homeopathyCount);
        assertEquals(2, homePreparedMedicineCount);
        assertEquals(2, naturopathyCount);
        assertEquals(2, traditionalChineseMedicineCount);
    }

    @Test
    void testUniqueProducts() {
        // Check uniqueness of products
        for (int i = 0; i < products.size(); i++) {
            for (int j = i + 1; j < products.size(); j++) {
                assertNotEquals(products.get(i), products.get(j));
            }
        }
    }
}
