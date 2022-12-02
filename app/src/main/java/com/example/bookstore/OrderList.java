package com.example.bookstore;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookstore.databinding.ActivityOrderListBinding;

import java.util.ArrayList;


public class OrderList extends Fragment implements IClickListener {
    private ArrayList<OrderListView> orderListViews;
    ActivityOrderListBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderListBinding.inflate(getLayoutInflater());



        RecyclerView recyclerView = (RecyclerView) binding.recyclerCartItemso;
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderList.this.requireActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        OrderAdapter orderAdapter = new OrderAdapter(OrderList.this, orderListViews);
        recyclerView.setAdapter(orderAdapter); // set the Adapter to RecyclerView

        binding.shoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderList.this.requireActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        return inflater.inflate(R.layout.activity_book_list_main, container, false);

    }

    @Override
    public void onIncrementClick(int price) {

    }

    @Override
    public void onDecrementClick(int price) {

    }

    @Override
    public void onItemClick() {
        Intent myIntent = new Intent(OrderList.this.requireActivity(), OrderHistory.class);
        this.startActivity(myIntent);
    }
}