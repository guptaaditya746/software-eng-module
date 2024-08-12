package de.buw.se.frontend.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.buw.se.backend.model.Material;
import javafx.stage.FileChooser;

public class DownloadUtils {

    public static void downloadMaterial(Material material) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(material.getFileName());
        File fileToSave = fileChooser.showSaveDialog(null);

        if (fileToSave != null) {
            try (var fos = new FileOutputStream(fileToSave)) {
                fos.write(material.getFile());
                System.out.println("Downloaded material: " + material.getFileName());
                NotificationHelper.showNotification(material.getFileName() + " Downloaded Successfully!!");
            } catch (IOException e) {
                System.err.println("Error downloading material: " + material.getFileName());
            }
        }
    }
}
