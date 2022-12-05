package com.example.bookstore;

import java.util.HashMap;

public class OrderListView {

    private HashMap<String, Object> orders;
    private int  totalPrice = 0;
    private String orderID;

    public OrderListView(){}

    public OrderListView(HashMap<String, Object>orders){
        this.orders = orders;
        this.totalPrice = ((Long) orders.get("totalPrice")).intValue();
        this.orderID  = (String) orders.get("orderID");
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int price) {
        this.totalPrice = price;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }


}
