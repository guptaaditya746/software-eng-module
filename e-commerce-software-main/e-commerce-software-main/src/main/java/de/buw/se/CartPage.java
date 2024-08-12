package de.buw.se;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CartPage extends Application {

    private AddToCart cart;
    private Label totalAmountLabel;

    public CartPage(AddToCart cart) {
        this.cart = cart;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        Label cartLabel = new Label("Your Cart");
        cartLabel.setFont(new Font(24));

        TableView<Product> cartTableView = new TableView<>();
        cartTableView.setPrefSize(800, 550);

        TableColumn<Product, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
        itemNameColumn.setPrefWidth(150);

        TableColumn<Product, Integer> yearOfPurchaseColumn = new TableColumn<>("Year of Purchase");
        yearOfPurchaseColumn.setCellValueFactory(cellData -> cellData.getValue().yearOfPurchaseProperty().asObject());
        yearOfPurchaseColumn.setPrefWidth(100);

        TableColumn<Product, Double> actualPriceColumn = new TableColumn<>("Actual Price(€)");
        actualPriceColumn.setCellValueFactory(cellData -> cellData.getValue().actualPriceProperty().asObject());
        actualPriceColumn.setPrefWidth(100);

        TableColumn<Product, Double> sellingPriceColumn = new TableColumn<>("Selling Price(€)");
        sellingPriceColumn.setCellValueFactory(cellData -> cellData.getValue().sellingPriceProperty().asObject());
        sellingPriceColumn.setPrefWidth(100);

        TableColumn<Product, Image> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellValueFactory(cellData -> cellData.getValue().imageProperty());
        imageColumn.setPrefWidth(200);
        imageColumn.setCellFactory(column -> new TableCell<Product, Image>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    setGraphic(imageView);
                }
            }
        });

        TableColumn<Product, Void> removeFromCartColumn = createRemoveFromCartColumn(cartTableView);

        cartTableView.getColumns().addAll(itemNameColumn, yearOfPurchaseColumn, actualPriceColumn, sellingPriceColumn,
                imageColumn, removeFromCartColumn);

        cartTableView.getItems().addAll(cart.getCartItems());

        // Total Amount Label
        totalAmountLabel = new Label();
        totalAmountLabel.setFont(new Font(18));
        updateTotalAmount();

        VBox.setVgrow(cartTableView, Priority.ALWAYS);

        root.getChildren().addAll(cartLabel, cartTableView, totalAmountLabel);

        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setTitle("Cart");
        primaryStage.show();
    }

    private void updateTotalAmount() {
        double total = cart.getCartItems().stream()
                .mapToDouble(product -> product.getSellingPrice())
                .sum();
        totalAmountLabel.setText(String.format("Total Amount: € %.2f", total));
    }

    private TableColumn<Product, Void> createRemoveFromCartColumn(TableView<Product> cartTableView) {
        TableColumn<Product, Void> removeFromCartColumn = new TableColumn<>("Remove");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<>() {

                    private final Button removeButton = new Button("Remove");

                    {
                        removeButton.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            cart.removeItem(product);
                            cartTableView.getItems().remove(product);
                            System.out.println("Removed from cart: " + product.getItemName());

                            // Show alert
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Product Removed");
                            alert.setHeaderText(null);
                            alert.setContentText("Product successfully removed from the cart: " + product.getItemName());
                            alert.showAndWait();

                            // Update total amount display
                            updateTotalAmount();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(removeButton);
                        }
                    }
                };
                return cell;
            }
        };

        removeFromCartColumn.setCellFactory(cellFactory);
        return removeFromCartColumn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
