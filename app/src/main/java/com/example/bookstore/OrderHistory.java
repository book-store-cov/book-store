package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity {

    private ArrayList<OrderHistoryList> orderHistoryLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        final Button button = (Button) this.findViewById(R.id.orderHome_btn);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),BookDetails.class);
                startActivity(i);

            }
        });
        final Button buttonl = (Button) this.findViewById(R.id.order_btn);

        buttonl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),OrderList.class);
                startActivity(i);

            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerCartItemsp);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(OrderHistory.this, orderHistoryLists);
        recyclerView.setAdapter(orderHistoryAdapter); // set the Adapter to RecyclerView



    }
}