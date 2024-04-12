package view;

import constant.UserRole;
import entity.User;
import main.Main;
import service.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private final UserService userService = new UserService();
    private final BookCategoryService bookCategoryService = new BookCategoryService();
    private final BookService bookService = new BookService(bookCategoryService);
    private final TransactionService transactionService = new TransactionService(userService);
    private final VoteHistoryService voteHistoryService = new VoteHistoryService(userService, bookService);
    private final BookBorrowService bookBorrowService = new BookBorrowService(userService, bookService, transactionService);


    private final AdminMenu adminMenu = new AdminMenu(userService, bookService, bookCategoryService, bookBorrowService, transactionService);
    private final UserMenu userMenu = new UserMenu(userService, bookBorrowService, transactionService, voteHistoryService,bookService);

    public void showMenu() {
        while (true) {
            System.out.println("---------- THƯ VIỆN MINI -----------");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký");
            System.out.println("3. Thoát");
            System.out.print("Xin mời chọn chức năng: ");
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
                    User user = userService.login();
                    if (user == null) {
                        System.out.println("Vượt quá số lần đăng nhập cho phép, vui lòng liên hệ admin để hỗ trợ");
                        return;
                    }
                    Main.loggedInUser = user;
                    if (user.getRole().equals(UserRole.USER)) {
                        userMenu.showUserMenu();
                        break;
                    }
                    adminMenu.showAdminMenu();
                    break;
                case 2:
                    userService.register();
                    break;
                case 3:
                    return;

            }
        }
    }

    // FILE - thêm haàm này, mỗi đối tượng có lưu trữ data thì đều cần các hàm set và findCurreantAutoId
    public void initializeData() {
        userService.setUsers();
        userService.createDefaultAdminUser(); // haàm này sẽ tự động tạo admin user nếu chua có, neu co rồi thì không
        // tạo nua . Hàm này hình như chưa được dùng .
        userService.findCurrentAutoId();
        // ví du
        bookService.setBooks();
        bookService.findCurrentAutoId();

        voteHistoryService.setVoteHistories();

        transactionService.setTransactionHistories();

        bookCategoryService.setBookCategories();
        bookCategoryService.findCurrentAutoId();

        bookBorrowService.setBookBorrows();
        bookBorrowService.findCurrentAutoId();

    }

}
