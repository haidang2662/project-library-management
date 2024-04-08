package service;

import constant.DateTimeConstant;
import entity.Book;
import entity.BookCategory;
import entity.User;
import util.FileUtil;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class BookService {

    private final FileUtil<Book> fileUtil = new FileUtil<>();
    private static final String BOOK_DATA_FILE = "books.json";
    private static int AUTO_ID;
    private List<Book> books;
    private final BookCategoryService bookCategoryService;

    public BookService(BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }

    public void setBooks() {
        List<Book> booksList = fileUtil.readDataFromFile(BOOK_DATA_FILE, Book[].class);
        books = booksList != null ? booksList : new ArrayList<>();
    }

    public void saveBookData() {
        fileUtil.writeDataToFile(books, BOOK_DATA_FILE);
    }

    public void findCurrentAutoId() {
        int maxId = -1;
        for (Book book : books) {
            if (book.getId() > maxId) {
                maxId = book.getId();
            }
        }
        AUTO_ID = maxId + 1;
    }

    public void inputBook() {
        System.out.println("Mời bạn nhập tên sách : ");
        String name = new Scanner(System.in).nextLine();
        System.out.println("Mời bạn nhập tên tác giả : ");
        String author = new Scanner(System.in).nextLine();
        BookCategory category;
        while (true) {
            System.out.println("Mời bạn nhập id của thể loại mà bạn muốn gán cho sách: ");
            int idCategory;
            try {
                idCategory = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                continue;
            }
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
        double price;
        while (true) {
            try {
                price = new Scanner(System.in).nextDouble();
                if (price < 0) {
                    System.out.println("Giá 1 quyển sách phải là số dương , vui lòng nhập lại ");
                    continue;
                }
                break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số tự nhiên . Vui lòng nhập lại.");
            }
        }
        System.out.println("Mời bạn nhập giá thuê sách theo ngày: ");
        double borrowPricePerDay;
        while (true) {
            try {
                borrowPricePerDay = new Scanner(System.in).nextDouble();
                if (borrowPricePerDay < 0) {
                    System.out.println("Giá thuê sách phải là số dương , vui lòng nhập lại ");
                    continue;
                }
                break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số tự nhiên . Vui lòng nhập lại.");
            }
        }
        System.out.println("Mới bạn nhập số lượng sách hiện có : ");
        int totalQuantity;
        while (true) {
            try {
                totalQuantity = new Scanner(System.in).nextInt();
                if (totalQuantity <= 0) {
                    System.out.println("Số lượng sách hiện có phải là số dương , vui lòng nhập lại ");
                    continue;
                }
                break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
            }
        }
        Book book = new Book(AUTO_ID++, name, author, category, publisher, publishedYear, price, borrowPricePerDay, totalQuantity);
        books.add(book);
        showBook(book);
        saveBookData(); // Lưu vào File dữ liệu liên quan đến books
    }

    public Book findBookById(int idBook) {
        for (Book book : books) {
            if (book.getIdCategory() == idBook) {
                return book;
            }
        }
        return null;
    }

    public void updateBook() {
        while (true) {
            System.out.println("Mời bạn nhập ID của sách : ");
            int bookId;
            while (true) {
                try {
                    bookId = new Scanner(System.in).nextInt();
                    break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                }
            }
            Book book = findBookById(bookId);
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
            int featureChoice;
            while (true) {
                try {
                    featureChoice = new Scanner(System.in).nextInt();
                    if (featureChoice < 1 || featureChoice > 9) {
                        System.out.println("Chức năng là số từ 1 tới 9, vui lòng nhập lại: ");
                        continue;
                    }
                    break;
                } catch (InputMismatchException ex) {
                    System.out.print("Lựa chọn phải là một số nguyên, vui lòng nhập lại: ");
                }
            }
            switch (featureChoice) {
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
                    while (true) {
                        System.out.println("Mời bạn nhập id của thể loại mới: ");
                        int idCategory;
                        try {
                            idCategory = new Scanner(System.in).nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                            continue;
                        }
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
                    double newPrice;
                    while (true) {
                        try {
                            newPrice = new Scanner(System.in).nextDouble();
                            if (newPrice <= 0) {
                                System.out.println("Giá 1 quyển sách phải là 1 số dương");
                                continue;
                            }
                            break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số tự nhiên hợp lệ
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị bạn vừa nhập không phải là một số tự nhiên . Vui lòng nhập lại.");
                        }
                    }
                    book.setPrice(newPrice);
                    break;
                case 7:
                    System.out.println("Mời bạn nhập tiền thuê theo ngày mới");
                    double newBorrowPricePerDay;
                    while (true) {
                        try {
                            newBorrowPricePerDay = new Scanner(System.in).nextDouble();
                            if (newBorrowPricePerDay <= 0) {
                                System.out.println("Tiền thuê theo ngày phải là 1 số dương");
                                continue;
                            }
                            break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số tự nhiên hợp lệ
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị bạn vừa nhập không phải là một số tự nhiên . Vui lòng nhập lại.");
                        }
                    }
                    book.setBorrowPricePerDay(newBorrowPricePerDay);
                    break;
                case 8:
                    System.out.println("Mời bạn nhập số lượng hiện có mới ");
                    int newTotalQuantity;
                    while (true) {
                        try {
                            newTotalQuantity = new Scanner(System.in).nextInt();
                            if (newTotalQuantity <= 0) {
                                System.out.println("Số lượng sách hiện có phải là số dương , vui lòng nhập lại ");
                                continue;
                            }
                            break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                        }
                    }
                    book.setTotalQuantity(newTotalQuantity);
                    break;
                case 9:
                    return;
            }
            showBooks();
            saveBookData(); // Lưu vào File dữ liệu liên quan đến books
            break;
        }
    }

    public List<Book> findBooksByCategoryId(int categoryId) {
        List<Book> books1 = new ArrayList<>();
        for (Book book : books) {
            if (book.getCategory().getIdCategory() == categoryId) {
                books1.add(book);
            }
        }
        return books1;
    }

    public void updateTotalQuantityBook(int idBook, int numberBook) {
        for (Book book : books) {
            if (book.getId() == idBook) {
                book.setTotalQuantity(book.getTotalQuantity() + numberBook);
                saveBookData(); // Lưu vào File dữ liệu liên quan đến books
                return;
            }
        }
    }

    public void findBookByName(){
        System.out.println("Mời bạn nhập tên của sách : ");
        String name = new Scanner(System.in).nextLine();
        List<Book> books1 = new ArrayList<>();
        for(Book book : books){
            if(book.getName().toLowerCase().contains(name.toLowerCase())){
                books1.add(book);
            }
        }
        showBooks(books1);
    }

    public void findBookByNameCategory(){
        System.out.println("Mời bạn nhập tên của thể loại : ");
        String name = new Scanner(System.in).nextLine();
        List<Book> books1 = new ArrayList<>();
        for(Book book : books){
            if(book.getCategory().getNameCategory().toLowerCase().contains(name.toLowerCase())){
                books1.add(book);
            }
        }
        showBooks(books1);
    }

    public void findBookByVoteStar(){
        ArrayList<Book> books1 = new ArrayList<>();
        for(Book book : books){
            if(book.getVoteStar()>=4 && book.getVoteStar()<=5){
                books1.add(book);
            }
        }
        showBooks(books1);
    }

    public void showBooks() {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n", "Id", "Name", "Author", "CateGory","Publisher","PublishedYear","Price","BorrowPricePerDay","TotalQuantity","VoteStar","VoteCount");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Book book : books) {
            showBook(book);
        }
    }

    public void showBook(Book book) {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n",book.getId(),book.getName(),book.getAuthor(),book.getCategory().getNameCategory(),
                book.getPublisher(),book.getPublishedYear(),book.getPrice(),book.getBorrowPricePerDay(),book.getTotalQuantity(),
                book.getVoteStar(),book.getVoteCount());
    }

    public void showBooks(List<Book> books1) {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n", "Id", "Name", "Author", "CateGory","Publisher","PublishedYear","Price","BorrowPricePerDay","TotalQuantity","VoteStar","VoteCount");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Book book : books1) {
            showBook(book);
        }
    }
}
