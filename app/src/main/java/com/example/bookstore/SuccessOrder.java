package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.bookstore.databinding.ActivitySuccessOrderBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SuccessOrder extends AppCompatActivity{

    ActivitySuccessOrderBinding binding;
    String uid;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_order);
        final Button button = (Button) this.findViewById(R.id.orderhistory_btn);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            uid = currentUser.getUid();
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),OrderList.class);
                startActivity(i);

            }
        });

//        Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        DatabaseReference isAdminRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("isAdmin");
        isAdminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if((boolean)snapshot.getValue()){
                    bottomNav.getMenu().removeItem(R.id.navbar_cart);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bottomNav.setSelectedItemId(R.id.navbar_cart);
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.navbar_orders:
                        Intent intent = new Intent(SuccessOrder.this, OrderList.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_cart:
                        Intent intent1 = new Intent(SuccessOrder.this, Cart.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_logout:
                        mAuth.signOut();
                        startActivity(new Intent(SuccessOrder.this, Signin.class));
                        return true;
                    case R.id.navbar_home:
                        Intent intent2 = new Intent(SuccessOrder.this, BookListMain.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });



    }



}