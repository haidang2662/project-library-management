package entity;

import java.time.LocalDate;

public class VoteHistory {
    private User user;
    private Book book;
    private String ratedContent;
    private LocalDate createdDate;
    private double voteStarHistory;

    public VoteHistory() {
    }

    public VoteHistory(User user, Book book, String ratedContent, double voteStarHistory) {
        this.user = user;
        this.book = book;
        this.ratedContent = ratedContent;
        this.createdDate = LocalDate.now();
        this.voteStarHistory = voteStarHistory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getRatedContent() {
        return ratedContent;
    }

    public void setRatedContent(String ratedContent) {
        this.ratedContent = ratedContent;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public double getVoteStarHistory() {
        return voteStarHistory;
    }

    public void setVoteStarHistory(double voteStarHistory) {
        this.voteStarHistory = voteStarHistory;
    }

    @Override
    public String toString() {
        return "HistoryVote{" +
                "user=" + user +
                ", book=" + book +
                ", ratedContent='" + ratedContent + '\'' +
                ", localDate=" + createdDate +
                ", voteStarHistory=" + voteStarHistory +
                '}';
    }
}
