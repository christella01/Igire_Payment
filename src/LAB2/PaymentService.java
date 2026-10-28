package LAB2;

import LAB1.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PaymentService {

    // Database connection and the three DAO classes.
    private Connection connection;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private ProcessedRequestDAO requestDAO;

    // Constructor: receive the same database connection.
    public PaymentService(Connection connection) {
        this.connection = connection;

        accountDAO = new AccountDAO(connection);
        transactionDAO = new TransactionDAO(connection);
        requestDAO = new ProcessedRequestDAO(connection);
    }

    // DEPOSIT: Add money and save its transaction.
    public boolean deposit(int accountId, double amount,
                           String referenceId) throws SQLException {

        return processOneAccountTransaction(
                accountId, amount, referenceId, "DEPOSIT");
    }

    // WITHDRAW: Remove money and save its transaction.
    public boolean withdraw(int accountId, double amount,
                            String referenceId) throws SQLException {

        return processOneAccountTransaction(
                accountId, amount, referenceId, "WITHDRAWAL");
    }

    // Process a deposit or withdrawal as one database transaction.
    private boolean processOneAccountTransaction(
            int accountId, double amount,
            String referenceId, String type) throws SQLException {

        // Validate transaction details before changing the database.
        validatePayment(amount, referenceId);

        boolean previousAutoCommit = connection.getAutoCommit();

        // Begin a database transaction.
        connection.setAutoCommit(false);

        try {
            // Register the reference first.
            // If it already exists, reject the repeated request.
            boolean newRequest =
                    requestDAO.addProcessedRequest(referenceId);

            if (!newRequest) {
                connection.rollback();
                return false;
            }

            // Change the account balance.
            boolean balanceChanged;

            if (type.equals("DEPOSIT")) {
                balanceChanged = accountDAO.deposit(accountId, amount);
            } else {
                balanceChanged = accountDAO.withdraw(accountId, amount);
            }

            // If the account does not exist or the withdrawal fails,
            // undo the reference registration too.
            if (!balanceChanged) {
                connection.rollback();
                return false;
            }

            // Create the transaction record.
            Transaction transaction = new Transaction(
                    0,
                    accountId,
                    referenceId.trim(),
                    amount,
                    type,
                    LocalDateTime.now()
            );

            transactionDAO.addTransaction(transaction);

            // Save all changes together.
            connection.commit();

            return true;

        } catch (SQLException | RuntimeException e) {

            // Undo database changes if any step fails.
            connection.rollback();
            throw e;

        } finally {
            // Restore the original connection setting.
            connection.setAutoCommit(previousAutoCommit);
        }
    }

    // TRANSFER: Send money between two accounts.
    public boolean transfer(int fromAccountId, int toAccountId,
                            double amount, String referenceId)
            throws SQLException {

        validatePayment(amount, referenceId);

        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException(
                    "Source and destination accounts must be different.");
        }

        boolean previousAutoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);

        try {
            // Reject a reference that has already been processed.
            boolean newRequest =
                    requestDAO.addProcessedRequest(referenceId);

            if (!newRequest) {
                connection.rollback();
                return false;
            }

            // First withdraw from the sending account.
            boolean withdrawn =
                    accountDAO.withdraw(fromAccountId, amount);

            if (!withdrawn) {
                connection.rollback();
                return false;
            }

            // Then deposit into the receiving account.
            boolean deposited =
                    accountDAO.deposit(toAccountId, amount);

            if (!deposited) {
                connection.rollback();
                return false;
            }

            // Record the sending account side of the transfer.
            Transaction outgoing = new Transaction(
                    0, fromAccountId, referenceId.trim(),
                    amount, "TRANSFER", LocalDateTime.now());

            transactionDAO.addTransaction(outgoing);

            // Record the receiving account side of the transfer.
            Transaction incoming = new Transaction(
                    0, toAccountId, referenceId.trim(),
                    amount, "TRANSFER", LocalDateTime.now());

            transactionDAO.addTransaction(incoming);

            // Commit both balances and both records together.
            connection.commit();

            return true;

        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw e;

        } finally {
            connection.setAutoCommit(previousAutoCommit);
        }
    }

    // Check that a payment amount and reference are valid.
    private void validatePayment(double amount, String referenceId) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero.");
        }

        if (referenceId == null
                || referenceId.trim().isEmpty()
                || referenceId.trim().length() > 100) {
            throw new IllegalArgumentException(
                    "Reference ID must contain 1 to 100 characters.");
        }
    }
}
