package com.example.bookstore;

public class Overview {
    private int allBooks, soldBooks, availableBooks;


    public Overview(){
    }
    public Overview(int allBooks, int soldBooks, int availableBooks){
        this.allBooks = allBooks;
        this.availableBooks = availableBooks;
        this.soldBooks = soldBooks;
    }

    public int getAllBooks() {
        return allBooks;
    }

    public void setAllBooks(int allBooks) {
        this.allBooks = allBooks;
    }

    public int getSoldBooks() {
        return soldBooks;
    }

    public void setSoldBooks(int soldBooks) {
        this.soldBooks = soldBooks;
    }

    public int getAvailableBooks() {
        return availableBooks;
    }

    public void setAvailableBooks(int availableBooks) {
        this.availableBooks = availableBooks;
    }
}
