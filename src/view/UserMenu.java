package view;

import service.BookBorrowService;
import service.BookCategoryService;
import service.BookService;
import service.UserService;

import java.util.Scanner;

public class UserMenu {

    private final UserService userService;
    private final BookService bookService;
    private final BookCategoryService bookCategoryService;

    private final BookBorrowService bookBorrowService;

    public UserMenu(UserService userService, BookService bookService, BookCategoryService bookCategoryService, BookBorrowService bookBorrowService) {
        this.userService = userService;
        this.bookService = bookService;
        this.bookCategoryService = bookCategoryService;
        this.bookBorrowService = bookBorrowService;
    }

    public void showUserMenu() {
        while (true) {
            System.out.println("------------ THƯ VIỆN MINI (User) ------------");
            System.out.println("1. Quản lý thông tin tài khoản");
            System.out.println("2. Quản lý thông tin mượn trả sách");
            System.out.println("3. Quản lý lượt đánh giá sách");
            System.out.println("4. Thoát");
            int featureChoice = new Scanner(System.in).nextInt();
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
            System.out.println("2. Xem lượt đánh giá");
            System.out.println("3. Thoát");
            int featureChoice = new Scanner(System.in).nextInt();
            switch (featureChoice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
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
            int featureChoice = new Scanner(System.in).nextInt();
            switch (featureChoice) {
                case 1:
                    // TODO - làm
                    break;
                case 2:
                    // TODO - làm
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
            int featureChoice = new Scanner(System.in).nextInt();
            switch (featureChoice) {
                case 1:
                    // TODO - làm
                    break;
                case 2:
                    // TODO - làm
                    break;
                case 3:
                    // TODO - làm
                    break;
                case 4:
                    // TODO - làm
                    break;
                case 5:
                    return;
            }
        }
    }

}
