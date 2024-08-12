package de.buw.se.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;



import java.io.FileNotFoundException;
import java.sql.*;

public class ExportService {
    private int userID; 
  

    public ExportService(int userID) {
        this.userID = userID;
    }
 
    public void exportDataToPdf(String pdfFilePath) {
        String DB_URL = "jdbc:h2:./src/main/resources/eventPlannerDB";
        String user = "sa";
        String password = "";
         

        try (Connection conn = DriverManager.getConnection(DB_URL, user, password)) {
            String userQuery = "SELECT USERNAME FROM USERS WHERE ID = ?";

            try (PreparedStatement user_pstmt = conn.prepareStatement(userQuery)) {
                user_pstmt.setInt(1, userID);

                try (ResultSet user_rs = user_pstmt.executeQuery()) {
                    if (user_rs.next()) {
                        String userName = user_rs.getString("USERNAME");

                        String eventQuery = "SELECT * FROM EVENTS WHERE USERID = ?";

                        try (PreparedStatement event_pstmt = conn.prepareStatement(eventQuery)) {
                            event_pstmt.setInt(1, userID);

                            try (ResultSet event_rs = event_pstmt.executeQuery()) {
                                try (PdfWriter writer = new PdfWriter(pdfFilePath);
                                    PdfDocument pdfDoc = new PdfDocument(writer);
                                    Document document = new Document(pdfDoc)) {

                                    document.add(new Paragraph("Events for " + userName));

                                    Table table = new Table(5);
                                    table.addCell("EVENTID");
                                    table.addCell("EVENTNAME");
                                    table.addCell("EVENTDATE");
                                    table.addCell("EVENTTIME");
                                    table.addCell("DESCRIPTION");

                                    while (event_rs.next()) {
                                        table.addCell(event_rs.getString("EVENTID"));
                                        table.addCell(event_rs.getString("EVENTNAME"));
                                        table.addCell(event_rs.getString("EVENTDATE"));
                                        table.addCell(event_rs.getString("EVENTTIME"));
                                        table.addCell(event_rs.getString("DESCRIPTION"));
                                    }

                                    document.add(table);

                                }
                            }
                        }
                    } else {
                        System.err.println("No user found for ID: " + userID);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("PDF file not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}