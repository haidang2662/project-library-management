package service;

import constant.DateTimeConstant;
import constant.TransactionType;
import entity.*;
import main.Main;
import util.FileUtil;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BookBorrowService {

    private final FileUtil<BookBorrow> fileUtil = new FileUtil<>();

    private static final String BOOK_BORROW_DATA_FILE = "bookBorrows.json";

    private List<BookBorrow> bookBorrows; // danh sách các lượt mượn/trả sách

    private final UserService userService;
    private final BookService bookService;

    private final TransactionService transactionService;

    public BookBorrowService(UserService userService, BookService bookService, TransactionService transactionService) {
        this.userService = userService;
        this.bookService = bookService;
        this.transactionService = transactionService;
    }

    public void setBookBorrows() {
        List<BookBorrow> bookBorrowList = fileUtil.readDataFromFile(BOOK_BORROW_DATA_FILE, BookBorrow[].class);
        bookBorrows = bookBorrowList != null ? bookBorrowList : new ArrayList<>();
    }

    public void saveBookBorrowsData() {
        fileUtil.writeDataToFile(bookBorrows, BOOK_BORROW_DATA_FILE);
    }

    public void createBorrow() {
        User borrower = inputBorrower();
        int userId = borrower.getId();
        BookBorrow borrow = findBorrowing(userId);
        if (borrow != null) {
            System.out.println("Bạn đọc này đang có lượt mượn sách chưa trả. " +
                    "Vui lòng thực hiện trả sách đang mượn để có thể tiếp tục mượn các cuốn sách khác.");
            return;
        }
        double soDuTK = borrower.getBalance();
        if (soDuTK < 50000) {
            System.out.println("Bạn đọc này có số dư tài khoản dưới 50.000 , vui lòng nạp thêm tài khoản ");
            return;
        }
        System.out.println("Số dư tài khoản hiện tại của bạn đọc : " + soDuTK);
        System.out.println("Bạn đọc này có thể tiếp tục mượn sách ");
        // nhập danh sách các đầu sách muốn mượn (kèm theo số lượng và tình trạng tương ứng)
        List<BookBorrowDetail> details = createdBorrowDetail(borrower);
        if (details.isEmpty()) {
            System.out.println("Danh sách sách muốn mượn đang rỗng, mượn không thành công.");
            return;
        }

        System.out.println("Mời bạn nhập ngày trả dự kiến (DD/MM/YYYY): ");
        LocalDate expectedReturnDate;
        while (true) {
            try {
                expectedReturnDate = LocalDate.parse(new Scanner(System.in).nextLine(), DateTimeConstant.DATE_FORMATTER);
                break;
            } catch (DateTimeException e) {
                System.out.println("Định dạng không hợp lệ vui lòng nhập lại ");
            }
        }

        LocalDate createdDate = LocalDate.now();// ngày mượn
        long borrowingDayCount = ChronoUnit.DAYS.between(createdDate, expectedReturnDate);// số ngày mượn


        double tongTienCoc = 0, tongTienThue = 0;
        for (BookBorrowDetail detail : details) {
            double expectedBorrowFee = detail.getBook().getBorrowPricePerDay() * borrowingDayCount * detail.getBorrowQuantity();
            detail.setExpectedBorrowFee(expectedBorrowFee);
            tongTienThue += expectedBorrowFee;

            double expectedDepositAmount = detail.getBook().getPrice() * detail.getBorrowQuantity();
            detail.setDepositAmount(expectedDepositAmount);
            tongTienCoc += expectedDepositAmount;
        }
        userService.updateUserBalance(userId, -tongTienCoc);
        for (BookBorrowDetail detail : details) {
            bookService.updateTotalQuantityBook(detail.getBook().getId(), -detail.getBorrowQuantity());
        }

        BookBorrow bookBorrow = new BookBorrow(borrower, details, createdDate, expectedReturnDate,
                tongTienCoc, tongTienThue);
        bookBorrows.add(bookBorrow);
        saveBookBorrowsData(); // Lưu dữ liêu file liên quan đến BookBorrow
        String transactionContent = borrower.getFullName() + " thực hiện mượn sách thư viện";
        Transaction transaction = new Transaction(borrower, createdDate, tongTienCoc, TransactionType.BORROW, transactionContent);
        transactionService.saveTransaction(transaction);
    }

    private List<BookBorrowDetail> createdBorrowDetail(User borrower) {
        List<BookBorrowDetail> details = new ArrayList<>(); // danh sách các đầu sách mà người dùng muốn mượn
        System.out.println("Mời bạn nhập số đầu sách muốn mượn : ");
        int numberOfTypeBook;
        while (true) {
            try {
                numberOfTypeBook = new Scanner(System.in).nextInt();
                if (numberOfTypeBook <= 0) {
                    System.out.println("Số lượng đầu sách  phải là số dương , vui lòng nhập lại ");
                    continue;
                }
                break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
            }
        }
        double soDuTaiKhoan = borrower.getBalance();
        for (int i = 0; i < numberOfTypeBook; i++) {
            Book book;
            while (true) {
                System.out.println("Mời bạn nhập id của quyển sách bạn muốn mượn: ");
                int bookId;
                try {
                    bookId = new Scanner(System.in).nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                    continue;
                }
                book = bookService.findBookById(bookId);
                if (book == null) {
                    System.out.println("id không chính xác vui lòng nhập lại ");
                    continue;
                }
                break;
            }
            System.out.println("Mời bạn nhập số lượng sách muốn mượn: ");
            int borrowQuantity;
            boolean muonCuonKhac = false;
            while (true) {
                try {
                    borrowQuantity = new Scanner(System.in).nextInt();
                    if (borrowQuantity <= 0) {
                        System.out.println("Số lượng đầu sách  phải là số dương, vui lòng nhập lại ");
                        continue;
                    }

                    if (borrowQuantity > book.getTotalQuantity()) {
                        System.out.println("Số sách hiện có trong thư viện: " + book.getTotalQuantity());
                        System.out.println("Sách bạn muốn mượn đang không có đủ trong thư viện, " +
                                "bạn có muốn tiếp tục mượn sách này không (Y/N) : ");
                        String choice = new Scanner(System.in).nextLine();
                        if (choice.equalsIgnoreCase("n")) {
                            muonCuonKhac = true;
                            break;
                        }
                        System.out.print("Nhập lại số sách muốn mượn: ");
                        continue;
                    }
                    if (borrowQuantity < book.getTotalQuantity()){
                        break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                }
            }
            if (!muonCuonKhac) {
                continue;
            }

            if (book.getPrice() * borrowQuantity > soDuTaiKhoan) {
                System.out.println("Số dư tài khoản không đủ đê mượn đầu sách này. " +
                        "Hệ thống sẽ tự động lập phiếu mượn sách với các cuôn sách đã mượn thành công .");
                break;
            }

            System.out.println("Mời bạn nhập tình trạng ban đầu của sách: ");
            String originalStatus = new Scanner(System.in).nextLine();
            BookBorrowDetail bookBorrowDetail = new BookBorrowDetail(book, borrowQuantity, originalStatus);
            details.add(bookBorrowDetail);
            double soDuTkMoi = borrower.getBalance() - book.getPrice() * borrowQuantity;
            System.out.println("Số dư tài khoản còn lại tạm tính " + soDuTkMoi);
            System.out.println("Bạn có muốn tiếp tục mượn không (Y/N):");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equalsIgnoreCase("n")) {
                break;
            }
        }
        return details;
    }

    public void returnBook() {
        User borrower = inputBorrower();
        BookBorrow borrowing = findBorrowing(borrower.getId());
        if (borrowing == null) {
            System.out.println("Bạn đọc này đã trả tất cả các sách mượn ở các phiếu rồi.");
            return;
        }
        LocalDate returnDate = LocalDate.now();
        borrowing.setActualReturnDate(returnDate);
        long soNgayThueThucTe = ChronoUnit.DAYS.between(returnDate, borrowing.getCreatedDate());
        double tienThueThucTe = 0;
        for (BookBorrowDetail detail : borrowing.getDetail()) {
            tienThueThucTe += soNgayThueThucTe * detail.getBook().getBorrowPricePerDay() * detail.getBorrowQuantity();
        }
        borrowing.setTotalActualBorrowFee(tienThueThucTe);

        double tienPhatThueQuaHan = 0;
        long soNgayQuaHan = ChronoUnit.DAYS.between(returnDate, borrowing.getExpectedReturnDate());
        if (soNgayQuaHan > 0) {
            tienPhatThueQuaHan = tienThueThucTe - borrowing.getTotalExpectedBorrowFee();
            System.out.println("Đã quá hạn trả " + soNgayQuaHan + " ngày, bạn đọc cần nộp thêm tiền thuê tương ứng " +
                    "với số ngày vượt quá là " + tienPhatThueQuaHan);

        }
        double tienPhatDoTinhTrangSach = 0;
        double tienCocTraLai = 0;
        for (BookBorrowDetail detail : borrowing.getDetail()) {
            Book book = detail.getBook();
            System.out.println("Mời bạn nhập số sách mà khách hàng trả : ");
            int numberBookReturn;
            while (true) {
                try {
                    numberBookReturn = new Scanner(System.in).nextInt();
                    if (numberBookReturn < 0) {
                        System.out.println("Số sách trả về phải là số dương hoặc bằng 0, vui lòng nhập lại");
                        continue;
                    }
                    break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                }
            }
            detail.setReturnQuantity(numberBookReturn);
            System.out.println("Mời bạn nhập tình trạng sách khi trả : ");
            String status = new Scanner(System.in).nextLine();
            System.out.println("Mời bạn nhập sô tiền phạt tính theo % giá sách : ");
            float x;
            while (true) {
                try {
                    x = new Scanner(System.in).nextInt();
                    if (x < 0) {
                        System.out.println("Số % phạt phải là số dương hoặc bằng 0, vui lòng nhập lại");
                        continue;
                    }
                    break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                }
            }
            tienPhatDoTinhTrangSach += x / 100 * book.getPrice();
            tienCocTraLai += numberBookReturn * book.getPrice();
            book.setTotalQuantity(book.getTotalQuantity() + numberBookReturn);
            detail.setReturnQuantity(numberBookReturn);
            detail.setReturnStatus(status);
        }

        double tongTienPhat = tienPhatThueQuaHan + tienPhatDoTinhTrangSach;
        borrowing.setTotalPunishAmount(tongTienPhat);
        borrower.setBalance(borrower.getBalance() - tongTienPhat + tienCocTraLai);
        String returnBookTransactionContent = "Hoàn trả tiền cọc sách khi bạn đọc " + borrower.getFullName() + " trả sách";
        Transaction returnBookTransaction = new Transaction(borrower, returnDate, tienCocTraLai, TransactionType.RETURN, returnBookTransactionContent);
        transactionService.saveTransaction(returnBookTransaction);
        if (tongTienPhat > 0) {
            String punishedTransactionContent = "Phạt khi bạn đọc " + borrower.getFullName() + " trả sách";
            Transaction punishedTransaction = new Transaction(borrower, returnDate, -tongTienPhat, TransactionType.PUNISH, punishedTransactionContent);
            transactionService.saveTransaction(punishedTransaction);
        }
        saveBookBorrowsData();
        userService.saveUserData();
        bookService.saveBookData();
    }

    public List<BookBorrow> findByUserId(int userId) { // tìm tất cả các lượt mượn sách từ trước tới giờ của user theo id
        List<BookBorrow> borrows = new ArrayList<>();
        for (BookBorrow bookBorrow : bookBorrows) {
            if (bookBorrow.getBorrower().getId() == userId) {
                borrows.add(bookBorrow);
            }
        }
        return borrows;
    }

    private User inputBorrower() {
        User borrower;
        while (true) {
            System.out.print("Mời bạn nhâp mã bạn đọc: ");
            int idUser;
            try {
                idUser = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                continue;
            }
            borrower = userService.findUserById(idUser);
            if (borrower == null) {
                System.out.println("Nhập sai thông tin vui lòng nhập lại");
                continue;
            }
            break;
        }
        return borrower;
    }

    private BookBorrow findBorrowing(int userId) { // tìm kiếm lượt mượn sách mà CHƯA trả của 1 user nhất định
        List<BookBorrow> borrows = findByUserId(userId);
        // lọc xem trong tất cả các lượt mượn kia có lượt nào là lượt chưa trả hay không
        for (BookBorrow borrow : borrows) {
            if (borrow.getActualReturnDate() == null) {// không có giá trị ngày trả ==> tức là chưa trả sách
                return borrow;
            }
        }
        return null;
    }

    public List<BookBorrow> findByUserIdShowMenu() {
        System.out.println("Mời bạn nhập ID của User: ");
        int userId;
        while (true) {
            try {
                userId = new Scanner(System.in).nextInt();
                if (userId < 0) {
                    System.out.println("ID của User phải là số dương , vui lòng nhập lại ");
                    continue;
                }
                break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
            }
        }
        return findByUserId(userId);
    }

    public List<BookBorrow> findByBookName() {
        System.out.println("Mời bạn nhập tên của sách: ");
        String nameBook = new Scanner(System.in).nextLine();
        List<BookBorrow> borrows = new ArrayList<>();
        for (BookBorrow borrow : bookBorrows) {
            List<BookBorrowDetail> details = borrow.getDetail();
            for (BookBorrowDetail detail : details) {
                if (detail.getBook().getName().toLowerCase().contains(nameBook.toLowerCase())) {
                    borrows.add(borrow);
                }
            }
        }
        return borrows;

    }

    public void findNearestBorrowByUserId() {// tìm kiếm lượt mượn gần nhất
        List<BookBorrow> borrows = findByUserId(Main.loggedInUser.getId());
        long minDifference = Long.MAX_VALUE;
        BookBorrow nearestBorrow = null;
        for (BookBorrow borrow : borrows) {
            long difference = ChronoUnit.DAYS.between(borrow.getActualReturnDate(), LocalDate.now());
            if (difference >= 0 && difference < minDifference) {
                minDifference = difference;
                nearestBorrow = borrow;
            }
        }
        System.out.println(nearestBorrow);
    }

    public List<BookBorrow> findByUserName() {
        System.out.println("Mời bạn nhập tên của bạn đọc: ");
        String userName = new Scanner(System.in).nextLine();
        List<BookBorrow> borrows = new ArrayList<>();
        for (BookBorrow borrow : bookBorrows) {
            if (borrow.getBorrower().getFullName().toLowerCase().contains(userName.toLowerCase())) {
                borrows.add(borrow);
            }
        }
        return borrows;
    }

    public void showBookBorrows() {
        System.out.printf("%-30s%-30s%-15s%-25s%-25s%-25s%-25s%-25s%-25s%n", "Name", "Details", "createdDate", "expectedReturnDate","actualReturnDate","totalDepositAmount","totalExpectedBorrowFee"
                ,"totalActualBorrowFee","totalPunishAmount");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (BookBorrow borrow : bookBorrows) {
            showBookBorrow(borrow);
        }
    }

    public void showBookBorrow(BookBorrow borrow) {
        List<BookBorrowDetail> details = borrow.getDetail();
        for (int i = 0; i < details.size(); i++) {
            System.out.printf("%-30s%-30s%-15s%-25s%-25s%-25s%-25s%-25s%-25s%n",borrow.getBorrower().getFullName(),
                    borrow.getDetail().get(i),borrow.getCreatedDate(),borrow.getExpectedReturnDate(),
                    borrow.getActualReturnDate(),borrow.getTotalDepositAmount(),borrow.getTotalExpectedBorrowFee(),
                    borrow.getTotalActualBorrowFee(),borrow.getTotalPunishAmount());
        }



    }

}
