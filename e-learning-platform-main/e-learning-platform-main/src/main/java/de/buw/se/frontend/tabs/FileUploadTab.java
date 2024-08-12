package de.buw.se.frontend.tabs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import de.buw.se.backend.model.Material;
import de.buw.se.backend.model.User;
import de.buw.se.backend.service.MaterialService;
import de.buw.se.frontend.utils.AlertUtils;
import de.buw.se.frontend.utils.CursorUtils;
import de.buw.se.frontend.utils.MaterialTabUtils;
import de.buw.se.frontend.utils.NotificationHelper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class FileUploadTab {

    private final MaterialService materialService;
    private final int userId;
    private final User currentUser;
    private final BorderPane listingTabContent;
    private final TabPane tabPane;
    private TextField filePathField;
    private int editMaterialId = -1;
    private String editMaterialName = "";
    private MaterialListingTab materialListingTab;

    public FileUploadTab(MaterialService materialService, int userId, User currentUser, BorderPane listingTabContent, TabPane tabPane) {
        this.materialService = materialService;
        this.userId = userId;
        this.listingTabContent = listingTabContent;
        this.currentUser = currentUser;
        this.tabPane = tabPane;
    }

    public void setMaterialListingTab(MaterialListingTab materialListingTab) {
        this.materialListingTab = materialListingTab;
    }

    public void notifyMaterialListingTab() throws SQLException, ClassNotFoundException {
        if (materialListingTab != null) {
            materialListingTab.refreshMaterialsListings();
        }
    }

    public BorderPane createFileUploadPane() {
        BorderPane borderPane = new BorderPane();

        // Upload File Tab
        GridPane uploadFileGrid = new GridPane();
        uploadFileGrid.setPadding(new Insets(20));
        uploadFileGrid.setVgap(10);
        uploadFileGrid.setHgap(10);

        Label fileLabel = new Label("Choose File:");
        filePathField = new TextField();
        filePathField.setPromptText("No file chosen");
        filePathField.setEditable(false);

        Button browseButton = new Button("Browse");
        CursorUtils.setPointerCursor(browseButton);
        Button uploadButton = new Button("Upload");
        CursorUtils.setPointerCursor(uploadButton);

        uploadFileGrid.add(fileLabel, 0, 0);
        uploadFileGrid.add(filePathField, 1, 0);
        uploadFileGrid.add(browseButton, 2, 0);
        uploadFileGrid.add(uploadButton, 1, 1);

        borderPane.setCenter(uploadFileGrid);

        // FileChooser setup
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");

        // Button Actions
        browseButton.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(null);
            System.out.println("Browse button called");
            if (selectedFile != null) {
                System.out.println("Browse button if");
                filePathField.setText(selectedFile.getAbsolutePath());
            }
        });

        uploadButton.setOnAction(event -> {
            String filePath = filePathField.getText();
            if (filePath.isEmpty()) {
                System.out.println("No file chosen error");
                AlertUtils.showAlert("No file chosen.", "Error");
            } else {
                System.out.println("file chosen section");
                File file = new File(filePath);
                if (file.exists()) {
                    System.out.println("file exist section");
                    try {
                        byte[] fileData = new byte[(int) file.length()];
                        try (FileInputStream fis = new FileInputStream(file)) {
                            fis.read(fileData);
                            System.out.println("file read try section");
                        }

                        Timestamp uploadedDate = Timestamp.valueOf(LocalDateTime.now());
                        if (editMaterialId != -1 && !file.getName().equals(editMaterialName)) {
                            boolean isSuccess = materialService.updateMaterial(editMaterialId, userId, currentUser.getUsername(), uploadedDate, fileData, file.getName());
                            if (isSuccess) {
                                System.out.println("Update material success section");
                                resetEditState();
                                showSuccessAndRefresh("File updated successfully: " + file.getName());
                            } else {
                                System.out.println("Update material fail section");
                                AlertUtils.showAlert("File update failed. File might be too large.", "Error");
                            }
                        } else {
                            boolean isSuccess = materialService.addMaterial(userId, currentUser.getUsername(), uploadedDate, fileData, file.getName());
                            if (isSuccess) {
                                System.out.println("Add material success section");
                                resetEditState();
                                showSuccessAndRefresh("File uploaded successfully: " + file.getName());
                            } else {
                                System.out.println("Add material fail section");
                                AlertUtils.showAlert("File upload failed. File might be too large.", "Error");
                            }
                        }
                    } catch (IOException e) {
                        AlertUtils.showAlert("An error occurred while reading the file.", "Error");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    AlertUtils.showAlert("File does not exist.", "Error");
                }
            }
        });

        // Listener for tab changes
        tabPane.getSelectionModel()
                .selectedItemProperty().addListener((observable, oldTab, newTab) -> {
                    if (newTab != null && newTab.getText().equals("Upload Material")) {
                        // User navigated to the upload tab, do nothing
                    } else {
                        // User navigated away from the upload tab, reset the edit state
                        resetEditState();
                    }
                });

        return borderPane;
    }

    public void setMaterialToEdit(Material material) {
        this.editMaterialId = material.getId();
        this.editMaterialName = material.getFileName();
        filePathField.setText(material.getFileName());
        tabPane.getSelectionModel().select(1); // Switch to the upload tab
    }

    private void resetEditState() {
        editMaterialId = -1;
        editMaterialName = "";
        filePathField.clear();
    }

    private void showSuccessAndRefresh(String message) {
        NotificationHelper.showNotification(message);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(0.7),
                e -> {
                    try {
                        MaterialTabUtils.refreshMaterialsListings(listingTabContent, materialService, userId, materialListingTab);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    tabPane.getSelectionModel().select(0); // Switch to the listing tab
                }));
        timeline.play();
    }
}
