package com.example.bookstore;

import static com.example.bookstore.R.id.increment_btn;
import static com.example.bookstore.R.id.itemName;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Cart extends AppCompatActivity {
    private ArrayList<CartList> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final Button button = (Button) this.findViewById(R.id.proceedToCheckout);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),ShipmentDetails.class);
                startActivity(i);

            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerCartItems);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CartAdapter cartAdapter = new CartAdapter(Cart.this, cartList);
        recyclerView.setAdapter(cartAdapter); // set the Adapter to RecyclerView
    }

}