package entity;

import java.time.LocalDate;

public class Book {
    private static int autoId;
    private int id ;
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
        this.id = autoId++;
    }

    public Book(String name, String author, BookCategory category, String publisher, LocalDate publishedYear, double price, double borrowPricePerDay, int totalQuantity) {
        this.id = autoId++;
        this.name = name;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.price = price;
        this.borrowPricePerDay = borrowPricePerDay;
        this.totalQuantity = totalQuantity;
    }

    public int getIdCategory() {
        return id;
    }

    public void setIdCategory(int idCategory) {
        this.id = idCategory;
    }

    public String getNameCategory() {
        return name;
    }

    public void setNameCategory(String nameCategory) {
        this.name = nameCategory;
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



    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", nameBook='" + name + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", publishingCompany='" + publisher + '\'' +
                ", publishingYear=" + publishedYear +
                ", priceBook=" + price +
                ", rentByDay=" + borrowPricePerDay +
                ", numerBooks=" + totalQuantity +
                '}';
    }
}
