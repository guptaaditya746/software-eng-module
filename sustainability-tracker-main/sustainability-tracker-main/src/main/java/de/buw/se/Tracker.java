package de.buw.se;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.*;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import javafx.event.ActionEvent;

public class Tracker extends JFrame {

    protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected JPasswordField confirmPasswordField;
    protected JButton signInButton;

    protected JTextField variable1Field;
    protected JTextField variable2Field;
    protected JTextField variable3Field;
    protected JTextField variable4Field;
    protected JTextField variable5Field;
    protected JTextField variable6Field;
    protected JTextField variable7Field;
    protected JTextField variable8Field;
    private JButton displayButton;
    private JButton backButton;
    private  JButton helpButton;
    private JDialog Dialog;
    private JTextPane displayPane;
    private JDialog displayDialog;
    public String errorMessage = "";

    private final String CSV_PATH = "src/main/resources/database.csv";
    public Tracker() {
       
    	
        initSignInDialog();
        
    }

    protected void initSignInDialog() {
    	variable1Field = new JTextField(); // Initialize variable1Field
        variable2Field = new JTextField(); // Initialize variable2Field
        variable3Field = new JTextField(); // Initialize variable3Field
        variable4Field = new JTextField(); // Initialize variable4Field
        variable5Field = new JTextField(); // Initialize variable5Field
        variable6Field = new JTextField(); // Initialize variable6Field
        variable7Field = new JTextField(); // Initialize variable7Field
        variable8Field = new JTextField(); // Initialize variable8Field
        JDialog signInDialog = new JDialog(this, "Sign In", true);
        signInDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        signInDialog.setLayout(new BorderLayout());

        JLabel instructionLabel = new JLabel("<html><div style='text-align: center;'><font size='6'><b><font color='green'>The Sustainability Tracker application aims to empower individuals<br>to monitor and enhance their sustainability efforts in their daily lives.<br>With a growing global focus on environmental conservation.</font></b></font></div></html>");
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionLabel.setOpaque(true);
        instructionLabel.setBackground(new Color(240, 255, 240));
        signInDialog.add(instructionLabel, BorderLayout.NORTH);

        JPanel signInPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setPreferredSize(new Dimension(100, 40));
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        signInPanel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 1;
        gbc.gridy = 0;
        signInPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setPreferredSize(new Dimension(100, 40));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        signInPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 1;
        gbc.gridy = 1;
        signInPanel.add(passwordField, gbc);

        signInButton = new JButton("Sign In");
        signInButton.setPreferredSize(new Dimension(200, 40));
        signInButton.setFont(new Font("Arial", Font.BOLD, 16));
        signInButton.addActionListener(e -> signInAction());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        signInPanel.add(signInButton, gbc);
        
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(200, 40));
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.addActionListener(e -> initRegisterDialog(signInDialog));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        signInPanel.add(registerButton, gbc);

        signInDialog.add(signInPanel, BorderLayout.CENTER);

