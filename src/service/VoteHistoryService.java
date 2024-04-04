package service;

import entity.Book;
import entity.User;
import entity.VoteHistory;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VoteHistoryService {

    private final ArrayList<VoteHistory> voteHistories = new ArrayList<>();

    private final UserService userService;

    private final BookService bookService;

    public VoteHistoryService(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    public void inputVote() {
        User user = userService.getLoggedInUser();
        System.out.println("Mời bạn nhập ID sách : ");
        Book book;
        while (true) {
            int bookId;
            while (true) {
                try {
                    bookId = new Scanner(System.in).nextInt();
                    break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                }
            }
            book = bookService.findBookById(bookId);
            if (book == null) {
                System.out.println("thông tin ID không chính xác , vui lòng nhập lại ");
                continue;
            }
            break;
        }
        System.out.println("Mời bạn nhập số sao đánh giá cho sách này (trong khoảng từ 0 dến 5): ");
        double newVoteStar;
        while (true) {
            while (true) {
                try {
                    newVoteStar = new Scanner(System.in).nextDouble();
                    break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số tụ nhiên . Vui lòng nhập lại.");
                }
            }
            if (newVoteStar < 0 || newVoteStar > 5) {
                System.out.println("Số sao đánh giá trong khoảng từ 0 dến 5 , vui lòng nhập lại ");
                continue;
            }
            break;
        }
        System.out.println("Mời bạn ghi nhận xét về quyển sách : ");
        String comment = new Scanner(System.in).nextLine();
        VoteHistory voteHistory = new VoteHistory(user, book, comment, newVoteStar);
        voteHistories.add(voteHistory);

        book.setVoteCount(book.getVoteCount() + 1);
        book.setVoteStar((book.getVoteStar() * (book.getVoteCount() - 1) + newVoteStar) / book.getVoteCount());
    }

    public void findHistoryVoteByBookId() {
        System.out.println("Mời bạn nhập ID sách : ");
        Book book;
        int bookId;
        while (true) {
            while (true) {
                try {
                    bookId = new Scanner(System.in).nextInt();
                    break; // Thoát khỏi vòng lặp nếu giá trị được nhập vào là số nguyên hợp lệ
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                }
            }
            book = bookService.findBookById(bookId);
            if (book == null) {
                System.out.println("thông tin ID không chính xác , vui lòng nhập lại ");
                continue;
            }
            break;
        }
        ArrayList<VoteHistory> votesFindHistory = new ArrayList<>();
        for (VoteHistory voteHistory : voteHistories) {
            if (voteHistory.getBook().getId() == bookId) {
                votesFindHistory.add(voteHistory);
            }
        }
        System.out.println(votesFindHistory);
    }

    public void findHistoryVoteByBookName() {
        System.out.println("Mời bạn nhập tên sách : ");
        String bookName = new Scanner(System.in).nextLine();
        ArrayList<VoteHistory> votesFindHistory = new ArrayList<>();
        for (VoteHistory voteHistory : voteHistories) {
            if (voteHistory.getBook().getName().toLowerCase().contains(bookName.toLowerCase())) {
                votesFindHistory.add(voteHistory);
            }
        }
        System.out.println(votesFindHistory);
    }
}
