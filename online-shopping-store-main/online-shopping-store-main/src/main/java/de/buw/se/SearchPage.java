package de.buw.se;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SearchPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome to");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label searchLabel = new Label("Search Bar:");
        GridPane.setConstraints(searchLabel, 0, 0);
        TextField searchField = new TextField();
        searchField.setPromptText("Search Bar:");
        searchField.setId("searchField"); // Set ID for testing
        GridPane.setConstraints(searchField, 1, 0);

        Button searchButton = new Button("Search");
        searchButton.setId("searchButton"); // Set ID for testing
        GridPane.setConstraints(searchButton, 2, 0);

        // Add console label
        Label consoleLabel = new Label();
        consoleLabel.setId("consoleLabel"); // Set ID for testing
        GridPane.setConstraints(consoleLabel, 1, 1);

        searchButton.setOnAction(e -> {
            String query = searchField.getText();

            if (query.isEmpty()) {
                consoleLabel.setText("");
            } else {
                consoleLabel.setText("Search Query: " + query);
                System.out.println("Search Query: " + query);
            }
        });

        grid.getChildren().addAll(searchLabel, searchField, searchButton, consoleLabel);

        Scene scene = new Scene(grid, 400, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}