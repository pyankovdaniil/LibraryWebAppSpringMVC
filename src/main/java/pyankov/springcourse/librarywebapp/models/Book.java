package pyankov.springcourse.librarywebapp.models;

import javax.validation.constraints.*;

public class Book {
    @NotEmpty(message = "Title should be not empty")
    @Size(min = 4, max = 50)
    private String title;

    @NotEmpty(message = "Author should be not empty")
    @Size(min = 4, max = 50, message = "Author name should be  of 8 <= size <= 100")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+",
            message = "Please enter: Surname, Name")
    private String author;

    @Min(value = 1700, message = "Year of publishing should be >= 1700")
    @Max(value = 2022, message = "Year of publishing should be <=  2022")
    private int yearOfPublishing;

    private int bookId;

    public Book() {
    }

    public Book(String title, String author, int yearOfPublishing) {
        this.title = title;
        this.author = author;
        this.yearOfPublishing = yearOfPublishing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return this.title + ", " + this.author + ", " + this.yearOfPublishing;
    }
}
