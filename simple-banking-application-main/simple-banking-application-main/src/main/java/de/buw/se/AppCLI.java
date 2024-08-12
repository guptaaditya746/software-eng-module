package de.buw.se;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AppCLI {

	private static Bank bank = new Bank();

	public AppCLI(Bank bank) {
		AppCLI.bank = bank;
	}

	static Scanner scanner = new Scanner(System.in);
	static User currentUser = null;

	protected static void showMainMenu() throws IOException {
		System.out.println("*******");
		System.out.println("Welcome to SBA");
		System.out.println("*******");
		System.out.println("Menu : ");
		System.out.println("1. User Login");
		System.out.println("2. Registration");
		System.out.println("3. Forgot Password");
		System.out.print("Choose an option : ");
		String choice = scanner.nextLine();

		switch (choice) {
			case "1" -> login();
			case "2" -> register();
			case "3" -> forgotPassword();
			default -> System.out.println("Invalid choice. Please try again.");
		}
	}

	protected static void register() throws IOException {
		System.out.print("Enter username: ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		registerUser(username, password);

	}

	protected static void registerUser(String username, String password) throws IOException {
		if (username.trim().isEmpty()) {
			System.out.println("Please enter valid details");
			throw new IOException("This field is required");
		}

		if (password.trim().isEmpty()) {
			System.out.println("Please enter valid details");
			throw new IOException("This field is required");
		}

		if (bank.registerUser(username, password)) {
			System.out.println("Registration successful.");
		} else {
			System.out.println("Username already taken. Please try again.");
		}
	}

	protected static void login() {
		System.out.print("*******\n");
		System.out.print("Enter username: ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		currentUser = bank.loginUser(username, password);
		if (currentUser != null) {
			System.out.println("*******");
			System.out.println("Login successful.");
			System.out.println("*******");
		} else {
			System.out.println("*****************");
			System.out.println("Invalid username or password. Please try again.");
			System.out.println("*****************");
		}
	}

	protected static void forgotPassword() {
		System.out.print("\nEnter username: ");
		String username = scanner.nextLine();

		if (bank.userExists(username)) {
			System.out.print("Enter new password: ");
			String newPassword = scanner.nextLine();
			bank.resetPassword(username, newPassword);
			System.out.println("\nPassword reset successful. You can now log in with your new password.");
		} else {
			System.out.println("\nUsername does not exist.\n");
		}
	}

	private static void showUserMenu() throws IOException {
		System.out.println("User Menu:\n");

		System.out.println("1. Check balance");
		System.out.println("2. Add Money");
		System.out.println("3. Withdraw Money");
		System.out.println("4. See Transaction History");
		System.out.println("5. Last 5 Transactions");
		System.out.println("6. Change Password");
		System.out.println("0. Logout");
		System.out.print("Choose an option: ");
		String choice = scanner.nextLine();

		switch (choice) {
			case "0" -> logout();
			case "1" -> checkBalance();
			case "2" -> addMoney();
			case "3" -> withdrawMoney();
			case "4" -> viewTransactionHistory();
			case "5" -> viewRecentTransactions();
			case "6" -> changePassword();
			default -> inValidChoice();
		}
	}

	private static void inValidChoice() {
		System.out.println("\nInvalid choice. Please try again\n");
	}

	private static void logout() {
		System.out.println("\nLogout successful.\n");
		currentUser = null;
	}

	protected static void checkBalance() {
		System.out.println("Current balance: " + currentUser.getBalance());
	}

	protected static void addMoney() throws NumberFormatException {
		System.out.print("Enter amount to add: ");
		while (true) {
			try {
				System.out.print("Enter amount to add: ");
				String amountStr = scanner.nextLine();
				addAmount(amountStr);
				break; // Exit loop if amount is successfully added
			} catch (NumberFormatException e) {
				System.out.println("Error: Please enter a valid numeric amount.");
			}
		}
	}

	protected static void addAmount(String amountStr) throws NumberFormatException {
		double amount = 0.0;
		if (isValidNumber(amountStr)) {
			amount = Double.parseDouble(amountStr);
		} else {
			throw new NumberFormatException("Invalid Number Format");
		}
		if (amount > 0) {
			currentUser.deposit(amount);
			bank.saveUser(currentUser);
			System.out.println("Money added successfully.");
		} else {
			System.out.println("Amount must be positive.");
		}
	}

	protected static boolean isValidNumber(String amount) {
		// Regular expression to check for a valid number
		String numberPattern = "-?\\d+(\\.\\d+)?";
		return amount.matches(numberPattern);
	}

	protected static void withdrawMoney() {
		System.out.print("Enter amount to withdraw: ");
		double amount = Double.parseDouble(scanner.nextLine());
		if (amount > 0) {
			if (currentUser.withdraw(amount)) {
				bank.saveUser(currentUser);
				System.out.println("Money withdrawn successfully.");
			} else {
				System.out.println("Insufficient balance.");
			}
		} else {
			System.out.println("Amount must be positive.");
		}
	}

	protected static void viewTransactionHistory() {
		System.out.println("Transaction History:");
		List<Transaction> transactions = currentUser.getTransactions();
		if (transactions.isEmpty()) {
			System.out.println("No transactions found.");
		} else {
			for (Transaction t : transactions) {
				System.out.println(t);
			}
		}
	}

	protected static void viewRecentTransactions() {
		System.out.println("Recent Transactions:");
		List<Transaction> transactions = currentUser.getTransactions();
		int recentCount = 5;
		if (transactions.isEmpty()) {
			System.out.println("No transactions found.");
		} else {
			for (int i = Math.max(0, transactions.size() - recentCount); i < transactions.size(); i++) {
				System.out.println(transactions.get(i));
			}
		}
	}

	protected static void changePassword() {
		System.out.print("Enter current password: ");
		String oldPassword = scanner.nextLine();
		System.out.print("Enter new password: ");
		String newPassword = scanner.nextLine();

		if (bank.changePassword(currentUser.getUsername(), oldPassword, newPassword)) {
			System.out.println("\nPassword changed successfully.\n");
		} else {
			System.out.println("Current password is incorrect. Please try again.");
		}
	}

	public static void main(String[] args) throws IOException {
		while (true) {
			if (currentUser == null) {
				showMainMenu();
			} else {
				showUserMenu();
			}
		}
	}
}

