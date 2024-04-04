package service;


import entity.Transaction;
import entity.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TransactionService {
    private final List<Transaction> transactionHistories = new ArrayList<>();

    private final UserService userService;


    public TransactionService(UserService userService) {
        this.userService = userService;
    }

    public void saveTransaction(Transaction transaction) {
        transactionHistories.add(transaction);
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
}
