
//package LAB3;
//
//import LAB1.Account;
//import LAB1.Customer;
//import LAB1.Transaction;
//
//import LAB2.DBConnection;
//import LAB2.CustomerDAO;
//import LAB2.AccountDAO;
//import LAB2.TransactionDAO;
//import LAB2.PaymentService;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Scanner;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        Scanner input = new Scanner(System.in);
//
//        // Connect Lab 3 to the existing PostgreSQL database.
//        try (Connection connect = DBConnection.getConnection()) {
//
//            System.out.println("IgirePay Lab 3");
//            System.out.println("Database connected successfully!");
//
//            // Reuse the DAO classes created in Lab 2.
//            CustomerDAO customerDAO = new CustomerDAO(connect);
//            AccountDAO accountDAO = new AccountDAO(connect);
//            TransactionDAO transactionDAO =
//                    new TransactionDAO(connect);
//
//            // Handle deposits, withdrawals, transfers and duplicate requests.
//            PaymentService paymentService =
//                    new PaymentService(connect);
//
//            // Handle transaction reports.
//            TransactionReportService reportService =
//                    new TransactionReportService();
//
//            boolean running = true;
//
//            // Continue displaying the menu until the user exits.
//            while (running) {
//
//                System.out.println("\n===== IgirePay LAB3 Menu =====");
//                System.out.println("1. Add customer");
//                System.out.println("2. View customers");
//                System.out.println("3. Update customer");
//                System.out.println("4. Delete customer");
//                System.out.println("5. Search customer by email");
//                System.out.println("6. Create account");
//                System.out.println("7. View all accounts");
//                System.out.println("8. Search account by ID");
//                System.out.println("9. View accounts by customer ID");
//                System.out.println("10. Deposit money");
//                System.out.println("11. Withdraw money");
//                System.out.println("12. Update account type");
//                System.out.println("13. Delete inactive account");
//                System.out.println("14. Transfer money");
//                System.out.println("15. View transaction history");
//                System.out.println("16. Export transaction history to CSV");
//                System.out.println("17. Exit");
//
//                System.out.print("Choose an option: ");
//                String choice = input.nextLine();
//
//                try {
//                    switch (choice) {
//
//                        case "1": {
//                            // Add a new customer.
//                            System.out.print("Enter full name: ");
//                            String name = input.nextLine();
//
//                            System.out.print("Enter email: ");
//                            String email = input.nextLine();
//
//                            System.out.print("Enter phone number: ");
//                            String phone = input.nextLine();
//
//                            Customer customer =
//                                    new Customer(0, name, email, phone);
//
//                            int id = customerDAO.addCustomer(customer);
//
//                            System.out.println(
//                                    "Customer added successfully. ID: " + id);
//                            break;
//                        }
//
//                        case "2": {
//                            // Display all customers.
//                            List<Customer> customers =
//                                    customerDAO.getAllCustomers();
//
//                            if (customers.isEmpty()) {
//                                System.out.println("No customers found.");
//                            } else {
//                                for (Customer customer : customers) {
//                                    System.out.println(customer);
//                                }
//                            }
//                            break;
//                        }
//
//                        case "3": {
//                            // Update an existing customer's details.
//                            System.out.print("Enter customer ID: ");
//                            int id = Integer.parseInt(input.nextLine());
//
//                            Customer existing =
//                                    customerDAO.getCustomerById(id);
//
//                            if (existing == null) {
//                                System.out.println("Customer not found.");
//                                break;
//                            }
//
//                            System.out.print("Enter new full name: ");
//                            String name = input.nextLine();
//
//                            System.out.print("Enter new email: ");
//                            String email = input.nextLine();
//
//                            System.out.print("Enter new phone number: ");
//                            String phone = input.nextLine();
//
//                            Customer customer =
//                                    new Customer(id, name, email, phone);
//
//                            boolean updated =
//                                    customerDAO.updateCustomer(customer);
//
//                            System.out.println(updated
//                                    ? "Customer updated successfully."
//                                    : "Customer was not updated.");
//                            break;
//                        }
//
//                        case "4": {
//                            // Delete a customer if database rules allow it.
//                            System.out.print("Enter customer ID to delete: ");
//                            int id = Integer.parseInt(input.nextLine());
//
//                            boolean deleted =
//                                    customerDAO.deleteCustomer(id);
//
//                            System.out.println(deleted
//                                    ? "Customer deleted successfully."
//                                    : "Customer not found.");
//                            break;
//                        }
//
//                        case "5": {
//                            // Search for a customer using their email.
//                            System.out.print("Enter customer email: ");
//                            String email = input.nextLine();
//
//                            Customer customer =
//                                    customerDAO.getCustomerByEmail(email);
//
//                            System.out.println(customer == null
//                                    ? "Customer not found."
//                                    : customer);
//                            break;
//                        }
//
//                        case "6": {
//                            // Create a wallet or savings account.
//                            System.out.print("Enter customer ID: ");
//                            int customerId =
//                                    Integer.parseInt(input.nextLine());
//
//                            Customer customer =
//                                    customerDAO.getCustomerById(customerId);
//
//                            if (customer == null) {
//                                System.out.println("Customer not found.");
//                                break;
//                            }
//
//                            System.out.print(
//                                    "Enter account type (WALLET/SAVINGS): ");
//                            String type = input.nextLine();
//
//                            // New accounts start with zero balance.
//                            int accountId = accountDAO.createAccount(
//                                    customerId, type, 0.0);
//
//                            System.out.println(
//                                    "Account created. ID: " + accountId);
//                            break;
//                        }
//
//                        case "7": {
//                            // Display all accounts.
//                            List<Account> accounts =
//                                    accountDAO.getAllAccounts();
//
//                            if (accounts.isEmpty()) {
//                                System.out.println("No accounts found.");
//                            } else {
//                                for (Account account : accounts) {
//                                    System.out.println(account);
//                                }
//                            }
//                            break;
//                        }
//
//                        case "8": {
//                            // Find an account by ID.
//                            System.out.print("Enter account ID: ");
//                            int id = Integer.parseInt(input.nextLine());
//
//                            Account account =
//                                    accountDAO.getAccountById(id);
//
//                            System.out.println(account == null
//                                    ? "Account not found."
//                                    : account);
//                            break;
//                        }
//
//                        case "9": {
//                            // Display accounts belonging to a customer.
//                            System.out.print("Enter customer ID: ");
//                            int customerId =
//                                    Integer.parseInt(input.nextLine());
//
//                            List<Account> accounts =
//                                    accountDAO.getAccountsByCustomerId(
//                                            customerId);
//
//                            if (accounts.isEmpty()) {
//                                System.out.println(
//                                        "No accounts found for this customer.");
//                            } else {
//                                for (Account account : accounts) {
//                                    System.out.println(account);
//                                }
//                            }
//                            break;
//                        }
//
//                        case "10": {
//                            // Deposit money and record the transaction.
//                            System.out.print("Enter account ID: ");
//                            int id = Integer.parseInt(input.nextLine());
//
//                            System.out.print("Enter deposit amount: ");
//                            double amount =
//                                    Double.parseDouble(input.nextLine());
//
//                            System.out.print("Enter unique reference ID: ");
//                            String referenceId = input.nextLine();
//
//                            boolean deposited = paymentService.deposit(
//                                    id, amount, referenceId);
//
//                            System.out.println(deposited
//                                    ? "Deposit successful."
//                                    : "Deposit failed. Check the account ID "
//                                    + "or reference ID.");
//                            break;
//                        }
//
//                        case "11": {
//                            // Withdraw money and record the transaction.
//                            System.out.print("Enter account ID: ");
//                            int id = Integer.parseInt(input.nextLine());
//
//                            System.out.print("Enter withdrawal amount: ");
//                            double amount =
//                                    Double.parseDouble(input.nextLine());
//
//                            System.out.print("Enter unique reference ID: ");
//                            String referenceId = input.nextLine();
//
//                            boolean withdrawn = paymentService.withdraw(
//                                    id, amount, referenceId);
//
//                            System.out.println(withdrawn
//                                    ? "Withdrawal successful."
//                                    : "Withdrawal failed. Check the account, "
//                                    + "balance or reference ID.");
//                            break;
//                        }
//
//                        case "12": {
//                            // Update the type of an existing account.
//                            System.out.print("Enter account ID: ");
//                            int id = Integer.parseInt(input.nextLine());
//
//                            System.out.print(
//                                    "Enter new type (WALLET/SAVINGS): ");
//                            String type = input.nextLine();
//
//                            boolean updated =
//                                    accountDAO.updateAccountType(id, type);
//
//                            System.out.println(updated
//                                    ? "Account type updated."
//                                    : "Account not found.");
//                            break;
//                        }
//
//                        case "13": {
//                            // Delete an inactive account.
//                            System.out.print("Enter account ID: ");
//                            int id = Integer.parseInt(input.nextLine());
//
//                            boolean deleted =
//                                    accountDAO.deleteInactiveAccount(id);
//
//                            System.out.println(deleted
//                                    ? "Inactive account deleted."
//                                    : "Account was not deleted. It may have "
//                                    + "a balance or transaction history.");
//                            break;
//                        }
//
//                        case "14": {
//                            // Transfer money between two accounts.
//                            System.out.print("Enter sender account ID: ");
//                            int fromAccountId =
//                                    Integer.parseInt(input.nextLine());
//
//                            System.out.print("Enter receiver account ID: ");
//                            int toAccountId =
//                                    Integer.parseInt(input.nextLine());
//
//                            System.out.print("Enter transfer amount: ");
//                            double amount =
//                                    Double.parseDouble(input.nextLine());
//
//                            System.out.print("Enter unique reference ID: ");
//                            String referenceId = input.nextLine();
//
//                            boolean transferred = paymentService.transfer(
//                                    fromAccountId,
//                                    toAccountId,
//                                    amount,
//                                    referenceId);
//
//                            System.out.println(transferred
//                                    ? "Transfer successful."
//                                    : "Transfer failed. Check both account "
//                                    + "IDs, balance or reference ID.");
//                            break;
//                        }
//
//                        case "15": {
//                            // Display all saved transaction records.
//                            List<Transaction> transactions =
//                                    transactionDAO.getAllTransactions();
//
//                            if (transactions.isEmpty()) {
//                                System.out.println("No transactions found.");
//                            } else {
//                                System.out.println(
//                                        "\n===== Transaction History =====");
//
//                                for (Transaction transaction : transactions) {
//                                    System.out.println(transaction);
//                                }
//                            }
//                            break;
//                        }
//
//                        case "16": {
//                            // Export transaction history to a CSV file.
//                            try {
//                                reportService.exportTransactionsToCSV(
//                                        transactionDAO);
//
//                            } catch (IOException e) {
//                                System.out.println(
//                                        "Could not export transaction history: "
//                                                + e.getMessage());
//                            }
//                            break;
//                        }
//
//                        case "17": {
//                            // Exit the application.
//                            running = false;
//                            System.out.println(
//                                    "Thank you for using IgirePay Lab 3.");
//                            break;
//                        }
//
//                        default:
//                            System.out.println(
//                                    "Invalid option. Please try again.");
//                    }
//
//                } catch (NumberFormatException e) {
//                    System.out.println(
//                            "Please enter a valid number.");
//
//                } catch (IllegalArgumentException e) {
//                    System.out.println(e.getMessage());
//
//                } catch (SQLException e) {
//                    System.out.println(
//                            "Database operation failed: " + e.getMessage());
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "Database connection failed: " + e.getMessage());
//
//        } finally {
//            input.close();
//        }
//    }
//}


