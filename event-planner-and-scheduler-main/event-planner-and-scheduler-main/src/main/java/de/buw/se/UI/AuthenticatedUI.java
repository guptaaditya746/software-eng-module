package de.buw.se.UI;

import de.buw.se.EventManagementSystem;
import de.buw.se.UI.utils.NotificationHelper;
import de.buw.se.context.SessionContext;
import de.buw.se.model.Event;
import de.buw.se.model.User;
import de.buw.se.service.EventService;
import de.buw.se.service.ExportService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public class AuthenticatedUI { 
    private EventManagementSystem app; 
    EventService eventService; 
    private int userId;
    private User currentUser;
    private TabPane tabPane;

    public AuthenticatedUI(EventManagementSystem app, String username) throws SQLException {
        this.app = app;
        this.eventService = new EventService();
        this.currentUser = SessionContext.getInstance().getCurrentUser();
        
        this.userId = currentUser.getId();
    }

    public TabPane getTabPane() {
        tabPane = new TabPane();

        // Listing Tab
        Tab listTab = new Tab("Events");
        listTab.setContent(createListingPane());
        listTab.setClosable(false);
        tabPane.getTabs().add(listTab);

        // Creation Tab
        Tab createTab = new Tab("Create Event");
        createTab.setContent(createCreationPane());
        createTab.setClosable(false);
        tabPane.getTabs().add(createTab);

        //Export Tab
        Tab exportTab = new Tab("Export Data");
        VBox exportLayout = new VBox(20);
        exportLayout.setAlignment(Pos.CENTER);
        Button exportButton = new Button("Export to Pdf");
        int userID = SessionContext.getInstance().getCurrentUser().getId();
        ExportService exportService = new ExportService(userID);
        String pdfFilePath = "src/main/resources/Pdf export/Exported_event_data_"+currentUser.getUsername()+".pdf";
        exportButton.setOnAction(event -> exportService.exportDataToPdf(pdfFilePath));
        exportLayout.getChildren().add(exportButton);
        exportTab.setContent(exportLayout);
        exportTab.setClosable(false);
        tabPane.getTabs().add(exportTab);

        // Logout Tab
        Tab logoutTab = new Tab("Logout");
        VBox logoutLayout = new VBox(20);
        logoutLayout.setAlignment(Pos.CENTER);
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> app.showSignInUI());
        logoutLayout.getChildren().add(logoutButton);
        logoutTab.setContent(logoutLayout);
        logoutTab.setClosable(false);
        tabPane.getTabs().add(logoutTab);

        return tabPane;
    }

    protected List<Event> getAllEventsForUser() {
        if (userId != -1) {
            try {
                return eventService.getAllEventsForUser(userId);
            } catch (Exception e) {
                // Log the error
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    private void showEditEventDialog(Event event) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle("Edit Event");

        GridPane editEventGrid = new GridPane();
        editEventGrid.setPadding(new Insets(20));
        editEventGrid.setVgap(10);
        editEventGrid.setHgap(10);

        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField(event.getEventName());

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker(event.getEventDate().toLocalDateTime().toLocalDate());

        Label timeLabel = new Label("Time:");
        TextField timeField = new TextField(event.getEventTime());
        Label timeFormatNote = new Label("(Time format: HH:mm, i.e. 23:59, 00:01)");
        timeFormatNote.setFont(Font.font(10));
        timeFormatNote.setTextFill(Color.GRAY);

        Label descriptionLabel = new Label("Description:");
        TextArea descriptionField = new TextArea(event.getDescription());
        descriptionField.setWrapText(true);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String eventName = titleField.getText();
            LocalDate eventDate = datePicker.getValue();
            String eventTime = timeField.getText();
            String description = descriptionField.getText();

            // Perform validation
            if (eventName.isEmpty()) {
                showAlert("Title is required.", "Error");
            } else if (eventDate == null) {
                showAlert("Date is required.", "Error");
            } else if (eventDate.isBefore(LocalDate.now())) {
                showAlert("Date cannot be before today.", "Error");
            } else if (eventTime.isEmpty()) {
                showAlert("Time is required.", "Error");
            } else if (!isValidTime(eventTime)) {
                showAlert("Invalid time format.", "Error");
            } else {
                LocalDateTime dateTime = eventDate.atStartOfDay();
                Timestamp timestamp = Timestamp.valueOf(dateTime);

                boolean isSuccess = eventService.updateEvent(event.getEventId(), eventName, timestamp, eventTime, description);
                if (isSuccess) {
                    System.out.println("Event Updated Successfully!!");
                    refreshEventListing();
                    dialogStage.close();
                } else {
                    System.out.println("Error in updating event!!");
                }
            }
        });

        editEventGrid.add(titleLabel, 0, 0);
        editEventGrid.add(titleField, 1, 0);
        editEventGrid.add(dateLabel, 0, 1);
        editEventGrid.add(datePicker, 1, 1);
        editEventGrid.add(timeLabel, 0, 2);
        editEventGrid.add(timeField, 1, 2);
        editEventGrid.add(timeFormatNote, 1, 3);
        editEventGrid.add(descriptionLabel, 0, 4);
        editEventGrid.add(descriptionField, 1, 4);
        editEventGrid.add(saveButton, 1, 5);

        Scene dialogScene = new Scene(editEventGrid);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }
    
    protected ObservableValue<LocalDate> convertTimestampToLocalDate(SimpleObjectProperty<Timestamp> timestampProperty) {
        return new SimpleObjectProperty<>(timestampProperty.getValue().toLocalDateTime().toLocalDate());
    }

    protected BorderPane createListingPane() {
        BorderPane borderPane = new BorderPane();

        // Create TableView for listing events
        TableView<Event> eventTable = new TableView<>();
        eventTable.setEditable(true);

        // Title column
        TableColumn<Event, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().eventNameProperty());
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setMinWidth(150);

        // Date column
        TableColumn<Event, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn
                .setCellValueFactory(cellData -> convertTimestampToLocalDate(cellData.getValue().eventDateProperty()));
        dateColumn.setMinWidth(100);

        // Time column
        TableColumn<Event, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().eventTimeProperty());
        timeColumn.setMinWidth(100);

        // Description column
        TableColumn<Event, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        descriptionColumn.setMinWidth(200);

        // Edit and Delete buttons column
        TableColumn<Event, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(param -> new TableCell<Event, Void>() {
            protected final Button editButton = new Button("Edit");
            protected final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    // @SuppressWarnings("unused")
                    Event selectedEvent = getTableView().getItems().get(getIndex());
                    showEditEventDialog(selectedEvent);
                });

                deleteButton.setOnAction(event -> {
                    Event selectedEvent = getTableView().getItems().get(getIndex());
                    if (selectedEvent != null) {
                        deleteEvent(selectedEvent.getEventId());
                    } else {
                        System.out.println("Error: No event selected.");
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, editButton, deleteButton));
                }
            }
        });
        @SuppressWarnings("unchecked")
        TableColumn<Event, ?>[] columns = new TableColumn[] { titleColumn, dateColumn, timeColumn, descriptionColumn,
                actionColumn };
        eventTable.getColumns().addAll(columns);

        // Populate event list
        List<Event> events = getAllEventsForUser();
        eventTable.setItems(FXCollections.observableArrayList(events));

        borderPane.setCenter(eventTable);
        return borderPane;
    }

    protected void deleteEvent(int eventId) {
        boolean isDeleted = eventService.deleteEvent(eventId);
        if (isDeleted) {
            System.out.println("Event Deleted Successfully!!");
            // Refresh the event listing
            refreshEventListing();
            // Show success notification
            String successMessage = "Event Deleted Successfully!!";
            NotificationHelper.showNotification(successMessage);
        } else {
            System.out.println("Error in deleting event!!");
        }
    }

    protected BorderPane createCreationPane() {
        BorderPane borderPane = new BorderPane();

        // Add Events Tab
        GridPane addEventGrid = new GridPane();
        addEventGrid.setPadding(new Insets(20));
        addEventGrid.setVgap(10);
        addEventGrid.setHgap(10);

        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();
        titleField.setPromptText("Enter title");

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setMaxWidth(Double.MAX_VALUE);

        Label timeLabel = new Label("Time:");
        TextField timeField = new TextField();
        timeField.setPromptText("Enter time");
        Label timeFormatNote = new Label("(Time format: HH:mm, i.e. 23:59, 00:01)");
        timeFormatNote.setFont(Font.font(10));
        timeFormatNote.setTextFill(Color.GRAY);

        Label descriptionLabel = new Label("Description:");
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Enter description");
        descriptionField.setWrapText(true);
        descriptionField.setMaxWidth(Double.MAX_VALUE);
        descriptionField.setPrefRowCount(3);

        Button addEventButton = new Button("Add Event");
        addEventButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        addEventGrid.add(titleLabel, 0, 0);
        addEventGrid.add(titleField, 1, 0);
        addEventGrid.add(dateLabel, 0, 1);
        addEventGrid.add(datePicker, 1, 1);
        addEventGrid.add(timeLabel, 0, 2);
        addEventGrid.add(timeField, 1, 2);
        addEventGrid.add(timeFormatNote, 1, 3);
        addEventGrid.add(descriptionLabel, 0, 4);
        addEventGrid.add(descriptionField, 1, 4);
        addEventGrid.add(addEventButton, 1, 5);

        borderPane.setCenter(addEventGrid);

        // Button Actions
        addEventButton.setOnAction(event -> {
            String eventName = titleField.getText();
            LocalDate eventDate = datePicker.getValue();
            String eventTime = timeField.getText();
            String description = descriptionField.getText();

            // Perform validation
            if (eventName.isEmpty()) {
                showAlert("Title is required.", "Error");
            } else if (eventDate == null) {
                showAlert("Date is required.", "Error");
            } else if (eventDate.isBefore(LocalDate.now())) {
                showAlert("Date cannot be before today.", "Error");
            } else if (eventTime.isEmpty()) {
                showAlert("Time is required.", "Error");
            } else if (!isValidTime(eventTime)) {
                showAlert("Invalid time format.", "Error");
            } else {
                LocalDateTime dateTime = eventDate.atStartOfDay();
                Timestamp timestamp = Timestamp.valueOf(dateTime);

                boolean isSuccess = eventService.addEvent(userId, eventName, timestamp,
                        eventTime, description);
                if (isSuccess) {
                    System.out.println("Event Created Successful!!");
                    clearFields(titleField, descriptionField, timeField);
                    // Refresh the event listing
                    refreshEventListing();

                    // Show success notification
                    String successMessage = "Event Created Successful!!";
                    NotificationHelper.showNotification(successMessage);

                    // Refreshing the event listing and navigating after 2 seconds
                    Timeline timeline = new Timeline(new KeyFrame(
                            Duration.seconds(2),
                            e -> {
                                refreshEventListing();
                                tabPane.getSelectionModel().select(0);
                            }));
                    timeline.play();

                    tabPane.getSelectionModel().select(0);
                } else {
                    System.out.println("Error in event creation!!");
                }
            }
        });

        return borderPane;
    }

    protected void refreshEventListing() {
        // Get the center node of the listing tab
        Node centerNode = ((BorderPane) ((Tab) tabPane.getTabs().get(0)).getContent()).getCenter();

        // Check if the center node is a TableView
        if (centerNode instanceof TableView<?>) {
            @SuppressWarnings("unchecked")
            TableView<Event> eventTable = (TableView<Event>) centerNode;

            // Clear the current event listing
            eventTable.getItems().clear();

            // Populate the event list again
            List<Event> events = getAllEventsForUser();
            eventTable.setItems(FXCollections.observableArrayList(events));
        } else {
            System.err.println("Error: Center node is not a TableView");
        }
    }

    protected boolean isValidTime(String time) {
        try {
            LocalTime.parse(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void clearFields(TextField titleField, TextArea descriptionField, TextField timeField) {
        titleField.clear();
        descriptionField.clear();
        timeField.clear();
    }

    protected void showAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	
}