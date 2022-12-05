package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

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

public class OrderHistory extends AppCompatActivity
{

    ArrayList<OrderHistoryList> orderHistoryLists;
    String uid;
    String orderID;
    TextView nameText, streetAddressText, cityText, postCodeText, totalPriceText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
         orderHistoryLists= new ArrayList<OrderHistoryList>();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            uid = currentUser.getUid();
        }
//        Set up intent params
        if(getIntent().getExtras()!=null){
            orderID = getIntent().getExtras().getString("orderID");
        }

        nameText = findViewById(R.id.name_value);
        streetAddressText = findViewById(R.id.address_value);
        cityText= findViewById(R.id.city_value);
        postCodeText = findViewById(R.id.postcode_value);
        totalPriceText = findViewById(R.id.total_price_value);

//        SET UP RECYCLER VIEW
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerCartItemsp);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(OrderHistory.this, orderHistoryLists);
        recyclerView.setAdapter(orderHistoryAdapter); // set the Adapter to RecyclerView


        Log.d("debug4", "order referencing id: "+orderID);
        DatabaseReference orderRef = dbRef.child("orders").child(uid).child(orderID);
        DatabaseReference orderedBooksRef = orderRef.child("books");

//        Fetching single view data from DB
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> orderObj = (HashMap<String, Object>) snapshot.getValue();
                HashMap<String, Object> shippingObj = (HashMap<String, Object>)orderObj.get("shipping");
                nameText.setText(shippingObj.get("name").toString());
                streetAddressText.setText(shippingObj.get("streetAddress").toString());
                cityText.setText(shippingObj.get("city").toString());
                postCodeText.setText(shippingObj.get("postCode").toString());
                totalPriceText.setText("£"+orderObj.get("totalPrice").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//      Fetching recycler data from DB
        orderedBooksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        OrderHistoryList bookList = snap.getValue(OrderHistoryList.class);

                        Log.d("debug4", "order history books "+snap.getValue()+ " bookList.get value: "+ bookList.getTitle());
                        orderHistoryLists.add(bookList);
                    }
                    orderHistoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





//        Bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.navbar_orders);
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.navbar_orders:
                        Intent intent = new Intent(OrderHistory.this, OrderList.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_cart:
                        Intent intent1 = new Intent(OrderHistory.this, Cart.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_logout:
//                        Logout;
                        return true;
                    case R.id.navbar_home:
                        Intent intent2 = new Intent(OrderHistory.this, BookListMain.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });




    }


}