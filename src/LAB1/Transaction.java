//package LAB1;
//
//import java.time.LocalDateTime;
//
//public class Transaction {
//    private int transactionId;
//    private String referenceId;
//    private double amount;
//    private String transactionType;
//    private int  timestamp;
//
//    public Transaction(int transactionId,
//                       String referenceId, double amount,
//                       String transactionType,
//                       int timestamp) {
//        this.transactionId = transactionId;
//        this.referenceId = referenceId;
//        this.amount = amount;
//        this.transactionType = transactionType;
//        this.timestamp = timestamp;
//    }
//    public Transaction(int id, int accountId, String referenceId, double amount, String transactionType, LocalDateTime time) {
//    }
//
//
//    public int getTransactionId() {
//        return transactionId;
//    }
//
//    public void setTransactionId(int transactionId) {
//        this.transactionId = transactionId;
//    }
//
//
//
//    public String getReferenceId() {
//        return referenceId;
//    }
//
//    public void setReferenceId(String referenceId) {
//        this.referenceId = referenceId;
//    }
//
//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
//
//    public String getTransactionType() {
//        return transactionType;
//    }
//
//    public void setTransactionType(String transactionType) {
//        this.transactionType = transactionType;
//    }
//
//    public int getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(int  timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    @Override
//    public String toString() {
//        return "Transaction ID: " + transactionId
//
//                + ", Reference ID: " + referenceId
//                + ", Amount: " + amount
//                + ", Type: " + transactionType
//                + ", Timestamp: " + timestamp;
//    }
//
//    public void getId(double amount){
//        System.out.println(amount);
//    }
//
//}
//

package LAB1;

import java.time.LocalDateTime;

public class Transaction {

    // Transaction information.
    private int transactionId;
    private int accountId;
    private String referenceId;
    private double amount;
    private String transactionType;
    private LocalDateTime timestamp;

    // Constructor initializes the transaction.
    public Transaction(int transactionId, int accountId,
                       String referenceId, double amount,
                       String transactionType,
                       LocalDateTime timestamp) {

        this.transactionId = transactionId;
        this.accountId = accountId;
        this.referenceId = referenceId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.timestamp = timestamp;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Display transaction details.
    @Override
    public String toString() {
        return "Transaction ID: " + transactionId
                + ", Account ID: " + accountId
                + ", Reference ID: " + referenceId
                + ", Amount: " + amount
                + ", Type: " + transactionType
                + ", Timestamp: " + timestamp;
    }
}