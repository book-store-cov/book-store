package com.example.bookstore;


import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class BookListData {

    private String bookTitle;
    private String bookAuthor;
    private Long bookPrice;
    private String bookImage;
    private String ISBN;
    private Long bookDescription;
    private String publicationDate;


    public BookListData(){
        Log.d("debug2", "reached no arg constructor");

    }


    public BookListData(Object title, Object author, Object price, Object imageURL) {
        this.bookTitle = (String) title;
        this.bookAuthor = (String) author;
        this.bookPrice = (Long) price;
        this.bookImage = (String) imageURL;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Long getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Long bookPrice) {
        this.bookPrice = bookPrice;
    }

    public URL getBookImage() throws MalformedURLException {
        if(bookImage!=null) {
            return new URL(bookImage);
        }
        return new URL("");
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Long getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(Long bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }








}