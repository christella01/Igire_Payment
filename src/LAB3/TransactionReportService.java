
//package LAB3;
//
//import LAB1.Transaction;
//import LAB2.TransactionDAO;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.sql.SQLException;
//import java.util.List;
//
//public class TransactionReportService {
//
//    // Export all transaction records from the database to a CSV file.
//    public void exportTransactionsToCSV(
//            TransactionDAO transactionDAO)
//            throws SQLException, IOException {
//
//        // Retrieve transaction records from PostgreSQL.
//        List<Transaction> transactions =
//                transactionDAO.getAllTransactions();
//
//        // The file will be created in the project's working directory.
//        Path filePath = Path.of("transactions.csv");
//
//        // Open the file and write using UTF-8 encoding.
//        try (BufferedWriter writer = Files.newBufferedWriter(
//                filePath, StandardCharsets.UTF_8)) {
//
//            // Write the column names as the first row.
//            writer.write(
//                    "Transaction ID,Account ID,Reference ID,"
//                            + "Amount,Transaction Type,Timestamp");
//            writer.newLine();
//
//            // Write every transaction as one row.
//            for (Transaction transaction : transactions) {
//
//                String timestamp = transaction.getTimestamp() == null
//                        ? ""
//                        : transaction.getTimestamp().toString();
//
//                writer.write(
//                        transaction.getTransactionId() + ","
//                                + transaction.getAccountId() + ","
//                                + csvValue(transaction.getReferenceId()) + ","
//                                + transaction.getAmount() + ","
//                                + csvValue(transaction.getTransactionType()) + ","
//                                + csvValue(timestamp)
//                );
//
//                writer.newLine();
//            }
//        }
//
//        System.out.println(
//                "Transaction history exported successfully.");
//        System.out.println(
//                "File location: " + filePath.toAbsolutePath());
//    }
//
//    // Keep commas and quotation marks inside CSV values correctly formatted.
//    private String csvValue(String value) {
//
//        if (value == null) {
//            return "";
//        }
//
//        return "\""
//                + value.replace("\"", "\"\"")
//                + "\"";
//    }
//}