        signInDialog.pack();
        signInDialog.setLocationRelativeTo(this);
        signInDialog.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //System.exit(0);
    }
    
    protected void initRegisterDialog(JDialog signInDialog) {
    	signInDialog.dispose();
    	JDialog registerDialog = new JDialog(this, "Register", true);
        registerDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        registerDialog.setLayout(new BorderLayout());
       
        JPanel registerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setPreferredSize(new Dimension(100, 40));
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        gbc.gridx = 0;
        gbc.gridy = 0;
        registerPanel.add(usernameLabel, gbc);
        
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 1;
        gbc.gridy = 0;
        registerPanel.add(usernameField, gbc);

        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setPreferredSize(new Dimension(100, 40));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        gbc.gridx = 0;
        gbc.gridy = 1;
        registerPanel.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 1;
        gbc.gridy = 1;
        registerPanel.add(passwordField, gbc);
        
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setPreferredSize(new Dimension(100, 40));
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        gbc.gridx = 0;
        gbc.gridy = 2;
        registerPanel.add(confirmPasswordLabel, gbc);
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 1;
        gbc.gridy = 2;
        registerPanel.add(confirmPasswordField, gbc);
        
        
        
        
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(200, 40));
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.addActionListener(e -> registerAction());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        registerPanel.add(registerButton, gbc);
        registerDialog.add(registerPanel, BorderLayout.CENTER);
        
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            registerDialog.dispose();
            initSignInDialog(); // Go back to sign in dialog
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        registerPanel.add(backButton, gbc);
        registerDialog.add(registerPanel, BorderLayout.CENTER);
        

        registerDialog.pack();
        registerDialog.setLocationRelativeTo(this);
        registerDialog.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //System.exit(0);
        }
    
    private void registerAction() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        if (username.isEmpty() && password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (username.isEmpty()){
            JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (password.isEmpty()){
            JOptionPane.showMessageDialog(this, "Password cannot be empty.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (confirmPassword.isEmpty()) {
        	JOptionPane.showMessageDialog(this, "Please confirm your password.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        	
        } else if (!password.equals(confirmPassword)) {
        	JOptionPane.showMessageDialog(this, "Passwords do not match!", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        	
        } else if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (username.contains(" ") || password.contains(" ")) {
            JOptionPane.showMessageDialog(this, "Username and password cannot contain white spaces.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[0].equals(username)) {
                    JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to read the registration data.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        // Save the new user data
        try (FileWriter fw = new FileWriter(CSV_PATH, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(username + "," + password);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save the registration data.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }


           JOptionPane.showMessageDialog(this, "Successfully registered as " + username, "Registration Success", JOptionPane.INFORMATION_MESSAGE);
           SwingUtilities.getWindowAncestor(usernameField).dispose(); // This disposes the JDialog containing usernameField

           initSignInDialog();

      
    }

      
    
    
    
      

    private void signInAction() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        
       
        if (username.isEmpty() && password.isEmpty()) {
        	errorMessage = "Username and password cannot be empty.";
            JOptionPane.showMessageDialog(this, errorMessage, "Sign In Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (username.isEmpty()){
        	errorMessage = "Username cannot be empty.";
            JOptionPane.showMessageDialog(this, errorMessage, "Sign In Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (password.isEmpty()){
        	errorMessage = "Password cannot be empty.";
            JOptionPane.showMessageDialog(this, errorMessage, "Sign In Error", JOptionPane.ERROR_MESSAGE);
            return;
        } 
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            boolean isAuthenticated = false;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    isAuthenticated = true;
                    break;
                }
            }

            if (isAuthenticated) {
                JOptionPane.showMessageDialog(this, "Successfully signed in as " + username, "Sign In Success", JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.getWindowAncestor(usernameField).dispose(); // This disposes the JDialog containing usernameField
                initInputDialog(); // Initialize main input dialog after successful sign-in
            } else {
                errorMessage = "Invalid username or password.";
                JOptionPane.showMessageDialog(this, errorMessage, "Sign In Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to read the user data.", "Sign In Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

      
    }

    protected void initInputDialog() {
        JDialog inputDialog = new JDialog(this, "Input Variables", true);
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addInputLabelAndField(contentPanel, gbc, 0, "Consumption of Electricity (KWh):", variable1Field = new JTextField(), "Enter the amount of electricity consumed in kilowatt-hours (KWh)");
        addInputLabelAndField(contentPanel, gbc, 1, "Household fuel usage (Kg):", variable2Field = new JTextField(), "Enter the amount of household fuel usage in kilograms (Kg)");
        addInputLabelAndField(contentPanel, gbc, 2, "Fuel for Private Transport (Litres):", variable3Field = new JTextField(), "Enter the amount of fuel used for private transportation in litres (L)");
        addInputLabelAndField(contentPanel, gbc, 3, "Distance Travelled by Rail (Km):", variable4Field = new JTextField(), "Enter the distance travelled by rail in kilometers (Km)");
        addInputLabelAndField(contentPanel, gbc, 4, "Amount of Waste Generated (Kg):", variable5Field = new JTextField(), "Enter the amount of waste generated in kilograms (Kg)");
        addInputLabelAndField(contentPanel, gbc, 5, "Water Consumption (m^3):", variable6Field = new JTextField(), "Enter the amount of water consumed in cubic meters (M^3)");
        addInputLabelAndField(contentPanel, gbc, 6, "Distance Travelled by Foot/Bicycle (Km):", variable7Field = new JTextField(), "Enter the total distance travelled by foot or bicycle in kilometers (Km)");
        addInputLabelAndField(contentPanel, gbc, 7, "Number of Trees Planted(per year):", variable8Field = new JTextField(), "Enter the number of trees planted");

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        JButton displayButton = new JButton("Display Variables");
        displayButton.setPreferredSize(new Dimension(200, 40));
        displayButton.setFont(new Font("Arial", Font.BOLD, 16));
        displayButton.addActionListener(e -> {
            if (validateInputs()) {
                inputDialog.dispose();
                displayVariables();
            } else {
                JOptionPane.showMessageDialog(inputDialog, "Please enter valid numeric values for all fields.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        contentPanel.add(displayButton, gbc);

        gbc.gridy = 9;
        JButton helpButton = new JButton("Help");
        helpButton.setPreferredSize(new Dimension(200, 40));
        helpButton.setFont(new Font("Arial", Font.BOLD, 16));
        helpButton.addActionListener(e -> showHelpDialog());
        contentPanel.add(helpButton, gbc);

        gbc.gridy = 10;
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            inputDialog.dispose();
            initSignInDialog(); // Go back to sign in dialog
        });
        contentPanel.add(backButton, gbc);

        inputDialog.add(contentPanel);
        inputDialog.pack();
        inputDialog.setLocationRelativeTo(this);
        inputDialog.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //System.exit(0);
        
    }

    private void addInputLabelAndField(JPanel panel, GridBagConstraints gbc, int y, String label, JTextField field, String tooltip) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        JLabel jLabel = new JLabel(label);
        jLabel.setPreferredSize(new Dimension(200, 40));
        jLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        jLabel.setToolTipText(tooltip);
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        field.setPreferredSize(new Dimension(300, 40));
        panel.add(field, gbc);
    }

    protected boolean validateInputs() {
        try {
            if (variable1Field.getText().isEmpty()) {
                showErrorDialog("Consumption of Electricity is required.");
                return false;
            }
            if (variable2Field.getText().isEmpty()) {
                showErrorDialog("Household fuel usage is required.");
                return false;
            }
            if (variable3Field.getText().isEmpty()) {
                showErrorDialog("Fuel for Private Transport is required.");
                return false;
            }
            if (variable4Field.getText().isEmpty()) {
                showErrorDialog("Distance Travelled by Rail is required.");
                return false;
            }
            if (variable5Field.getText().isEmpty()) {
                showErrorDialog("Amount of Waste Generated is required.");
                return false;
            }
            if (variable6Field.getText().isEmpty()) {
                showErrorDialog("Water Consumption is required.");
                return false;
            }
            if (variable7Field.getText().isEmpty()) {
                showErrorDialog("Distance Travelled by Foot/Bicycle is required.");
                return false;
            }
            if (variable8Field.getText().isEmpty()) {
                showErrorDialog("Number of Trees Planted is required.");
                return false;
            }
            double var1=Double.parseDouble(variable1Field.getText());
            double var2=Double.parseDouble(variable2Field.getText());
            double var3=Double.parseDouble(variable3Field.getText());
            double var4=Double.parseDouble(variable4Field.getText());
            double var5=Double.parseDouble(variable5Field.getText());
            double var6=Double.parseDouble(variable6Field.getText());
            double var7=Double.parseDouble(variable7Field.getText());
            double var8=Double.parseDouble(variable8Field.getText());
            
            if (var1 < 0 || var2 < 0 || var3 < 0 || var4 < 0 || var5 < 0 || var6 < 0 || var7 < 0 || var8 < 0) {
                showErrorDialog("Values cannot be negative.");
                return false;
            }
            
            if (var8 != (int) var8) {
                showErrorDialog("Number of Trees Planted must be a whole number.");
                return false;
            }
            
            return true;
        } catch (NumberFormatException e) {
            showErrorDialog("Please enter valid numeric values for all fields.");
            return false;
        }
    }



    protected void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }

    private void displayVariables() {
    	 displayDialog = new JDialog(this, "Display Results", true);
         displayDialog.setLayout(new BorderLayout());

         JTextPane displayPane = new JTextPane();
         displayPane.setContentType("text/html");
         displayPane.setEditable(false);
         displayPane.setFont(new Font("Arial", Font.PLAIN, 16));
         displayPane.setPreferredSize(new Dimension(1000, 500));
        try {
            double electricity = Double.parseDouble(variable1Field.getText());
            double fuel = Double.parseDouble(variable2Field.getText());
            double transportationprivate = Double.parseDouble(variable3Field.getText());
            double rail = Double.parseDouble(variable4Field.getText());
            double waste = Double.parseDouble(variable5Field.getText());
            double water = Double.parseDouble(variable6Field.getText());
            double foot = Double.parseDouble(variable7Field.getText());
            double tree = Double.parseDouble(variable8Field.getText());

            double total = calculateTotal(electricity, fuel, transportationprivate, rail, waste, water, foot, tree);
            double index = total / 14000;
            

            String recommendations = generateRecommendations(index);
            String personalizedRecommendations = generatePersonalizedRecommendations(electricity, fuel, transportationprivate, rail, waste, water, foot, tree);
            String result = generateResultHtml(total, index, recommendations + personalizedRecommendations, electricity, fuel, transportationprivate, rail, waste, water, foot, tree);

            displayPane.setText(result);
        } catch (NumberFormatException ex) {
            displayPane.setText("<html><body><h3>Invalid input</h3></body></html>");
        }
        displayDialog.add(new JScrollPane(displayPane), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            displayDialog.dispose();
            initInputDialog();
        });
        bottomPanel.add(backButton);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(200, 40));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(displayDialog,
                    "Are you sure you want to log out?", "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        bottomPanel.add(logoutButton);

        displayDialog.add(bottomPanel, BorderLayout.SOUTH);

        displayDialog.pack();
        displayDialog.setLocationRelativeTo(this);
        displayDialog.setVisible(true);
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }

    protected double calculateTotal(double electricity, double fuel, double transportationPrivate, double rail, double waste, double water, double foot, double tree) { //Modified for Testin Private -> Protected
    	if(electricity<0 || fuel<0 ||transportationPrivate<0||rail<0||waste<0||water<0||foot<0||tree<0 ) {
    		throw new IllegalArgumentException("Negative values are not allowed.");
    	}else {
        return electricity * 354.75 + fuel * 2300 + transportationPrivate * 2157 + rail * 32.77 + waste * 242.858 + water * 395 - foot * 72.16 - tree * 242.858;
    	}
    }

    protected String generateRecommendations(double index) { //Modified for Testin Private -> Protected
        if (index < 0) {
        	return "Invalid index";
        } else if (index <= 5) {
            return "<font color='green'><b>Outstanding! Your sustainability index is excellent. You're setting an incredible example of how to live sustainably. Your efforts are making a significant impact. Keep leading the way and inspiring others to follow in your footsteps!</b></font><br>";
        } else if (index <= 10) {
            return "<font color='green'><b>Fantastic! Your sustainability index is above average. You're making great strides in living sustainably. Keep up the exceptional work and continue to inspire those around you!</b></font><br>";
        } else if (index <= 15) {
            return "<font color='red'><b>Good job! Your sustainability index is average. You're making steady progress in living sustainably. Keep pushing forward and explore new ways to improve your impact on the environment.</b></font><br>";
        } else {
            return "<font color='red'><b>You're on the right path! Your sustainability index is low, but every small step counts. Consider making a few more changes to your daily habits, and you'll see significant improvements in no time. Keep striving for a more sustainable lifestyle.</b></font><br>";
        }
    }

    protected String generatePersonalizedRecommendations(double electricity, double fuel, double transportationprivate, double rail, double waste, double water, double foot, double tree) {
        StringBuilder recommendations = new StringBuilder("<html><body><h2>Personalized Recommendations</h2><ul>");

        if (electricity >= 20) {
            recommendations.append("<li>Consider reducing your electricity consumption by using energy-efficient appliances and lighting.</li>");
            recommendations.append("<li>Turn off appliances and lights when not in use to save electricity.</li>");
            recommendations.append("<li>Consider installing solar panels to generate your own electricity.</li>");
        }

        if (fuel >= 5) {
            recommendations.append("<li>Reduce household fuel usage by improving insulation and using more efficient heating systems.</li>");
            recommendations.append("<li>Consider switching to renewable energy sources for heating, such as heat pumps or biomass.</li>");
            recommendations.append("<li>Plan and combine errands to minimize driving and fuel consumption.</li>");
        }

        if (transportationprivate >= 2) {
            recommendations.append("<li>Reduce private transport fuel consumption by using public transport, carpooling, or switching to electric vehicles.</li>");
            recommendations.append("<li>Consider telecommuting or working from home to reduce commuting fuel usage.</li>");
            recommendations.append("<li>Practice eco-driving techniques such as smooth acceleration and maintaining steady speeds.</li>");
        }

        if (rail >= 20) {
            recommendations.append("<li>Try to combine trips to reduce the distance traveled by rail.</li>");
            recommendations.append("<li>Consider purchasing tickets in advance to take advantage of discounts and promotions.</li>");
            recommendations.append("<li>Explore options for alternative modes of transportation such as biking or walking for short distances.</li>");
        }

        if (waste >= 1) {
            recommendations.append("<li>Implement waste reduction practices such as recycling and composting.</li>");
            recommendations.append("<li>Reduce packaging waste by buying products with minimal packaging or in bulk.</li>");
            recommendations.append("<li>Consider donating or selling items instead of throwing them away.</li>");
        }

        if (water > 100) {
            recommendations.append("<li>Reduce water consumption by fixing leaks and using water-saving fixtures.</li>");
            recommendations.append("<li>Install a rainwater harvesting system to collect rainwater for outdoor use.</li>");
            recommendations.append("<li>Take shorter showers and turn off the tap when brushing teeth or washing dishes.</li>");
        }

        if (foot < 5) {
            recommendations.append("<li>Increase your physical activity by walking or cycling more often.</li>");
            recommendations.append("<li>Take the stairs instead of the elevator whenever possible to incorporate more physical activity into your daily routine.</li>");
            recommendations.append("<li>Join a local walking or hiking group to make exercising more enjoyable and social.</li>");
        }

        if (tree < 80) {
            recommendations.append("<li>Plant more trees to help offset carbon emissions.</li>");
            recommendations.append("<li>Participate in community tree-planting events to contribute to reforestation efforts.</li>");
            recommendations.append("<li>Support organizations dedicated to preserving and protecting forests and natural habitats.</li>");
        }

        recommendations.append("</ul></body></html>");
        return recommendations.toString();
    }

    private String generateResultHtml(double total, double index, String recommendations, double electricity, double fuel, double transportationprivate, double rail, double waste, double water, double foot, double tree) {
        return "<html><body style='font-size:14px; font-family:Arial;'>" +
                "<h3 style='color:blue;'>Input Variables and Values:</h3>" +
                "<p>Consumption of Electricity (KWh): " + electricity + "</p>" +
                "<p>Household Fuel Usage (Kg): " + fuel + "</p>" +
                "<p>Fuel for Private Transport (Litres): " + transportationprivate + "</p>" +
                "<p>Distance Travelled by Rail (Km): " + rail + "</p>" +
                "<p>Amount of Waste Generated (Kg): " + waste + "</p>" +
                "<p>Water Consumption (M^3): " + water + "</p>" +
                "<p>Distance Travelled by Foot/Bicycle (Km): " + foot + "</p>" +
                "<p>Number of Trees Planted: " + tree + "</p>" +
                "<h3 style='color:red; font-size:18px; font-weight:bold;'>Carbon Emission: " + total + "</h3>" +
                "<h3 style='color:red; font-size:18px; font-weight:bold;'>Sustainability Index: " + index + "</h3>" +
                "<h3 style='color:blue; font-size:18px; font-weight:bold;'>Recommendations:</h3>" +
                recommendations +
                "</body></html>";
    }

    private void showHelpDialog() {
        String message = "<html><body>" +
                "<h3>Help - Input Variables</h3>" +
                "<p><b>Consumption of Electricity (KWh):</b> Enter the amount of electricity consumed in kilowatt-hours (KWh).</p>" +
                "<p><b>Household fuel usage (Kg):</b> Enter the amount of household fuel usage in kilograms (Kg).</p>" +
                "<p><b>Fuel for Private Transport (Litres):</b> Enter the amount of fuel used for private transportation in litres (L).</p>" +
                "<p><b>Distance Travelled by Rail (Km):</b> Enter the distance travelled by rail in kilometers (Km).</p>" +
                "<p><b>Amount of Waste Generated (Kg):</b> Enter the amount of waste generated in kilograms (Kg).</p>" +
                "<p><b>Water Consumption (M^3):</b> Enter the amount of water consumed in cubic meters (M^3).</p>" +
                "<p><b>Distance Travelled by Foot/Bicycle (Km):</b> Enter the total distance travelled by foot or bicycle in kilometers (Km).</p>" +
                "<p><b>Number of Trees Planted:</b> Enter the number of trees planted.</p>" +
                "</body></html>";
        JOptionPane.showMessageDialog(this, message, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tracker tracker = new Tracker();
            tracker.setVisible(true);
        });
    }
}
