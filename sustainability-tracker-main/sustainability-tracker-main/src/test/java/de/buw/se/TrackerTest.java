package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

// // ----------------------------Using Only JUnit 5 --------------------------------------

import java.awt.Dimension;
import javax.swing.JOptionPane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrackerTest {
    private Tracker tracker;

    private boolean isDialogVisible(String message) {
        JOptionPane pane = JOptionPane.getRootFrame() != null ? new JOptionPane() : null;
        if (pane != null) {
            JOptionPane.showMessageDialog(null, message);
            return true;
        } else {
            return false;
        }
    }

    @BeforeEach
    void setUp() {
        tracker = new Tracker();
        tracker.setSize(new Dimension(1200, 900));
        tracker.setVisible(true);
    }

    // BLACK BOX TESTING
    // -------------------------------------------------------------------------------------------------------------------------
    @Test
    void notAcceptEmptyUsernameAndPassword() {
        // Test empty username 
        tracker.usernameField.setText("");
        tracker.passwordField.setText("");
        tracker.signInButton.doClick();
        assertTrue(tracker.errorMessage.equals("Username and password cannot be empty."));
    }

    @Test
    void notAcceptEmptyUsername() {
        // Test empty username 
        tracker.usernameField.setText("");
        tracker.passwordField.setText("password");
        tracker.signInButton.doClick();
        assertTrue(tracker.errorMessage.equals("Username cannot be empty."));
        
    }

    @Test
    void notAcceptEmptyPassword() {
        // Test empty username 
        tracker.usernameField.setText("username");
        tracker.passwordField.setText("");
        tracker.signInButton.doClick();
        assertTrue(tracker.errorMessage.equals("Password cannot be empty."));

    }

    @Test
    void validUsernameAndPassword() {
        tracker.usernameField.setText("validUser");
        tracker.passwordField.setText("validPassword");
        tracker.signInButton.doClick();
        assertTrue(isDialogVisible("Account created and signed in successfully!"));
    }

    @Test
    void CreateAccountAndSignIn() {
        tracker.usernameField.setText("newUser");
        tracker.passwordField.setText("newPassword");
        tracker.signInButton.doClick();
        assertTrue(isDialogVisible("Account created and signed in successfully!"));
    }  

    @Test 
    void notNegativeNumbers() { // Check if the program don´t accept negative numbers (Ilogical)
        tracker = new Tracker();
        assertThrows(IllegalArgumentException.class, () -> {
            tracker.calculateTotal(-1, -1, -1, -1, -1, -1, -1, -1);
        });
    }
    

    @Test 
    void ceroConsumptionTest() { // Check if the program return no emision CO2 when puting 0 data
        tracker = new Tracker();
        assertEquals(0,tracker.calculateTotal(0,0,0,0,0,0,0,0));
    }
    
    @Test 
    void checkCalculationsResult() { // Check if the calculation is related with the asumptions in "Initial_Parameter_Implementation.pdf"
        // Create Manually expected values (all in gr./day of CO2)
        double CO2_electricity = 30 * 354.75;
        double CO2_HouseFuel = 8 * 2300; //They indicate Natual Gas in kg, But in table only in m3 (Data Internet)
        double CO2_Water = 340 * 395;
        double CO2_TransFuel = 5 * 2157;
        double CO2_Waste = 2 * 242.858;
        double CO2_PublicTrans = 10 * 32.77;
        double CO2_feet = 2 * -72.16;
        double CO2_Trees = 0 *-242.858; //Trees planted in 1 year

        double resultexpected = CO2_electricity + CO2_HouseFuel + CO2_PublicTrans + CO2_TransFuel + CO2_Water + CO2_Waste + CO2_feet + CO2_Trees;

        // Data Program
        tracker = new Tracker();
        double resultReal = tracker.calculateTotal(30,8,5,10,2,340,2,0);

        assertEquals(resultexpected, resultReal);
    } 

    @Test
    void checkAllFieldsAcceptOnlyNumbers() {
        // Check if all fields accept only numeric values
        tracker.variable1Field.setText("123");
        tracker.variable2Field.setText("456");
        tracker.variable3Field.setText("789");
        tracker.variable4Field.setText("1011");
        tracker.variable5Field.setText("1213");
        tracker.variable6Field.setText("1415");
        tracker.variable7Field.setText("1617");
        tracker.variable8Field.setText("1819");
        assertTrue(tracker.validateInputs()); // All valid numeric values
    }
    
    @Test 
    void notAcceptStringInData() { // Check if index goes acording with "Initial_Parameter_Implementation.pdf"
        tracker = new Tracker();
        tracker.variable1Field.setText("Hola");
        tracker.variable2Field.setText("Hola");
        tracker.variable3Field.setText("Hola");
        tracker.variable4Field.setText("Hola");
        tracker.variable5Field.setText("Hola");
        tracker.variable6Field.setText("Hola");
        tracker.variable7Field.setText("Hola");
        tracker.variable8Field.setText("Hola");

        boolean result = tracker.validateInputs();

        assertFalse(result);
    }

    @Test
    void checkPlantedTreesAcceptIntegers() {
        // Check if "Number of Trees Planted" field accept integers
        tracker.variable1Field.setText("0");
        tracker.variable2Field.setText("0");
        tracker.variable3Field.setText("0");
        tracker.variable4Field.setText("0");
        tracker.variable5Field.setText("0");
        tracker.variable6Field.setText("0");
        tracker.variable7Field.setText("0");
        tracker.variable8Field.setText("5"); // Valid integer
        boolean result = tracker.validateInputs();
        assertTrue(result);
    }

    @Test
    void checkPlantedTreesAcceptNotAcceptDecimals() {
        // Check if "Number of Trees Planted" field not accept decimals
        tracker.variable1Field.setText("0");
        tracker.variable2Field.setText("0");
        tracker.variable3Field.setText("0");
        tracker.variable4Field.setText("0");
        tracker.variable5Field.setText("0");
        tracker.variable6Field.setText("0");
        tracker.variable7Field.setText("0");
        tracker.variable8Field.setText("5.5");
        boolean result = tracker.validateInputs();
        assertFalse(result);
    }

    @Test 
    void regularConsumptionUS() { //Check if the program return high Index for US Citizen with high Consumptions (US EPA) 
        // Check if the program returns an index greater than one (reflecting above average consumption in the US)
        tracker = new Tracker();
        double result = tracker.calculateTotal(30,8,5,10,2,340,2,0);
        double index = result/14000;
        assertTrue(index >1);
        assertFalse(index <= 1);
    }
    
    @Test 
    void ecologicalConsumption() { //Check if the program return low Index for recomended Consumptions (OMS -> OSD) 
        // Check if the program returns an index lower than one
        tracker = new Tracker();
        double result = tracker.calculateTotal(20,5,2,20,1,100,5,80);
        double index = result/14000;
        assertFalse(index <= 1);
        assertTrue(index >1);
    }
    
    // WHITE BOX TESTING
    //-------------------------------------------------------------------------------------------------------------------------
    
    @Test 
    void checkElectricity_Calculation() { // Check individual calculation is related with the asumptions in "Initial_Parameter_Implementation.pdf"
        
        double CO2_electricity = 30 * 354.75;

        // Data Program
        tracker = new Tracker();
        double resultReal = tracker.calculateTotal(30,0,0,0,0,0,0,0);

        assertEquals(CO2_electricity, resultReal);
    } 
    
    @Test 
    void checkHouseFuel_Calculation() { // Check individual calculation is related with the asumptions in "Initial_Parameter_Implementation.pdf"
        
        double CO2_HouseFuel = 8 * 2300; //They indicate Natual Gas in kg, But in table only in m3 (Data Internet 2.3 kg -> 2300 gr)

        // Data Program
        tracker = new Tracker();
        double resultReal = tracker.calculateTotal(0,8,0,0,0,0,0,0);

        assertEquals(CO2_HouseFuel, resultReal);
    }
    
    @Test 
    void checkWater_Calculation() { // Check individual calculation is related with the asumptions in "Initial_Parameter_Implementation.pdf"
        
        double CO2_Water = 340 * 395;

        // Data Program
        tracker = new Tracker();
        double resultReal = tracker.calculateTotal(0,0,0,0,0,340,0,0);

        assertEquals(CO2_Water, resultReal);
    }
    
    @Test 
    void checkTranspPrivateFuel_Calculation() { // Check individual calculation is related with the asumptions in "Initial_Parameter_Implementation.pdf"
        
        double CO2_TransFuel = 5 * 2157;

        // Data Program
        tracker = new Tracker();
        double resultReal = tracker.calculateTotal(0,0,5,0,0,0,0,0);

        assertEquals(CO2_TransFuel, resultReal);
    } 

    @Test 
    void checkWaste_Calculation() { // Check individual calculation is related with the asumptions in "Initial_Parameter_Implementation.pdf"
        
        double CO2_Waste = 2 * 242.858;

        // Data Program
        tracker = new Tracker();
        double resultReal = tracker.calculateTotal(0,0,0,0,2,0,0,0);

        assertEquals(CO2_Waste, resultReal);
    } 

    @Test 
    void checkPublicTransp_Calculation() { // Check individual calculation is related with the asumptions in "Initial_Parameter_Implementation.pdf"
        
        double CO2_PublicTrans = 10 * 32.77;

        // Data Program
        tracker = new Tracker();
        double resultReal = tracker.calculateTotal(0,0,0,10,0,0,0,0);

        assertEquals(CO2_PublicTrans, resultReal);
    }

    @Test 
    void checkFeetTravel_Calculation() { // Check individual calculation is related with the asumptions in "Initial_Parameter_Implementation.pdf"
        
        double CO2_feet = 2 * -72.16;

        // Data Program
        tracker = new Tracker();
        double resultReal = tracker.calculateTotal(0,0,0,0,0,0,2,0);

        assertEquals(CO2_feet, resultReal);
    }

    @Test 
    void checkPlanteTrees_Calculation() { // Check individual calculation is related with the asumptions in "Initial_Parameter_Implementation.pdf"

        double CO2_Trees = 10 *-242.858; 

        // Data Program
        tracker = new Tracker();
        double resultReal = tracker.calculateTotal(0,0,0,0,0,0,0,10);

        assertEquals(CO2_Trees, resultReal);
    }
    @Test
    public void testGenerateRecommendations_negativeValue() {
        String recommendations = tracker.generateRecommendations(-100);
        assertTrue(recommendations.contains("Invalid index"));
        assertTrue(recommendations.contains("Invalid index"));
    }

    @Test
    public void testGenerateRecommendations_Outstanding() {
        String recommendations = tracker.generateRecommendations(4);
        assertTrue(recommendations.contains("Outstanding!"));
        assertTrue(recommendations.contains("Your sustainability index is excellent."));
    }

    @Test
    public void testGenerateRecommendations_Fantastic() {
        String recommendations = tracker.generateRecommendations(7.5);
        assertTrue(recommendations.contains("Fantastic!"));
        assertTrue(recommendations.contains("Your sustainability index is above average."));
    }

    @Test
    public void testGenerateRecommendations_GoodJob() {
        String recommendations = tracker.generateRecommendations(12.0);
        assertTrue(recommendations.contains("Good job!"));
        assertTrue(recommendations.contains("Your sustainability index is average."));
    }

    @Test
    public void testGenerateRecommendations_OnRightPath() {
        String recommendations = tracker.generateRecommendations(16.0);
        assertTrue(recommendations.contains("You're on the right path!"));
        assertTrue(recommendations.contains("Your sustainability index is low, but every small step counts."));
    }
    
    @Test
    void checkEmpty_Recomendation(){
        String resultManual = "<html><body><h2>Personalized Recommendations</h2><ul></ul></body></html>";
        
        // Data Program
        String resultProgram = tracker.generatePersonalizedRecommendations(0, 0, 0, 0, 0, 0, 5, 80);
        assertEquals(resultProgram, resultManual);
    }

    @Test
    void checkElectricity_Recomendation(){
        String resultManual = "<html><body><h2>Personalized Recommendations</h2><ul><li>Consider reducing your electricity consumption by using energy-efficient appliances and lighting.</li><li>Turn off appliances and lights when not in use to save electricity.</li><li>Consider installing solar panels to generate your own electricity.</li></ul></body></html>";
        
        // Data Program
        String resultProgram = tracker.generatePersonalizedRecommendations(30, 0, 0, 0, 0, 0, 5, 80);
        assertEquals(resultProgram, resultManual);
    }

    @Test
    void checkHouseFuel_Recomendation(){
        String resultManual = "<html><body><h2>Personalized Recommendations</h2><ul><li>Reduce household fuel usage by improving insulation and using more efficient heating systems.</li><li>Consider switching to renewable energy sources for heating, such as heat pumps or biomass.</li><li>Plan and combine errands to minimize driving and fuel consumption.</li></ul></body></html>";
        
        // Data Program
        String resultProgram = tracker.generatePersonalizedRecommendations(0, 8, 0, 0, 0, 0, 5, 80);
        assertEquals(resultProgram, resultManual);
    }

    @Test
    void checkWater_Recomendation(){
        String resultManual = "<html><body><h2>Personalized Recommendations</h2><ul><li>Reduce water consumption by fixing leaks and using water-saving fixtures.</li><li>Install a rainwater harvesting system to collect rainwater for outdoor use.</li><li>Take shorter showers and turn off the tap when brushing teeth or washing dishes.</li></ul></body></html>";
        
        // Data Program
        String resultProgram = tracker.generatePersonalizedRecommendations(0, 0, 0, 0, 0, 340, 5, 80);
        assertEquals(resultProgram, resultManual);
    }

    @Test
    void checkTranspPrivateFuel_Recomendation(){
        String resultManual = "<html><body><h2>Personalized Recommendations</h2><ul><li>Reduce private transport fuel consumption by using public transport, carpooling, or switching to electric vehicles.</li><li>Consider telecommuting or working from home to reduce commuting fuel usage.</li><li>Practice eco-driving techniques such as smooth acceleration and maintaining steady speeds.</li></ul></body></html>";
        
        // Data Program
        String resultProgram = tracker.generatePersonalizedRecommendations(0, 0, 10, 0, 0, 0, 5, 80);
        assertEquals(resultProgram, resultManual);
    }

    @Test
    void checkWaste_Recomendation(){
        String resultManual = "<html><body><h2>Personalized Recommendations</h2><ul><li>Implement waste reduction practices such as recycling and composting.</li><li>Reduce packaging waste by buying products with minimal packaging or in bulk.</li><li>Consider donating or selling items instead of throwing them away.</li></ul></body></html>";
        
        // Data Program
        String resultProgram = tracker.generatePersonalizedRecommendations(0, 0, 0, 0, 2, 0, 5, 80);
        assertEquals(resultProgram, resultManual);
    }

    @Test
    void checkPublicTransport_Recomendation(){
        String resultManual = "<html><body><h2>Personalized Recommendations</h2><ul><li>Try to combine trips to reduce the distance traveled by rail.</li><li>Consider purchasing tickets in advance to take advantage of discounts and promotions.</li><li>Explore options for alternative modes of transportation such as biking or walking for short distances.</li></ul></body></html>";
        
        // Data Program
        String resultProgram = tracker.generatePersonalizedRecommendations(0, 0, 0, 25, 0, 0, 5, 80);
        assertEquals(resultProgram, resultManual);
    }

    @Test
    void FeetTravel_Recomendation(){
        String resultManual = "<html><body><h2>Personalized Recommendations</h2><ul><li>Increase your physical activity by walking or cycling more often.</li><li>Take the stairs instead of the elevator whenever possible to incorporate more physical activity into your daily routine.</li><li>Join a local walking or hiking group to make exercising more enjoyable and social.</li></ul></body></html>";
        
        // Data Program
        String resultProgram = tracker.generatePersonalizedRecommendations(0, 0, 0, 0, 0, 0, 2, 80);
        assertEquals(resultProgram, resultManual);
    }

    @Test
    void PlantedTree_Recomendation(){
        String resultManual = "<html><body><h2>Personalized Recommendations</h2><ul><li>Plant more trees to help offset carbon emissions.</li><li>Participate in community tree-planting events to contribute to reforestation efforts.</li><li>Support organizations dedicated to preserving and protecting forests and natural habitats.</li></ul></body></html>";
        
        // Data Program
        String resultProgram = tracker.generatePersonalizedRecommendations(0, 0, 0, 0, 0, 0, 5, 0);
        assertEquals(resultProgram, resultManual);
    }
}

