import java.io.*;
import java.util.*;

class Transaction {
    String date;
    String type;
    double amount;
    String category;

    Transaction(String date, String type, double amount, String category) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.category = category;
    }

    public String toString() {
        return date + "," + type + "," + amount + "," + category;
    }
}

public class FinanceTracker {

    static ArrayList<Transaction> transactions = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "transactions.txt";

    public static void main(String[] args) {
        loadData();

        int choice;

        do {
            System.out.println("\n===== Personal Finance Tracker =====");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Transactions");
            System.out.println("4. Monthly Report");
            System.out.println("5. Date-wise Filter");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addIncome();
                    break;

                case 2:
                    addExpense();
                    break;

                case 3:
                    viewTransactions();
                    break;

                case 4:
                    monthlyReport();
                    break;

                case 5:
                    dateWiseFilter();
                    break;

                case 6:
                    saveData();
                    System.out.println("Thank you! Data saved successfully.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }

    static void addIncome() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        System.out.print("Enter income amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter source (Salary/Gift/etc): ");
        String source = sc.nextLine();

        transactions.add(new Transaction(date, "Income", amount, source));
        System.out.println("Income added successfully.");
    }

    static void addExpense() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        System.out.print("Enter expense amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter category (Food/Travel/Shopping/etc): ");
        String category = sc.nextLine();

        transactions.add(new Transaction(date, "Expense", amount, category));
        System.out.println("Expense added successfully.");
    }

    static void viewTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\nDate\t\tType\t\tAmount\t\tCategory/Source");
        System.out.println("------------------------------------------------------");

        for (Transaction t : transactions) {
            System.out.println(t.date + "\t" + t.type + "\t\t" + t.amount + "\t\t" + t.category);
        }
    }

    static void monthlyReport() {
        System.out.print("Enter month (YYYY-MM): ");
        String month = sc.nextLine();

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction t : transactions) {
            if (t.date.startsWith(month)) {
                if (t.type.equalsIgnoreCase("Income")) {
                    totalIncome += t.amount;
                } else if (t.type.equalsIgnoreCase("Expense")) {
                    totalExpense += t.amount;
                }
            }
        }

        double balance = totalIncome - totalExpense;

        System.out.println("\n===== Monthly Report =====");
        System.out.println("Month: " + month);
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expenses: " + totalExpense);
        System.out.println("Balance: " + balance);
    }

    static void dateWiseFilter() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        boolean found = false;

        System.out.println("\nTransactions on " + date);
        System.out.println("--------------------------------");

        for (Transaction t : transactions) {
            if (t.date.equals(date)) {
                System.out.println(t.date + " | " + t.type + " | " + t.amount + " | " + t.category);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No transactions found for this date.");
        }
    }

    static void saveData() {
        try {
            FileWriter fw = new FileWriter(FILE_NAME);

            for (Transaction t : transactions) {
                fw.write(t.toString() + "\n");
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("Error while saving data.");
        }
    }

    static void loadData() {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                return;
            }

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split(",");

                if (data.length == 4) {
                    transactions.add(new Transaction(
                            data[0],
                            data[1],
                            Double.parseDouble(data[2]),
                            data[3]
                    ));
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error while loading data.");
        }
    }
}
