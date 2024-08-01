import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
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
            printMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1 -> addExpense(scanner);
                case 2 -> listExpenses();
                case 3 -> displayTotalExpenses();
                case 4 -> deleteExpense(scanner);
                case 5 -> updateExpense(scanner);
                case 6 -> saveExpenses();
                case 7 -> loadExpenses();
                case 8 -> listExpensesByAmount();
                case 9 -> {
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Add Expense");
        System.out.println("2. List Expenses");
        System.out.println("3. Display Total Expenses");
        System.out.println("4. Delete Expense");
        System.out.println("5. Update Expense");
        System.out.println("6. Save Expenses");
        System.out.println("7. Load Expenses");
        System.out.println("8. List Expenses by Amount");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter amount: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            expenses.add(new Expense(description, amount));
            System.out.println("Expense added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please try again.");
        }
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
        double total = expenses.stream().mapToDouble(expense -> expense.amount).sum();
        System.out.println("Total Expenses: $" + total);
    }

    private static void deleteExpense(Scanner scanner) {
        System.out.print("Enter expense number to delete: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < expenses.size()) {
                expenses.remove(index);
                System.out.println("Expense deleted successfully.");
            } else {
                System.out.println("Invalid expense number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private static void updateExpense(Scanner scanner) {
        System.out.print("Enter expense number to update: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < expenses.size()) {
                System.out.print("Enter new description: ");
                String description = scanner.nextLine();
                System.out.print("Enter new amount: ");
                double amount = Double.parseDouble(scanner.nextLine());
                expenses.get(index).description = description;
                expenses.get(index).amount = amount;
                System.out.println("Expense updated successfully.");
            } else {
                System.out.println("Invalid expense number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please try again.");
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

    private static void listExpensesByAmount() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses available.");
        } else {
            expenses.sort(Comparator.comparingDouble(exp -> exp.amount));
            listExpenses();
        }
    }
}
