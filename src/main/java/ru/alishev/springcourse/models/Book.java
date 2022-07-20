package ru.alishev.springcourse.models;

import javax.validation.constraints.*;

public class Book {
    int id;
    @NotEmpty(message = "Book name should not be empty")
    @Size(min = 2, max = 30, message = "Book name should be between 2 and 30 characters")
    String name;

    @NotEmpty(message = "Book name should not be empty")
    @Size(min = 2, max = 30, message = "Author name should be between 2 and 30 characters")
    @Pattern(regexp = "[A-zА-я ]+",
            message = "Author name should have only letters or keyboard spaces")
    String author;

    @Min(value = 1000, message = "Year should be greater than 1000")
    int year;

    public Book() {}

    public Book(int id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
