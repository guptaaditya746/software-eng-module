package de.buw.se;

import java.util.ArrayList;
import java.util.List;

public class AddToCart {

    private List<Product> cartItems;

    public AddToCart() {
        cartItems = new ArrayList<>();
    }

    public void addItem(Product product) {
        cartItems.add(product);
    }

    public void removeItem(Product product) {
        cartItems.remove(product);
    }

    public List<Product> getCartItems() {
        return cartItems;
    }
}
