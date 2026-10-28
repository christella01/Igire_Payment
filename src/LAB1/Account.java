package LAB1;

import java.time.LocalDateTime;

public class Account {

    private int accountId;
    private int customerId;
    private String accountType;
    private double balance;
    private LocalDateTime createdAt;

    public Account(int accountId, int customerId,
                   String accountType, double balance) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }

        balance = balance + amount;
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance = balance - amount;
        return true;
    }

    public boolean processTransaction(Transaction transaction) {
        if (transaction == null) {
            return false;
        }

        // Check whether this transaction belongs to this account.
        if (transaction.getAccountId() != accountId) {
            return false;
        }

        String type = transaction.getTransactionType();

        if (type == null) {
            return false;
        }

        if (type.equalsIgnoreCase("DEPOSIT")) {
            return deposit(transaction.getAmount());
        }

        if (type.equalsIgnoreCase("WITHDRAWAL")
                || type.equalsIgnoreCase("WITHDRAW")) {
            return withdraw(transaction.getAmount());
        }

        return false;
    }

    @Override
    public String toString() {
        return "Account ID: " + accountId
                + ", Customer ID: " + customerId
                + ", Type: " + accountType
                + ", Balance: " + balance
                + ", Created At: " + createdAt;
    }
}