package LAB1;

// Import classes that help us manage data using collections.
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PaymentManager {

    // Map stores each customer using their customer ID.
    private Map<Integer, Customer> customers = new HashMap<>();

    // Map stores each account using its account ID.
    private Map<Integer, Account> accounts = new HashMap<>();

    // List stores the history of successful transactions.
    private List<Transaction> transactionHistory = new ArrayList<>();

    // Set stores reference IDs that have already been processed.
    // A Set helps us identify duplicate transaction requests.
    private Set<String> processedReferenceIds = new HashSet<>();

    // List stores messages about failed transactions.
    private List<String> failedTransactionLogs = new ArrayList<>();

    // Add a customer to the Map using the customer's ID.
    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    // Add an account to the Map using the account's ID.
    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    // Process a transaction and return true if it succeeds.
    public boolean processTransaction(Transaction transaction) {

        // Check whether the transaction object exists.
        if (transaction == null) {
            failedTransactionLogs.add("Transaction is missing.");
            return false;
        }

        // Get the reference ID of the transaction.
        String referenceId = transaction.getReferenceId();

        // Reject a transaction with a missing reference ID.
        if (referenceId == null || referenceId.trim().isEmpty()) {
            failedTransactionLogs.add(
                    "Missing transaction reference ID.");
            return false;
        }

        // Check whether this reference ID was processed before.
        if (processedReferenceIds.contains(referenceId)) {
            failedTransactionLogs.add(
                    "Duplicate transaction rejected: " + referenceId);
            return false;
        }

        // Find the account that belongs to this transaction.
        Account account = accounts.get(transaction.getTransactionId());

        // Reject the transaction if the account does not exist.
        if (account == null) {
            failedTransactionLogs.add(
                    "Account not found for reference: " + referenceId);
            return false;
        }

        // Ask the account to process the deposit or withdrawal.
        boolean successful = account.processTransaction(transaction);

        // Save the transaction only when it succeeds.
        if (successful) {

            // Remember this reference ID to prevent duplicates.
            processedReferenceIds.add(referenceId);

            // Add the successful transaction to the history.
            transactionHistory.add(transaction);

        } else {
            // Record a message when the transaction fails.
            failedTransactionLogs.add(
                    "Transaction failed: " + referenceId);
        }

        // Return the result of the transaction.
        return successful;
    }

    // Find all accounts belonging to a particular customer.
    public List<Account> getAccountsForCustomer(int customerId) {

        // Create a new List to hold the customer's accounts.
        List<Account> customerAccounts = new ArrayList<>();

        // Go through every account stored in the Map.
        for (Account account : accounts.values()) {

            // Compare the account's customer ID with the given ID.
            if (account.getCustomerId() == customerId) {

                // Add matching accounts to the List.
                customerAccounts.add(account);
            }
        }

        // Return the accounts found for this customer.
        return customerAccounts;
    }

    // Return a copy of the transaction history.
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    // Return a copy of the processed reference IDs.
    public Set<String> getProcessedReferenceIds() {
        return new HashSet<>(processedReferenceIds);
    }

    // Return a copy of the failed transaction logs.
    public List<String> getFailedTransactionLogs() {
        return new ArrayList<>(failedTransactionLogs);
    }
}