package service;

import constant.DateTimeConstant;
import entity.Book;
import entity.BookCategory;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class BookService {

    private final ArrayList<Book> books = new ArrayList<>();
    private final BookCategoryService bookCategoryService;

    public BookService(BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }

    public void inputBook() {

        System.out.println("Mời bạn nhập tên sách : ");
        String name = new Scanner(System.in).nextLine();
        System.out.println("Mời bạn nhập tên tác giả : ");
        String author = new Scanner(System.in).nextLine();
        BookCategory category;
        while (true) {
            System.out.println("Mời bạn nhập id của thể loại mà bạn muốn gán cho sách  : ");
            int idCategory = new Scanner(System.in).nextInt();
            category = bookCategoryService.findCategoryById(idCategory);
            if (category == null) {
                System.out.println("Thông tin không chính xác vui lòng nhập lại ");
                continue;
            }
            break;
        }

        System.out.println("Mời bạn nhập nhà xuất bản : ");
        String publisher = new Scanner(System.in).nextLine();
        LocalDate publishedYear;
        while (true) {
            System.out.println("Mời bạn nhập năm xuất bản theo định dạng dd/MM/yyyy: ");
            try {
                publishedYear = LocalDate.parse(new Scanner(System.in).nextLine(), DateTimeConstant.DATE_FORMATTER);
                break;
            } catch (DateTimeException e) {
                System.out.println("Định dạng không hợp lệ vui lòng nhập lại ");
            }
        }
        System.out.println("Mời bạn nhập giá quyên sách :");
        double price = new Scanner(System.in).nextDouble();
        System.out.println("Mời bạn nhập giá thuê sách theo ngày: ");
        double borrowPricePerDay = new Scanner(System.in).nextDouble();
        System.out.println("Mới bạn nhập số lượng sách hiện có : ");
        int totalQuantity = new Scanner(System.in).nextInt();
        Book book = new Book(name, author, category, publisher, publishedYear, price, borrowPricePerDay, totalQuantity);
        books.add(book);
        System.out.println(books);
    }

    public Book findBookById(int idBook) {
        for (int j = 0; j < books.size(); j++) {
            if (books.get(j).getIdCategory() == idBook) {
                return books.get(j);
            }
        }
        return null;
    }

    public void updateBook() {
        while (true) {
            System.out.println("Mời bạn nhập ID của sách : ");
            int idBook = new Scanner(System.in).nextInt();
            Book book = findBookById(idBook);
            if (book == null) {
                System.out.println("Thông tin không chính xác , vui lòng nhập lại : ");
                continue;
            }
            System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa : ");
            System.out.println("1. Tên sách");
            System.out.println("2. Tác giả");
            System.out.println("3. Thể loại");
            System.out.println("4. Nhà xuất bản");
            System.out.println("5. Năm xuất bản");
            System.out.println("6. Giá");
            System.out.println("7. Tiền thuê theo ngày");
            System.out.println("8. Số lượng hiện có");
            System.out.println("9. Thoát");
            int choice1 = new Scanner(System.in).nextInt();
            switch (choice1) {
                case 1:
                    System.out.println("Mời bạn nhập tên sách mới");
                    String newName = new Scanner(System.in).nextLine();
                    book.setNameCategory(newName);
                    break;
                case 2:
                    System.out.println("Mời bạn nhập tên tác giả mới");
                    String newAuthor = new Scanner(System.in).nextLine();
                    book.setAuthor(newAuthor);
                    break;
                case 3:
                    while (true) {
                        System.out.println("Mời bạn nhập id của thể loại mới: ");
                        int idCategory = new Scanner(System.in).nextInt();
                        BookCategory bookCategory = bookCategoryService.findCategoryById(idCategory);
                        if (bookCategory == null) {
                            System.out.println("Thông tin nhập không chính xác");
                            continue;
                        }
                        book.setCategory(bookCategory);
                        System.out.println(book);
                        break;
                    }
                    break;
                case 4:
                    System.out.println("Mời bạn nhập nhà xuất bản mới");
                    String newPublisher = new Scanner(System.in).nextLine();
                    book.setPublisher(newPublisher);
                    break;
                case 5:
                    LocalDate newPublishedYear;
                    while (true) {
                        System.out.println("Mời bạn nhập năm xuất bản mới theo định dạng dd/MM/yyyy: ");
                        try {
                            newPublishedYear = LocalDate.parse(new Scanner(System.in).nextLine(), DateTimeConstant.DATE_FORMATTER);
                            break;
                        } catch (DateTimeException e) {
                            System.out.println("Định dạng không hợp lệ vui lòng nhập lại ");
                        }
                    }
                    book.setPublishedYear(newPublishedYear);
                    System.out.println(book);
                    break;
                case 6:
                    System.out.println("Mời bạn nhập giá mới: ");
                    double newPrice = new Scanner(System.in).nextDouble();
                    book.setPrice(newPrice);
                    break;
                case 7:
                    System.out.println("Mời bạn nhập tiền thuê theo ngày mới");
                    double newBorrowPricePerDay = new Scanner(System.in).nextDouble();
                    book.setBorrowPricePerDay(newBorrowPricePerDay);
                    break;
                case 8:
                    System.out.println("Mời bạn nhập số lượng hiện có mới ");
                    int newTotalQuantity = new Scanner(System.in).nextInt();
                    book.setTotalQuantity(newTotalQuantity);
                    break;
                case 9:
                    return;
            }
        }
    }

}
