package com.example.bookstore;


public class BookListData {

    private final String bookName;
    private final String bookAuthor;
    private final String bookPrice;
    private final Integer bookImage;

    public BookListData(String bookName, String bookAuthor, String bookPrice, Integer bookImage) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
        this.bookImage = bookImage;

    }

    public String getBookName() {
        return bookName;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public Integer getBookImage() {
        return bookImage;
    }


}