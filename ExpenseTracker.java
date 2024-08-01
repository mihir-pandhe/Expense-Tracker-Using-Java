import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Expense implements Serializable {
    String description;
    double amount;

    Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }
}

public class ExpenseTracker {
    private static ArrayList<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Add Expense");
            System.out.println("2. List Expenses");
            System.out.println("3. Display Total Expenses");
            System.out.println("4. Delete Expense");
            System.out.println("5. Update Expense");
            System.out.println("6. Save Expenses");
            System.out.println("7. Load Expenses");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter description: ");
                String description = scanner.nextLine();
                System.out.print("Enter amount: ");
                double amount = scanner.nextDouble();
                expenses.add(new Expense(description, amount));
                System.out.println("Expense added successfully.");
            } else if (choice == 2) {
                listExpenses();
            } else if (choice == 3) {
                displayTotalExpenses();
            } else if (choice == 4) {
                System.out.print("Enter expense number to delete: ");
                int index = scanner.nextInt();
                deleteExpense(index - 1);
            } else if (choice == 5) {
                System.out.print("Enter expense number to update: ");
                int index = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new description: ");
                String description = scanner.nextLine();
                System.out.print("Enter new amount: ");
                double amount = scanner.nextDouble();
                updateExpense(index - 1, description, amount);
            } else if (choice == 6) {
                saveExpenses();
            } else if (choice == 7) {
                loadExpenses();
            } else if (choice == 8) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void listExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses available.");
        } else {
            for (int i = 0; i < expenses.size(); i++) {
                Expense expense = expenses.get(i);
                System.out.println((i + 1) + ". " + expense.description + ": $" + expense.amount);
            }
        }
    }

    private static void displayTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.amount;
        }
        System.out.println("Total Expenses: $" + total);
    }

    private static void deleteExpense(int index) {
        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            System.out.println("Expense deleted successfully.");
        } else {
            System.out.println("Invalid expense number.");
        }
    }

    private static void updateExpense(int index, String description, double amount) {
        if (index >= 0 && index < expenses.size()) {
            Expense expense = expenses.get(index);
            expense.description = description;
            expense.amount = amount;
            System.out.println("Expense updated successfully.");
        } else {
            System.out.println("Invalid expense number.");
        }
    }

    private static void saveExpenses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("expenses.dat"))) {
            oos.writeObject(expenses);
            System.out.println("Expenses saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadExpenses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("expenses.dat"))) {
            expenses = (ArrayList<Expense>) ois.readObject();
            System.out.println("Expenses loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }
}
