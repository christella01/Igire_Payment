package LAB1;

public class SavingsAccount extends Account {

    // Fee charged whenever money is withdrawn.
    private double withdrawalFee;

    // Constructor creates a savings account.
    public SavingsAccount(int accountId, int customerId,
                          double balance, double withdrawalFee) {

        // Initialize the common account information.
        super(accountId, customerId, "SAVINGS", balance);

        // Set the withdrawal fee.
        setWithdrawalFee(withdrawalFee);
    }

    // Returns the withdrawal fee.
    public double getWithdrawalFee() {
        return withdrawalFee;
    }

    // Updates the fee, but does not allow a negative fee.
    public void setWithdrawalFee(double withdrawalFee) {
        if (withdrawalFee >= 0) {
            this.withdrawalFee = withdrawalFee;
        }
    }

    // Override the parent's withdrawal method.
    @Override
    public boolean withdraw(double amount) {

        // Reject zero or negative withdrawals.
        if (amount <= 0) {
            return false;
        }

        // Calculate the withdrawal amount plus the fee.
        double totalAmount = amount + withdrawalFee;

        // Check whether the balance covers both amounts.
        if (totalAmount > getBalance()) {
            return false;
        }

        // Deduct the amount and the fee.
        setBalance(getBalance() - totalAmount);

        return true;
    }

    // Display the savings account details.
    @Override
    public String toString() {
        return super.toString()
                + ", Withdrawal Fee: " + withdrawalFee;
    }
}
