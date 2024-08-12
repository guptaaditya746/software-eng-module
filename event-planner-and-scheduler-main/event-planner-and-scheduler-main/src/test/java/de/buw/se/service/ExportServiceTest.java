package de.buw.se.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import org.junit.jupiter.api.*;

class ExportServiceTest {

    private ExportService exportService;
    private int testUserId = 1; // Assuming test user ID for testing
    private String testPdfFilePath = "./src/test/resources/test-export.pdf"; // Adjust as needed

    @BeforeEach
    void setUp() {
        exportService = new ExportService(testUserId);
    }
 
    @AfterEach
    void tearDown() {
        // Clean up any generated PDF files after each test
        deleteTestPdfFile();
    }

    private void deleteTestPdfFile() {
        File pdfFile = new File(testPdfFilePath);
        
        if (pdfFile.exists()) {
            pdfFile.delete();
        }
    }

    @Test
    void testExportDataToPdf() {
        // Assuming the database contains relevant data for the testUserId

        // Generate a unique file name for the test PDF
        String uniqueId = UUID.randomUUID().toString();
        String pdfFilePath = "./src/test/resources/test-export-" + uniqueId + ".pdf";

        // Perform the export operation
        exportService.exportDataToPdf(pdfFilePath);

        // Verify if the PDF file was created
        File pdfFile = new File(pdfFilePath);
        assertTrue(!pdfFile.exists(), "PDF file was not generated");

        // Additional assertions can be added to verify content or structure of the PDF if needed
    }
}