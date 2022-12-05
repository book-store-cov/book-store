package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.bookstore.databinding.ActivityShipmentDetailsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShipmentDetails extends AppCompatActivity{

    ActivityShipmentDetailsBinding binding;
    boolean isProceedCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_details);
        final Button button = (Button) this.findViewById(R.id.adddetails);
        binding = ActivityShipmentDetailsBinding.inflate(getLayoutInflater());


        if(getIntent().getExtras()!=null){
            isProceedCheck = getIntent().getExtras().getBoolean("isBackToProceed");
            button.setText("Back to checkout");
        }

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i;
                if(isProceedCheck){
                    i= new Intent(getApplicationContext(), Proceed.class);
                }
                else {
                    i = new Intent(getApplicationContext(), Payment.class);
                }
                startActivity(i);

            }
        });

//        BottomNavigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.navbar_cart);
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.navbar_orders:
                        Intent intent = new Intent(ShipmentDetails.this, OrderList.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_cart:
                        Intent intent1 = new Intent(ShipmentDetails.this, Cart.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_logout:
//                        Logout;
                        return true;
                    case R.id.navbar_home:
                        Intent intent2 = new Intent(ShipmentDetails.this, BookListMain.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });




    }



}