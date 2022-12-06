package com.example.bookstore.cart;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;


public class CartData {

    String ISBN, imgURL, title;
    private int count;
    int price;

    public CartData(Object ISBN, Object imgURL, Object count, Object title, Object price) {
        this.count= ((Long)count).intValue();
        this.imgURL = (String)imgURL;
        this.price = ((Long)price).intValue();
        this.title= (String) title;
        this.ISBN = (String) ISBN;
    }

    public CartData(String ISBN, String imgURL, int count, String title,Long price){
        this.count= count;
        this.imgURL = imgURL;
        this.price =  price.intValue();
        this.title= title;
        this.ISBN = ISBN;
    }




    public CartData(){}



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
