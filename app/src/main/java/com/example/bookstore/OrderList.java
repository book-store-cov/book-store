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

import com.example.bookstore.databinding.ActivityOrderListBinding;
import com.example.bookstore.orders.OnOrderClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class OrderList extends AppCompatActivity implements OnOrderClickListener {

    DatabaseReference database;
    ArrayList<OrderListView> orderListViews;
    //private ArrayList<OrderListView> orderListViews;
    ActivityOrderListBinding binding;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderListBinding.inflate(getLayoutInflater());


        setContentView(R.layout.activity_order_list);

        database = FirebaseDatabase.getInstance().getReference().child("orders");
        orderListViews = new ArrayList<OrderListView>();



        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerCartItems);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderList.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        OrderAdapter orderAdapter = new OrderAdapter(OrderList.this, orderListViews);
        recyclerView.setAdapter(orderAdapter); // set the Adapter to RecyclerView


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HashMap<String, Object> data = (HashMap<String, Object>) dataSnapshot.getValue();
                    OrderListView orderList = new OrderListView(data.get("title"), data.get("ISBN"), data.get("price"), data.get("imageURL"), data.get("orderID"));
                    Log.d("debug2", "order list1" + orderList);

                    orderListViews.add(orderList);
                }
                Log.d("debug2", "order list" + orderListViews);
                orderAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
    public void onOrderClick(View view, int position){
        Intent i = new Intent(OrderList.this, OrderHistory.class);
        i.putExtra("orderId", orderListViews.get(position).getOrderID());
        startActivity(i);
  }


}