package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class OrderList extends AppCompatActivity  implements IClickListener{
    private ArrayList<OrderListView> orderListViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerCartItemso);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        OrderAdapter orderAdapter = new OrderAdapter(OrderList.this, orderListViews);
        recyclerView.setAdapter(orderAdapter); // set the Adapter to RecyclerView

    }

    @Override
    public void onIncrementClick(int price) {

    }

    @Override
    public void onDecrementClick(int price) {

    }

    @Override
    public void onItemClick() {
        Intent myIntent = new Intent(OrderList.this, OrderHistory.class);
        this.startActivity(myIntent);
    }
}