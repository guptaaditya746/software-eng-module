package de.buw.se;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ProductPage extends Application {

    protected TableView<Product> tableView;
    private String username;
    private AddToCart cart;

    public ProductPage() {
        // Default constructor for JavaFX
    }

    public ProductPage(String username, AddToCart cart) {
        this.username = username;
        this.cart = cart;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setPrefSize(1000, 600);

        HBox topPane = new HBox();
        topPane.setPrefSize(1000, 50);
        topPane.setStyle("-fx-background-color: #0F1110;");
        topPane.setPadding(new Insets(10));

        Label storeLabel = new Label("Electronic Gadget Store");
        storeLabel.setStyle("-fx-background-color: #0F1110;");
        storeLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        storeLabel.setFont(new Font(24.0));
        storeLabel.setPadding(new Insets(0, 0, 0, 10));

        // Create a Region to push the button to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button viewCartButton = new Button("View Cart");
        viewCartButton.setPadding(new Insets(5, 5, 5, 5));
        viewCartButton.setOnAction(event -> displayCart(primaryStage));

        // Add the storeLabel, spacer, and viewCartButton to the topPane
        topPane.getChildren().addAll(storeLabel, spacer, viewCartButton);

        HBox centerPane = new HBox();
        centerPane.setPrefSize(1000, 550);

        VBox leftPane = new VBox(10);
        leftPane.setPrefSize(200, 550);
        leftPane.setStyle("-fx-background-color: #62CBF2;");
        leftPane.setPadding(new Insets(20, 10, 20, 10));

        Label welcomeLabel = new Label("Welcome, ");
        welcomeLabel.setFont(new Font(16.0));

        Text userText = new Text(username != null ? username : "User");
        userText.setFont(new Font(16.0));

        Button mobilesButton = new Button("Mobiles");
        mobilesButton.setOnAction(event -> filterProductsByCategory("Mobile"));

        Button laptopsButton = new Button("Laptops");
        laptopsButton.setOnAction(event -> filterProductsByCategory("Laptop"));

        Button appleProductsButton = new Button("Apple Products");
        appleProductsButton.setOnAction(event -> filterProductsByCategory("Apple Product"));

        Button electronicsButton = new Button("Electronic Gadgets");
        electronicsButton.setOnAction(event -> filterProductsByCategory("Electronic Gadgets"));

        Button sellProductButton = new Button("Sell Your Product");
        sellProductButton.setOnAction(event -> displaySellingPage(primaryStage));

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(event -> loadProductData(tableView));

        Button signOutButton = new Button("Sign Out");
        signOutButton.setOnAction(event -> displayHomePage(primaryStage));

        leftPane.getChildren().addAll(welcomeLabel, userText, mobilesButton, laptopsButton, appleProductsButton,
                electronicsButton, sellProductButton, refreshButton, signOutButton);

        VBox rightPane = new VBox(10);
        rightPane.setPrefSize(800, 550);
        rightPane.setPadding(new Insets(20, 20, 20, 20));
        rightPane.setStyle("-fx-background-color: #D0D0D0;");

        tableView = new TableView<>();
        tableView.setPrefSize(800, 550);

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

        TableColumn<Product, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        categoryColumn.setPrefWidth(150);

        TableColumn<Product, Void> addToCartColumn = createAddToCartColumn();

        tableView.getColumns().addAll(itemNameColumn, yearOfPurchaseColumn, actualPriceColumn, sellingPriceColumn,
                imageColumn, categoryColumn, addToCartColumn);

        rightPane.getChildren().add(tableView);

        centerPane.getChildren().addAll(leftPane, rightPane);
        root.getChildren().addAll(topPane, centerPane);

        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setTitle("Main Window");
        primaryStage.show();

        loadProductData(tableView);
    }

    private TableColumn<Product, Void> createAddToCartColumn() {
        TableColumn<Product, Void> addToCartColumn = new TableColumn<>("Add to Cart");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<>() {

                    private final Button addButton = new Button("Add");

                    {
                        addButton.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            cart.addItem(product);
                            showAddToCartPopup(product.getItemName()); // Display popup notification
                            System.out.println("Added to cart: " + product.getItemName());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(addButton);
                        }
                    }
                };
                return cell;
            }
        };

        addToCartColumn.setCellFactory(cellFactory);
        return addToCartColumn;
    }

    private void showAddToCartPopup(String productName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Product Added to Cart");
        alert.setHeaderText(null);
        alert.setContentText("Product '" + productName + "' has been added to your cart.");
        alert.showAndWait();
    }

    private void displaySellingPage(Stage primaryStage) {
        SellingPage sellingPage = new SellingPage(this);
        sellingPage.start(new Stage());
    }

    private void displayHomePage(Stage primaryStage) {
        HomePage homepage = new HomePage();
        homepage.start(new Stage());
        primaryStage.close();
    }

    private void displayCart(Stage primaryStage) {
        CartPage cartPage = new CartPage(cart);
        cartPage.start(new Stage());
    }

    public void loadProductData(TableView<Product> tableView) {
        tableView.getItems().clear();
        try (Connection conn = DataStoreSql.getConnection();
             Statement stmt = conn.createStatement()) {

            String query = "SELECT ITEM_NAME, YEAR_OF_PURCHASE, ACTUAL_PRICE, SELLING_PRICE, IMAGE, CATEGORY FROM Products";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String itemName = rs.getString("ITEM_NAME");
                int yearOfPurchase = rs.getInt("YEAR_OF_PURCHASE");
                double actualPrice = rs.getDouble("ACTUAL_PRICE");
                double sellingPrice = rs.getDouble("SELLING_PRICE");
                byte[] imageBytes = rs.getBytes("IMAGE");
                String category = rs.getString("CATEGORY");

                Image image = new Image(new ByteArrayInputStream(imageBytes));
                Product product = new Product(itemName, yearOfPurchase, actualPrice, sellingPrice, image, category);
                tableView.getItems().add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void filterProductsByCategory(String category) {
        tableView.getItems().clear();
        try (Connection conn = DataStoreSql.getConnection();
             Statement stmt = conn.createStatement()) {

            String query = "SELECT ITEM_NAME, YEAR_OF_PURCHASE, ACTUAL_PRICE, SELLING_PRICE, IMAGE, CATEGORY FROM Products WHERE CATEGORY = '"
                    + category + "'";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String itemName = rs.getString("ITEM_NAME");
                int yearOfPurchase = rs.getInt("YEAR_OF_PURCHASE");
                double actualPrice = rs.getDouble("ACTUAL_PRICE");
                double sellingPrice = rs.getDouble("SELLING_PRICE");
                byte[] imageBytes = rs.getBytes("IMAGE");
                String categoryResult = rs.getString("CATEGORY");

                Image image = new Image(new ByteArrayInputStream(imageBytes));
                Product product = new Product(itemName, yearOfPurchase, actualPrice, sellingPrice, image,
                        categoryResult);
                tableView.getItems().add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
