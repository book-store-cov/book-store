package com.example.bookstore;

public class OrderHistoryList {

    String title;
    int count;
    int price;

    public OrderHistoryList(){}

    public OrderHistoryList(Object title, Object count, Object price, Object ISBN, Object imageURL){
        this.price = ((Long) price).intValue();
        this.count = ((Long) count).intValue();
        this.title = (String) title;
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
