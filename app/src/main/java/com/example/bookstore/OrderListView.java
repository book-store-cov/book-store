package com.example.bookstore;

public class OrderListView {

    public OrderListView(){


    }


    public OrderListView(Object title, Object orderID, Object price, Object imageURL, Object ISBN) {
        this.bookTitle = (String) title;
        this.orderID = (String) orderID;
        this.bookPrice = (Long) price;
        this.bookImage = (String) imageURL;
        this.ISBN = (String) ISBN;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Long getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Long bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookImage() {
        return bookImage;
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

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    private String bookTitle;

    private Long bookPrice;
    private String bookImage;
    private String ISBN;

    private String orderID;
}
