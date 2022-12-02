package com.example.bookstore;


import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.bookstore.databinding.ActivityCartBinding;

import java.util.ArrayList;


public class Cart extends Fragment {
    private ArrayList<CartList> cartList;
    ActivityCartBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());


        final Button button = (Button) binding.proceedToCheckout;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Cart.this.requireActivity(),ShipmentDetails.class);
                startActivity(i);

            }
        });

        RecyclerView recyclerView = (RecyclerView) binding.recyclerCartItems;
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Cart.this.requireActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CartAdapter cartAdapter = new CartAdapter(Cart.this, cartList);
        recyclerView.setAdapter(cartAdapter); // set the Adapter to RecyclerView

        return inflater.inflate(R.layout.activity_book_list_main, container, false);
    }

}