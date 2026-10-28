//package LAB2;
//
//import LAB1.Customer;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Scanner;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        // Read the user's menu choices and customer details.
//        Scanner input = new Scanner(System.in);
//
//        // Connect to PostgreSQL using DBConnection.java.
//        try (Connection connect = DBConnection.getConnection()) {
//
//            System.out.println("Database connected successfully!");
//
//            // Give the connection to CustomerDAO.
//            CustomerDAO customerDAO = new CustomerDAO(connect);
//
//            boolean running = true;
//
//            // Continue displaying the menu until the user exits.
//            while (running) {
//
//                System.out.println("\n===== IgirePay Customer Menu =====");
//                System.out.println("1. Add customer");
//                System.out.println("2. View customers");
//                System.out.println("3. Exit");
//                System.out.print("Choose an option: ");
//
//                String choice = input.nextLine();
//
//                try {
//                    switch (choice) {
//
//                        case "1":
//                            // Collect the customer's details.
//                            System.out.print("Enter full name: ");
//                            String name = input.nextLine();
//
//                            System.out.print("Enter email: ");
//                            String email = input.nextLine();
//
//                            System.out.print("Enter phone number: ");
//                            String phone = input.nextLine();
//
//                            // Create the customer object.
//                            Customer customer = new Customer(
//                                    0, name, email, phone);
//
//                            // Save the customer in PostgreSQL.
//                            int customerId =
//                                    customerDAO.addCustomer(customer);
//
//                            System.out.println(
//                                    "Customer added successfully!");
//                            System.out.println(
//                                    "Customer ID: " + customerId);
//                            break;
//
//                        case "2":
//                            // Retrieve all saved customers.
//                            List<Customer> customers =
//                                    customerDAO.getAllCustomers();
//
//                            System.out.println("\n===== Customer List =====");
//
//                            if (customers.isEmpty()) {
//                                System.out.println(
//                                        "No customers found.");
//                            } else {
//                                for (Customer savedCustomer : customers) {
//                                    System.out.println(savedCustomer);
//                                }
//                            }
//                            break;
//
//                        case "3":
//                            // End the program.
//                            running = false;
//                            System.out.println(
//                                    "Thank you for using IgirePay.");
//                            break;
//
//                        default:
//                            System.out.println(
//                                    "Invalid option. Please try again.");
//                    }
//
//                } catch (SQLException e) {
//                    System.out.println(
//                            "Customer operation failed: "
//                                    + e.getMessage());
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Database connection failed: "
//                    + e.getMessage());
//        } finally {
//            input.close();
//        }
//    }
//}
//package LAB2;
//
//import LAB1.Customer;
//
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
//        // Connect to PostgreSQL.
//        try (Connection connect = DBConnection.getConnection()) {
//
//            System.out.println("Database connected successfully!");
//
//            // Create the DAO using the database connection.
//            CustomerDAO customerDAO = new CustomerDAO(connect);
//
//            boolean running = true;
//
//            // Keep displaying the menu until the user exits.
//            while (running) {
//
//                System.out.println("\n===== IgirePay Customer Menu =====");
//                System.out.println("1. Add customer");
//                System.out.println("2. View customers");
//                System.out.println("3. Update customer");
//                System.out.println("4. Delete customer");
//                System.out.println("5. Search customer by email");
//                System.out.println("6. Exit");
//                System.out.print("Choose an option: ");
//
//                String choice = input.nextLine();
//
//                try {
//                    switch (choice) {
//
//                        case "1":
//                            // Read the new customer's details.
//                            System.out.print("Enter full name: ");
//                            String name = input.nextLine();
//
//                            System.out.print("Enter email: ");
//                            String email = input.nextLine();
//
//                            System.out.print("Enter phone number: ");
//                            String phone = input.nextLine();
//
//                            Customer customer = new Customer(
//                                    0, name, email, phone);
//
//                            // Save the customer in PostgreSQL.
//                            int newId = customerDAO.addCustomer(customer);
//
//                            System.out.println(
//                                    "Customer added successfully!");
//                            System.out.println("Customer ID: " + newId);
//                            break;
//
//                        case "2":
//                            // Retrieve and display all customers.
//                            List<Customer> customers =
//                                    customerDAO.getAllCustomers();
//
//                            System.out.println("\n===== Customer List =====");
//
//                            if (customers.isEmpty()) {
//                                System.out.println("No customers found.");
//                            } else {
//                                for (Customer savedCustomer : customers) {
//                                    System.out.println(savedCustomer);
//                                }
//                            }
//                            break;
//
//                        case "3":
//                            // Update an existing customer.
//                            System.out.print("Enter customer ID: ");
//                            int updateId =
//                                    Integer.parseInt(input.nextLine());
//
//                            // Check that the customer exists first.
//                            Customer existing =
//                                    customerDAO.getCustomerById(updateId);
//
//                            if (existing == null) {
//                                System.out.println("Customer not found.");
//                                break;
//                            }
//
//                            System.out.print("Enter new full name: ");
//                            String newName = input.nextLine();
//
//                            System.out.print("Enter new email: ");
//                            String newEmail = input.nextLine();
//
//                            System.out.print("Enter new phone number: ");
//                            String newPhone = input.nextLine();
//
//                            Customer updatedCustomer = new Customer(
//                                    updateId, newName, newEmail, newPhone);
//
//                            boolean updated =
//                                    customerDAO.updateCustomer(updatedCustomer);
//
//                            if (updated) {
//                                System.out.println(
//                                        "Customer updated successfully!");
//                            } else {
//                                System.out.println("Customer was not updated.");
//                            }
//                            break;
//
//                        case "4":
//                            // Delete a customer by ID.
//                            System.out.print("Enter customer ID to delete: ");
//                            int deleteId =
//                                    Integer.parseInt(input.nextLine());
//
//                            boolean deleted =
//                                    customerDAO.deleteCustomer(deleteId);
//
//                            if (deleted) {
//                                System.out.println(
//                                        "Customer deleted successfully!");
//                            } else {
//                                System.out.println("Customer not found.");
//                            }
//                            break;
//
//                        case "5":
//                            // Search for a customer by email.
//                            System.out.print("Enter customer email: ");
//                            String searchEmail = input.nextLine();
//
//                            Customer found =
//                                    customerDAO.getCustomerByEmail(searchEmail);
//
//                            if (found != null) {
//                                System.out.println("Customer found:");
//                                System.out.println(found);
//                            } else {
//                                System.out.println("Customer not found.");
//                            }
//                            break;
//
//                        case "6":
//                            running = false;
//                            System.out.println(
//                                    "Thank you for using IgirePay.");
//                            break;
//
//                        default:
//                            System.out.println(
//                                    "Invalid option. Please try again.");
//                    }
//
//                } catch (NumberFormatException e) {
//                    System.out.println(
//                            "Please enter a valid numeric customer ID.");
//                } catch (SQLException e) {
//                    System.out.println(
//                            "Database operation failed: " + e.getMessage());
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "Database connection failed: " + e.getMessage());
//        } finally {
//            input.close();
//        }
//    }
//}

