package de.buw.se;

// Import Libraries 
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.stage.WindowEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppGUI extends JFrame implements ActionListener, ChangeListener {
    
    // Defining the attributes for the password generator GUI
    private JMenuBar MenuBar;
    private JMenu MHome, MAboutUs, MColor;
    private JMenuItem MiCreator, MiDeepBlue, MiGray, MiBlack;
    private JCheckBox checkBoxForLowerCase;
    private JCheckBox checkBoxForUpperCase;
    private JCheckBox checkBoxForNumbers;
    private JCheckBox checkBoxForSpecialCharacters;
    private JSpinner lengthOfPassword;
    private JTextField passwordTextField, Bar1, Bar2, Bar3;
    private JButton generateButton, copyButton, SecurityTips, StoragePassword, LanguageButton;
    private JLabel passwordStrengthLabel, LabelLogo, LabelLenghtPassword, PasswordTitle, LSpace, LSpace2, LSlogan;
    public boolean a1 = false, a2 = false, a3 = false, a4 = false;
    public String Tex_strength; 
    private ResourceBundle messages;
    String menuItemChoice = "MiDeepBlue";
    
    // Initializing the constructor for the GUI
    public AppGUI() {
        // Set default language to English
        loadLanguage("en");
        

        // START IMPROVE OF GUI - Call and put all components inside GUI  
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(messages.getString("title"));
        getContentPane().setBackground(new Color(30, 30, 50));
        Image icon = Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\Icon.jpg");
        setIconImage(icon);

        MenuBar = new JMenuBar();
        MenuBar.setBackground(new Color(200, 200, 200));
        setJMenuBar(MenuBar);

        MHome = new JMenu(messages.getString("home"));
        MHome.setBackground(new Color(200, 200, 200));
        MHome.setFont(new Font("Arial Narrow", 1, 12));
        MHome.setForeground(new Color(0, 0, 0));
        MenuBar.add(MHome);

        MAboutUs = new JMenu(messages.getString("options"));
        MAboutUs.setBackground(new Color(200, 200, 200));
        MAboutUs.setFont(new Font("Arial Narrow", 1, 12));
        MAboutUs.setForeground(new Color(0, 0, 0));
        MenuBar.add(MAboutUs);

        MColor = new JMenu(messages.getString("color"));
        MColor.setBackground(new Color(200, 200, 200));
        MColor.setFont(new Font("Arial Narrow", 1, 12));
        MColor.setForeground(new Color(0, 0, 0));
        MHome.add(MColor);

        LanguageButton = new JButton(messages.getString("languages"));
        LanguageButton.setBackground(new Color(200, 200, 200));
        LanguageButton.setFont(new Font("Arial Narrow", 1, 12));
        LanguageButton.setForeground(new Color(0, 0, 0));
        MenuBar.add(Box.createHorizontalGlue()); // TO PUSH THE BUTTON TO THE RIGHTMOST CORNER
        MenuBar.add(LanguageButton);
        LanguageButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LanguageButton.addActionListener(this);

        MiDeepBlue = new JMenuItem(messages.getString("DeepBlue"));
        MiDeepBlue.setBackground(new Color(200, 200, 200));
        MiDeepBlue.setFont(new Font("Arial Narrow", 1, 12));
        MiDeepBlue.setForeground(new Color(0, 0, 0));
        MiDeepBlue.addActionListener(this);
        MColor.add(MiDeepBlue);

        MiGray = new JMenuItem(messages.getString("Gray"));
        MiGray.setBackground(new Color(200, 200, 200));
        MiGray.setFont(new Font("Arial Narrow", 1, 12));
        MiGray.setForeground(new Color(0, 0, 0));
        MiGray.addActionListener(this);
        MColor.add(MiGray);

        MiBlack = new JMenuItem(messages.getString("Black"));
        MiBlack.setBackground(new Color(200, 200, 200));
        MiBlack.setFont(new Font("Arial Narrow", 1, 12));
        MiBlack.setForeground(new Color(0, 0, 0));
        MiBlack.addActionListener(this);
        MColor.add(MiBlack);

        MiCreator = new JMenuItem(messages.getString("creator"));
        MiCreator.setBackground(new Color(200, 200, 200));
        MiCreator.setFont(new Font("Arial Narrow", 1, 12));
        MiCreator.setForeground(new Color(0, 0, 0));
        MAboutUs.add(MiCreator);
        MiCreator.addActionListener(this);

        LabelLogo = new JLabel();
        LabelLogo.setIcon(new ImageIcon("src\\main\\resources\\Logo.png"));
        LabelLogo.setBounds(260, 20, 162, 100);
        LabelLogo.setBackground(new Color(30, 30, 50));
        add(LabelLogo);

        PasswordTitle = new JLabel(messages.getString("typesOfCharacters"));
        PasswordTitle.setBounds(15, 5, 250, 30);
        PasswordTitle.setFont(new Font("Arial Narrow", 1, 16));
        PasswordTitle.setForeground(new Color(255, 255, 255));
        add(PasswordTitle);

        LSlogan = new JLabel(messages.getString("slogan"));
        LSlogan.setBounds(275, 130, 250, 30);
        LSlogan.setFont(new Font("Arial Narrow", 3, 16));
        LSlogan.setForeground(new Color(255, 255, 255));
        add(LSlogan);

        LSpace = new JLabel("-----------------------------------------------------");
        LSpace.setBounds(15, 22, 250, 20);
        LSpace.setFont(new Font("Arial Narrow", 1, 16));
        LSpace.setForeground(new Color(255, 255, 255));
        add(LSpace);

        passwordStrengthLabel = new JLabel(messages.getString("passwordStrength"));
        passwordStrengthLabel.setBounds(15, 350, 220, 30);
        passwordStrengthLabel.setFont(new Font("Arial Narrow", 1, 16));
        passwordStrengthLabel.setForeground(new Color(255, 255, 255));
        add(passwordStrengthLabel);

        LabelLenghtPassword = new JLabel(messages.getString("lengthOfPassword"));
        LabelLenghtPassword.setBounds(15, 200, 180, 30);
        LabelLenghtPassword.setFont(new Font("Arial Narrow", 1, 16));
        LabelLenghtPassword.setForeground(new Color(255, 255, 255));
        add(LabelLenghtPassword);

        checkBoxForLowerCase = new JCheckBox(messages.getString("includeLowercase"));
        checkBoxForLowerCase.setBounds(15, 40, 220, 30);
        checkBoxForLowerCase.setFont(new Font("Arial Narrow", 1, 16));
        checkBoxForLowerCase.setBackground(new Color(30, 30, 50));
        checkBoxForLowerCase.setForeground(new Color(255, 255, 255));
        checkBoxForLowerCase.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBoxForLowerCase.addChangeListener(this);
        add(checkBoxForLowerCase);

        checkBoxForUpperCase = new JCheckBox(messages.getString("includeUppercase"));
        checkBoxForUpperCase.setBounds(15, 70, 220, 30);
        checkBoxForUpperCase.setFont(new Font("Arial Narrow", 1, 16));
        checkBoxForUpperCase.setBackground(new Color(30, 30, 50));
        checkBoxForUpperCase.setForeground(new Color(255, 255, 255));
        checkBoxForUpperCase.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBoxForUpperCase.addChangeListener(this);
        add(checkBoxForUpperCase);

        checkBoxForNumbers = new JCheckBox(messages.getString("includeNumbers"));
        checkBoxForNumbers.setBounds(15, 100, 220, 30);
        checkBoxForNumbers.setFont(new Font("Arial Narrow", 1, 16));
        checkBoxForNumbers.setBackground(new Color(30, 30, 50));
        checkBoxForNumbers.setForeground(new Color(255, 255, 255));
        checkBoxForNumbers.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBoxForNumbers.addChangeListener(this);
        add(checkBoxForNumbers);

        checkBoxForSpecialCharacters = new JCheckBox(messages.getString("includeSpecialCharacters"));
        checkBoxForSpecialCharacters.setBounds(15, 130, 220, 30);
        checkBoxForSpecialCharacters.setFont(new Font("Arial Narrow", 1, 16));
        checkBoxForSpecialCharacters.setBackground(new Color(30, 30, 50));
        checkBoxForSpecialCharacters.setForeground(new Color(255, 255, 255));
        checkBoxForSpecialCharacters.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBoxForSpecialCharacters.addChangeListener(this);
        add(checkBoxForSpecialCharacters);

        lengthOfPassword = new JSpinner(new SpinnerNumberModel(16, 16, 30, 1));
        lengthOfPassword.setBounds(155, 200, 30, 30);
        lengthOfPassword.setFont(new Font("Arial Narrow", 1, 12));
        lengthOfPassword.setBackground(new Color(30, 30, 50));
        lengthOfPassword.setForeground(new Color(255, 255, 255));
        add(lengthOfPassword);

        passwordTextField = new JTextField(messages.getString("passwordFieldPlaceholder"));
        passwordTextField.setEditable(false);
        passwordTextField.setBounds(15, 250, 210, 30);
        passwordTextField.setBackground(new Color(50, 50, 80));
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 10));
        passwordTextField.setForeground(new Color(255, 255, 255));
        add(passwordTextField);

        Bar1 = new JTextField();
        Bar1.setEditable(false);
        Bar1.setBounds(15, 310, 60, 30);
        Bar1.setBackground(new Color(255, 255, 255));
        add(Bar1);

        Bar2 = new JTextField();
        Bar2.setEditable(false);
        Bar2.setBounds(85, 310, 60, 30);
        Bar2.setBackground(new Color(255, 255, 255));
        add(Bar2);

        Bar3 = new JTextField();
        Bar3.setEditable(false);
        Bar3.setBounds(155, 310, 60, 30);
        Bar3.setBackground(new Color(255, 255, 255));
        add(Bar3);

        LSpace2 = new JLabel("-----------------------------------------------------");
        LSpace2.setBounds(15, 220, 250, 20);
        LSpace2.setFont(new Font("Arial Narrow", 1, 16));
        LSpace2.setForeground(new Color(255, 255, 255));
        add(LSpace2);

        generateButton = new JButton(messages.getString("generatePassword"));
        generateButton.setBounds(250, 170, 200, 50);
        generateButton.setBackground(new Color(255, 255, 255));
        generateButton.setFont(new Font("Arial Narrow", 1, 17));
        generateButton.setForeground(new Color(0, 0, 0));
        generateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        generateButton.addActionListener(this);
        add(generateButton);

        copyButton = new JButton(messages.getString("copyPassword"));
        copyButton.setBounds(250, 230, 200, 50);
        copyButton.setBackground(new Color(255, 255, 255));
        copyButton.setFont(new Font("Arial Narrow", 1, 17));
        copyButton.setForeground(new Color(0, 0, 0));
        copyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyButton.addActionListener(this);
        add(copyButton);

        SecurityTips = new JButton(messages.getString("securityTips"));
        SecurityTips.setBounds(250, 290, 200, 50);
        SecurityTips.setBackground(new Color(255, 255, 255));
        SecurityTips.setFont(new Font("Arial Narrow", 1, 17));
        SecurityTips.setForeground(new Color(0, 0, 0));
        SecurityTips.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SecurityTips.addActionListener(this);
        add(SecurityTips);

        StoragePassword = new JButton(messages.getString("storagePasswords"));
        StoragePassword.setBounds(250, 350, 200, 50);
        StoragePassword.setBackground(new Color(255, 255, 255));
        StoragePassword.setFont(new Font("Arial Narrow", 1, 17));
        StoragePassword.setForeground(new Color(0, 0, 0));
        StoragePassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        StoragePassword.addActionListener(this);
        add(StoragePassword);
    }

    // LOAD THE RESOURCE BUNDLE FOR SELECTED LANGUAGE
    private void loadLanguage(String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        messages = ResourceBundle.getBundle("MessagesBundle", locale);
    }

    // Introducing a method for generating password
    public void actionPerformed(ActionEvent e) {
        // Configurate the behaviour when touch each Button
        if (e.getSource() == generateButton) {
            int length = (int) lengthOfPassword.getValue();
            String password = passwordEngine.generatePassword(length, a1, a2, a3, a4);
            // Implementing a condition to check whether one of the boxes are checked
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, messages.getString("checkboxWarning"));
            } else {
                // Formule to Check the level of Security of the Password and return Password
                passwordTextField.setText(password);
                int interval = (30 - 16) / 3;
                int lowerThreshold = 16 + interval;
                int upperThreshold = 30 - interval;

                // Update the password strength label and paint the bars
                if (password.length() >= upperThreshold) {
                    Tex_strength = "Strong";
                    passwordStrengthLabel.setText(messages.getString("passwordStrength") + Tex_strength);
                    passwordStrengthLabel.setForeground(Color.GREEN);
                    Bar1.setBackground(Color.GREEN);
                    Bar2.setBackground(Color.GREEN);
                    Bar3.setBackground(Color.GREEN);
                } else if (password.length() >= lowerThreshold) {
                    Tex_strength = "Medium";
                    passwordStrengthLabel.setText(messages.getString("passwordStrength") + Tex_strength);
                    passwordStrengthLabel.setForeground(new Color(255, 170, 51));
                    Bar1.setBackground(new Color(255, 170, 51));
                    Bar2.setBackground(new Color(255, 170, 51));
                    Bar3.setBackground(new Color(255, 255, 255));
                } else {
                    Tex_strength = "Poor";
                    passwordStrengthLabel.setText(messages.getString("passwordStrength") + Tex_strength);
                    this.passwordStrengthLabel.setForeground(Color.RED);
                    Bar1.setBackground(Color.RED);
                    Bar2.setBackground(new Color(255, 255, 255));
                    Bar3.setBackground(new Color(255, 255, 255));
                }
            }
        }
        if (e.getSource() == copyButton) {
            // Save Text in Password
            String password = passwordTextField.getText();

            // Check if Password is empty, then copy to Clipboard
            if (!password.isEmpty()) {
                StringSelection selection = new StringSelection(password);
                java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, null);
                JOptionPane.showMessageDialog(this, messages.getString("copySuccess"));
            } else {
                JOptionPane.showMessageDialog(this, messages.getString("passwordEmpty"));
            }
        }
        if (e.getSource() == SecurityTips) { // PUT INSIDE WINDOW OF INFO-TIPS SECURITY
            TransactionData data = new TransactionData(menuItemChoice);
            InfoGUI wInfo = new InfoGUI(messages.getLocale().toLanguageTag(), data);
            wInfo.setBounds(0, 0, 500, 500);
            wInfo.setVisible(true);
            wInfo.setResizable(false);
        }

        if (e.getSource() == StoragePassword) { // PUT INSIDE STORAGE OF THE PARK
            // Create an instance of CalendarV2
            passwordStorage calendarWindow = new passwordStorage();
            // Set visibility to true to display the calendar window
            calendarWindow.setVisible(true);
        }

        if (e.getSource() == MiDeepBlue) {
            menuItemChoice = "MiDeepBlue";
            getContentPane().setBackground(new Color(30, 30, 50));
            passwordTextField.setBackground(new Color(50, 50, 80));
            checkBoxForLowerCase.setBackground(new Color(30, 30, 50));
            checkBoxForUpperCase.setBackground(new Color(30, 30, 50));
            checkBoxForNumbers.setBackground(new Color(30, 30, 50));
            checkBoxForSpecialCharacters.setBackground(new Color(30, 30, 50));
            passwordTextField.setForeground(new Color(255, 255, 255));
            PasswordTitle.setForeground(new Color(255, 255, 255));
            passwordStrengthLabel.setForeground(new Color(255, 255, 255));
            passwordTextField.setForeground(new Color(255, 255, 255));
            lengthOfPassword.setForeground(new Color(255, 255, 255));
            checkBoxForLowerCase.setForeground(new Color(255, 255, 255));
            checkBoxForUpperCase.setForeground(new Color(255, 255, 255));
            checkBoxForNumbers.setForeground(new Color(255, 255, 255));
            checkBoxForSpecialCharacters.setForeground(new Color(255, 255, 255));
            LabelLenghtPassword.setForeground(new Color(255, 255, 255));
        }

        if (e.getSource() == MiBlack) {
            menuItemChoice = "MiBlack";
            getContentPane().setBackground(new Color(10, 10, 10));
            passwordTextField.setBackground(new Color(0, 0, 0));
            checkBoxForLowerCase.setBackground(new Color(0, 0, 0));
            checkBoxForUpperCase.setBackground(new Color(0, 0, 0));
            checkBoxForNumbers.setBackground(new Color(0, 0, 0));
            checkBoxForSpecialCharacters.setBackground(new Color(0, 0, 0));
            passwordTextField.setForeground(new Color(255, 255, 255));
            PasswordTitle.setForeground(new Color(255, 255, 255));
            passwordStrengthLabel.setForeground(new Color(255, 255, 255));
            passwordTextField.setForeground(new Color(255, 255, 255));
            lengthOfPassword.setForeground(new Color(255, 255, 255));
            checkBoxForLowerCase.setForeground(new Color(255, 255, 255));
            checkBoxForUpperCase.setForeground(new Color(255, 255, 255));
            checkBoxForNumbers.setForeground(new Color(255, 255, 255));
            checkBoxForSpecialCharacters.setForeground(new Color(255, 255, 255));
            LabelLenghtPassword.setForeground(new Color(255, 255, 255));
        }

        if (e.getSource() == MiGray) {
            menuItemChoice = "MiGray";
            getContentPane().setBackground(new Color(100, 100, 100));
            passwordTextField.setBackground(new Color(150, 150, 150));
            checkBoxForLowerCase.setBackground(new Color(100, 100, 100));
            checkBoxForUpperCase.setBackground(new Color(100, 100, 100));
            checkBoxForNumbers.setBackground(new Color(100, 100, 100));
            checkBoxForSpecialCharacters.setBackground(new Color(100, 100, 100));
            passwordTextField.setForeground(new Color(0, 0, 0));
            PasswordTitle.setForeground(new Color(0, 0, 0));
            passwordStrengthLabel.setForeground(new Color(0, 0, 0));
            passwordTextField.setForeground(new Color(0, 0, 0));
            lengthOfPassword.setForeground(new Color(0, 0, 0));
            checkBoxForLowerCase.setForeground(new Color(0, 0, 0));
            checkBoxForUpperCase.setForeground(new Color(0, 0, 0));
            checkBoxForNumbers.setForeground(new Color(0, 0, 0));
            checkBoxForSpecialCharacters.setForeground(new Color(0, 0, 0));
            LabelLenghtPassword.setForeground(new Color(0, 0, 0));
        }

        if (e.getSource() == MiCreator) {
            JOptionPane.showMessageDialog(null, messages.getString("creatorInfo"));
        }

        if (e.getSource() == LanguageButton) {
            // SHOWING LANGUAGE OPTION TO THE USER
            Object[] options = {"English", "German", "Spanish"};
            int n = JOptionPane.showOptionDialog(this,
                "Select a language:",
                "Languages",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

            if (n == 0) { // English
                loadLanguage("en");
                updateTexts();
            } else if (n == 1) { // German
                loadLanguage("de");
                updateTexts();
            } else if (n == 2) { // Spanish
                loadLanguage("es");
                updateTexts();
            }
        }

     }

    // UPDATING TEXT FIELDS AND LABELS WITH SELECTED LANGUAGES
    private void updateTexts() {
        setTitle(messages.getString("title"));
        MHome.setText(messages.getString("home"));
        MAboutUs.setText(messages.getString("options"));
        MColor.setText(messages.getString("color"));
        MiCreator.setText(messages.getString("creator"));
        LanguageButton.setText(messages.getString("languages"));
        passwordStrengthLabel.setText(messages.getString("passwordStrength"));
        LabelLenghtPassword.setText(messages.getString("lengthOfPassword"));
        checkBoxForLowerCase.setText(messages.getString("includeLowercase"));
        checkBoxForUpperCase.setText(messages.getString("includeUppercase"));
        checkBoxForNumbers.setText(messages.getString("includeNumbers"));
        checkBoxForSpecialCharacters.setText(messages.getString("includeSpecialCharacters"));
        passwordTextField.setText(messages.getString("passwordFieldPlaceholder"));
        generateButton.setText(messages.getString("generatePassword"));
        copyButton.setText(messages.getString("copyPassword"));
        SecurityTips.setText(messages.getString("securityTips"));
        StoragePassword.setText(messages.getString("storagePasswords"));
        LSlogan.setText(messages.getString("slogan"));
        PasswordTitle.setText(messages.getString("typesOfCharacters"));
        MiDeepBlue.setText(messages.getString("DeepBlue"));
        MiGray.setText(messages.getString("Gray"));
        MiBlack.setText(messages.getString("Black"));
    }

    // Change of values when CheckBox are/aren´t Selected
    public void stateChanged(ChangeEvent e) {
        if (checkBoxForLowerCase.isSelected() == true) {
            a1 = true;
        } else {
            a1 = false;
        }

        if (checkBoxForUpperCase.isSelected() == true) {
            a2 = true;
        } else {
            a2 = false;
        }

        if (checkBoxForNumbers.isSelected() == true) {
            a3 = true;
        } else {
            a3 = false;
        }

        if (checkBoxForSpecialCharacters.isSelected() == true) {
            a4 = true;
        } else {
            a4 = false;
        }
    }

    // Show the GUI on the Screen
    // public static void main(String[] args) {
    //     AppGUI wGUI = new AppGUI();
    //     wGUI.setBounds(0, 0, 500, 500);
    //     wGUI.setVisible(true);
    //     wGUI.setResizable(false);
    //     wGUI.setLocationRelativeTo(null);
    // }
    // Modify the main method in AppGUI to integrate login logic
// Modify the main method in AppGUI to integrate login logic
    // Modify the main method in AppGUI to integrate login logic
    public static void main(String[] args) {
        // Initialize the login system first
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.setVisible(true);

        // If login is successful, initialize the AppGUI
        if (loginSystem.isLoggedIn()) {
            // Pass any necessary information from loginSystem to AppGUI if needed
            EventQueue.invokeLater(() -> {
                AppGUI wGUI = new AppGUI();
                wGUI.setBounds(0, 0, 500, 500);
                wGUI.setVisible(true);
                wGUI.setResizable(false);
                wGUI.setLocationRelativeTo(null);
            });
        }
    }

}
