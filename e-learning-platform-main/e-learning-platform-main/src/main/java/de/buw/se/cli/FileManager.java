package de.buw.se.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
    private static final String UPLOAD_FOLDER = "uploads/";

    public void selectOption(Scanner scanner, String UPLOAD_FOLDER) {
        boolean running = true;

        while (running) {
            System.out.println("................................");
            System.out.println("Select an option:");
            System.out.println("1. Upload a file");
            System.out.println("2. Download a file");
            System.out.println("3. Delete a file");
            System.out.println("4. Logout");
            System.out.println("................................");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    uploadFile(scanner, UPLOAD_FOLDER);
                    break;
                case 2:
                    downloadFile(scanner, UPLOAD_FOLDER);
                    break;
                case 3:
                    deleteFile(scanner);
                    break;
                case 4:
                    // consume newline character
                    System.out.println(".......logged out.......");
                    scanner.nextLine();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void uploadFile(Scanner scanner, String UPLOAD_FOLDER) {
        // get file owner name
        System.out.println("Please enter file owner name");
        // consume newline character
        scanner.nextLine();
        String fileOwner = scanner.nextLine().toLowerCase();
        //get file title
        System.out.println("Please enter file title");
        String title = scanner.nextLine().toLowerCase();
        // get file category
        System.out.println("Please enter file category");
        String category = scanner.nextLine().toLowerCase();

        // get filepath
        System.out.println("Enter the path of the file to upload: e,g C:/downloads/file.pdf");
        String filePath = scanner.nextLine();

        File file = new File(filePath);
        // get name of uploaded file
        String uploadFilename = file.getName();
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(UPLOAD_FOLDER + uploadFilename)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            DataStoreCsv.addFile(fileOwner, title, category, uploadFilename);
            System.out.println("File uploaded successfully.");
        } catch (IOException e) {
            System.out.println("Error uploading file: " + e.getMessage());
        }
    }

    private void downloadFile(Scanner scanner, String UPLOAD_FOLDER) {
        System.out.println("List of available files in DB");
        System.out.println(DataStoreCsv.listFiles());

        System.out.println(".............................");
        System.out.println("Enter the name of the file to download:");
        String fileName = scanner.next();

        File file = new File(UPLOAD_FOLDER + fileName);
        if (!file.exists()) {
            System.out.println("File does not exist.");
           ;
        }
        else {

        String absolutePath = file.getAbsolutePath();
            
        System.out.println(absolutePath);
        System.out.println("Copy and Paste the above address to access the file");
        }
    }
    private void deleteFile(Scanner scanner) {
        System.out.println("List of available files in DB");
        System.out.println(DataStoreCsv.listFiles());

        System.out.println(".............................");
        System.out.println("Enter the name of the file to delete:");
        String fileName = scanner.next();

        boolean deleted = DataStoreCsv.deleteFile(fileName);
       if (deleted) {
            File fileToDelete = new File(UPLOAD_FOLDER + fileName);
            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    System.out.println("File deleted successfully.");
                } else {
                    System.out.println("Unable to delete the file from the uploads folder.");
                }
            } else {
                System.out.println("File not found in the uploads folder.");
            }
        } else {
            System.out.println("File not found or deletion failed.");
        }
    }


}