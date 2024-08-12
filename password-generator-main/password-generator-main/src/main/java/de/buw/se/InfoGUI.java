package de.buw.se;

// Import Libraries 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class InfoGUI extends JFrame implements ActionListener{

    private JMenuBar MenuBar;
    private JMenu MHome, MAboutUs, MColor;
    private JMenuItem MiCreator, MiDeepBlue, MiGray, MiBlack;
    private JLabel LabelLogo, LSlogan, Labelthief, LabelTitleTips, LSpace;
    private JButton LanguageButton, ReturnButton;
    private JTextArea ta1;
    private ResourceBundle messages;


    public InfoGUI(String languageTag, TransactionData data) {
        // Load info from transaction
        loadLanguage(languageTag);
        Color colorBackGround = data.getColorBackGround();
        Color colorBackTA = data.getColorBackTA();
        Color colorForeTA = data.getColorForeTA();
        Color colorLetters = data.getColorLetters();

        setLayout(null);
        setTitle(messages.getString("title2"));
        getContentPane().setBackground(colorBackGround);
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

        // SEPARATE BUTTON FOR LANGUAGE SELECTION
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
        LabelLogo.setBounds(300, 60, 162, 100);
        LabelLogo.setBackground(new Color(30, 30, 50));
        add(LabelLogo);

        Labelthief = new JLabel();
        Labelthief.setIcon(new ImageIcon("src\\main\\resources\\thief.png"));
        Labelthief.setBounds(340, 220, 100, 120);
        Labelthief.setBackground(new Color(30, 30, 50));
        add(Labelthief);

        LSlogan = new JLabel(messages.getString("slogan"));
        LSlogan.setBounds(320, 170, 250, 30);
        LSlogan.setFont(new Font("Arial Narrow", 3, 16));
        LSlogan.setForeground(new Color(255, 255, 255));
        add(LSlogan);

        LabelTitleTips = new JLabel(messages.getString("tip"));
        LabelTitleTips.setBounds(15, 10, 450, 28);
        LabelTitleTips.setFont(new Font("Arial Narrow", 1, 18));
        LabelTitleTips.setForeground(colorLetters);
        add(LabelTitleTips);

        LSpace = new JLabel("---------------------------------------------------------------------------------------------------------------");
        LSpace.setBounds(15, 25, 450, 20);
        LSpace.setFont(new Font("Arial Narrow", 1, 16));
        LSpace.setForeground(new Color(255, 255, 255));
        add(LSpace);

        ta1 = new JTextArea(messages.getString("Text"));
        ta1.setEditable(false);
        ta1.setForeground(colorForeTA);
        ta1.setBackground(colorBackTA);
        ta1.setFont(new Font("Arial Narrow",0,14));
        ta1.setBounds(15,50,280,350);
        add (ta1);

        ReturnButton = new JButton(messages.getString("Return"));
        ReturnButton.setBounds(320, 350, 150, 50);
        ReturnButton.setBackground(new Color(255, 255, 255));
        ReturnButton.setFont(new Font("Arial Narrow", 1, 17));
        ReturnButton.setForeground(new Color(0, 0, 0));
        ReturnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ReturnButton.addActionListener(this);
        add(ReturnButton);

    }

    protected void loadLanguage(String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        messages = ResourceBundle.getBundle("MessagesBundle", locale);
    }

    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == ReturnButton) {
            this.setVisible(false);
        }

        if (e.getSource() == MiDeepBlue) {
            getContentPane().setBackground(new Color(30, 30, 50));
            ta1.setBackground(new Color(50,50,80));
            ta1.setForeground(new Color(255,255,255));
            LabelTitleTips.setForeground(new Color(255, 255, 255));
        }

        if (e.getSource() == MiBlack) {
            getContentPane().setBackground(new Color(10, 10, 10));
            ta1.setBackground(new Color(40,40,40));
            ta1.setForeground(new Color(255,255,255));
            LabelTitleTips.setForeground(new Color(255, 255, 255));
        }

        if (e.getSource() == MiGray) {
            getContentPane().setBackground(new Color(100, 100, 100));
            ta1.setBackground(new Color(150,150,150));
            ta1.setForeground(new Color(0,0,0));
            LabelTitleTips.setForeground(new Color(0, 0, 0));
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
    protected void updateTexts() {
        setTitle(messages.getString("title2"));
        MHome.setText(messages.getString("home"));
        MAboutUs.setText(messages.getString("options"));
        MColor.setText(messages.getString("color"));
        MiCreator.setText(messages.getString("creator"));
        LanguageButton.setText(messages.getString("languages"));
        LSlogan.setText(messages.getString("slogan"));
        LabelTitleTips.setText(messages.getString("tip"));
        MiDeepBlue.setText(messages.getString("DeepBlue"));
        MiGray.setText(messages.getString("Gray"));
        MiBlack.setText(messages.getString("Black"));
        ta1.setText(messages.getString("Text"));
        ReturnButton.setText(messages.getString("Return"));
    }
    
    public JMenuItem getMiDeepBlue() {
        return MiDeepBlue;
    }

    public JMenuItem getMiBlack() {
        return MiBlack;
    }

    public JMenuItem getMiGray() {
        return MiGray;
    }

    public JButton getReturnButton() {
        return ReturnButton;
    }

    public Color getTextAreaBackgroundColor() {
        return ta1.getBackground();
    }

    public Color getTextAreaForegroundColor() {
        return ta1.getForeground();
    }

    public Color getLabelTitleTipsColor() {
        return LabelTitleTips.getForeground();
    }

}
