package service;

import entity.Book;
import entity.BookCategory;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class BookCategoryService {

    private final List<BookCategory> bookCategories = new ArrayList<>();

    public void create() {
        BookCategory bookCategory = new BookCategory();
        System.out.println("Mời bạn nhập tên cho thể loại : ");
        bookCategory.setNameCategory(new Scanner(System.in).nextLine());
        bookCategories.add(bookCategory);
        System.out.println(bookCategory);
    }

    public BookCategory findCategoryById(int idCategory) {
        for (int i = 0; i < bookCategories.size(); i++) {
            if (bookCategories.get(i).getIdCategory() == idCategory) {
                return bookCategories.get(i);
            }
        }
        return null;
    }

    public void updateCategoryById() {
        while (true) {
            System.out.println("Mời bạn nhập ID của thể loại sách muốn cập nhật: ");
            int idCategory;
            while (true) {
                try {
                    idCategory = new Scanner(System.in).nextInt();
                    break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                }
            }
            BookCategory bookCategory = findCategoryById(idCategory);
            if (bookCategory == null) {
                System.out.println("Id vừa nhập không tồn tại trong hệ thống, vui lòng nhập lại:  ");
                continue;
            }
            System.out.println("Mời bạn nhập tên thể loại mới : ");
            bookCategory.setNameCategory(new Scanner(System.in).nextLine());
            System.out.println(bookCategory);
            break;
        }
    }

    public void deleteCategoryById(int idCategory) {
        for (BookCategory category : bookCategories) {
            if (category.getIdCategory() == idCategory) {
                bookCategories.remove(category);
            }
        }
    }
}
