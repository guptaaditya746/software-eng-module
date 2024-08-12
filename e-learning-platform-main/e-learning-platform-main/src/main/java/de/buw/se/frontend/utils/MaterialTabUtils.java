package de.buw.se.frontend.utils;

import java.sql.SQLException;
import java.util.List;

import de.buw.se.backend.model.Material;
import de.buw.se.backend.service.MaterialService;
import de.buw.se.frontend.tabs.MaterialListingTab;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MaterialTabUtils {

    @SuppressWarnings("unchecked")
    public static void refreshMaterialsListings(BorderPane listingTabContent, MaterialService materialService, int userId, MaterialListingTab materialListingTab) throws SQLException, ClassNotFoundException {
        // Get the center node of the listing tab
        Node centerNode = listingTabContent.getCenter();

        // Use pattern matching for instanceof
        // Need to use Version 16 of java to use instance matching
        if (centerNode instanceof VBox) {
            VBox vbox = (VBox) centerNode;
            // Locate the TableView within the VBox
            for (Node node : vbox.getChildren()) {
                if (node instanceof TableView<?>) {
                    TableView<Material> materialTable = (TableView<Material>) node;
                    materialTable.getItems().clear();

                    List<Material> materials = materialService.getAllMaterials(userId);
                    materialListingTab.updateMaterialsList(materials); // Update allMaterials in MaterialListingTab
                    materialTable.setItems(FXCollections.observableArrayList(materials));
                    return; // Refresh complete, exit the method
                }
            }

            System.err.println("Error: TableView not found within VBox.");
        } else {
            System.err.println("Error: Center node is not a VBox. Center node type: ");
        }
    }
}
