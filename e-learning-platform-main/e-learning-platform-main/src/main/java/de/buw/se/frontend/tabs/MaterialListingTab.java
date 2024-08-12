package de.buw.se.frontend.tabs;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.buw.se.backend.model.Material;
import de.buw.se.backend.service.MaterialService;
import de.buw.se.frontend.utils.CursorUtils;
import de.buw.se.frontend.utils.DownloadUtils;
import de.buw.se.frontend.utils.MaterialTabUtils;
import de.buw.se.frontend.utils.NotificationHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MaterialListingTab {

    private final MaterialService materialService;
    private final int userId;
    private final String loggedInUsername;
    private FileUploadTab fileUploadTab;
    private final TabPane tabPane;

    public MaterialListingTab(MaterialService materialService, int userId, String loggedInUsername, FileUploadTab fileUploadTab, TabPane tabPane) {
        this.materialService = materialService;
        this.userId = userId;
        this.loggedInUsername = loggedInUsername;
        this.fileUploadTab = fileUploadTab;
        this.tabPane = tabPane;
    }
    private BorderPane borderPane;
    private TableView<Material> tableView;
    private List<Material> allMaterials;

    public void setFileUploadTab(FileUploadTab fileUploadTab) {
        this.fileUploadTab = fileUploadTab;
    }

    @SuppressWarnings("unchecked")
    public BorderPane createMaterialListingPane() {
        borderPane = new BorderPane();
        // Search bar and reset button
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search by author, material name or ID");
        Button resetButton = new Button("Reset");
        CursorUtils.setPointerCursor(resetButton);
        HBox searchBox = new HBox(10);
        searchBox.setPadding(new Insets(10));
        searchBox.setStyle("-fx-alignment: center-right;");
        // Adding spacing and pushing the elements to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        searchBox.getChildren().addAll(spacer, searchBar, resetButton);
        // Main layout containing the searchBox and tableView
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        tableView = new TableView<>();
        // ID Column
        TableColumn<Material, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        // Author Name Column
        TableColumn<Material, String> authorNameColumn = new TableColumn<>("Author Name");
        authorNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        // Uploaded Date Column
        TableColumn<Material, Timestamp> uploadedDateColumn = new TableColumn<>("Uploaded Date");
        uploadedDateColumn.setCellValueFactory(new PropertyValueFactory<>("uploadedDate"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        uploadedDateColumn.setCellFactory((TableColumn<Material, Timestamp> column) -> new TableCell<Material, Timestamp>() {
            @Override
            protected void updateItem(Timestamp item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item.toLocalDateTime()));
                }
            }
        });
        // Uploaded Time Column
        TableColumn<Material, Timestamp> uplodaedTimeColumn = new TableColumn<>("Uploaded Time");
        uplodaedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("uploadedDate"));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        uplodaedTimeColumn.setCellFactory((TableColumn<Material, Timestamp> column) -> new TableCell<Material, Timestamp>() {
            @Override
            protected void updateItem(Timestamp item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(timeFormatter.format(item.toLocalDateTime()));
                }
            }
        });
        // File Name Column
        TableColumn<Material, String> fileNameColumn = new TableColumn<>("File Name");
        fileNameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        // Buttons Column
        TableColumn<Material, Void> buttonsColumn = new TableColumn<>("Actions");
        buttonsColumn.setCellFactory(param -> new TableCell<Material, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox pane = new HBox(5, editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    Material selectedMaterial = getTableView().getItems().get(getIndex());
                    if (selectedMaterial != null) {
                        fileUploadTab.setMaterialToEdit(selectedMaterial);
                    } else {
                        System.out.println("Error: No material selected.");
                    }
                });
                deleteButton.setOnAction(event -> {
                    Material selectedMaterial = getTableView().getItems().get(getIndex());
                    if (selectedMaterial != null) {
                        deleteMaterial(selectedMaterial.getId());
                    } else {
                        System.out.println("Error: No Material selected.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Material currentMaterial = getTableView().getItems().get(getIndex());
                    if (currentMaterial.getAuthorName().equals(loggedInUsername)) {
                        setGraphic(pane);
                        CursorUtils.setPointerCursor(editButton);
                        CursorUtils.setPointerCursor(deleteButton);
                    } else {
                        // Hide buttons if the user is not the author
                        setGraphic(null);
                    }
                }
            }
        });
        // Download Column
        TableColumn<Material, Void> downloadColumn = new TableColumn<>("Download");
        downloadColumn.setCellFactory(param -> new TableCell<Material, Void>() {
            private final Button downloadButton = new Button("Download");

            {
                downloadButton.setOnAction(event -> {
                    Material selectedMaterial = getTableView().getItems().get(getIndex());
                    if (selectedMaterial != null) {
                        DownloadUtils.downloadMaterial(selectedMaterial);
                    } else {
                        System.out.println("Error: No material selected.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(downloadButton);
                    CursorUtils.setPointerCursor(downloadButton);
                }
            }
        });
        tableView.getColumns().addAll(idColumn, authorNameColumn, uploadedDateColumn, uplodaedTimeColumn, fileNameColumn, buttonsColumn, downloadColumn);
        // Fetch and list the uploaded materials
        allMaterials = getAllMaterials();
        ObservableList<Material> materials = FXCollections.observableArrayList(allMaterials);
        tableView.setItems(materials);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterMaterials(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        resetButton.setOnAction(event -> resetMaterials(searchBar));
        vbox.getChildren().addAll(searchBox);
        vbox.getChildren().addAll(tableView);
        borderPane.setCenter(vbox);
        return borderPane;
    }

    private List<Material> getAllMaterials() {
        try {
            return materialService.getAllMaterials(userId);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private void deleteMaterial(int materialId) {
        try {
            boolean isDeleted = materialService.deleteMaterial(materialId);
            if (isDeleted) {

                // Remove the deleted material from the allMaterials list
                allMaterials = allMaterials.stream()
                        .filter(material -> material.getId() != materialId)
                        .collect(Collectors.toList());

                // Refresh the table view
                tableView.setItems(FXCollections.observableArrayList(allMaterials));

                NotificationHelper.showNotification("Material Deleted Successfully!!");
            } else {
                System.out.println("Error in deleting Material!!");
            }
        } catch (Exception e) {
            System.err.println("Error deleting material: " + e.getMessage());
        }
    }

    public void refreshMaterialsListings() throws SQLException, ClassNotFoundException {
        BorderPane newBPane = ((BorderPane) ((Tab) tabPane.getTabs().get(0)).getContent());
        MaterialTabUtils.refreshMaterialsListings(newBPane, materialService, userId, this);
        allMaterials = materialService.getAllMaterials(userId);
        tableView.setItems(FXCollections.observableArrayList(allMaterials));
    }

    public void updateMaterialsList(List<Material> updatedMaterials) {
        this.allMaterials = updatedMaterials;
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableArrayList(allMaterials));
    }

    private void filterMaterials(String query) throws SQLException, ClassNotFoundException {
        if (query == null || query.isEmpty()) {
            tableView.setItems(FXCollections.observableArrayList(allMaterials));
            refreshMaterialsListings();
            return;
        }
        String lowerCaseQuery = query.toLowerCase();
        List<Material> filteredMaterials = allMaterials.stream()
                .filter(material
                        -> String.valueOf(material.getId()).contains(lowerCaseQuery)
                || material.getAuthorName().toLowerCase().contains(lowerCaseQuery)
                || material.getFileName().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());
        // Force the TableView to refresh
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableArrayList(filteredMaterials));
    }

    private void resetMaterials(TextField searchBarField) {
        if (!searchBarField.getText().isEmpty()) {
            searchBarField.clear();
            tableView.setItems(FXCollections.observableArrayList(allMaterials));
        }
    }
}
