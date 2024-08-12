package de.buw.se;

import java.io.File;
import java.time.Year;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SellingPage extends Application {

    private ProductPage productPage;
    private TextField itemNameField;
    private TextField yearOfPurchaseField;
    private TextField actualPriceField;
    private TextField sellingPriceField;
    private TextField imagesField;
    private TextArea commentsArea;
    private ComboBox<String> categoryComboBox;
    private File selectedImageFile;

    public SellingPage() {
        // No-arg constructor for JavaFX
    }

    public SellingPage(ProductPage productPage) {
        this.productPage = productPage;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setPrefSize(640.0, 600.0);

        AnchorPane anchorPane = new AnchorPane();
        VBox.setVgrow(anchorPane, javafx.scene.layout.Priority.ALWAYS);

        Label titleLabel = createLabel("Selling Item Details", 214.0, 14.0, 25.0);

        Label itemNameLabel = createLabel("Item Name :-", 33.0, 66.0, 20.0);
        Label categoryLabel = createLabel("Category :-", 37.0, 109.0, 20.0);
        Label yearOfPurchaseLabel = createLabel("Year of Purchase :-", 33.0, 150.0, 20.0);
        Label actualPriceLabel = createLabel("Actual Price(€) :-", 34.0, 198.0, 20.0);
        Label sellingPriceLabel = createLabel("Selling Price(€) :-", 34.0, 240.0, 20.0);
        Label imagesLabel = createLabel("Images :-", 34.0, 287.0, 20.0);
        Label commentsLabel = createLabel("Comments :-", 40.0, 352.0, 20.0);

        itemNameField = createTextField(276.0, 66.0);
        categoryComboBox = createComboBox(276.0, 111.0);
        yearOfPurchaseField = createTextField(275.0, 150.0);
        actualPriceField = createTextField(276.0, 198.0);
        sellingPriceField = createTextField(276.0, 240.0);
        imagesField = createTextField(275.0, 287.0);

        commentsArea = new TextArea();
        commentsArea.setLayoutX(181.0);
        commentsArea.setLayoutY(343.0);
        commentsArea.setPrefSize(435.0, 63.0);

        Button submitButton = new Button("Submit");
        submitButton.setLayoutX(495.0);
        submitButton.setLayoutY(429.0);
        submitButton.setOnAction(e -> handleSubmitButtonAction(primaryStage));

        Hyperlink importImageLink = new Hyperlink("Import Image");
        importImageLink.setLayoutX(358.0);
        importImageLink.setLayoutY(290.0);
        importImageLink.setOnAction(e -> handleImportImage(primaryStage));

        anchorPane.getChildren().addAll(
                titleLabel, itemNameLabel, categoryLabel, yearOfPurchaseLabel, actualPriceLabel, sellingPriceLabel, imagesLabel, commentsLabel,
                itemNameField, categoryComboBox, yearOfPurchaseField, actualPriceField, sellingPriceField, commentsArea, imagesField,
                submitButton, importImageLink
        );

        root.getChildren().add(anchorPane);

        primaryStage.setScene(new Scene(root, 640, 600));
        primaryStage.setTitle("Selling Item Details");
        primaryStage.show();
    }

    private Label createLabel(String text, double x, double y, double fontSize) {
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setStyle("-fx-background-color: #A99D9D;");
        label.setFont(new Font(fontSize));
        return label;
    }

    private TextField createTextField(double x, double y) {
        TextField textField = new TextField();
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setPrefSize(246.0, 30.0);
        return textField;
    }

    private ComboBox<String> createComboBox(double x, double y) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setLayoutX(x);
        comboBox.setLayoutY(y);
        comboBox.setPrefSize(246.0, 26.0);
        comboBox.setPromptText("Category");
        // Add categories
        comboBox.getItems().addAll("Mobile", "Laptop", "Apple Product", "Electronic Gadgets");
        return comboBox;
    }

    private void handleImportImage(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            imagesField.setText(file.getPath());
            selectedImageFile = file;
        }
    }

    private void handleSubmitButtonAction(Stage primaryStage) {
        try {
            String itemName = itemNameField.getText().trim();
            if (itemName.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Product addition failed!", "Item name cannot be empty or just spaces.");
                return;
            }

            String category = categoryComboBox.getValue();
            if (category == null || category.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Product addition failed!", "Please select a category.");
                return;
            }

            int yearOfPurchase;
            try {
                yearOfPurchase = Integer.parseInt(yearOfPurchaseField.getText());
                if (yearOfPurchase < 0) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Product addition failed!", "Year of purchase cannot be negative.");
                    return;
                }
                int currentYear = Year.now().getValue();
                if (yearOfPurchase > currentYear) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Product addition failed!", "Year of purchase cannot be in the future.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Product addition failed!", "Year of purchase must be a valid number.");
                return;
            }

            double actualPrice;
            try {
                actualPrice = Double.parseDouble(actualPriceField.getText());
                if (actualPrice < 0) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Product addition failed!", "Actual price cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Product addition failed!", "Actual price must be a valid number.");
                return;
            }

            double sellingPrice;
            try {
                sellingPrice = Double.parseDouble(sellingPriceField.getText());
                if (sellingPrice < 0) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Product addition failed!", "Selling price cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Product addition failed!", "Selling price must be a valid number.");
                return;
            }

            String comments = commentsArea.getText();

            if (selectedImageFile == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Product addition failed!", "Please select an image.");
                return;
            }

            // Add the product to your data store
            DataStoreSql.addProduct(itemName, category, yearOfPurchase, actualPrice, sellingPrice, selectedImageFile, comments);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully!", "The product details have been added to the database.");

            // Clear fields after successful submission
            itemNameField.clear();
            categoryComboBox.setValue(null);
            yearOfPurchaseField.clear();
            actualPriceField.clear();
            sellingPriceField.clear();
            imagesField.clear();
            commentsArea.clear();
            selectedImageFile = null;

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Product addition failed!", "There was an error adding the product. Please check your inputs.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
