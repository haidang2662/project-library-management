package service;

import entity.Book;
import entity.BookCategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class BookService {

    private final ArrayList<Book> books = new ArrayList<>();


    private final BookCategoryService bookCategoryService = new BookCategoryService();

    public void inputBook() {
        Book book = new Book();
        System.out.println("Mời bạn nhập tên sách : ");
        book.setNameCategory(new Scanner(System.in).nextLine());
        System.out.println("Mời bạn nhập tên tác giả : ");
        book.setAuthor(new Scanner(System.in).nextLine());
        System.out.println("Mơi bạn nhập id của thể loại mà bạn muốn gán cho sách  : ");
        int idCategory = new Scanner(System.in).nextInt();
        BookCategory bookCategory = bookCategoryService.findCategoryById(idCategory);
        book.setCategory(bookCategory);
        System.out.println(book);
        System.out.println("Mời bạn nhập nhà xuất bản : ");
        book.setPublisher(new Scanner(System.in).nextLine());
        System.out.println("Mời bạn nhập năm xuất bản theo định dạng yyyy-MM-dd: ");
        book.setPublishedYear(LocalDate.parse(new Scanner(System.in).nextLine()));
        System.out.println("Mời bạn nhập giá quyên sách :");
        book.setPrice(new Scanner(System.in).nextDouble());
        System.out.println("Mời bạn nhập giá thuê sách theo ngày: ");
        book.setBorrowPricePerDay(new Scanner(System.in).nextDouble());
        System.out.println("Mới bạn nhập số lượng sách hiện có : ");
        book.setTotalQuantity(new Scanner(System.in).nextInt());
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

    public void manageBook() {
        while (true) {
            System.out.println("Mời bạn nhập ID của sách : ");
            int idBook = new Scanner(System.in).nextInt();
            Book book = findBookById(idBook);
            if (book == null) {
                System.out.println("Thông tin không chính xác , vui lòng nhập lại : ");
                continue;
            }
            if (book != null) {
                System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa : ");
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
                        while (true){
                            System.out.println("Mời bạn nhập ID của thuộc tính Category : ");
                            int idCategory = new Scanner(System.in).nextInt();
                            BookCategory bookCategory = bookCategoryService.findCategoryById(idCategory);
                            if (bookCategory == null) {
                                System.out.println("Thông tin nhập không chính xác ");
                                continue;
                            } else {
                                System.out.println("Mời bạn nhập tên thể loại mới : ");
                                bookCategory.setNameCategory(new Scanner(System.in).nextLine());
                                book.setCategory(bookCategory);
                                System.out.println(bookCategory);
                               break;
                            }
                        }


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
            System.out.println("Do you want to contine fix infomation : ");
            String choice = new Scanner(System.in).nextLine();
            if(choice.equalsIgnoreCase("n")){
                return;
            }
        }
    }

}
