package com.example.bookstore;

public interface IClickListener {
    void onIncrementClick(int price);
    void onDecrementClick(int price);
    void onItemClick();
}
