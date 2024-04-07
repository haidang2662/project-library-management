package service;


import entity.Transaction;
import entity.User;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private final FileUtil<Transaction> fileUtil = new FileUtil<>();
    private static final String TRANSACTION_DATA_FILE = "transactions.json";
    private List<Transaction> transactionHistories;

    private final UserService userService;


    public TransactionService(UserService userService) {
        this.userService = userService;
    }


    public void setTransactionHistories() {
        List<Transaction> transactionList = fileUtil.readDataFromFile(TRANSACTION_DATA_FILE, Transaction[].class);
        transactionHistories = transactionList != null ? transactionList : new ArrayList<>();
    }

    public void saveTransactionHistoriesData() {
        fileUtil.writeDataToFile(transactionHistories, TRANSACTION_DATA_FILE);
    }

    public void saveTransaction(Transaction transaction) {
        transactionHistories.add(transaction);
        saveTransactionHistoriesData(); // Lưu File
    }

    public ArrayList<Transaction> showTransactionHistories() {
        User user = userService.getLoggedInUser();
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : transactionHistories) {
            if (transaction.getUser().getId() == user.getId()) {
                transactions.add(transaction);
            }
        }
        return transactions;

    }

    public void showTransactions() {
        System.out.printf("%-30s%-20s%-15s%-25s%-30s%n", "User", "createdDate", "amount", "transactionType","transactionContent");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        for (Transaction transaction : transactionHistories) {
            showTransaction(transaction);
        }
    }

    public void showTransaction(Transaction transaction) {
        System.out.printf("%-30s%-20s%-15s%-25s%-30s%n",transaction.getUser(),transaction.getCreatedDate(),
                transaction.getAmount(),transaction.getTransactionType(),transaction.getTransactionContent() );
    }
}
