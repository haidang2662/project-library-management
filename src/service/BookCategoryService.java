package service;

import entity.BookCategory;
import entity.User;
import util.FileUtil;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class BookCategoryService {

    private final FileUtil<BookCategory> fileUtil = new FileUtil<>();

    private static final String BOOK_CATEGORY_DATA_FILE = "bookCategories.json";

    private static int AUTO_ID;

    private List<BookCategory> bookCategories;

    public void setBookCategories() {
        List<BookCategory> bookCategoriesList = fileUtil.readDataFromFile(BOOK_CATEGORY_DATA_FILE, BookCategory[].class);
        bookCategories = bookCategoriesList != null ? bookCategoriesList : new ArrayList<>();
    }

    public void saveBookCategoryData() {
        fileUtil.writeDataToFile(bookCategories, BOOK_CATEGORY_DATA_FILE);
    }

    public void findCurrentAutoId() {
        int maxId = -1;
        for (BookCategory bookCategory : bookCategories) {
            if (bookCategory.getIdCategory() > maxId) {
                maxId = bookCategory.getIdCategory();
            }
        }
        AUTO_ID = maxId + 1;
    }

    public void create() {
        BookCategory bookCategory = new BookCategory(AUTO_ID++);
        System.out.println("Mời bạn nhập tên cho thể loại : ");
        bookCategory.setNameCategory(new Scanner(System.in).nextLine());
        bookCategories.add(bookCategory);
        System.out.println(bookCategory);
        saveBookCategoryData(); // Lưu dữ liệu file
    }

    public BookCategory findCategoryById(int idCategory) {
        for (BookCategory bookCategory : bookCategories) {
            if (bookCategory.getIdCategory() == idCategory) {
                return bookCategory;
            }
        }
        return null;
    }

    public void updateCategoryById() {
        while (true) {
            System.out.println("Mời bạn nhập ID của thể loại sách muốn cập nhật: ");
            int idCategory;
            try {
                idCategory = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                continue;
            }
            BookCategory bookCategory = findCategoryById(idCategory);
            if (bookCategory == null) {
                System.out.println("Id vừa nhập không tồn tại trong hệ thống, vui lòng nhập lại:  ");
                continue;
            }
            System.out.println("Mời bạn nhập tên thể loại mới : ");
            bookCategory.setNameCategory(new Scanner(System.in).nextLine());
            System.out.println(bookCategory);
            saveBookCategoryData(); // Lưu dữ liệu file
            break;
        }
    }

    public void deleteCategoryById(int idCategory) {
        for (BookCategory category : bookCategories) {
            if (category.getIdCategory() == idCategory) {
                bookCategories.remove(category);
                saveBookCategoryData(); // Lưu dữ liệu file
            }
        }
    }

    public void showCategories() {
        System.out.printf("%-5s%-30s%n", "Id", "Name");
        System.out.println("----------------------------------------");
        for (BookCategory category : bookCategories) {
            showCategory(category);
        }
    }

    public void showCategory(BookCategory category) {
        System.out.printf("%-5s%-30s%n", category.getIdCategory(),category.getNameCategory());
    }
}