//package LAB3;
//
//import LAB1.Account;
//import LAB1.Customer;
//import LAB1.Transaction;
//
//import LAB2.DBConnection;
//import LAB2.CustomerDAO;
//import LAB2.AccountDAO;
//import LAB2.TransactionDAO;
//import LAB2.PaymentService;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Scanner;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        Scanner input = new Scanner(System.in);
//
//        // Connect Lab 3 to the existing PostgreSQL database.
//        try (Connection connect = DBConnection.getConnection()) {
//
//            System.out.println("IgirePay Lab 3");
//            System.out.println("Database connected successfully!");
//
//            // Reuse the DAO classes created in Lab 2.
//            CustomerDAO customerDAO = new CustomerDAO(connect);
//            AccountDAO accountDAO = new AccountDAO(connect);
//            TransactionDAO transactionDAO =
//                    new TransactionDAO(connect);
//
//            // Handle deposits, withdrawals, and transfers.
//            PaymentService paymentService =
//                    new PaymentService(connect);
//
//            // Handle CSV exports and transaction reports.
//            TransactionReportService reportService =
//                    new TransactionReportService();
//
//            boolean running = true;
//
//            // Keep showing the menu until the user chooses Exit.
//            while (running) {
//
//                System.out.println("\n===== IgirePay LAB3 Menu =====");
//                System.out.println("1. Add customer");
//                System.out.println("2. View customers");
//                System.out.println("3. Update customer");
//                System.out.println("4. Delete customer");
//                System.out.println("5. Search customer by email");
//                System.out.println("6. Create account");
//                System.out.println("7. View all accounts");
//                System.out.println("8. Search account by ID");
//                System.out.println("9. View accounts by customer ID");
//                System.out.println("10. Deposit money");
//                System.out.println("11. Withdraw money");
//                System.out.println("12. Update account type");
//                System.out.println("13. Delete inactive account");
//                System.out.println("14. Transfer money");
//                System.out.println("15. View transaction history");
//                System.out.println("16. Export transaction history to CSV");
//                System.out.println("17. View daily transaction summary");
//                System.out.println("18. Exit");
//
//                System.out.print("Choose an option: ");
//                String choice = input.nextLine();
//
//                try {
//                    switch (choice) {
//
//                        case "1": {
//                            // Add a new customer.
//                            System.out.print("Enter full name: ");
//                            String name = input.nextLine();
//
//                            System.out.print("Enter email: ");
//                            String email = input.nextLine();
//
//                            System.out.print("Enter phone number: ");
//                            String phone = input.nextLine();
//
//                            Customer customer =
//                                    new Customer(0, name, email, phone);
//
//                            int id = customerDAO.addCustomer(customer);
//
//                            System.out.println(
//                                    "Customer added successfully. ID: " + id);
//                            break;
//                        }
//
//                        case "2": {
//                            // Display all customers.
//                            List<Customer> customers =
//                                    customerDAO.getAllCustomers();
//
//                            if (customers.isEmpty()) {
//                                System.out.println("No customers found.");
//                            } else {
//                                for (Customer customer : customers) {
//                                    System.out.println(customer);
//                                }
//                            }
//                            break;
//                        }
//
//                        case "3": {
//                            // Update an existing customer's details.
//                            System.out.print("Enter customer ID: ");
//                            int id =
//                                    Integer.parseInt(input.nextLine());
//
//                            Customer existing =
//                                    customerDAO.getCustomerById(id);
//
//                            if (existing == null) {
//                                System.out.println("Customer not found.");
//                                break;
//                            }
//
//                            System.out.print("Enter new full name: ");
//                            String name = input.nextLine();
//
//                            System.out.print("Enter new email: ");
//                            String email = input.nextLine();
//
//                            System.out.print("Enter new phone number: ");
//                            String phone = input.nextLine();
//
//                            Customer customer =
//                                    new Customer(id, name, email, phone);
//
//                            boolean updated =
//                                    customerDAO.updateCustomer(customer);
//
//                            System.out.println(updated
//                                    ? "Customer updated successfully."
//                                    : "Customer was not updated.");
//                            break;
//                        }
//
//                        case "4": {
//                            // Delete a customer if database rules allow it.
//                            System.out.print("Enter customer ID to delete: ");
//                            int id =
//                                    Integer.parseInt(input.nextLine());
//
//                            boolean deleted =
//                                    customerDAO.deleteCustomer(id);
//
//                            System.out.println(deleted
//                                    ? "Customer deleted successfully."
//                                    : "Customer not found.");
//                            break;
//                        }
//
//                        case "5": {
//                            // Search for a customer using their email.
//                            System.out.print("Enter customer email: ");
//                            String email = input.nextLine();
//
//                            Customer customer =
//                                    customerDAO.getCustomerByEmail(email);
//
//                            System.out.println(customer == null
//                                    ? "Customer not found."
//                                    : customer);
//                            break;
//                        }
//
//                        case "6": {
//                            // Create a wallet or savings account.
//                            System.out.print("Enter customer ID: ");
//                            int customerId =
//                                    Integer.parseInt(input.nextLine());
//
//                            Customer customer =
//                                    customerDAO.getCustomerById(customerId);
//
//                            if (customer == null) {
//                                System.out.println("Customer not found.");
//                                break;
//                            }
//
//                            System.out.print(
//                                    "Enter account type (WALLET/SAVINGS): ");
//                            String type = input.nextLine();
//
//                            // New accounts start with a zero balance.
//                            int accountId = accountDAO.createAccount(
//                                    customerId, type, 0.0);
//
//                            System.out.println(
//                                    "Account created. ID: " + accountId);
//                            break;
//                        }
//
//                        case "7": {
//                            // Display all accounts.
//                            List<Account> accounts =
//                                    accountDAO.getAllAccounts();
//
//                            if (accounts.isEmpty()) {
//                                System.out.println("No accounts found.");
//                            } else {
//                                for (Account account : accounts) {
//                                    System.out.println(account);
//                                }
//                            }
//                            break;
//                        }
//
//                        case "8": {
//                            // Find an account by its ID.
//                            System.out.print("Enter account ID: ");
//                            int id =
//                                    Integer.parseInt(input.nextLine());
//
//                            Account account =
//                                    accountDAO.getAccountById(id);
//
//                            System.out.println(account == null
//                                    ? "Account not found."
//                                    : account);
//                            break;
//                        }
//
//                        case "9": {
//                            // Display accounts belonging to a customer.
//                            System.out.print("Enter customer ID: ");
//                            int customerId =
//                                    Integer.parseInt(input.nextLine());
//
//                            List<Account> accounts =
//                                    accountDAO.getAccountsByCustomerId(
//                                            customerId);
//
//                            if (accounts.isEmpty()) {
//                                System.out.println(
//                                        "No accounts found for this customer.");
//                            } else {
//                                for (Account account : accounts) {
//                                    System.out.println(account);
//                                }
//                            }
//                            break;
//                        }
//
//                        case "10": {
//                            // Deposit money using PaymentService.
//                            System.out.print("Enter account ID: ");
//                            int id =
//                                    Integer.parseInt(input.nextLine());
//
//                            System.out.print("Enter deposit amount: ");
//                            double amount =
//                                    Double.parseDouble(input.nextLine());
//
//                            System.out.print("Enter unique reference ID: ");
//                            String referenceId = input.nextLine();
//
//                            boolean deposited = paymentService.deposit(
//                                    id, amount, referenceId);
//
//                            System.out.println(deposited
//                                    ? "Deposit successful."
//                                    : "Deposit failed. Check the account ID "
//                                    + "or reference ID.");
//                            break;
//                        }
//
//                        case "11": {
//                            // Withdraw money using PaymentService.
//                            System.out.print("Enter account ID: ");
//                            int id =
//                                    Integer.parseInt(input.nextLine());
//
//                            System.out.print("Enter withdrawal amount: ");
//                            double amount =
//                                    Double.parseDouble(input.nextLine());
//
//                            System.out.print("Enter unique reference ID: ");
//                            String referenceId = input.nextLine();
//
//                            boolean withdrawn = paymentService.withdraw(
//                                    id, amount, referenceId);
//
//                            System.out.println(withdrawn
//                                    ? "Withdrawal successful."
//                                    : "Withdrawal failed. Check the account, "
//                                    + "balance or reference ID.");
//                            break;
//                        }
//
//                        case "12": {
//                            // Update the account type.
//                            System.out.print("Enter account ID: ");
//                            int id =
//                                    Integer.parseInt(input.nextLine());
//
//                            System.out.print(
//                                    "Enter new type (WALLET/SAVINGS): ");
//                            String type = input.nextLine();
//
//                            boolean updated =
//                                    accountDAO.updateAccountType(id, type);
//
//                            System.out.println(updated
//                                    ? "Account type updated."
//                                    : "Account not found.");
//                            break;
//                        }
//
//                        case "13": {
//                            // Delete an inactive account.
//                            System.out.print("Enter account ID: ");
//                            int id =
//                                    Integer.parseInt(input.nextLine());
//
//                            boolean deleted =
//                                    accountDAO.deleteInactiveAccount(id);
//
//                            System.out.println(deleted
//                                    ? "Inactive account deleted."
//                                    : "Account was not deleted. It may have "
//                                    + "a balance or transaction history.");
//                            break;
//                        }
//
//                        case "14": {
//                            // Transfer money between two accounts.
//                            System.out.print("Enter sender account ID: ");
//                            int fromAccountId =
//                                    Integer.parseInt(input.nextLine());
//
//                            System.out.print("Enter receiver account ID: ");
//                            int toAccountId =
//                                    Integer.parseInt(input.nextLine());
//
//                            System.out.print("Enter transfer amount: ");
//                            double amount =
//                                    Double.parseDouble(input.nextLine());
//
//                            System.out.print("Enter unique reference ID: ");
//                            String referenceId = input.nextLine();
//
//                            boolean transferred = paymentService.transfer(
//                                    fromAccountId,
//                                    toAccountId,
//                                    amount,
//                                    referenceId);
//
//                            System.out.println(transferred
//                                    ? "Transfer successful."
//                                    : "Transfer failed. Check both account "
//                                    + "IDs, balance or reference ID.");
//                            break;
//                        }
//
//                        case "15": {
//                            // Display transaction history.
//                            List<Transaction> transactions =
//                                    transactionDAO.getAllTransactions();
//
//                            if (transactions.isEmpty()) {
//                                System.out.println("No transactions found.");
//                            } else {
//                                System.out.println(
//                                        "\n===== Transaction History =====");
//
//                                for (Transaction transaction : transactions) {
//                                    System.out.println(transaction);
//                                }
//                            }
//                            break;
//                        }
//
//                        case "16": {
//                            // Export all transactions to a CSV file.
//                            try {
//                                reportService.exportTransactionsToCSV(
//                                        transactionDAO);
//
//                            } catch (IOException e) {
//                                System.out.println(
//                                        "Could not export transaction history: "
//                                                + e.getMessage());
//                            }
//                            break;
//                        }
//
//                        case "17": {
//                            // Display a summary of transactions by day.
//                            reportService.displayDailySummary(
//                                    transactionDAO);
//                            break;
//                        }
//
//                        case "18": {
//                            // Exit the application.
//                            running = false;
//                            System.out.println(
//                                    "Thank you for using IgirePay Lab 3.");
//                            break;
//                        }
//
//                        default:
//                            System.out.println(
//                                    "Invalid option. Please try again.");
//                    }
//
//                } catch (NumberFormatException e) {
//                    System.out.println(
//                            "Please enter a valid number.");
//
//                } catch (IllegalArgumentException e) {
//                    System.out.println(e.getMessage());
//
//                } catch (SQLException e) {
//                    System.out.println(
//                            "Database operation failed: " + e.getMessage());
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "Database connection failed: " + e.getMessage());
//
//        } finally {
//            input.close();
//        }
//    }
//}


