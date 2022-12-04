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
import android.widget.Button;
import android.widget.TextView;


import com.example.bookstore.cart.CartData;
import com.example.bookstore.databinding.ActivityCartBinding;
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


public class Cart extends AppCompatActivity implements IClickListener  {
    private ArrayList<CartData> cartList;
    IClickListener iClickListener;
    private TextView priceButton;
    private int totalPrice = 0;
    ActivityCartBinding binding;
    String uid;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            uid = currentUser.getUid();
        }

        setContentView(R.layout.activity_cart);

        final Button button = (Button) binding.proceedToCheckout;
         priceButton = (TextView) binding.subtotal;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Cart.this,ShipmentDetails.class);
                startActivity(i);

            }
        });


        RecyclerView recyclerView = (RecyclerView) binding.recyclerCartItems;
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Cart.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CartAdapter cartAdapter = new CartAdapter(Cart.this, cartList);
        recyclerView.setAdapter(cartAdapter); // set the Adapter to RecyclerView

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();


        DatabaseReference cartRef = dbRef.child("cart").child(uid);


        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CartData cartObj = dataSnapshot.getValue(CartData.class);
                    cartList.add(cartObj);
                    }
                    cartAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        //BOTTOM NAVIGATION SETUP
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.navbar_cart);
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.navbar_orders:
                        Intent intent = new Intent(Cart.this, OrderList.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_cart:
                        Intent intent1 = new Intent(Cart.this, Cart.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_logout:
//                        Logout;
                        return true;
                    case R.id.navbar_home:
                        Intent intent2 = new Intent(Cart.this, BookListMain.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });




    }





    public ArrayList getDummyData()
    { ArrayList cartItems = new ArrayList<>();
        CartList cartList1=new CartList("book1","Merly1",25,1,1);
        CartList cartList2=new CartList("book2","Merly2",50,2,1);
        CartList cartList3=new CartList("book3","Merly3",10,3,1);
        CartList cartList4=new CartList("book4","Merly4",100,4,1);

        cartItems.add(cartList1);
        cartItems.add(cartList2);
        cartItems.add(cartList3);
        cartItems.add(cartList4);

        return cartItems;

    }

    @Override
    public void onIncrementClick(int price) {
        totalPrice = totalPrice+price;
priceButton.setText(""+totalPrice);
    }

    @Override
    public void onDecrementClick(int price) {
        totalPrice = totalPrice-price;
        priceButton.setText(""+totalPrice);
    }

    @Override
    public void onItemClick() {

    }

}