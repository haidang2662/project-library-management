package service;


import constant.TransactionType;
import entity.Transaction;
import entity.User;
import main.Main;
import util.FileUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
        System.out.printf("%-50s%-20s%-15s%-25s%-30s%n", "User", "createdDate", "amount", "transactionType","transactionContent");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        for (Transaction transaction : transactionHistories) {
            showTransaction(transaction);
        }
    }

    public void showTransaction(Transaction transaction) {
        System.out.printf("%-50s%-20s%-15s%-25s%-30s%n",transaction.getUser(),transaction.getCreatedDate(),
                transaction.getAmount(),transaction.getTransactionType(),transaction.getTransactionContent() );
    }

    public void showTransactions(List<Transaction> transactions1) {
        System.out.printf("%-50s%-20s%-15s%-25s%-30s%n", "User", "createdDate", "amount", "transactionType","transactionContent");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        for (Transaction transaction : transactions1) {
            showTransaction(transaction);
        }
    }

    public void updateBalance() {
        User user = Main.loggedInUser;
        double money;
        while (true) {
            try {
                System.out.println("Mời bạn nhập số tiền muốn nạp : ");
                money = new Scanner(System.in).nextDouble();
                if (money < 0) {
                    System.out.println("số tiền nạp vào bắt buộc phải > 0 , vui lòng nhập lại ");
                    continue;
                }
                break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số tự nhiên hợp lệ
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số tự nhiên . Vui lòng nhập lại.");
            }
        }

        user.setBalance(user.getBalance() + money);
        userService.saveUserData();// FILE - khi có thay đổi về list user, can luu vao file
        Transaction transaction = new Transaction(user,LocalDate.now(),money,TransactionType.DEPOSIT,"Nạp tiền vào tài khoản");
        transactionHistories.add(transaction);
        saveTransactionHistoriesData();
    }
}
