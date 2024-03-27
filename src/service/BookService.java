package service;

import entity.Book;
import entity.BookCategory;
import jdk.jfr.Category;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class BookService {

    private final ArrayList<Book> books = new ArrayList<>();
    private final BookService bookService = new BookService();

    public void inputBook() {
        Book book = new Book();
        BookCategory bookCategory = new BookCategory();
        System.out.println("Mời bạn nhập tên sách : ");
        book.setName(new Scanner(System.in).nextLine());
        System.out.println("Mời bạn nhập tên tác giả : ");
        book.setAuthor(new Scanner(System.in).nextLine());
        System.out.println("Mơi bạn nhập thể loại : ");
//        book.setCategory(new Scanner(System.in).nextLine()); // mắc
        System.out.println("Mời bạn nhập nhà xuất bản : ");
        book.setPublisher(new Scanner(System.in).nextLine());
        System.out.println("Mời bạn nhập năm xuất bản : ");
        book.setPublishedYear(LocalDate.parse(new Scanner(System.in).nextLine()));
        System.out.println("Mời bạn nhập giá quyên sách :");
        book.setPrice(new Scanner(System.in).nextDouble());
        System.out.println("Mời bạn nhập giá thuê sách theo ngày: ");
        book.setBorrowPricePerDay(new Scanner(System.in).nextDouble());
        System.out.println("Mới bạn nhập số lượng sách hiện có : ");
        book.setTotalQuantity(new Scanner(System.in).nextInt());
        books.add(book);
    }

    public Book findBookById(int idBook) {
        for (int j = 0; j < books.size(); j++) {
            if (books.get(j).getId() == idBook) {
                return books.get(j);
            }
        }
        return null;
    }
    public void manageBook(){
        while (true){
            System.out.println("Mời bạn nhập ID của sách : ");
            int idBook = new Scanner(System.in).nextInt();
            Book book = bookService.findBookById(idBook);
            if (book == null) {
                System.out.println("Thông tin không chính xác , vui lòng nhập lại : ");
                continue;
            }
            if (book != null) {
                System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa : ");
                int choice1 = new Scanner(System.in).nextInt();
                System.out.println("1 : Tên sách");
                System.out.println("2 : Tác giả");
                System.out.println("3 : Thể loại");
                System.out.println("4 : Nhà xuất bản");
                System.out.println("5 : Năm xuất bản");
                System.out.println("6 : Giá");
                System.out.println("7 : Tiền thuê theo ngày");
                System.out.println("8 : Số lượng hiện có");
                System.out.println("9 : Điểm vote ");
                System.out.println("10 : Lượt vote ");
                switch (choice1) {
                    case 1:
                        System.out.println("Mời bạn nhập tên sách mới");
                        String newName = new Scanner(System.in).nextLine();
                        book.setName(newName);
                        break;
                    case 2:
                        System.out.println("Mời bạn nhập tên tác giả mới");
                        String newAuthor = new Scanner(System.in).nextLine();
                        book.setAuthor(newAuthor);
                        break;
                    case 3:
                        break;
                    case 4:
                        System.out.println("Mời bạn nhập nhà xuất bản mới");
                        String newPublisher = new Scanner(System.in).nextLine();
                        book.setPublisher(newPublisher);
                        break;
                    case 5:
                        System.out.println("Mời bạn nhập năm sản xuất mới: ");
                        LocalDate newPuplishedYear = LocalDate.ofEpochDay(new Scanner(System.in).nextInt());
                        book.setPublishedYear(newPuplishedYear);
                        break;
                    case 6:
                        System.out.println("Mời bạn nhập giá mới: ");
                        Double newPrice = new Scanner(System.in).nextDouble();
                        book.setPrice(newPrice);
                        break;
                    case 7:
                        System.out.println("Mời bạn nhập tiền thuê theo ngày mới");
                        Double newBorrowPricePerDay = new Scanner(System.in).nextDouble();
                        book.setBorrowPricePerDay(newBorrowPricePerDay);
                        break;
                    case 8:
                        System.out.println("Mời bạn nhập số lượng hiện có mới ");
                        int newTotalQuantity = new Scanner(System.in).nextInt();
                        book.setTotalQuantity(newTotalQuantity);
                        break;
                    case 9:
                        break;
                    case 10:
                        break;

                }
            }
            break;
        }
    }

}
