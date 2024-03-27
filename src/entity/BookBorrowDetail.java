package entity;

public class BookBorrowDetail {
    private Book book;
    private int borrowQuantity;
    private int returnQuantity;
    private String originalStatus;
    private String returnStatus;

    private double expectedBorrowFee;// dự kiến
    private double actualBorrowFee; // thực tế
    private double punishAmount;

    public BookBorrowDetail() {
    }

    public BookBorrowDetail(Book book, int borrowQuantity, int returnQuantity, String originalStatus, String returnStatus, double expectedBorrowFee, double actualBorrowFee, double punishAmount) {
        this.book = book;
        this.borrowQuantity = borrowQuantity;
        this.returnQuantity = returnQuantity;
        this.originalStatus = originalStatus;
        this.returnStatus = returnStatus;
        this.expectedBorrowFee = expectedBorrowFee;
        this.actualBorrowFee = actualBorrowFee;
        this.punishAmount = punishAmount;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getBorrowQuantity() {
        return borrowQuantity;
    }

    public void setBorrowQuantity(int borrowQuantity) {
        this.borrowQuantity = borrowQuantity;
    }

    public int getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(int returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public String getOriginalStatus() {
        return originalStatus;
    }

    public void setOriginalStatus(String originalStatus) {
        this.originalStatus = originalStatus;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public double getExpectedBorrowFee() {
        return expectedBorrowFee;
    }

    public void setExpectedBorrowFee(double expectedBorrowFee) {
        this.expectedBorrowFee = expectedBorrowFee;
    }

    public double getActualBorrowFee() {
        return actualBorrowFee;
    }

    public void setActualBorrowFee(double actualBorrowFee) {
        this.actualBorrowFee = actualBorrowFee;
    }

    public double getPunishAmount() {
        return punishAmount;
    }

    public void setPunishAmount(double punishAmount) {
        this.punishAmount = punishAmount;
    }

    @Override
    public String toString() {
        return "BookBorrowDetail{" +
                "book=" + book +
                ", borrowQuantity=" + borrowQuantity +
                ", returnQuantity=" + returnQuantity +
                ", originalStatus='" + originalStatus + '\'' +
                ", returnStatus='" + returnStatus + '\'' +
                ", expectedBorrowFee=" + expectedBorrowFee +
                ", actualBorrowFee=" + actualBorrowFee +
                ", totalPunishAmount=" + punishAmount +
                '}';
    }
}
