package com.example.bookstore;

import com.example.bookstore.cart.CartData;

public interface IClickListener {
    void onIncrementClick(CartData cartObj);
    void onDecrementClick(CartData cartObj);
    void onItemClick();
}
