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
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.bookstore.databinding.ActivityCartBinding;

import java.util.ArrayList;



public class Cart extends Fragment implements IClickListener {
    private ArrayList<CartList> cartList;
    IClickListener iClickListener;
    private TextView priceButton;
    private int totalPrice = 185;
    ActivityCartBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());

        final Button button = (Button) binding.proceedToCheckout;
         priceButton = (TextView) binding.subtotal;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Cart.this.requireActivity(),ShipmentDetails.class);
                startActivity(i);

            }
        });
        getDummyData();
        RecyclerView recyclerView = (RecyclerView) binding.recyclerCartItems;

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Cart.this.requireActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CartAdapter cartAdapter = new CartAdapter(Cart.this, cartList);
        recyclerView.setAdapter(cartAdapter); // set the Adapter to RecyclerView

        return inflater.inflate(R.layout.activity_book_list_main, container, false);

    }




    public void getDummyData()
    { cartList = new ArrayList<>();
        CartList cartList1=new CartList("book1","Merly1",25,1,1);
        CartList cartList2=new CartList("book2","Merly2",50,2,1);
        CartList cartList3=new CartList("book3","Merly3",10,3,1);
        CartList cartList4=new CartList("book4","Merly4",100,4,1);

        cartList.add(cartList1);
        cartList.add(cartList2);
        cartList.add(cartList3);
        cartList.add(cartList4);

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