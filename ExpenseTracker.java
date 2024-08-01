import java.util.ArrayList;
import java.util.Scanner;

class Expense {
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
            System.out.println("4. Exit");
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
            for (Expense expense : expenses) {
                System.out.println(expense.description + ": $" + expense.amount);
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
}
