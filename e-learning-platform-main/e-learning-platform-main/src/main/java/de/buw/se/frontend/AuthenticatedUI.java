package de.buw.se.frontend;

import java.sql.SQLException;

import de.buw.se.ELearningPlatform;
import de.buw.se.backend.context.SessionContext;
import de.buw.se.backend.model.User;
import de.buw.se.backend.service.MaterialService;
import de.buw.se.backend.service.UserService;
import de.buw.se.frontend.tabs.EditProfileTab;
import de.buw.se.frontend.tabs.FileUploadTab;
import de.buw.se.frontend.tabs.LogoutTab;
import de.buw.se.frontend.tabs.MaterialListingTab;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class AuthenticatedUI {

    private final ELearningPlatform app;
    private final MaterialService materialService;
    private final UserService userService;
    private final int userId;
    private final User currentUser;
    private TabPane tabPane;

    public AuthenticatedUI(ELearningPlatform app, String username) throws SQLException,ClassNotFoundException {
        this.app = app;
        this.materialService = new MaterialService();
        this.userService = new UserService();
        this.currentUser = SessionContext.getInstance().getCurrentUser();
        this.userId = currentUser.getId();
    }

    public TabPane getTabPane() {
        tabPane = new TabPane();

        BorderPane listingTabContent = new BorderPane();

        // Initialize MaterialListingTab first
        MaterialListingTab materialListingTab = new MaterialListingTab(materialService, userId, currentUser.getUsername(), null, tabPane);

        // Initialize FileUploadTab and set the reference to MaterialListingTab
        FileUploadTab fileUploadTab = new FileUploadTab(materialService, userId, currentUser, listingTabContent, tabPane);
        fileUploadTab.setMaterialListingTab(materialListingTab);

        // Set the reference to FileUploadTab in MaterialListingTab
        materialListingTab.setFileUploadTab(fileUploadTab);

        // FileUploadTab fileUploadTab = new FileUploadTab(materialService, userId, currentUser, listingTabContent, tabPane);
        // MaterialListingTab materialListingTab = new MaterialListingTab(materialService, userId, currentUser.getUsername(), fileUploadTab, tabPane);
        Tab listTab = new Tab("Home");
        listingTabContent.setCenter(materialListingTab.createMaterialListingPane().getCenter());
        listTab.setContent(listingTabContent);
        listTab.setClosable(false);
        tabPane.getTabs().add(listTab);

        Tab createTab = new Tab("Upload Material");
        createTab.setContent(fileUploadTab.createFileUploadPane());
        createTab.setClosable(false);
        tabPane.getTabs().add(createTab);

        // Create and add Edit Profile tab
        EditProfileTab editProfileTab = new EditProfileTab(currentUser, userService, app);
        Tab editTab = new Tab("Edit Profile");
        editTab.setContent(editProfileTab.createEditProfilePane());
        editTab.setClosable(false);
        tabPane.getTabs().add(editTab);

        LogoutTab logoutTab = new LogoutTab(app);
        Tab logoutTabInstance = new Tab("Logout");
        logoutTabInstance.setContent(logoutTab.createLogoutPane());
        logoutTabInstance.setClosable(false);
        tabPane.getTabs().add(logoutTabInstance);

        return tabPane;
    }
}
