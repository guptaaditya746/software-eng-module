package de.buw.se.backend.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.buw.se.backend.connection.DatabaseManager;
import de.buw.se.backend.model.Material;

public class MaterialDao {

    private final Connection connection;
    private  static final String driver = "org.h2.Driver";

    public MaterialDao() throws SQLException, ClassNotFoundException {
        this.connection = DatabaseManager.getConnection(driver);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet tables = metadata.getTables(null, null, "MATERIALS", null);
        if (!tables.next()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE MATERIALS ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "userId INT NOT NULL, "
                    + "authorName VARCHAR(255) NOT NULL, "
                    + "uploadedDate TIMESTAMP NOT NULL, "
                    + "file LONGBLOB NOT NULL, "
                    + "fileName VARCHAR(255) NOT NULL)");
            }
        }
    }

    public void addMaterial(Material material) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Materials (userId, authorName, uploadedDate, file, fileName) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection(driver); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, material.getUserId());
            pstmt.setString(2, material.getAuthorName());
            pstmt.setTimestamp(3, material.getUploadedDate());
            pstmt.setBytes(4, material.getFile());
            pstmt.setString(5, material.getFileName());
            pstmt.executeUpdate();
            System.out.println("Add Material called");
        }
    }

    public List<Material> getMaterialsByUserId(int userId) throws SQLException, ClassNotFoundException {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM Materials WHERE userId = ?";
        try (Connection conn = DatabaseManager.getConnection(driver); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Material material = new Material(
                    rs.getInt("id"),
                    rs.getInt("userId"),
                    rs.getString("authorName"),
                    rs.getTimestamp("uploadedDate"),
                    rs.getBytes("file"),
                    rs.getString("fileName")
                );
                materials.add(material);
            }
        }
        return materials;
    }

    public List<Material> getAllMaterials(int userId) throws SQLException, ClassNotFoundException {
        // Check if the user ID is valid
        if (!isUserIdValid(userId)) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        // Fetch all materials
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM Materials";
        try (Connection conn = DatabaseManager.getConnection(driver); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Material material = new Material(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("authorName"),
                        rs.getTimestamp("uploadedDate"),
                        rs.getBytes("file"),
                        rs.getString("fileName")
                );
                materials.add(material);
            }
        }
        return materials;
    }

    /*
     * public boolean updateEvent(int id, String authorName, Timestamp uploadedDate,
     *    bytes[] file, String fileName) {
     *      Logic to update an event in the database
     *  }
     */
    public boolean updateMaterial(Material material) throws SQLException, ClassNotFoundException {
        String selectSql = "SELECT file FROM Materials WHERE id = ?";
        String updateSql = "UPDATE Materials SET userId = ?, authorName = ?, uploadedDate = ?, file = ?, fileName = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection(driver); PreparedStatement selectPstmt = conn.prepareStatement(selectSql); PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {

            // Retrieve the existing file from the database
            selectPstmt.setInt(1, material.getId());
            ResultSet rs = selectPstmt.executeQuery();
            if (rs.next()) {
                byte[] existingFile = rs.getBytes("file");

                // Compare the existing file with the new file
                if (java.util.Arrays.equals(existingFile, material.getFile())) {
                    // If the files are the same, skip the update
                    return false;
                }
            }

            // Update the material
            updatePstmt.setInt(1, material.getUserId());
            updatePstmt.setString(2, material.getAuthorName());
            updatePstmt.setTimestamp(3, material.getUploadedDate());
            updatePstmt.setBytes(4, material.getFile());
            updatePstmt.setString(5, material.getFileName());
            updatePstmt.setInt(6, material.getId());

            int affectedRows = updatePstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean deleterMaterial(int materialId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Materials WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection(driver); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, materialId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // Validation methods.....
    private boolean isUserIdValid(int userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM Users WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection(driver); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }
}
