//package LAB1;
//
//// Import LocalDateTime so we can record when transactions are created.
//import java.time.LocalDateTime;
//
//public class Exercise13Main {
//
//    public static void main(String[] args) {
//
//        // Create a customer with example information.
//        Customer customer = new Customer(
//                1, "Christella M",
//                "christella@example.com", "0790830834");
//
//        // Create a wallet account with an initial balance of 5000.
//        WalletAccount wallet = new WalletAccount(
//                101, 1, 5000.0, true);
//
//        // Create a savings account with an initial balance of 10000.
//        // The withdrawal fee for this test is 10.
//        SavingsAccount savings = new SavingsAccount(
//                102, 1, 10000.0, 10.0);
//
//        // Create the manager that will handle payment records.
//        PaymentManager manager = new PaymentManager();
//
//        // Register the customer in the manager.
//        manager.addCustomer(customer);
//
//        // Register both accounts in the manager.
//        manager.addAccount(wallet);
//        manager.addAccount(savings);
//
//        // Create a deposit transaction for the wallet.
//        Transaction deposit = new Transaction(2900,"tyyy456",10000.0,"saving",01/1/2020);
//
//        // Create a normal withdrawal transaction for savings.
//        Transaction withdrawal = new Transaction(567889,"ggg567",4000.0,"wallent",03/3/2015);
//
//        // Create a withdrawal that exceeds the available balance.
//        Transaction largeWithdrawal = new Transaction(34567,"xcvbn567",20000.0,"withdrow",07/9/2010);
//
//        // Display the project title and customer.
//        System.out.println("===== IgirePay Exercise 1.3 =====");
//        System.out.println(customer);
//        System.out.println();
//
//        // Process the first deposit. It should succeed.
//        System.out.println("First deposit: "
//                + manager.processTransaction(deposit));
//
//        // Try the same transaction again.
//        // It should fail because REF-001 was already processed.
//        System.out.println("Repeat deposit: "
//                + manager.processTransaction(deposit));
//
//        // Process a withdrawal from the savings account.
//        System.out.println("Savings withdrawal: "
//                + manager.processTransaction(withdrawal));
//
//        // Attempt a withdrawal that is too large.
//        System.out.println("Large withdrawal: "
//                + manager.processTransaction(largeWithdrawal));
//
//        // Display all accounts belonging to customer ID 1.
//        System.out.println();
//        System.out.println("===== Customer Accounts =====");
//
//        for (Account account : manager.getAccountsForCustomer(1)) {
//            System.out.println(account);
//        }
//
//        // Display only successfully processed transactions.
//        System.out.println();
//        System.out.println("===== Successful Transaction History =====");
//
//        for (Transaction transaction : manager.getTransactionHistory()) {
//            System.out.println(transaction);
//        }
//
//        // Display the reference IDs that were processed successfully.
//        System.out.println();
//        System.out.println("===== Processed Reference IDs =====");
//        System.out.println(manager.getProcessedReferenceIds());
//
//        // Display messages about rejected or failed transactions.
//        System.out.println();
//        System.out.println("===== Failed Transaction Logs =====");
//
//        for (String log : manager.getFailedTransactionLogs()) {
//            System.out.println(log);
//        }
//    }
//}

package LAB1;

import java.time.LocalDateTime;

public class Exercise13Main {

    public static void main(String[] args) {

        // Create the customer and accounts.
        Customer customer = new Customer(
                1, "Christella M",
                "christella@example.com", "0790830834");

        WalletAccount wallet = new WalletAccount(
                101, 1, 5000.0, true);

        SavingsAccount savings = new SavingsAccount(
                102, 1, 10000.0, 10.0);

        // Create the payment manager for Exercise 1.3.
        PaymentManager manager = new PaymentManager();

        manager.addCustomer(customer);
        manager.addAccount(wallet);
        manager.addAccount(savings);

        // Create three transactions with the correct argument types.
        Transaction deposit = new Transaction(
                1, 101, "REF-001", 500.0,
                "DEPOSIT", LocalDateTime.now());

        Transaction withdrawal = new Transaction(
                2, 102, "REF-002", 100.0,
                "WITHDRAWAL", LocalDateTime.now());

        Transaction largeWithdrawal = new Transaction(
                3, 102, "REF-003", 20000.0,
                "WITHDRAWAL", LocalDateTime.now());

        System.out.println("===== IgirePay Exercise 1.3 =====");
        System.out.println(customer);
        System.out.println();

        // The first deposit should succeed.
        System.out.println("First deposit: "
                + manager.processTransaction(deposit));

        // The same reference should be rejected.
        System.out.println("Repeat deposit: "
                + manager.processTransaction(deposit));

        // Process the savings withdrawal.
        System.out.println("Savings withdrawal: "
                + manager.processTransaction(withdrawal));

        // This withdrawal should fail.
        System.out.println("Large withdrawal: "
                + manager.processTransaction(largeWithdrawal));

        // Display the customer's accounts.
        System.out.println("\n===== Customer Accounts =====");

        for (Account account : manager.getAccountsForCustomer(1)) {
            System.out.println(account);
        }

        // Display successful transactions.
        System.out.println("\n===== Successful Transaction History =====");

        for (Transaction transaction : manager.getTransactionHistory()) {
            System.out.println(transaction);
        }

        // Display the successfully processed reference IDs.
        System.out.println("\n===== Processed Reference IDs =====");
        System.out.println(manager.getProcessedReferenceIds());

        // Display failed transaction messages.
        System.out.println("\n===== Failed Transaction Logs =====");

        for (String log : manager.getFailedTransactionLogs()) {
            System.out.println(log);
        }
    }
}