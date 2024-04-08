package entity;

import java.time.LocalDate;

public class Book {

    private int id;
    private String name;
    private String author;
    private BookCategory category;
    private String publisher;
    private LocalDate publishedYear;
    private double price;
    private double borrowPricePerDay;
    private int totalQuantity;
    private double voteStar;
    private int voteCount;

    public Book() {

    }

    public Book(int id, String name, String author, BookCategory category, String publisher, LocalDate publishedYear,
                double price, double borrowPricePerDay, int totalQuantity) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.price = price;
        this.borrowPricePerDay = borrowPricePerDay;
        this.totalQuantity = totalQuantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {  // đoạn này tự  làm không chắc đúng không
        return id;
    }

    public int getIdCategory() {
        return id;
    }

    public void setIdCategory(int idCategory) {
        this.id = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(LocalDate publishedYear) {
        this.publishedYear = publishedYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getBorrowPricePerDay() {
        return borrowPricePerDay;
    }

    public void setBorrowPricePerDay(double borrowPricePerDay) {
        this.borrowPricePerDay = borrowPricePerDay;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }


    public void setVoteStar(double voteStar) {
        this.voteStar = voteStar;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;

    }

    public int getVoteCount() {
        return voteCount;
    }

    public double getVoteStar() {
        return voteStar;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
