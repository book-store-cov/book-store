package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookstore.cart.CartData;
import com.example.bookstore.databinding.ActivityOrderListBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class OrderList extends AppCompatActivity implements IClickListener{
    private ArrayList<OrderListView> orderListViews;
    ActivityOrderListBinding binding;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderListBinding.inflate(getLayoutInflater());


        setContentView(R.layout.activity_order_list);

        RecyclerView recyclerView = (RecyclerView) binding.recyclerCartItems;
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderList.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        OrderAdapter orderAdapter = new OrderAdapter(OrderList.this, orderListViews);
        recyclerView.setAdapter(orderAdapter); // set the Adapter to RecyclerView

        binding.shoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderList.this,BookListMain.class);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.navbar_orders);
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.navbar_orders:
                        Intent intent = new Intent(OrderList.this, OrderList.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_cart:
                        Intent intent1 = new Intent(OrderList.this, Cart.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_logout:
//                        Logout;
                        return true;
                    case R.id.navbar_home:
                        Intent intent2 = new Intent(OrderList.this, BookListMain.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });




    }



    @Override
    public void onIncrementClick(CartData cartObj) {

    }

    @Override
    public void onDecrementClick(CartData cartObj) {

    }

    @Override
    public void onItemClick() {
        Intent myIntent = new Intent(OrderList.this, OrderHistory.class);
        this.startActivity(myIntent);
    }
}