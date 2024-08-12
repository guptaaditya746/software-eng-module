package de.buw.se;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginSystem extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private Map<String, String> users; // Map to store registered usernames and passwords

    private boolean loggedIn; // Indicator for whether the user is logged in or not

    public LoginSystem() {
        setTitle("Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(150, 150, 150));
        Image icon = Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\Icon.jpg");
        setIconImage(icon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(registerButton);

        users = new HashMap<>(); // Initialize the map to store usernames and passwords
        loadCredentials(); // Load credentials from the file
        loggedIn = false; // Initially, the user is not logged in
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            if (users.containsKey(username) && users.get(username).equals(password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                loggedIn = true; // Set loggedIn to true when login is successful
                // Open an instance of the AppGUI class
                EventQueue.invokeLater(() -> {
                    AppGUI wGUI = new AppGUI();
                    wGUI.setBounds(0, 0, 500, 500);
                    wGUI.setVisible(true);
                    wGUI.setResizable(false);
                    wGUI.setLocationRelativeTo(null);
                });
                dispose(); // Close login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        } else if (e.getSource() == registerButton) {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username or password cannot be empty!");
            } else if (users.containsKey(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists!");
            } else {
                users.put(username, password);
                saveCredentials(username, password);
                JOptionPane.showMessageDialog(this, "Registration successful!");
            }
        }
    }

    private void saveCredentials(String username, String password) {
        try (FileWriter writer = new FileWriter("credentials.txt", true)) {
            writer.write(username + "," + password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader("credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check if the user is logged in
    public boolean isLoggedIn() {
        return loggedIn;
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.setVisible(true);
    }
}