package LAB2;

import LAB1.Account;
import LAB1.Customer;
import LAB1.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Connect to the PostgreSQL database.
        try (Connection connect = DBConnection.getConnection()) {

            System.out.println("Database connected successfully!");

            // Create the DAO and service objects.
            CustomerDAO customerDAO = new CustomerDAO(connect);
            AccountDAO accountDAO = new AccountDAO(connect);
            TransactionDAO transactionDAO = new TransactionDAO(connect);

            // PaymentService handles payments and duplicate references.
            PaymentService paymentService = new PaymentService(connect);

            boolean running = true;

            // Keep displaying the menu until the user chooses Exit.
            while (running) {

                System.out.println("\n===== IgirePay Menu =====");
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
                System.out.println("16. Exit");

                System.out.print("Choose an option: ");
                String choice = input.nextLine();

                try {
                    switch (choice) {

                        case "1": {
                            // CREATE a new customer.
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
                            // READ all customers from the database.
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
                            // UPDATE customer details.
                            System.out.print("Enter customer ID: ");
                            int id = Integer.parseInt(input.nextLine());

                            Customer oldCustomer =
                                    customerDAO.getCustomerById(id);

                            if (oldCustomer == null) {
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
                            // DELETE a customer when database rules allow it.
                            System.out.print("Enter customer ID to delete: ");
                            int id = Integer.parseInt(input.nextLine());

                            boolean deleted =
                                    customerDAO.deleteCustomer(id);

                            System.out.println(deleted
                                    ? "Customer deleted successfully."
                                    : "Customer not found.");
                            break;
                        }

                        case "5": {
                            // CUSTOM QUERY: Search customer by email.
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
                            // CREATE a wallet or savings account.
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

                            // New accounts start with zero balance.
                            int accountId = accountDAO.createAccount(
                                    customerId, type, 0.0);

                            System.out.println(
                                    "Account created. ID: " + accountId);
                            break;
                        }

                        case "7": {
                            // READ all accounts.
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
                            // READ one account by ID.
                            System.out.print("Enter account ID: ");
                            int id = Integer.parseInt(input.nextLine());

                            Account account = accountDAO.getAccountById(id);

                            System.out.println(account == null
                                    ? "Account not found."
                                    : account);
                            break;
                        }

                        case "9": {
                            // CUSTOM QUERY: Accounts belonging to a customer.
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
                            // DEPOSIT using PaymentService.
                            System.out.print("Enter account ID: ");
                            int id = Integer.parseInt(input.nextLine());

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
                            // WITHDRAW using PaymentService.
                            System.out.print("Enter account ID: ");
                            int id = Integer.parseInt(input.nextLine());

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
                                    + "available balance, or reference ID.");
                            break;
                        }

                        case "12": {
                            // UPDATE the account type.
                            System.out.print("Enter account ID: ");
                            int id = Integer.parseInt(input.nextLine());

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
                            // DELETE an inactive account.
                            System.out.print("Enter account ID: ");
                            int id = Integer.parseInt(input.nextLine());

                            boolean deleted =
                                    accountDAO.deleteInactiveAccount(id);

                            System.out.println(deleted
                                    ? "Inactive account deleted."
                                    : "Account was not deleted. It may have "
                                    + "a balance or transaction history.");
                            break;
                        }

                        case "14": {
                            // TRANSFER money between two accounts.
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
                                    : "Transfer failed. Check both account IDs, "
                                    + "the sender's balance, or reference ID.");
                            break;
                        }

                        case "15": {
                            // READ all transaction records.
                            List<Transaction> transactions =
                                    transactionDAO.getAllTransactions();

                            if (transactions.isEmpty()) {
                                System.out.println(
                                        "No transactions found.");
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
                            // EXIT the application.
                            running = false;
                            System.out.println(
                                    "Thank you for using IgirePay.");
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

