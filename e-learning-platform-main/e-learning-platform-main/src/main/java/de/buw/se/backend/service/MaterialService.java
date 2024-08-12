package de.buw.se.backend.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import de.buw.se.backend.db.MaterialDao;
import de.buw.se.backend.model.Material;

public class MaterialService {

    private final MaterialDao materialDao;

    public MaterialService() throws SQLException, ClassNotFoundException {
        this.materialDao = new MaterialDao();
    }

    public boolean addMaterial(int userId, String authorName, Timestamp uploadedDate, byte[] file, String fileName) throws SQLException, ClassNotFoundException {
        if (file.length > 500 * 1024) {
            System.out.println("File length is large.");
            return false; // File size exceeds 500KB
        }
        try {
            Material material = new Material(0, userId, authorName, uploadedDate, file, fileName);
            materialDao.addMaterial(material);
            return true;
        } catch (SQLException e) {
            System.out.println("Add material SQL exception");
            return false;
        }
    }

    public List<Material> getAllMaterials(int userId) throws SQLException, ClassNotFoundException{
        try {
            return materialDao.getAllMaterials(userId);
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Material> getMaterialsByUserId(int userId) throws SQLException, ClassNotFoundException{
        try {
            return materialDao.getMaterialsByUserId(userId);
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean updateMaterial(int id, int userId, String authorName, Timestamp uploadedDate, byte[] file, String fileName) throws SQLException, ClassNotFoundException {
        Material material = new Material(id, userId, authorName, uploadedDate, file, fileName);
        try {
            return materialDao.updateMaterial(material);
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteMaterial(int materialId) throws SQLException, ClassNotFoundException{
        return materialDao.deleterMaterial(materialId);
    }
}
