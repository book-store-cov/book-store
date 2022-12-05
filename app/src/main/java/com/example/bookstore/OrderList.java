package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.bookstore.databinding.ActivityOrderListBinding;
import com.example.bookstore.orders.OnOrderClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OrderList extends AppCompatActivity implements OnOrderClickListener {

    DatabaseReference dbRef;
    ArrayList<OrderListView> orderListViews;
    ActivityOrderListBinding binding;
    String uid;
    boolean isAdmin = false;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderListBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_order_list);
        dbRef = FirebaseDatabase.getInstance().getReference();

        orderListViews = new ArrayList<OrderListView>();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            uid = currentUser.getUid();
        }

        DatabaseReference isUserAnAdminRef = dbRef.child("users").child(uid).child("isAdmin");

//        set up admin check
        isUserAnAdminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isAdmin = (boolean) snapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        //SET UP RECYCLER VIEW

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerCartItemsp);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderList.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        OrderAdapter orderAdapter = new OrderAdapter(OrderList.this, orderListViews);
        recyclerView.setAdapter(orderAdapter); // set the Adapter to RecyclerView


//        FETCH DATA FOR ORDER LIST BASED ON ROLETYPE
        DatabaseReference orderRef = dbRef.child("orders");

        if(isAdmin){
            orderRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snap: snapshot.getChildren()){
                        HashMap<String, Object> resData =(HashMap<String, Object>) snap.getChildren();
                        for (Map.Entry<String, Object> set : resData.entrySet()){
                            OrderListView orderObj = new OrderListView((HashMap<String, Object>) set.getValue());
                            orderListViews.add(orderObj);
                            Log.d("debug4",  "RES DATA FOR ADMIN: "+resData);
                        }

                    }
                    orderAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {
            orderRef.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snap: snapshot.getChildren()){

                        OrderListView orderObj= snap.getValue(OrderListView.class);
                        orderListViews.add(orderObj);
                        Log.d("debug4",  "RES DATA FOR CUSTOMER: "+snap.getValue());
                    }
                    orderAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }





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

        i.putExtra("orderID", orderListViews.get(position).getOrderID());
        startActivity(i);
  }


}