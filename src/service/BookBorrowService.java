package service;

import constant.DateTimeConstant;
import entity.Book;
import entity.BookBorrow;
import entity.BookBorrowDetail;
import entity.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookBorrowService {

    private final List<BookBorrow> bookBorrows = new ArrayList<>(); // danh sách các lượt mượn/trả sách

    private final UserService userService;
    private final BookCategoryService bookCategoryService;
    private final BookService bookService;

    public BookBorrowService(UserService userService, BookCategoryService bookCategoryService, BookService bookService) {
        this.userService = userService;
        this.bookCategoryService = bookCategoryService;
        this.bookService = bookService;
    }

    public void createBorrow() {
        User borrower = inputBorrower();
        // TODO - check xem ông user vừa nhập có đnag có lượt mượn nào chưa trả không
        //  => Nếu có thì dừng, không cho mượn, yêu cầu trả sch trước khi mượn lượt tiếp theo . Đã làm xong
        // dựa vào hàm findByUserId
        int userId = borrower.getId();
        BookBorrow borrow = findBorrowing(userId);
        if(borrow != null){
            System.out.println("Bạn đọc này đang có lượt mượn sách chưa trả");
            return;
        }
        System.out.println("Bạn đọc này có thể tiếp tục mượn sách ");
        // TODO - in ra số dư tài khoản hiện tại
        //  => check xem tài khaon đang có trên 50k hay không, nếu không thì không cho mượn nữa . Đã làm xong nhưng phần
        //  balance của User chưa xử lý logic khi trừ tiền số dư khi tra sách .
        double soDuTK = borrower.getBalance();
        System.out.println(soDuTK);
        if(soDuTK<50000){
            System.out.println("Bạn đọc này có số dư tài khoản dưới 50.000 , vui lòng nạp thêm tài khoản ");
        }
        System.out.println("Bạn đọc này có thể tiếp tục mượn sách ");
        // nhập danh sách các đầu sách muốn mượn (kèm theo số lượng và tình trạng tương ứng)
        List<BookBorrowDetail> details = createdBorrowDetail();

        System.out.println("Mời bạn nhập ngày trả dự kiến (DD/MM/YYYY): ");
        LocalDate expectedReturnDate = LocalDate.parse(new Scanner(System.in).nextLine(),
                DateTimeConstant.DATE_FORMATTER);
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

        BookBorrow bookBorrow = new BookBorrow(borrower, details, createdDate,
                expectedReturnDate, tongTienCoc, tongTienThue);
        bookBorrows.add(bookBorrow);
    }

    private List<BookBorrowDetail> createdBorrowDetail() {
        List<BookBorrowDetail> details = new ArrayList<>(); // danh sách các đầu sách mà người dùng muốn mượn
        System.out.println("Mời bạn nhập số đầu sách muốn mượn : ");
        int numberOfBook = new Scanner(System.in).nextInt();
        for (int i = 0; i < numberOfBook; i++) {
            Book book;
            while (true) {
                System.out.println("Mời bạn nhập id của quyển sách bạn muốn mượn: ");
                int idBook = new Scanner(System.in).nextInt();
                book = bookService.findBookById(idBook);
                if (book == null) {
                    System.out.println("id không chính xác vui lòng nhập lại ");
                    continue;
                }
                break;
            }
            System.out.println("Mời bạn nhập số lượng sách muốn thuê: ");
            int borrowQuantity = new Scanner(System.in).nextInt();
            // TODO - nếu như mượn quyển i này thì còn đủ tiền không
            // TODO - nếu còn cho mượn tiếp, nếu không thì thoát vòng lặp
            System.out.println("Mời bạn nhập tình trạng ban đầu của sách: ");
            String originalStatus = new Scanner(System.in).nextLine();
            BookBorrowDetail bookBorrowDetail = new BookBorrowDetail(book, borrowQuantity, originalStatus);
            details.add(bookBorrowDetail);
            // TODO - in ra số dư tài khoản nếu thực hiện mượn cuốn sách thứ i này -> nếu muốn dưừng thì thoát vòng lặp
        }
        return details;
    }

    public void returnBook() {
        // TODO - hoàn thiện việc trả sách
        // ai trả
        User borrower = inputBorrower();

        // trả cho phiếu nào
        BookBorrow borrowing = findBorrowing(borrower.getId());
        if (borrowing == null) {
            System.out.println("Bạn đọc này đã trả tất cả các sách mượn ở các phiếu rồi.");
            return;
        }

        // cập nhật trạng thái phiếu trả:
        // --> có trả quá hạn không
        LocalDate returnDate = LocalDate.now();
        long soNgayQuaHan = ChronoUnit.DAYS.between(returnDate, borrowing.getExpectedReturnDate());
        if (soNgayQuaHan > 0) {
            System.out.println("Đã quá hạn trả " + soNgayQuaHan + " ngày, bạn đọc cần nộp thêm tiền thuê tương ứng với số ngày vượt quá");
        }

        // có rách, hỏng sách không -> nếu có thì phạt
        // tính tổng tiền phạt
        for (BookBorrowDetail detail : borrowing.getDetail()) {
            // xem từng đầu sách trả lại bao nhiêu, có đúng với số lượng mượn không => set giá trị cho returnQuantity
            // nếu trả thiếu ==> tính tiền phat
            // nếu rách, hỏng, ... ==> tính tiền phạt
            // cộng tổng tiền phạt lại => đó là giá trị của punishAmount

            // neeu mượn vượt quá ngày (soNgayQuaHan > 0) ==> tính số tiền mượn bù cho những ngày thuê quá
            // ==> đó là giá trị của actualBorrowFee
        }

        // tính tổng tiền cần nộp thêm
        // KHONG CO VIEC ADD VÀO LIST, MÀ PHẢI CẬP NHẬT GIÁ TRỊ CỦA CÁC THUỘC TÍNH CHO OBJECT borrowing THÔNG QUA CÁC HÀM SET
    }

    public List<BookBorrow> findByUserId(int userId) { // tìm tất cả các lượt mượn sách từ trước tới giờ của user theo id
        User borrower = userService.findUserById(userId);
        List<BookBorrow> borrows = null;
        for(BookBorrow bookBorrow : bookBorrows){
            if(bookBorrow.getBorrower() == borrower){
                borrows.add(bookBorrow);
                return borrows;
            }
        }
        // TODO - hoàn thiện hàm này . Đã làm xong .
        return null;
    }

    private User inputBorrower() {
        User borrower;
        while (true) {
            System.out.print("Mời bạn nhâp mã bạn đọc: ");
            int idUser = new Scanner(System.in).nextInt();
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
            if (borrow.getActualReturnDate() == null) {// không có giá trị ngày tr ==> tức là chưa trả sách
                return borrow;
            }
        }
        return null;
    }

}
