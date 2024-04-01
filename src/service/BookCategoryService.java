package service;

import entity.BookCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BookCategoryService {

//    private final List<BookCategory> bookCategories = Arrays.asList(
//            new BookCategory(-1 , "Chính kịch"),
//            new BookCategory(-2 , "Thiếu nhi"),
//            new BookCategory(-3,  "Lích sử")
//    );

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
            int idCategory = new Scanner(System.in).nextInt();
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

    public void deleteCategoryById() {
        // TODO - khi nào thì được phép xóa 1 thể loại sách??? . Khi thể loại sách đó không dược gán cho bất kỳ quyển sách nào
        // làm giống xóa user
        System.out.println("Mời bạn nhập ID của category cần xóa ");
        int idCategory = new Scanner(System.in).nextInt();
        BookCategory bookCategory = findCategoryById(idCategory);

        for (int i = 0; i < bookCategories.size(); i++) {
            if (bookCategories.get(i).getIdCategory() == idCategory) {
                bookCategories.remove(bookCategories.get(i));
                System.out.println("Đã xóa thành công Category");
                continue;
            }
            System.out.println("Thông tin không chính xác , vui lòng nhập lại ");
            return;
        }

    }
}
