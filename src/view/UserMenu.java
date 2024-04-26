package view;

import entity.BookBorrow;
import entity.Transaction;
import entity.User;
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

    private final BookService bookService;


    public UserMenu(UserService userService, BookBorrowService bookBorrowService, TransactionService transactionService,
                    VoteHistoryService voteHistoryService, BookService bookService) {
        this.userService = userService;
        this.bookBorrowService = bookBorrowService;
        this.transactionService = transactionService;
        this.voteHistoryService = voteHistoryService;
        this.bookService = bookService;
    }

    public void showUserMenu() {
        while (true) {
            System.out.println("------------ THƯ VIỆN MINI (User) ------------");
            System.out.println("1. Quản lý thông tin tài khoản");
            System.out.println("2. Xem thông tin mượn trả sách");
            System.out.println("3. Quản lý lượt đánh giá sách");
            System.out.println("4. Tìm kiếm sách");
            System.out.println("5. Đăng xuất");
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
                    showAccountManagementMenu();
                    break;
                case 2:
                    showBookBorrowingManagementMenu();
                    break;
                case 3:
                    showBookVotingMenu();
                    break;
                case 4:
                    findBookMenu();
                    break;
                case 5:
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
                    bookBorrowService.showBookBorrows(bookBorrows);
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
                    userService.updateUserInformation(Main.loggedInUser.getId());
                    break;
                case 2:
                    User user = Main.loggedInUser;
                    transactionService.deposit(user);
                    break;
                case 3:
                    ArrayList<Transaction> transactions = transactionService.showTransactionHistories();
                    transactionService.showTransactions(transactions);
                    break;
                case 4:
                    userService.showBalance();
                    break;
                case 5:
                    return;
            }
        }
    }

    private void findBookMenu() {
        while (true) {
            System.out.println("------------ Menu tìm kiếm sách  ------------");
            System.out.println("1. Tìm kiếm sách theo tên ");
            System.out.println("2. tìm kiếm sách theo lượt vote từ 4 - 5 sao  ");
            System.out.println("3. tìm kiếm sách theo thể loại sách  ");
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
                    bookService.findBookByName();
                    break;
                case 2:
                    bookService.findBookByVoteStar();
                    break;
                case 3:
                    bookService.findBookByNameCategory();
                    break;
                case 4:
                    return;
            }
        }
    }

}
