package com.example.bookstore;

public class CartList {

    public String bookName;
    public  String authorName;
    public int price;
    public int bookId;
    public int count;
    public int totalPrice;
    public int customerName;
    public int customerId;


    public CartList(String bookName, String authorName, int price, int bookId,int count) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.price = price;
        this.bookId = bookId;
        this.count = count;
    }

    public static int size() {
        return 0;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCustomerName() {
        return customerName;
    }

    public void setCustomerName(int customerName) {
        this.customerName = customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
