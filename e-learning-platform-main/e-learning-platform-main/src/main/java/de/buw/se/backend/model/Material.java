package de.buw.se.backend.model;

import java.sql.Timestamp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Material {

    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty userId;
    private final SimpleStringProperty authorName;
    private final SimpleObjectProperty<Timestamp> uploadedDate;
    private final SimpleStringProperty fileName;
    private final byte[] file;

    // Constructor
    public Material(int id, int userId, String authorName, Timestamp uploadedDate, byte[] file, String fileName) {
        this.id = new SimpleIntegerProperty(id);
        this.userId = new SimpleIntegerProperty(userId);
        this.authorName = new SimpleStringProperty(authorName);
        this.uploadedDate = new SimpleObjectProperty<>(uploadedDate);
        this.fileName = new SimpleStringProperty(fileName);
        this.file = file;
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public int getUserId() {
        return userId.get();
    }

    public String getAuthorName() {
        return authorName.get();
    }

    public Timestamp getUploadedDate() {
        return uploadedDate.get();
    }

    public byte[] getFile() {
        return file;
    }

    public String getFileName() {
        return fileName.get();
    }

    // JavaFX properties
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public SimpleStringProperty authorNameProperty() {
        return authorName;
    }

    public SimpleObjectProperty<Timestamp> uploadedDateProperty() {
        return uploadedDate;
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    // Override toString for readable output
    @Override
    public String toString() {
        return "Material{"
                + "id=" + id.get()
                + ", userId=" + userId.get()
                + ", authorName='" + authorName.get() + '\''
                + ", uploadedDate=" + uploadedDate.get()
                + ", fileName='" + fileName.get() + '\''
                // + +", fileSize=" + (file != null ? file.length : 0) + " bytes"
                // + ", fileSize=" + (file != null ? file.length : 0) + " bytes"
                + '}';
    }
}
