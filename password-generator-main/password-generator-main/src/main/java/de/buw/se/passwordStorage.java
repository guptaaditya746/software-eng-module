package de.buw.se;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class passwordStorage extends JFrame implements ActionListener {
    private List<Event> events = new ArrayList<>();
    private JList<Event> eventList;
    private DefaultListModel<Event> listModel;
    private JButton addButton;
    private JButton deleteButton;
    private static final String EVENTS_FILE = "events.txt";

    public passwordStorage() {
        setTitle("Password Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        initComponents();
        loadEvents();
    }

    private void initComponents() {
        listModel = new DefaultListModel<>();
        eventList = new JList<>(listModel);

        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addButton = new JButton("Add Password");
        deleteButton = new JButton("Remove Password");

        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(addButton);
        controlPanel.add(deleteButton);
        add(new JScrollPane(eventList), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
    }

    private Event createNewEvent() {
        String programName = JOptionPane.showInputDialog(this, "Enter program name:");
        if (programName == null || programName.trim().isEmpty()) {
            return null;
        }
        String password = JOptionPane.showInputDialog(this, "Enter password:");
        if (password == null || password.trim().isEmpty()) {
            return null;
        }
        return new Event(programName, password);
    }

    private void updateEventList() {
        listModel.clear();
        for (Event event : events) {
            listModel.addElement(event);
        }
    }

    private void saveEvents() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EVENTS_FILE))) {
            for (Event event : events) {
                writer.println(event.getProgramName() + "," + event.getPassword());
            }
        } catch (IOException e) {
            System.err.println("Error saving events: " + e.getMessage());
        }
    }

    private void loadEvents() {
        File file = new File(EVENTS_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating events file: " + e.getMessage());
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(EVENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String programName = parts[0];
                    String password = parts[1];
                    events.add(new Event(programName, password));
                }
            }
            updateEventList();
            System.out.println("You have " + events.size() + " passwords saved");
        } catch (IOException e) {
            System.err.println("Error loading events: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            Event newEvent = createNewEvent();
            if (newEvent != null) {
                events.add(newEvent);
                updateEventList();
                saveEvents();
            }
        } else if (e.getSource() == deleteButton) {
            Event selectedEvent = eventList.getSelectedValue();
            if (selectedEvent != null) {
                events.remove(selectedEvent);
                updateEventList();
                saveEvents();
                System.out.println("You have deleted a password"); // Add this line for debugging
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            passwordStorage storePasswords = new passwordStorage();
            storePasswords.setVisible(true);
        });
    }
}
