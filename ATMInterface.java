import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

class Transaction {
    private Date date;
    private String type;
    private double amount;
    private double balance;

    public Transaction(String type, double amount, double balance) {
        this.date = new Date();
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }
}

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private ArrayList<Transaction> transactions;

    public Account(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean checkPin(String pin) {
        return userPin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount, balance));
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction("Withdraw", amount, balance));
            return true;
        } else {
            System.out.println("Insufficient balance!");
            return false;
        }
    }

    public void transfer(Account destinationAccount, double amount) {
        if (withdraw(amount)) {
            destinationAccount.deposit(amount);
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed! Insufficient balance.");
        }
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}

public class ATMInterface {
    private static final String USER_ID = "801679";
    private static final String USER_PIN = "7232";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account account = new Account(USER_ID, USER_PIN, 26000.0);

        System.out.println("Welcome to the ATM!");
        System.out.print("Enter User ID: ");
        String userIdInput = scanner.nextLine();

        System.out.print("Enter User PIN: ");
        String userPinInput = scanner.nextLine();

        if (userIdInput.equals(USER_ID) && account.checkPin(userPinInput)) {
            System.out.println("Login Successful!");
            showMainMenu(account);
        } else {
            System.out.println("Login Failed. Invalid User ID or PIN.");
        }

        scanner.close();
    }

    private static void showMainMenu(Account account) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMain Menu:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showTransactionHistory(account);
                    break;
                case 2:
                    performWithdraw(account, scanner);
                    break;
                case 3:
                    performDeposit(account, scanner);
                    break;
                case 4:
                    performTransfer(account, scanner);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void showTransactionHistory(Account account) {
        ArrayList<Transaction> transactions = account.getTransactions();
        System.out.println("\nTransaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getType() + " - Amount: " + transaction.getAmount() +
                    ", Date: " + transaction.getDate() + ", Balance: " + transaction.getBalance());
        }
    }

    private static void performWithdraw(Account account, Scanner scanner) {
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        account.withdraw(amount);
    }

    private static void performDeposit(Account account, Scanner scanner) {
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
    }

    private static void performTransfer(Account account, Scanner scanner) {
        System.out.print("Enter the recipient's User ID: ");
        String recipientId = scanner.next();
    }
}