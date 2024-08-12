package de.buw.se;

import java.util.ArrayList;
import java.util.List;

public class ProductData {

    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        
        // Ayurvedic  Products
        products.add(new Product("Herbal Tea", "Ayurveda", "/Images/herbal_tea.jpg", "A soothing herbal tea blend.", 5.99));
        products.add(new Product("Ashwagandha Capsules", "Ayurveda", "/Images/ashwagandha_capsules.jpg", "Natural capsules to promote vitality.", 9.99));

        // Homeopathic  Products
        products.add(new Product("Homeopathic Remedy A", "Homeopathy", "/Images/homeopathic_remedy_a.jpg", "Effective homeopathic remedy.", 7.49));
        products.add(new Product("Homeopathic Remedy B", "Homeopathy", "/Images/homeopathic_remedy_b.jpg", "Another homeopathic remedy option.", 6.99));

        // Home Prepared Medicinall Products
        products.add(new Product("Herbal Tincture", "Home Prepared Medicine", "/Images/herbal_tincture.jpg", "Homemade herbal tincture.", 12.99));
        products.add(new Product("Handmade Balm", "Home Prepared Medicine", "/Images/handmade_balm.jpg", "All-natural healing balm.", 8.99));

        // Naturopathic  Products
        products.add(new Product("Herbal Soap", "Naturopathy", "/Images/herbal_soap.jpg", "Gentle herbal soap bar.", 4.99));
        products.add(new Product("Essential Oil", "Naturopathy", "/Images/essential_oil.jpg", "Pure essential oil for aromatherapy.", 11.99));

        // Traditional Chinese Medicincal  Products
        products.add(new Product("Ginseng Extract", "Traditional Chinese Medicine", "/Images/ginseng_extract.jpg", "High-quality ginseng extract.", 14.99));
        products.add(new Product("Chinese Herbal Mix", "Traditional Chinese Medicine", "/Images/chinese_herbal_mix.jpg", "Traditional herbal mix for various ailments.", 19.99));

        return products;

    }

    public static class Product {
        protected String name;
        protected String category;

        protected String imagePath;
        protected String description;
        protected double price;

        public Product(String name, String category, String imagePath, String description, double price) {
            this.name = name;
            this.category = category;
            this.imagePath = imagePath;
            this.description = description;
            this.price = price;
        }

        
        public String getName() {
            return name;
        }



        public String getCategory() {
            return category; }


        public String getImagePath() {
            return imagePath;
        }

        public String getDescription() {
            return description;
        }

        public double getPrice() {
            return price;
        }
    }
}
