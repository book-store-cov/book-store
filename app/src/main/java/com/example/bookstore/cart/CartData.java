package com.example.bookstore.cart;

public class CartData {

    String ISBN, imgURL, title;
    private int count;
    int price;



    public void CartData(){}

    public void CartData(String ISBN, String imgURL, int count, String title,int price){
        this.count= count;
        this.imgURL = imgURL;
        this.price = price;
        this.title= title;
        this.ISBN = ISBN;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }








}
