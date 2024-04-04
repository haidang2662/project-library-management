package view;

import entity.BookBorrow;
import entity.Transaction;
import main.Main;
import service.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserMenu {

    private final UserService userService;

    private final BookBorrowService bookBorrowService;
    private final TransactionService transactionService;

    private final VoteHistoryService voteHistoryService;


    public UserMenu(UserService userService, BookBorrowService bookBorrowService, TransactionService transactionService,
                    VoteHistoryService voteHistoryService) {
        this.userService = userService;
        this.bookBorrowService = bookBorrowService;
        this.transactionService = transactionService;
        this.voteHistoryService = voteHistoryService;
    }

    public void showUserMenu() {
        while (true) {
            System.out.println("------------ THƯ VIỆN MINI (User) ------------");
            System.out.println("1. Quản lý thông tin tài khoản");
            System.out.println("2. Quản lý thông tin mượn trả sách");
            System.out.println("3. Quản lý lượt đánh giá sách");
            System.out.println("4. Thoát");
            int featureChoice;
            while (true) {
                try {
                    featureChoice = new Scanner(System.in).nextInt();
                    if (featureChoice < 1 || featureChoice > 4) {
                        System.out.println("Chức năng là số từ 1 tới 4, vui lòng nhập lại: ");
                        continue;
                    }
                    break;
                } catch (InputMismatchException ex) {
                    System.out.print("Lựa chọn phải là một số nguyên, vui lòng nhập lại: ");
                }
            }
            switch (featureChoice) {
                case 1:
                    showAccountManagementMenu();
                    break;
                case 2:
                    showBookBorrowingManagementMenu();
                    break;
                case 3:
                    showBookVotingMenu();
                    break;
                case 4:
                    return;
            }
        }
    }

    private void showBookVotingMenu() {
        while (true) {
            System.out.println("------------ Quản lý lượt đánh giá sách ------------");
            System.out.println("1. Thực hiện đánh giá sách"); // vote
            System.out.println("2. Xem lượt đánh giá sách theo ID sách ");
            System.out.println("3. Xem lượt đánh giá sách theo tên sách ");
            System.out.println("4. Thoát");
            int featureChoice;
            while (true) {
                try {
                    featureChoice = new Scanner(System.in).nextInt();
                    if (featureChoice < 1 || featureChoice > 4) {
                        System.out.println("Chức năng là số từ 1 tới 4, vui lòng nhập lại: ");
                        continue;
                    }
                    break;
                } catch (InputMismatchException ex) {
                    System.out.print("Lựa chọn phải là một số nguyên, vui lòng nhập lại: ");
                }
            }
            switch (featureChoice) {
                case 1:
                    voteHistoryService.inputVote();
                    break;
                case 2:
                    voteHistoryService.findHistoryVoteByBookId();
                    break;
                case 3:
                    voteHistoryService.findHistoryVoteByBookName();
                    break;
                case 4:
                    return;
            }
        }
    }

    private void showBookBorrowingManagementMenu() {
        while (true) {
            System.out.println("------------ Quản lý thông tin mượn/trả sách ------------");
            System.out.println("1. Xem lượt mượn gần nhất");
            System.out.println("2. Xem lịch sử mượn/trả sách");
            System.out.println("3. Thoát");
            int featureChoice;
            while (true) {
                try {
                    featureChoice = new Scanner(System.in).nextInt();
                    if (featureChoice < 1 || featureChoice > 3) {
                        System.out.println("Chức năng là số từ 1 tới 3, vui lòng nhập lại: ");
                        continue;
                    }
                    break;
                } catch (InputMismatchException ex) {
                    System.out.print("Lựa chọn phải là một số nguyên, vui lòng nhập lại: ");
                }
            }
            switch (featureChoice) {
                case 1:
                    bookBorrowService.findNearestBorrowByUserId();
                    break;
                case 2:
                    List<BookBorrow> bookBorrows = bookBorrowService.findByUserId(Main.loggedInUser.getId());
                    System.out.println(bookBorrows);
                    break;
                case 3:
                    return;
            }
        }
    }

    private void showAccountManagementMenu() {
        while (true) {
            System.out.println("------------ Quản lý thông tin tài khoản ------------");
            System.out.println("1. Cập nhật thông tin tài khoản");
            System.out.println("2. Nạp tiền");
            System.out.println("3. Xem lịch sử giao dịch");
            System.out.println("4. Xem số dư tài khoản");
            System.out.println("5. Thoát");
            int featureChoice;
            while (true) {
                try {
                    featureChoice = new Scanner(System.in).nextInt();
                    if (featureChoice < 1 || featureChoice > 5) {
                        System.out.println("Chức năng là số từ 1 tới 5, vui lòng nhập lại: ");
                        continue;
                    }
                    break;
                } catch (InputMismatchException ex) {
                    System.out.print("Lựa chọn phải là một số nguyên, vui lòng nhập lại: ");
                }
            }
            switch (featureChoice) {
                case 1:
                    userService.updateUserInformation();
                    break;
                case 2:
                    userService.updateBalance();
                    break;
                case 3:
                    ArrayList<Transaction> transactions = transactionService.showTransactionHistories();
                    System.out.println(transactions);
                    break;
                case 4:
                    userService.showBalance();
                    break;
                case 5:
                    return;
            }
        }
    }

}
