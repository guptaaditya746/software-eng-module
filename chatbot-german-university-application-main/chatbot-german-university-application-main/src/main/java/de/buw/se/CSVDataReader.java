package de.buw.se;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVDataReader {

    // Logger instance for logging messages related to CSVDataReader operations
    protected static final Logger logger = Logger.getLogger(CSVDataReader.class.getName());

    protected String filePath;

    public CSVDataReader(String filePath) {
        if (!filePath.endsWith(".csv")) {
            throw new IllegalArgumentException("File path must end with '.csv'");
        }
        this.filePath = filePath;
    }

    public List<University> readCSVData() throws IOException {
        List<University> universities = new ArrayList<>();
        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                String fieldOfInterest = csvRecord.get("Field");
                String program = csvRecord.get("Program");
                String city = csvRecord.get("City");
                String universityName = csvRecord.get("University");
                String GmatorGRERequirement = csvRecord.get("GRE/GMAT");
                String cgpaRequirement = csvRecord.get("Requirements");

                University university = new University(fieldOfInterest, program, city, universityName, GmatorGRERequirement, cgpaRequirement);
                universities.add(university);

                // Log each university record read
                logger.info("Read university: " + university.toString()); // Assuming University.toString() provides a suitable representation
            }

            // Log successful reading of universities
            logger.info("Successfully read " + universities.size() + " universities from CSV file: " + filePath);

        } catch (IOException e) {
            // Log severe error if IOException occurs during file reading
            logger.severe("Error reading CSV file: " + filePath);
            throw e;
        } catch (IllegalArgumentException e) {
            // Log severe error if CSV format is invalid (e.g., missing headers)
            logger.severe("Invalid CSV file: " + e.getMessage());
            throw e;
        }
        return universities;
    }
}