//package LAB3;
//
//import LAB1.Transaction;
//import LAB2.TransactionDAO;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeMap;
//
//public class TransactionReportService {
//
//    // Export all saved transaction records to a CSV file.
//    public void exportTransactionsToCSV(
//            TransactionDAO transactionDAO)
//            throws SQLException, IOException {
//
//        // Get transaction records from the database.
//        List<Transaction> transactions =
//                transactionDAO.getAllTransactions();
//
//        Path filePath = Path.of("transactions.csv");
//
//        // Create the CSV file and write its contents.
//        try (BufferedWriter writer = Files.newBufferedWriter(
//                filePath, StandardCharsets.UTF_8)) {
//
//            // Write the column headings.
//            writer.write(
//                    "Transaction ID,Account ID,Reference ID,"
//                            + "Amount,Transaction Type,Timestamp");
//            writer.newLine();
//
//            // Write each transaction as one row.
//            for (Transaction transaction : transactions) {
//
//                String timestamp = transaction.getTimestamp() == null
//                        ? ""
//                        : transaction.getTimestamp().toString();
//
//                writer.write(
//                        transaction.getTransactionId() + ","
//                                + transaction.getAccountId() + ","
//                                + csvValue(transaction.getReferenceId()) + ","
//                                + transaction.getAmount() + ","
//                                + csvValue(transaction.getTransactionType()) + ","
//                                + csvValue(timestamp)
//                );
//
//                writer.newLine();
//            }
//        }
//
//        System.out.println(
//                "Transaction history exported successfully.");
//        System.out.println(
//                "File location: " + filePath.toAbsolutePath());
//    }
//
//    // Display a summary of transactions grouped by day.
//    public void displayDailySummary(
//            TransactionDAO transactionDAO) throws SQLException {
//
//        // Read transactions from PostgreSQL.
//        List<Transaction> transactions =
//                transactionDAO.getAllTransactions();
//
//        if (transactions.isEmpty()) {
//            System.out.println("No transactions found.");
//            return;
//        }
//
//        // TreeMap keeps the dates in chronological order.
//        Map<LocalDate, DailySummary> summaries = new TreeMap<>();
//
//        for (Transaction transaction : transactions) {
//
//            // A transaction without a timestamp cannot be grouped by date.
//            if (transaction.getTimestamp() == null) {
//                continue;
//            }
//
//            LocalDate date =
//                    transaction.getTimestamp().toLocalDate();
//
//            DailySummary summary = summaries.computeIfAbsent(
//                    date, key -> new DailySummary());
//
//            String type = transaction.getTransactionType();
//
//            if (type == null) {
//                continue;
//            }
//
//            type = type.trim().toUpperCase();
//
//            switch (type) {
//
//                case "DEPOSIT":
//                    summary.totalTransactions++;
//                    summary.depositCount++;
//                    summary.totalDeposits += transaction.getAmount();
//                    break;
//
//                case "WITHDRAWAL":
//                    summary.totalTransactions++;
//                    summary.withdrawalCount++;
//                    summary.totalWithdrawals += transaction.getAmount();
//                    break;
//
//                case "TRANSFER": {
//                    // A transfer creates two account records.
//                    // Count its reference only once in the daily summary.
//                    String referenceId = transaction.getReferenceId();
//
//                    if (referenceId != null
//                            && summary.transferReferences.add(referenceId)) {
//
//                        summary.totalTransactions++;
//                        summary.transferCount++;
//                        summary.totalTransfers += transaction.getAmount();
//                    }
//                    break;
//                }
//
//                default:
//                    System.out.println(
//                            "Skipped unrecognized transaction type: " + type);
//            }
//        }
//
//        // Print one summary for each date.
//        System.out.println("\n===== Daily Transaction Summary =====");
//
//        for (Map.Entry<LocalDate, DailySummary> entry
//                : summaries.entrySet()) {
//
//            LocalDate date = entry.getKey();
//            DailySummary summary = entry.getValue();
//
//            System.out.println("\nDate: " + date);
//            System.out.println(
//                    "Total transactions: " + summary.totalTransactions);
//
//            System.out.println(
//                    "Deposits: " + summary.depositCount
//                            + " | Total amount: "
//                            + summary.totalDeposits);
//
//            System.out.println(
//                    "Withdrawals: " + summary.withdrawalCount
//                            + " | Total amount: "
//                            + summary.totalWithdrawals);
//
//            System.out.println(
//                    "Transfers: " + summary.transferCount
//                            + " | Total amount: "
//                            + summary.totalTransfers);
//        }
//    }
//
//    // Format CSV values safely when they contain commas or quotation marks.
//    private String csvValue(String value) {
//
//        if (value == null) {
//            return "";
//        }
//
//        return "\"" + value.replace("\"", "\"\"") + "\"";
//    }
//
//    // Stores the summary numbers for one day.
//    private static class DailySummary {
//
//        private int totalTransactions;
//
//        private int depositCount;
//        private double totalDeposits;
//
//        private int withdrawalCount;
//        private double totalWithdrawals;
//
//        private int transferCount;
//        private double totalTransfers;
//
//        // Prevent counting both sides of a transfer twice.
//        private final Set<String> transferReferences = new HashSet<>();
//    }
//}


package LAB3;

import LAB1.Account;
import LAB1.Customer;
import LAB1.Transaction;

