package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.databinding.ActivityOrderHistoryBinding;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity
{

    ActivityOrderHistoryBinding binding;
    private ArrayList<OrderHistoryList> orderHistoryLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerCartItemsp);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(OrderHistory.this, orderHistoryLists);
        recyclerView.setAdapter(orderHistoryAdapter); // set the Adapter to RecyclerView

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.navbar_orders);
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.navbar_orders:
                        Intent intent = new Intent(OrderHistory.this, OrderList.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_cart:
                        Intent intent1 = new Intent(OrderHistory.this, Cart.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_logout:
//                        Logout;
                        return true;
                    case R.id.navbar_home:
                        Intent intent2 = new Intent(OrderHistory.this, BookListMain.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });




    }


}