package entity;

import java.time.LocalDate;
import java.util.List;

public class BookBorrow {

    private User borrower;// người thuê

    private List<BookBorrowDetail> detail; // danh sách các cuốn sách thuê kèm theo số lượng tương ứng

    private LocalDate createdDate; // ngày thuê

    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;

    private double totalDepositAmount;
    private double totalExpectedBorrowFee;
    private double totalActualBorrowFee;
    private double totalPunishAmount;



    public BookBorrow() {
    }

    public double getTotalBorrowFee() {
        return totalExpectedBorrowFee;
    }

    public void setTotalBorrowFee(double totalBorrowFee) {
        this.totalExpectedBorrowFee = totalBorrowFee;
    }



    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public List<BookBorrowDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<BookBorrowDetail> detail) {
        this.detail = detail;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public double getTotalDepositAmount() {
        return totalDepositAmount;
    }

    public void setTotalDepositAmount(double totalDepositAmount) {
        this.totalDepositAmount = totalDepositAmount;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public double getTotalExpectedBorrowFee() {
        return totalExpectedBorrowFee;
    }

    public void setTotalExpectedBorrowFee(double totalExpectedBorrowFee) {
        this.totalExpectedBorrowFee = totalExpectedBorrowFee;
    }

    public double getTotalActualBorrowFee() {
        return totalActualBorrowFee;
    }

    public void setTotalActualBorrowFee(double totalActualBorrowFee) {
        this.totalActualBorrowFee = totalActualBorrowFee;
    }

    public double getTotalPunishAmount() {
        return totalPunishAmount;
    }

    public void setTotalPunishAmount(double totalPunishAmount) {
        this.totalPunishAmount = totalPunishAmount;
    }

    @Override
    public String toString() {
        return "BookBorrow{" +
                "borrower=" + borrower +
                ", detail=" + detail +
                ", createdDate=" + createdDate +
                ", expectedReturnDate=" + expectedReturnDate +
                ", actualReturnDate=" + actualReturnDate +
                ", totalDepositAmount=" + totalDepositAmount +
                ", totalExpectedBorrowFee=" + totalExpectedBorrowFee +
                ", totalActualBorrowFee=" + totalActualBorrowFee +
                ", totalPunishAmount=" + totalPunishAmount +
                '}';
    }
}
