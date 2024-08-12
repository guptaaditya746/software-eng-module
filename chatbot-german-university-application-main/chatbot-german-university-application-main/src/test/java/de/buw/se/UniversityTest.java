package de.buw.se;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniversityTest {

    private University university;

    @BeforeEach
    public void setUp() {
        university = new University("computer science", "master", "munich", "Technical University of Munich", "GRE Score: 325", "Minimum GPA: 3.2");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("computer science", university.getFieldOfInterest(), "Field of Interest should be 'computer science'");
        assertEquals("master", university.getProgram(), "Program should be 'master'");
        assertEquals("munich", university.getCity(), "City should be 'munich'");
        assertEquals("Technical University of Munich", university.getName(), "Name should be 'Technical University of Munich'");
        assertEquals("GRE Score: 325", university.getGmatorGRERequirement(), "GRE/GMAT Requirement should be 'GRE Score: 325'");
        assertEquals("Minimum GPA: 3.2", university.getcgpaRequirement(), "CGPA Requirement should be 'Minimum GPA: 3.2'");
    }

    @Test
    public void testToString() {
        String expectedString = "University{fieldOfInterest='computer science', program='master', city='munich', name='Technical University of Munich', GmatorGRERequirement ='GRE Score: 325', cgpaRequirement = 'Minimum GPA: 3.2'}";
        assertEquals(expectedString, university.toString(), "toString method should return the correct string representation");
    }

    @Test
    public void testSetFieldOfInterest() {
        university.setFieldOfInterest("biology");
        assertEquals("biology", university.getFieldOfInterest(), "Field of Interest should be 'biology'");
    }

    @Test
    public void testSetProgram() {
        university.setProgram("MBA");
        assertEquals("MBA", university.getProgram(), "Program should be 'MBA'");
    }

    @Test
    public void testSetCity() {
        university.setCity("Berlin");
        assertEquals("Berlin", university.getCity(), "City should be 'Berlin'");
    }

    @Test
    public void testSetName() {
        university.setName("Humboldt University of Berlin");
        assertEquals("Humboldt University of Berlin", university.getName(), "Name should be 'Humboldt University of Berlin'");
    }

    @Test
    public void testSetGmatorGRERequirement() {
        university.setGmatorGRERequirement("GMAT Score: 700");
        assertEquals("GMAT Score: 700", university.getGmatorGRERequirement(), "GRE/GMAT Requirement should be 'GMAT Score: 700'");
    }

    @Test
    public void testSetCgpaRequirement() {
        university.setCgpaRequirement("Minimum GPA: 3.5");
        assertEquals("Minimum GPA: 3.5", university.getcgpaRequirement(), "CGPA Requirement should be 'Minimum GPA: 3.5'");
    }

    // Edge case tests
    @Test
    public void testSetFieldOfInterestEmptyString() {
        university.setFieldOfInterest("");
        assertEquals("", university.getFieldOfInterest(), "Field of Interest should be an empty string");
    }

    @Test
    public void testSetFieldOfInterestNull() {
        university.setFieldOfInterest(null);
        assertNull(university.getFieldOfInterest(), "Field of Interest should be null");
    }

    @Test
    public void testSetProgramEmptyString() {
        university.setProgram("");
        assertEquals("", university.getProgram(), "Program should be an empty string");
    }

    @Test
    public void testSetProgramNull() {
        university.setProgram(null);
        assertNull(university.getProgram(), "Program should be null");
    }

    @Test
    public void testSetCityEmptyString() {
        university.setCity("");
        assertEquals("", university.getCity(), "City should be an empty string");
    }

    @Test
    public void testSetCityNull() {
        university.setCity(null);
        assertNull(university.getCity(), "City should be null");
    }

    @Test
    public void testSetNameEmptyString() {
        university.setName("");
        assertEquals("", university.getName(), "Name should be an empty string");
    }

    @Test
    public void testSetNameNull() {
        university.setName(null);
        assertNull(university.getName(), "Name should be null");
    }

    @Test
    public void testSetGmatorGRERequirementEmptyString() {
        university.setGmatorGRERequirement("");
        assertEquals("", university.getGmatorGRERequirement(), "GRE/GMAT Requirement should be an empty string");
    }

    @Test
    public void testSetGmatorGRERequirementNull() {
        university.setGmatorGRERequirement(null);
        assertNull(university.getGmatorGRERequirement(), "GRE/GMAT Requirement should be null");
    }

    @Test
    public void testSetCgpaRequirementEmptyString() {
        university.setCgpaRequirement("");
        assertEquals("", university.getcgpaRequirement(), "CGPA Requirement should be an empty string");
    }

    @Test
    public void testSetCgpaRequirementNull() {
        university.setCgpaRequirement(null);
        assertNull(university.getcgpaRequirement(), "CGPA Requirement should be null");
    }

    @Test
    public void testSetFieldOfInterestCaseInsensitive() {
    university.setFieldOfInterest("Biology");
    assertEquals("biology", university.getFieldOfInterest(), "Field of Interest should be 'biology' regardless of case");
}

}