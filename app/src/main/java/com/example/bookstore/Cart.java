package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bookstore.cart.CartData;
import com.example.bookstore.databinding.ActivityCartBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    ArrayList<CartData> cartList = new ArrayList<CartData>();
    private TextView totalValue, emptyCartText;
    ActivityCartBinding binding;
    String uid;
    DatabaseReference dbRef;
    DatabaseReference cartRef;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            uid = currentUser.getUid();
        }
        dbRef= FirebaseDatabase.getInstance().getReference();
        cartRef =  dbRef.child("cart").child(uid).child("books");

        setContentView(R.layout.activity_cart);

        final Button button = findViewById(R.id.proceedToCheckout);

         totalValue = findViewById(R.id.total_value);
         emptyCartText = findViewById(R.id.empty_cart_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Cart.this,ShipmentDetails.class);
                i.putExtra("totalAmount", ""+getTotalPrice(cartList));
                startActivity(i);

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerCartItems);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        cartAdapter = new CartAdapter( this, cartList);
        recyclerView.setAdapter(cartAdapter);


        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    emptyCartText.setVisibility(View.GONE);
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        CartData cartObj = dataSnapshot.getValue(CartData.class);
                        int existingCartItem = isExistingCartItem(cartObj);
                        if(existingCartItem>-1){
                            cartList.set(existingCartItem,cartObj );

                        }else {
                            cartList.add(cartObj);

                        }
                    }
                    totalValue.setText("£"+getTotalPrice(cartList));

                }
                else {
                    emptyCartText.setVisibility(View.VISIBLE);
                    totalValue.setText("£0.00");

                }
                cartAdapter.notifyDataSetChanged();
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



    public int getTotalPrice(ArrayList<CartData> cartList){
        int totalPrice = 0;
        for(CartData cartItem: cartList){
            totalPrice += (cartItem.getPrice()*cartItem.getCount());

        }
        return totalPrice;
    }


    public int isExistingCartItem(CartData cartItem){
     int index = -1;

     for(int i=0; i<cartList.size();i++){
         if(cartList.get(i).getISBN()==cartItem.getISBN()){
             return i;
         }
     }

        return index;
    }



    @Override
    public void onIncrementClick(CartData cartObj) {


        int count = cartObj.getCount();
        count++;
        cartRef.child(cartObj.getISBN()).child("count").setValue(count).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Cart.this, "Item not added! Try again.", Toast.LENGTH_SHORT);
            }
        });


    }

    @Override
    public void onDecrementClick(CartData cartObj) {
        int count = cartObj.getCount();
        count--;
        if(count<1){
            cartRef.child(cartObj.getISBN()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    cartList.remove(isExistingCartItem(cartObj));
                    cartAdapter.notifyDataSetChanged();
                    totalValue.setText("£"+getTotalPrice(cartList));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Cart.this, "Failed to remove book! Try again.", Toast.LENGTH_SHORT );
                }
            });
        }
        else{

            cartRef.child(cartObj.getISBN()).child("count").setValue(count).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Cart.this, "Item not added! Try again.", Toast.LENGTH_SHORT);
                }
            });
        }
    }

    @Override
    public void onItemClick() {

    }

}