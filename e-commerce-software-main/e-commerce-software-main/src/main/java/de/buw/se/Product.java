package de.buw.se;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Product {

    private final StringProperty itemName;
    private final IntegerProperty yearOfPurchase;
    private final DoubleProperty actualPrice;
    private final DoubleProperty sellingPrice;
    private final ObjectProperty<Image> image;
    private final StringProperty category;

    public Product(String itemName, int yearOfPurchase, double actualPrice, double sellingPrice, Image image, String category) {
        this.itemName = new SimpleStringProperty(itemName);
        this.yearOfPurchase = new SimpleIntegerProperty(yearOfPurchase);
        this.actualPrice = new SimpleDoubleProperty(actualPrice);
        this.sellingPrice = new SimpleDoubleProperty(sellingPrice);
        this.image = new SimpleObjectProperty<>(image);
        this.category = new SimpleStringProperty(category);
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public IntegerProperty yearOfPurchaseProperty() {
        return yearOfPurchase;
    }

    public DoubleProperty actualPriceProperty() {
        return actualPrice;
    }

    public DoubleProperty sellingPriceProperty() {
        return sellingPrice;
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public String getItemName() {
        return itemName.get();
    }

    public double getSellingPrice() {
        return sellingPrice.get();
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice.set(sellingPrice);
    }
}
