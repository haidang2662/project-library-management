package view;

import entity.Book;
import entity.BookBorrow;
import entity.User;
import service.BookBorrowService;
import service.BookCategoryService;
import service.BookService;
import service.UserService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private final UserService userService;
    private final BookService bookService;
    private final BookCategoryService bookCategoryService;

    private final BookBorrowService bookBorrowService;

    public AdminMenu(UserService userService, BookService bookService, BookCategoryService bookCategoryService, BookBorrowService bookBorrowService) {
        this.userService = userService;
        this.bookService = bookService;
        this.bookCategoryService = bookCategoryService;
        this.bookBorrowService = bookBorrowService;
    }

    public void showAdminMenu() {
        while (true) {
            bookBorrowService.showBookBorrows();
            System.out.println("------------ MENU ADMIN MANAGEMENT ------------");
            System.out.println("1. Quản lý sách");// show ra menu  có nhập sách vào kho, cập nhật thông tin sách
            System.out.println("2. Quản lý mượn trả sách"); // show menu con: mượn, trả
            System.out.println("3. Quản lý thể loại sách"); // thêm sửa xóa thể loại
            System.out.println("4. Quản lý người dùng"); // Thêm , sửa , xóa User
            System.out.println("5. Đăng xuất");
            System.out.print("Chọn chức năng: ");
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
                    showBookManagementMenu();
                    break;
                case 2:
                    showBookBorrowingMenuManagement();
                    break;
                case 3:
                    showCategoryManagementMenu();
                    break;
                case 4:
                    showUserManagementMenu();
                    break;
                case 5:
                    return;
            }
        }
    }

    private void showBookBorrowingMenuManagement() {
        while (true) {
            System.out.println("---------- QUẢN LÝ MƯỢN/TRẢ SÁCH ------------");
            System.out.println("1. Lập phiếu mượn sách");
            System.out.println("2. Thực hiện thủ tục trả sách");
            System.out.println("3. Tìm kiếm lượt mượn/trả sách theo ID bạn đọc");
            System.out.println("4. Tìm kiếm lượt mượn/trả sách theo tên sách");
            System.out.println("5. Tìm kiếm lượt mượn/trả sách theo tên bạn đọc");
            System.out.println("6. Thoát");
            System.out.println("Mới bạn chọn chức năng: ");
            int featureChoice;
            while (true) {
                try {
                    featureChoice = new Scanner(System.in).nextInt();
                    if (featureChoice < 1 || featureChoice > 6) {
                        System.out.println("Chức năng là số từ 1 tới 6, vui lòng nhập lại: ");
                        continue;
                    }
                    break;
                } catch (InputMismatchException ex) {
                    System.out.print("Lựa chọn phải là một số nguyên, vui lòng nhập lại: ");
                }
            }
            switch (featureChoice) {
                case 1:
                    bookBorrowService.createBorrow();
                    break;
                case 2:
                    bookBorrowService.returnBook();
                    break;
                case 3:
                    List<BookBorrow> borrows2 = bookBorrowService.findByUserIdShowMenu();
                    System.out.println(borrows2);
                    break;
                case 4:
                    List<BookBorrow> borrows = bookBorrowService.findByBookName();
                    System.out.println(borrows);
                    break;
                case 5:
                    List<BookBorrow> borrows1 = bookBorrowService.findByUserName();
                    System.out.println(borrows1);
                    break;
                case 6:
                    return;
            }
        }
    }


    public void showBookManagementMenu() {
        while (true) {
            System.out.println("---------- QUẢN LÝ KHO SÁCH ------------");
            System.out.println("1. Nhập sách mới vào kho");
            System.out.println("2. Sửa thông tin sách đang có");
            System.out.println("3. Tìm kiếm sách theo tên");
            System.out.println("4. Tìm kiếm sách theo tên thể loại");
            System.out.println("5. Thoát");
            System.out.println("Mới bạn chọn chức năng: ");
            int featureChoice;
            while (true) {
                try {
                    featureChoice = new Scanner(System.in).nextInt();
                    if (featureChoice < 1 || featureChoice > 5) {
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
                    bookService.inputBook();
                    break;
                case 2:
                    bookService.updateBook();
                    break;
                case 3:
                    bookService.findBookByName();
                    // TODO - tìm kiếm sách theo tên . Đã làm xong .
                    break;
                case 4:
                    bookService.findBookByNameCategory();
                    // TODO - tìm kiếm sách theo thể loại . Đã làm xong .
                    break;
                case 5:
                    return;
            }
        }
    }

    public void showUserManagementMenu() {
        while (true) {
            System.out.println("Mời bạn chọn chức năng : ");
            System.out.println("1. Thêm user mới ");
            System.out.println("2. Cập nhật thông tin User ");
            System.out.println("3. Xóa User (chỉ xóa User khi User này chưa từng thực hiện giao dịch) ");
            System.out.println("4. Tìm kiếm User theo tên ");
            System.out.println("5. Thoát");
            int featureChoice;
            while (true) {
                try {
                    featureChoice = new Scanner(System.in).nextInt();
                    if (featureChoice < 1 || featureChoice > 5) {
                        System.out.println("Chức năng là số từ 1 tới 4, vui lòng nhập lại: ");
                        continue;
                    }
                    break;
                } catch (InputMismatchException ex) {
                    System.out.print("Lựa chọn phải là một số nguyên, vui lòng nhập lại: ");
                }
            }
            switch (featureChoice) {
                case 1 -> userService.createUserForAdmin();
                case 2 -> userService.updateUserInformation();
                case 3 -> {
                    User user;
                    int idUserDelete;
                    while (true) {
                        try {
                            System.out.println("Mời bạn nhập ID của User muốn xóa ");
                            idUserDelete = new Scanner(System.in).nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                            continue;
                        }
                        user = userService.findUserById(idUserDelete);
                        if (user == null) {
                            System.out.print("Thông tin không chính xác , vui lòng nhập lại : ");
                            continue;
                        }
                        break;
                    }
                    List<BookBorrow> luotMuonDaCo = bookBorrowService.findByUserId(idUserDelete);
                    if (luotMuonDaCo != null && !luotMuonDaCo.isEmpty()) {
                        System.out.println("Người dùng đã có dữ liệu mượn/trả trong hệ thống, không thể xóa");
                        break;
                    }
                    userService.deleteUserById(idUserDelete);
                }
                case 4 -> {
                    userService.findUserByName();
                    // TODO - tìm bạn đọc teo tên Đã làm xong
                }
                case 5 -> { // TODO sao đoạn này không return được
                    return;
                }
            }

        }
    }

    public void showCategoryManagementMenu() {
        while (true) {
            System.out.println("------------- Quản lý danh mục (thể loại) sách -----------");
            System.out.println("1. Thêm danh mục mới");
            System.out.println("2. Cập nhật thông tin danh mục");
            System.out.println("3. Xóa danh mục");
            System.out.println("4. Thoát");
            System.out.println("Mời bạn chọn chức năng : ");
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
                    bookCategoryService.create();
                    break;
                case 2:
                    bookCategoryService.updateCategoryById();
                    break;
                case 3:
                    System.out.println("Mời bạn nhập ID của thể loại cần xóa: ");
                    int idCategory = new Scanner(System.in).nextInt();
                    List<Book> books1 = bookService.findBooksByCategoryId(idCategory);
                    if (books1 == null || books1.isEmpty()) {
                        bookCategoryService.deleteCategoryById(idCategory);
                        break;
                    }
                    System.out.println("Không được xóa thể loại này do đã gán vào sách");
                    break;
                case 4:
                    return;
            }
        }
    }

}
