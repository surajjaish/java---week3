import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Expense {
    private Date date;
    private String description;
    private double amount;

    public Expense(Date date, String description, double amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date) + " | " + description + " | $" + amount;
    }
}

class ExpenseTracker {
    private ArrayList<Expense> expenses = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Scanner scanner = new Scanner(System.in);

    public void addExpense() {
        System.out.print("Enter date (yyyy-MM-dd): ");
        Date date;
        try {
            date = dateFormat.parse(scanner.next());
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
            return;
        }

        System.out.print("Enter description: ");
        String description = scanner.next();

        System.out.print("Enter amount: ");
        double amount;
        while (true) {
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
                break;
            } else {
                System.out.println("Invalid input. Enter a valid amount.");
                scanner.next(); // consume invalid input
            }
        }

        expenses.add(new Expense(date, description, amount));
        System.out.println("Expense added successfully!");
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to display.");
        } else {
            System.out.println("Date       | Description | Amount");
            System.out.println("--------------------------------");
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    public void saveExpensesToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(expenses);
            System.out.println("Expenses saved to file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving expenses to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadExpensesFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            expenses = (ArrayList<Expense>) ois.readObject();
            System.out.println("Expenses loaded from file successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading expenses from file: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ExpenseTracker expenseTracker = new ExpenseTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Save Expenses to File");
            System.out.println("4. Load Expenses from File");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    expenseTracker.addExpense();
                    break;
                case 2:
                    expenseTracker.viewExpenses();
                    break;
                case 3:
                    System.out.print("Enter filename to save: ");
                    String saveFilename = scanner.next();
                    expenseTracker.saveExpensesToFile(saveFilename);
                    break;
                case 4:
                    System.out.print("Enter filename to load: ");
                    String loadFilename = scanner.next();
                    expenseTracker.loadExpensesFromFile(loadFilename);
                    break;
                case 5:
                    System.out.println("Exiting Expense Tracker. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