import LAB2.AccountDAO;
import LAB2.CustomerDAO;
import LAB2.TransactionDAO;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TransactionReportService {

    // Export all transaction records to a CSV file.
    public void exportTransactionsToCSV(
            TransactionDAO transactionDAO)
            throws SQLException, IOException {

        List<Transaction> transactions =
                transactionDAO.getAllTransactions();

        Path filePath = Path.of("transactions.csv");

        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath, StandardCharsets.UTF_8)) {

            writer.write(
                    "Transaction ID,Account ID,Reference ID,"
                            + "Amount,Transaction Type,Timestamp");
            writer.newLine();

            for (Transaction transaction : transactions) {

                String timestamp = transaction.getTimestamp() == null
                        ? ""
                        : transaction.getTimestamp().toString();

                writer.write(
                        transaction.getTransactionId() + ","
                                + transaction.getAccountId() + ","
                                + csvValue(transaction.getReferenceId()) + ","
                                + transaction.getAmount() + ","
                                + csvValue(transaction.getTransactionType()) + ","
                                + csvValue(timestamp)
                );

                writer.newLine();
            }
        }

        System.out.println(
                "Transaction history exported successfully.");
        System.out.println(
                "File location: " + filePath.toAbsolutePath());
    }

    // Display transaction totals grouped by date.
    public void displayDailySummary(
            TransactionDAO transactionDAO) throws SQLException {

        List<Transaction> transactions =
                transactionDAO.getAllTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        // TreeMap displays the dates in chronological order.
        Map<LocalDate, DailySummary> summaries = new TreeMap<>();

        for (Transaction transaction : transactions) {

            if (transaction.getTimestamp() == null) {
                continue;
            }

            LocalDate date =
                    transaction.getTimestamp().toLocalDate();

            DailySummary summary = summaries.computeIfAbsent(
                    date, key -> new DailySummary());

            String type = transaction.getTransactionType();

            if (type == null) {
                continue;
            }

            type = type.trim().toUpperCase();

            switch (type) {

                case "DEPOSIT":
                    summary.totalTransactions++;
                    summary.depositCount++;
                    summary.totalDeposits += transaction.getAmount();
                    break;

                case "WITHDRAWAL":
                    summary.totalTransactions++;
                    summary.withdrawalCount++;
                    summary.totalWithdrawals += transaction.getAmount();
                    break;

                case "TRANSFER": {
                    // Count each transfer reference once per day.
                    String referenceId = transaction.getReferenceId();

                    if (referenceId != null
                            && summary.transferReferences.add(referenceId)) {

                        summary.totalTransactions++;
                        summary.transferCount++;
                        summary.totalTransfers += transaction.getAmount();
                    }
                    break;
                }

                default:
                    System.out.println(
                            "Skipped unrecognized transaction type: " + type);
            }
        }

        System.out.println("\n===== Daily Transaction Summary =====");

        for (Map.Entry<LocalDate, DailySummary> entry
                : summaries.entrySet()) {

            LocalDate date = entry.getKey();
            DailySummary summary = entry.getValue();

            System.out.println("\nDate: " + date);
            System.out.println(
                    "Total transactions: " + summary.totalTransactions);

            System.out.println(
                    "Deposits: " + summary.depositCount
                            + " | Total amount: "
                            + summary.totalDeposits);

            System.out.println(
                    "Withdrawals: " + summary.withdrawalCount
                            + " | Total amount: "
                            + summary.totalWithdrawals);

            System.out.println(
                    "Transfers: " + summary.transferCount
                            + " | Total amount: "
                            + summary.totalTransfers);
        }
    }

    // Display all transactions belonging to a customer's accounts.
    public void displayCustomerStatement(
            int customerId,
            CustomerDAO customerDAO,
            AccountDAO accountDAO,
            TransactionDAO transactionDAO) throws SQLException {

        // First, check that the customer exists.
        Customer customer =
                customerDAO.getCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        // Get all accounts owned by this customer.
        List<Account> accounts =
                accountDAO.getAccountsByCustomerId(customerId);

        System.out.println("\n===== Customer Transaction Statement =====");
        System.out.println("Customer: " + customer);

        if (accounts.isEmpty()) {
            System.out.println("This customer has no accounts.");
            return;
        }

        boolean foundTransactions = false;

        // Display transaction history for each account.
        for (Account account : accounts) {

            System.out.println("\nAccount: " + account);

            List<Transaction> transactions =
                    transactionDAO.getTransactionsByAccountId(
                            account.getAccountId());

            if (transactions.isEmpty()) {
                System.out.println("No transactions for this account.");
            } else {
                foundTransactions = true;

                for (Transaction transaction : transactions) {
                    System.out.println(transaction);
                }
            }
        }

        if (!foundTransactions) {
            System.out.println(
                    "\nThis customer's accounts have no transactions yet.");
        }
    }

    // Format CSV values safely if they contain commas or quotation marks.
    private String csvValue(String value) {

        if (value == null) {
            return "";
        }

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    // Store summary figures for one day.
    private static class DailySummary {

        private int totalTransactions;

        private int depositCount;
        private double totalDeposits;

        private int withdrawalCount;
        private double totalWithdrawals;

        private int transferCount;
        private double totalTransfers;

        // Prevent counting both records of a transfer twice.
        private final Set<String> transferReferences = new HashSet<>();
    }
}

