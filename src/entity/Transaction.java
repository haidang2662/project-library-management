package entity;

import constant.TransactionType;

import java.time.LocalDate;

public class Transaction {
    private User user;
    private LocalDate createdDate;
    private double amount;
    private TransactionType transactionType;
    private String transactionContent;

    public Transaction(User user, LocalDate createdDate, double amount, TransactionType transactionType, String transactionContent) {
        this.user = user;
        this.createdDate = createdDate;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionContent = transactionContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionContent() {
        return transactionContent;
    }

    public void setTransactionContent(String transactionContent) {
        this.transactionContent = transactionContent;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "user=" + user +
                ", createdDate=" + createdDate +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                ", transactionContent='" + transactionContent + '\'' +
                '}';
    }
}
