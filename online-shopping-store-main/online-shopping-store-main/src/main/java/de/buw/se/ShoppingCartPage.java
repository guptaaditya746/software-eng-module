package de.buw.se;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartPage extends Application {

    protected Stage primaryStage;
    protected Accordion productAccordion;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Shopping Cart");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label searchLabel = new Label("Search Bar:");
        GridPane.setConstraints(searchLabel, 0, 0);
        TextField searchField = new TextField();
        searchField.setPromptText("Enter search term...");
        searchField.setId("searchField"); // Set ID
        GridPane.setConstraints(searchField, 1, 0);

        Button searchButton = new Button("Search");
        searchButton.setId("searchButton"); // Set ID
        GridPane.setConstraints(searchButton, 2, 0);
        searchButton.setOnAction(e -> {
            String query = searchField.getText();
            filterProducts(query, productAccordion);
        });

        Label typesLabel = new Label("Medicine Types:");
        GridPane.setConstraints(typesLabel, 0, 1);

        CheckBox ayurvedaCheckBox = new CheckBox("Ayurveda");
        ayurvedaCheckBox.setId("ayurvedaCheckBox"); // Set ID
        GridPane.setConstraints(ayurvedaCheckBox, 0, 2);

        CheckBox homeopathyCheckBox = new CheckBox("Homeopathy");
        homeopathyCheckBox.setId("homeopathyCheckBox"); // Set ID
        GridPane.setConstraints(homeopathyCheckBox, 0, 3);

        CheckBox homePreparedCheckBox = new CheckBox("Home Prepared Medicine");
        homePreparedCheckBox.setId("homePreparedCheckBox"); // Set ID
        GridPane.setConstraints(homePreparedCheckBox, 0, 4);

        CheckBox naturopathyCheckBox = new CheckBox("Naturopathy");
        naturopathyCheckBox.setId("naturopathyCheckBox"); // Set ID
        GridPane.setConstraints(naturopathyCheckBox, 0, 5);

        CheckBox chineseMedicineCheckBox = new CheckBox("Traditional Chinese Medicine");
        chineseMedicineCheckBox.setId("chineseMedicineCheckBox"); // Set ID
        GridPane.setConstraints(chineseMedicineCheckBox, 0, 6);

        Label cartLabel = new Label("Your Shopping Cart:");
        GridPane.setConstraints(cartLabel, 0, 7);

        Label productLabel = new Label("Products:");
        GridPane.setConstraints(productLabel, 0, 8);

        productAccordion = new Accordion();
        productAccordion.setId("productAccordion"); // Set ID
        GridPane.setConstraints(productAccordion, 0, 9, 3, 1);

        ScrollPane scrollPane = new ScrollPane(productAccordion);
        scrollPane.setPrefHeight(300);
        scrollPane.setFitToWidth(true);
        GridPane.setConstraints(scrollPane, 0, 10, 3, 1);

        Button logoutButton = new Button("Logout");
        logoutButton.setId("logoutButton");
        GridPane.setConstraints(logoutButton, 0, 11);
        logoutButton.setOnAction(e -> redirectToLoginPage());

        List<CheckBox> checkBoxes = List.of(ayurvedaCheckBox, homeopathyCheckBox, homePreparedCheckBox, naturopathyCheckBox, chineseMedicineCheckBox);
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnAction(e -> filterProducts(checkBoxes, productAccordion));
        }

        grid.getChildren().addAll(searchLabel, searchField, searchButton, typesLabel, ayurvedaCheckBox, homeopathyCheckBox,
                homePreparedCheckBox, naturopathyCheckBox, chineseMedicineCheckBox, cartLabel, productLabel, scrollPane, logoutButton);

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    protected void filterProducts(String query, Accordion productAccordion) {
        List<ProductData.Product> products = ProductData.loadProducts();
        productAccordion.getPanes().clear();
        if (query.isEmpty()) {
            return;
        }
        for (ProductData.Product product : products) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                TitledPane productPane = createProductPane(product);
                if (productPane != null) {
                    productAccordion.getPanes().add(productPane);
                }
            }
        }
    }

    protected void filterProducts(List<CheckBox> checkBoxes, Accordion productAccordion) {
        List<String> selectedTypes = new ArrayList<>();
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                selectedTypes.add(checkBox.getText());
            }
        }

        List<ProductData.Product> products = ProductData.loadProducts();
        productAccordion.getPanes().clear();
        for (ProductData.Product product : products) {
            if (selectedTypes.contains(product.getCategory())) {
                TitledPane productPane = createProductPane(product);
                if (productPane != null) {
                    productAccordion.getPanes().add(productPane);
                }
            }
        }
    }

    protected TitledPane createProductPane(ProductData.Product product) {
        ImageView imageView = createImageView(product.getImagePath());
        if (imageView == null) {
            return null;
        }

        Label nameLabel = new Label("Name: " + product.getName());
        Label descriptionLabel = new Label("Description: " + product.getDescription());
        Label priceLabel = new Label("Price: " + product.getPrice() + " €");

        VBox vbox = new VBox(10, nameLabel, descriptionLabel, priceLabel);
        TitledPane pane = new TitledPane(product.getName(), vbox);
        pane.setGraphic(imageView);
        pane.setExpanded(false); // Collapse the pane initially
        return pane;
    }

    protected ImageView createImageView(String imagePath) {
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return imageView;
        } catch (NullPointerException e) {
            System.err.println("Couldn't find file: " + imagePath);
            return null;
        }
    }

    protected void redirectToLoginPage() {
        AppGUI appGUI = new AppGUI();
        appGUI.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}