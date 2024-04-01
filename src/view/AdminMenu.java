package view;

import entity.BookBorrow;
import service.BookBorrowService;
import service.BookCategoryService;
import service.BookService;
import service.UserService;

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
            System.out.println("------------ MENU ADMIN MANAGEMENT ------------");
            System.out.println("1. Quản lý sách");// show ra menu  có nhập sách vào kho, cập nhật thông tin sách
            System.out.println("2. Quản lý mượn trả sách"); // show menu con: mượn, trả
            System.out.println("3. Quản lý thể loại sách"); // thêm sửa xóa thể loại
            System.out.println("4. Quản lý người dùng"); // Thêm , sửa , xóa User
            System.out.println("5. Đăng xuất");
            System.out.print("Chọn chức năng: ");
            int featureChoice = new Scanner(System.in).nextInt();
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
            System.out.println("3. Tìm kiếm lượt mượn/trả sách");
            System.out.println("4. Thoát");
            System.out.println("Mới bạn chọn chức năng: ");
            int choice = new Scanner(System.in).nextInt();
            switch (choice) {
                case 1:
                    bookBorrowService.createBorrow();
                    break;
                case 2:
                    bookBorrowService.returnBook();
                    break;
                case 3:
                    // TODO - tìm kiếm: tìm theo người mượn, tên sách
                    break;
                case 4:
                    return;
            }
        }
    }


    public void showBookManagementMenu() {
        while (true) {
            System.out.println("---------- QUẢN LÝ KHO SÁCH ------------");
            System.out.println("1. Nhập sách mới vào kho");
            System.out.println("2. Sửa thông tin sách đang có");
            System.out.println("3. Thoát");
            System.out.println("Mới bạn chọn chức năng: ");
            int choice = new Scanner(System.in).nextInt();
            switch (choice) {
                case 1:
                    bookService.inputBook();
                    break;
                case 2:
                    bookService.updateBook();
                    break;
                case 3:
                    return;
            }
        }
    }

    public void showUserManagementMenu() {
        while (true) {
            System.out.println("Mời bạn chọn chức năng : ");
            System.out.println("1. Thêm user mới ");
            System.out.println("2. Cập nhật thông tin user ");
            System.out.println("3. Xóa user ");
            System.out.println("4. Thoát");
            int choice = new Scanner(System.in).nextInt();
            switch (choice) {
                case 1 -> userService.createUserForAdmin();
                case 2 -> userService.updateUserInformation();
                case 3 -> {
                    System.out.println("Mời bạn nhập ID của User muốn xóa ");
                    int idUserDelete = new Scanner(System.in).nextInt();
                    // TODO  - tìm xem user này đã phát sinh lượt mượn/trả nào chưa, nếu có thì không cho xóa
                    List<BookBorrow> luotMuonDaCo = bookBorrowService.findByUserId(idUserDelete);
                    if (luotMuonDaCo == null || !luotMuonDaCo.isEmpty()) {
                        System.out.println("Người dùng đã có dữ liệu mượn/trả trong hệ thống, không thể xóa");
                        break;
                    }
                    userService.deleteUserById(idUserDelete);
                }
                case 4 -> {
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
            int choice = new Scanner(System.in).nextInt();
            switch (choice) {
                case 1:
                    bookCategoryService.create();
                    break;
                case 2:
                    bookCategoryService.updateCategoryById();
                    break;
                case 3:
                    bookCategoryService.deleteCategoryById();
                    break;
                case 4:
                    return;
            }
        }
    }

}
