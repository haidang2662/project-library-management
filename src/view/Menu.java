package view;

import constant.UserRole;
import entity.Book;
import entity.User;
import main.Main;
import service.BookCategoryService;
import service.BookService;
import service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Menu {

    private final UserService userService = new UserService();
    private final BookService bookService = new BookService();

    private final BookCategoryService bookCategoryService = new BookCategoryService();

    public void showMenu() {
        System.out.println("---------- THƯ VIỆN MINI -----------");
        loginMenu();
        System.out.print("Xin mời chức năng: ");
        int featureChoice = new Scanner(System.in).nextInt();
        switch (featureChoice) {
            case 1:
                User user = userService.login();
                if (user == null) {
                    System.out.println("Vượt quá số lần đăng nhập cho phép, vui lòng liên hệ admin để hỗ trợ");
                    return;
                }
                Main.loggedInUser = user;
                if (user.getRole().equals(UserRole.USER)) {
                    showUserMenu();
                    break;
                }
                showAdminMenu();
                break;
            case 2:
                userService.register();
                break;
            case 3:
        }
    }

    public void showAdminMenu () {
        while (true) {
            System.out.println("------------ THƯ VIỆN MINI ------------");
            System.out.println("1. Quản lý sách");// show ra menu con có nhập sách vào kho, cập nhật thông tin sách
            System.out.println("2. Quản lý mượn trả sách"); // show menu con: mượn, trả
            System.out.println("3. Quản lý thể loại sách "); // thêm sửa xóa thể loại
            System.out.println("4. Quản lý người dùng "); // Thêm , sửa , xóa User
            System.out.print("Chọn chức năng: ");
            int featureChoice = new Scanner(System.in).nextInt();
            switch (featureChoice) {
                case 1:
                    showAdminMenu_BookManagement();
                    break;
                case 2:
                    break;
                case 3:
                    showAdminMenu_CategoyManageMent();
                    break;
                case 4:
                    showAdminMenu_UserManagement();
                    break;
            }
            System.out.println("Do you want to contine : ");
            String choice = new Scanner(System.in).nextLine();
            if(choice.equalsIgnoreCase("n")){
                return;
            }
        }
    }

    private void showUserMenu() {
        while (true) {
            System.out.println("------------ THƯ VIỆN MINI ------------");
            System.out.println("1. ");
        }
    }

    public void loginMenu() { // cái này có thể đẩy lên tren cho hợp lý hơn
        System.out.println("1. Đăng nhập");
        System.out.println("2. Đăng ký");
        System.out.println("3. Thoát");
        // chức năng quên mật khẩu để sau
    }

    public void showAdminMenu_BookManagement() {
        System.out.println("1 : Nhập sách mới vào kho ");
        System.out.println("2 : Sửa thông tin sách đang có ");
        System.out.println("Mới bạn chọn chức năng : ");
        int choice = new Scanner(System.in).nextInt();
        switch (choice) {
            case 1:
                bookService.inputBook();
                break;
            case 2:
                bookService.manageBook();
                break;
        }
    }

    public void showAdminMenu_UserManagement() {
        System.out.println("Mời bạn chọn chức năng : ");
        int choice = new Scanner(System.in).nextInt();
        System.out.println("1 : Thêm user mới ");
        System.out.println("2 : Cập nhật thông tin user ");
        System.out.println("3 : Xóa user ");
        switch (choice) {
            case 1:
                userService.register();
                break;
            case 2:
                userService.updateUserInformation();
                break;
            case 3:
                userService.deleteUserById();
                break;
        }
    }
    public void showAdminMenu_CategoyManageMent(){
        System.out.println("Mời bạn chọn chức năng : ");
        System.out.println("1 : Thêm Category mới ");
        System.out.println("2 : Cập nhật thông tin Category ");
        System.out.println("3 : Xóa Category : ");
        int choice = new Scanner(System.in).nextInt();
        switch (choice){
            case 1:
                bookCategoryService.creat();
                break;
            case 2:
                bookCategoryService.updateCategoryById();
                break;
            case 3:
                bookCategoryService.deleteCategoryById();
                break;
        }
    }

}
