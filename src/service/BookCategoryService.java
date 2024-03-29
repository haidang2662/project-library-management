package service;

import entity.BookCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BookCategoryService {

    private final List<BookCategory> bookCategories = new ArrayList<>();



    public BookCategory creat() {
        BookCategory bookCategory = new BookCategory();
        System.out.println("Mời bạn nhập ID cho Category : ");
        bookCategory.setIdCategory(new Scanner(System.in).nextInt());
        System.out.println("Mời bạn nhập tên cho Category : ");
        bookCategory.setNameCategory(new Scanner(System.in).nextLine());
        bookCategories.add(bookCategory);
        System.out.println(bookCategory);
        return bookCategory;
    }

    public BookCategory findCategoryById(int idCategory) {
        for (int i = 0; i < bookCategories.size(); i++) {
            if (bookCategories.get(i).getIdCategory() == idCategory) {
                return bookCategories.get(i);
            }
        }
        return null;
    }
    public void updateCategoryById(){
        System.out.println("Mời bạn nhập ID của thuộc tính Category : ");
        int idCategory = new Scanner(System.in).nextInt();
        BookCategory bookCategory = findCategoryById(idCategory);
        if (bookCategory == null) {
            System.out.println("Thông tin nhập không chính xác ");
        } else {
            System.out.println("Mời bạn nhập tên thể loại mới : ");
            bookCategory.setNameCategory(new Scanner(System.in).nextLine());
            System.out.println(bookCategory);
        }
    }
    public void deleteCategoryById(){
        System.out.println("Mời bạn nhập ID của category cần xóa ");
        int idCategory = new Scanner(System.in).nextInt();
        for (int i = 0; i < bookCategories.size(); i++) {
            if(bookCategories.get(i).getIdCategory() == idCategory){
                bookCategories.remove(bookCategories.get(i));
                System.out.println("Đã xóa thành công Category");
                continue;
            }
            System.out.println("Thông tin không chính xác , vui lòng nhập lại ");
            return;
        }

    }
}
