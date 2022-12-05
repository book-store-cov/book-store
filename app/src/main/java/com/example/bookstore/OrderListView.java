package com.example.bookstore;

public class OrderListView {
    private String orderID;

    private Long totalPrice;
    public OrderListView(){


    }

    public OrderListView(Object totalPrice, Object orderID) {

        this.orderID = (String) orderID;
        this.totalPrice = (Long) totalPrice;

    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }







}
