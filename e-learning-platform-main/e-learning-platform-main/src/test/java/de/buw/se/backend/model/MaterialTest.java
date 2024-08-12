package de.buw.se.backend.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
// import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialTest {

    private Material material;
    private int id;
    private int userId;
    private String authorName;
    private Timestamp uploadedDate;
    private byte[] file;
    private String fileName;

    @BeforeEach
    public void setUp() {
        id = 1;
        userId = 123;
        authorName = "John Doe";
        uploadedDate = new Timestamp(System.currentTimeMillis());
        file = new byte[]{1, 2, 3, 4, 5};
        fileName = "document.pdf";
        material = new Material(id, userId, authorName, uploadedDate, file, fileName);
    }

    @Test
    public void testConstructor() {
        assertNotNull(material);
    }

    @Test
    public void testGetId() {
        assertEquals(id, material.getId());
    }

    @Test
    public void testGetUserId() {
        assertEquals(userId, material.getUserId());
    }

    @Test
    public void testGetAuthorName() {
        assertEquals(authorName, material.getAuthorName());
    }

    @Test
    public void testGetUploadedDate() {
        assertEquals(uploadedDate, material.getUploadedDate());
    }

    @Test
    public void testGetFile() {
        assertArrayEquals(file, material.getFile());
    }

    @Test
    public void testGetFileName() {
        assertEquals(fileName, material.getFileName());
    }

    @Test
    public void testToString() {
        String expected = "Material{"
                + "id=" + id
                + ", userId=" + userId
                + ", authorName='" + authorName + '\''
                + ", uploadedDate=" + uploadedDate
                + ", fileName='" + fileName + '\''
                + '}';
        assertEquals(expected, material.toString());
    }
}
