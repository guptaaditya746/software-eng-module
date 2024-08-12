package de.buw.se;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CSVDataReaderTest {
    @Test
    public void testCSVDataReader() {

        //Testing with a CSV File
        String filePath1 = "src/test/resources/empty.csv";
        CSVDataReader reader = new CSVDataReader(filePath1);
        //Testing if the file paths only reads CSV Files
        assertTrue(reader.filePath.endsWith(".csv"), "File path should end with '.csv'");

        //Testing with a Non-CSV file
        String filePath2 = "src/test/resources/empty.mv.db";
        //Testing if the file paths only reads CSV Files
        assertThrows(IllegalArgumentException.class, () -> {

            new CSVDataReader(filePath2);
        }, "Creating CSVDataReader with a non-CSV file path should throw IllegalArgumentException");

    }
    // This test fails because the program converts the input to lowercase to compare with the database
    // Accepting the input regardless of the case improves the quality of the software.
    @Test
    public void testReadCSVData() throws IOException {
        //use two file paths
        String filePath1 = "src/test/resources/universities.csv";
        String filePath2 = "src/test/resources/book.csv";

        //Create two objects of the reader file and perform test operations on them
        CSVDataReader reader1 = new CSVDataReader(filePath1);
        CSVDataReader reader2 = new CSVDataReader(filePath2);

        //Call the readCSVData Method and perform test
        List<University> universities = reader1.readCSVData();
        assertNotNull(universities);
        assertTrue(!universities.isEmpty());

        // Verify specific attributes of the first University object
        University firstUniversity = universities.get(1);
        assertEquals("Computer Science", firstUniversity.getFieldOfInterest());
        assertEquals("MSc", firstUniversity.getProgram());
        assertEquals("Berlin", firstUniversity.getCity());
        assertEquals("Technical University of Berlin", firstUniversity.getName());
        assertEquals("GRE Score: 320", firstUniversity.getGmatorGRERequirement());
        assertEquals("Minimum GPA: 3.0", firstUniversity.getcgpaRequirement());

        //Checking to see if test throw an argument exception if CSV files does not contain reuired fields
        assertThrows(IllegalArgumentException.class, () -> {

            reader2.readCSVData();
        });

    }

    @Test
    public void testReadEmptyCSVFile() throws IOException {
        // Define a file path to an empty CSV file
        String filePath = "src/test/resources/empty.csv";

        // Create a CSVDataReader instance
        CSVDataReader reader = new CSVDataReader(filePath);

        // Call readCSVData() method
        List<University> universities = reader.readCSVData();

        // Assert that the list of universities is not null and empty
        assertNotNull(universities);
        assertTrue(universities.isEmpty(), "List of universities should be empty for empty CSV file");
    }
}
