//package LAB1;
//
//import java.time.LocalDateTime;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        Customer customer = new Customer(
//                1, "Christella M",
//                "christella@example.com", "0790830834");
//
//        WalletAccount wallet = new WalletAccount(
//                101, customer.getId(), 5000.0, true);
//
//        SavingsAccount savings = new SavingsAccount(
//                102, customer.getId(), 10000.0, 10.0);
//
//        int i = 00;
//        Transaction deposit = new Transaction(3434,"rty5567",60000.0,"rtyio",15/7/2026);
//
//        Transaction withdrawal = new Transaction(24545,"eryeuig",27584.0,"erhgjrr",21/3/2025);
//
//
//        Transaction largeWithdrawal = new Transaction(235777,"hhfuf553",30000.0,"yrfgg",20/3/2020);
//
//        System.out.println("===== IgirePay Payment System =====");
//        System.out.println(customer);
//        System.out.println();
//
//        System.out.println("===== Exercise 1.2: Transactions =====");
//
//        boolean depositResult = wallet.processTransaction(deposit);
//        System.out.println("Deposit successful: " + depositResult);
//        System.out.println("Wallet balance: " + wallet.getBalance());
//        System.out.println();
//
//        boolean withdrawalResult =
//                savings.processTransaction(withdrawal);
//        System.out.println("Withdrawal successful: " + withdrawalResult);
//        System.out.println("Savings balance: " + savings.getBalance());
//        System.out.println();
//
//        boolean largeWithdrawalResult =
//                savings.processTransaction(largeWithdrawal);
//        System.out.println("Large withdrawal successful: "
//                + largeWithdrawalResult);
//        System.out.println("Savings balance: " + savings.getBalance());
//
//        System.out.println();
//        System.out.println("===== Final Account Details =====");
//        System.out.println(wallet);
//        System.out.println(savings);
//    }
//}

package LAB1;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        // Create the customer.
        Customer customer = new Customer(
                1, "Christella M",
                "christella@example.com", "0790830834");

        // Create wallet and savings accounts.
        WalletAccount wallet = new WalletAccount(
                101, customer.getId(), 5000.0, true);

        SavingsAccount savings = new SavingsAccount(
                102, customer.getId(), 10000.0, 10.0);

        // Create a deposit transaction.
        Transaction deposit = new Transaction(
                1, 101, "REF-001", 500.0,
                "DEPOSIT", LocalDateTime.now());

        // Create a normal savings withdrawal.
        Transaction withdrawal = new Transaction(
                2, 102, "REF-002", 100.0,
                "WITHDRAWAL", LocalDateTime.now());

        // Create a withdrawal that exceeds the available balance.
        Transaction largeWithdrawal = new Transaction(
                3, 102, "REF-003", 20000.0,
                "WITHDRAWAL", LocalDateTime.now());

        System.out.println("===== IgirePay Payment System =====");
        System.out.println(customer);

        System.out.println("\n===== Exercise 1.2: Transactions =====");

        boolean depositResult =
                wallet.processTransaction(deposit);

        System.out.println("Deposit successful: " + depositResult);
        System.out.println("Wallet balance: " + wallet.getBalance());

        System.out.println();

        boolean withdrawalResult =
                savings.processTransaction(withdrawal);

        System.out.println("Withdrawal successful: " + withdrawalResult);
        System.out.println("Savings balance: " + savings.getBalance());

        System.out.println();

        boolean largeWithdrawalResult =
                savings.processTransaction(largeWithdrawal);

        System.out.println("Large withdrawal successful: "
                + largeWithdrawalResult);
        System.out.println("Savings balance: " + savings.getBalance());

        System.out.println("\n===== Final Account Details =====");
        System.out.println(wallet);
        System.out.println(savings);
    }
}