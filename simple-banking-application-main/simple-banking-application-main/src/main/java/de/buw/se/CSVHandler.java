package de.buw.se;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CSVHandler {
	
	private String USERS_FILE;
    private String TRANSACTIONS_FILE;

    // Constructor accepting file paths
    public CSVHandler(String usersFile, String transactionsFile) {
        this.USERS_FILE = usersFile;
        this.TRANSACTIONS_FILE = transactionsFile;
    }

    // Default constructor using hard coded file paths
    public CSVHandler() {
        this("src/main/resources/users.csv", "src/main/resources/transactions.csv");
    }

    public Map<String, User> loadUsers() throws IOException {
        Map<String, User> users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
                double balance = Double.parseDouble(data[2]);
                User user = new User(username, password);
                user.deposit(balance); // Initial balance setting
                users.put(username, user);
            }
        } catch (FileNotFoundException e) {
            throw new IOException("Users file not found", e);
        } catch (IOException e) {
            throw new IOException("Error reading users file", e);
        }
        loadTransactions(users);
        return users;
    }
    
    public void loadTransactions(Map<String, User> users) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String type = data[1];
                double amount = Double.parseDouble(data[2]);
                String dateTime = data[3];
                User user = users.get(username);
                if (user != null) {
                    Transaction transaction = new Transaction(type, amount);
                    user.getTransactions().add(transaction);
                    if (type.equals("Deposit")) {
                        user.deposit(amount);
                    } else if (type.equals("Withdrawal")) {
                        user.withdraw(amount);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new IOException("Transactions file not found", e);
        } catch (IOException e) {
            throw new IOException("Error reading transactions file", e);
        }
    }

    public void saveUser(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            bw.write(user.getUsername() + "," + user.getPassword() + "," + user.getBalance() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        // Load all users, update the specific user, and rewrite the file
        Map<String, User> users;
        try {
            users = loadUsers();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        users.put(user.getUsername(), user);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User u : users.values()) {
                bw.write(u.getUsername() + "," + u.getPassword() + "," + u.getBalance() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (User u : users.values()) {
                for (Transaction t : u.getTransactions()) {
                    bw.write(u.getUsername() + "," + t.toCSV() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}