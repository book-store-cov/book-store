package com.example.bookstore.orders;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderBook {

    private HashMap<String, Object> books;
    private int count, price;
    private String ISBN;


    public OrderBook(){}
    public OrderBook(HashMap<String, Object> books){
        this.books = books;
        Log.d("debug4", "count: "+books.get("count")+ " price:"+ books.get("price")+" isbn:"+books.get("ISBN"));
        this.count = ((Long)books.get("count")).intValue();
        this.price = ((Long)books.get("price")).intValue();
        this.ISBN = String.valueOf(books.get("ISBN"));
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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