package LAB3;

import LAB1.Account;
import LAB1.Customer;
import LAB1.Transaction;

import LAB2.DBConnection;
import LAB2.CustomerDAO;
import LAB2.AccountDAO;
import LAB2.TransactionDAO;
import LAB2.PaymentService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Connect Lab 3 to the existing PostgreSQL database.
        try (Connection connect = DBConnection.getConnection()) {

            System.out.println("IgirePay Lab 3");
            System.out.println("Database connected successfully!");

            // Reuse the DAO classes created in Lab 2.
            CustomerDAO customerDAO = new CustomerDAO(connect);
            AccountDAO accountDAO = new AccountDAO(connect);
            TransactionDAO transactionDAO =
                    new TransactionDAO(connect);

            // Handle deposits, withdrawals and transfers.
            PaymentService paymentService =
                    new PaymentService(connect);

            // Handle transaction reports.
            TransactionReportService reportService =
                    new TransactionReportService();

            boolean running = true;

            // Keep displaying the menu until the user exits.
            while (running) {

                System.out.println("\n===== IgirePay LAB3 Menu =====");
                System.out.println("1. Add customer");
                System.out.println("2. View customers");
                System.out.println("3. Update customer");
                System.out.println("4. Delete customer");
                System.out.println("5. Search customer by email");
                System.out.println("6. Create account");
                System.out.println("7. View all accounts");
                System.out.println("8. Search account by ID");
                System.out.println("9. View accounts by customer ID");
                System.out.println("10. Deposit money");
                System.out.println("11. Withdraw money");
                System.out.println("12. Update account type");
                System.out.println("13. Delete inactive account");
                System.out.println("14. Transfer money");
                System.out.println("15. View transaction history");
                System.out.println("16. Export transaction history to CSV");
                System.out.println("17. View daily transaction summary");
                System.out.println("18. Display customer transaction statement");
                System.out.println("19. Exit");

                System.out.print("Choose an option: ");
                String choice = input.nextLine();

                try {
                    switch (choice) {

                        case "1": {
                            // Add a new customer.
                            System.out.print("Enter full name: ");
                            String name = input.nextLine();

                            System.out.print("Enter email: ");
                            String email = input.nextLine();

                            System.out.print("Enter phone number: ");
                            String phone = input.nextLine();

                            Customer customer =
                                    new Customer(0, name, email, phone);

                            int id = customerDAO.addCustomer(customer);

                            System.out.println(
                                    "Customer added successfully. ID: " + id);
                            break;
                        }

                        case "2": {
                            // Display all customers.
                            List<Customer> customers =
                                    customerDAO.getAllCustomers();

                            if (customers.isEmpty()) {
                                System.out.println("No customers found.");
                            } else {
                                for (Customer customer : customers) {
                                    System.out.println(customer);
                                }
                            }
                            break;
                        }

                        case "3": {
                            // Update customer details.
                            System.out.print("Enter customer ID: ");
                            int id =
                                    Integer.parseInt(input.nextLine());

                            Customer existing =
                                    customerDAO.getCustomerById(id);

                            if (existing == null) {
                                System.out.println("Customer not found.");
                                break;
                            }

                            System.out.print("Enter new full name: ");
                            String name = input.nextLine();

                            System.out.print("Enter new email: ");
                            String email = input.nextLine();

                            System.out.print("Enter new phone number: ");
                            String phone = input.nextLine();

                            Customer customer =
                                    new Customer(id, name, email, phone);

                            boolean updated =
                                    customerDAO.updateCustomer(customer);

                            System.out.println(updated
                                    ? "Customer updated successfully."
                                    : "Customer was not updated.");
                            break;
                        }

                        case "4": {
                            // Delete a customer if database rules allow it.
                            System.out.print("Enter customer ID to delete: ");
                            int id =
                                    Integer.parseInt(input.nextLine());

                            boolean deleted =
                                    customerDAO.deleteCustomer(id);

                            System.out.println(deleted
                                    ? "Customer deleted successfully."
                                    : "Customer not found.");
                            break;
                        }

                        case "5": {
                            // Search for a customer by email.
                            System.out.print("Enter customer email: ");
                            String email = input.nextLine();

                            Customer customer =
                                    customerDAO.getCustomerByEmail(email);

                            System.out.println(customer == null
                                    ? "Customer not found."
                                    : customer);
                            break;
                        }

                        case "6": {
                            // Create a wallet or savings account.
                            System.out.print("Enter customer ID: ");
                            int customerId =
                                    Integer.parseInt(input.nextLine());

                            Customer customer =
                                    customerDAO.getCustomerById(customerId);

                            if (customer == null) {
                                System.out.println("Customer not found.");
                                break;
                            }

                            System.out.print(
                                    "Enter account type (WALLET/SAVINGS): ");
                            String type = input.nextLine();

                            int accountId = accountDAO.createAccount(
                                    customerId, type, 0.0);

                            System.out.println(
                                    "Account created. ID: " + accountId);
                            break;
                        }

                        case "7": {
                            // Display all accounts.
                            List<Account> accounts =
                                    accountDAO.getAllAccounts();

                            if (accounts.isEmpty()) {
                                System.out.println("No accounts found.");
                            } else {
                                for (Account account : accounts) {
                                    System.out.println(account);
                                }
                            }
                            break;
                        }

                        case "8": {
                            // Search for an account by ID.
                            System.out.print("Enter account ID: ");
                            int id =
                                    Integer.parseInt(input.nextLine());

                            Account account =
                                    accountDAO.getAccountById(id);

                            System.out.println(account == null
                                    ? "Account not found."
                                    : account);
                            break;
                        }

                        case "9": {
                            // Display accounts belonging to a customer.
                            System.out.print("Enter customer ID: ");
                            int customerId =
                                    Integer.parseInt(input.nextLine());

                            List<Account> accounts =
                                    accountDAO.getAccountsByCustomerId(
                                            customerId);

                            if (accounts.isEmpty()) {
                                System.out.println(
                                        "No accounts found for this customer.");
                            } else {
                                for (Account account : accounts) {
                                    System.out.println(account);
                                }
                            }
                            break;
                        }

                        case "10": {
                            // Deposit money and record the transaction.
                            System.out.print("Enter account ID: ");
                            int id =
                                    Integer.parseInt(input.nextLine());

                            System.out.print("Enter deposit amount: ");
                            double amount =
                                    Double.parseDouble(input.nextLine());

                            System.out.print("Enter unique reference ID: ");
                            String referenceId = input.nextLine();

                            boolean deposited = paymentService.deposit(
                                    id, amount, referenceId);

                            System.out.println(deposited
                                    ? "Deposit successful."
                                    : "Deposit failed. Check the account ID "
                                    + "or reference ID.");
                            break;
                        }

                        case "11": {
                            // Withdraw money and record the transaction.
                            System.out.print("Enter account ID: ");
                            int id =
                                    Integer.parseInt(input.nextLine());

                            System.out.print("Enter withdrawal amount: ");
                            double amount =
                                    Double.parseDouble(input.nextLine());

                            System.out.print("Enter unique reference ID: ");
                            String referenceId = input.nextLine();

                            boolean withdrawn = paymentService.withdraw(
                                    id, amount, referenceId);

                            System.out.println(withdrawn
                                    ? "Withdrawal successful."
                                    : "Withdrawal failed. Check the account, "
                                    + "balance or reference ID.");
                            break;
                        }

                        case "12": {
                            // Update the account type.
                            System.out.print("Enter account ID: ");
                            int id =
                                    Integer.parseInt(input.nextLine());

                            System.out.print(
                                    "Enter new type (WALLET/SAVINGS): ");
                            String type = input.nextLine();

                            boolean updated =
                                    accountDAO.updateAccountType(id, type);

                            System.out.println(updated
                                    ? "Account type updated."
                                    : "Account not found.");
                            break;
                        }

                        case "13": {
                            // Delete an inactive account.
                            System.out.print("Enter account ID: ");
                            int id =
                                    Integer.parseInt(input.nextLine());

                            boolean deleted =
                                    accountDAO.deleteInactiveAccount(id);

                            System.out.println(deleted
                                    ? "Inactive account deleted."
                                    : "Account was not deleted. It may have "
                                    + "a balance or transaction history.");
                            break;
                        }

                        case "14": {
                            // Transfer money between two accounts.
                            System.out.print("Enter sender account ID: ");
                            int fromAccountId =
                                    Integer.parseInt(input.nextLine());

                            System.out.print("Enter receiver account ID: ");
                            int toAccountId =
                                    Integer.parseInt(input.nextLine());

                            System.out.print("Enter transfer amount: ");
                            double amount =
                                    Double.parseDouble(input.nextLine());

                            System.out.print("Enter unique reference ID: ");
                            String referenceId = input.nextLine();

                            boolean transferred = paymentService.transfer(
                                    fromAccountId,
                                    toAccountId,
                                    amount,
                                    referenceId);

                            System.out.println(transferred
                                    ? "Transfer successful."
                                    : "Transfer failed. Check both account "
                                    + "IDs, balance or reference ID.");
                            break;
                        }

                        case "15": {
                            // Display the transaction history.
                            List<Transaction> transactions =
                                    transactionDAO.getAllTransactions();

                            if (transactions.isEmpty()) {
                                System.out.println("No transactions found.");
                            } else {
                                System.out.println(
                                        "\n===== Transaction History =====");

                                for (Transaction transaction : transactions) {
                                    System.out.println(transaction);
                                }
                            }
                            break;
                        }

                        case "16": {
                            // Export transaction history to a CSV file.
                            try {
                                reportService.exportTransactionsToCSV(
                                        transactionDAO);

                            } catch (IOException e) {
                                System.out.println(
                                        "Could not export transaction history: "
                                                + e.getMessage());
                            }
                            break;
                        }

                        case "17": {
                            // Display a daily transaction summary.
                            reportService.displayDailySummary(
                                    transactionDAO);
                            break;
                        }

                        case "18": {
                            // Display a customer's transaction statement.
                            System.out.print("Enter customer ID: ");
                            int customerId =
                                    Integer.parseInt(input.nextLine());

                            reportService.displayCustomerStatement(
                                    customerId,
                                    customerDAO,
                                    accountDAO,
                                    transactionDAO);
                            break;
                        }

                        case "19": {
                            // Exit the application.
                            running = false;
                            System.out.println(
                                    "Thank you for using IgirePay Lab 3.");
                            break;
                        }

                        default:
                            System.out.println(
                                    "Invalid option. Please try again.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Please enter a valid number.");

                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());

                } catch (SQLException e) {
                    System.out.println(
                            "Database operation failed: " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Database connection failed: " + e.getMessage());

        } finally {
            input.close();
        }
    }
}
