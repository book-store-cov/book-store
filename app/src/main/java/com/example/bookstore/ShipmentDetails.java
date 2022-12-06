package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.databinding.ActivityShipmentDetailsBinding;
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShipmentDetails extends AppCompatActivity{

    ActivityShipmentDetailsBinding binding;
    boolean isProceedCheck = false;
    TextView errMessage ;
    String totalAmtIntent;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_details);
        final Button button = (Button) this.findViewById(R.id.adddetails);

        binding = ActivityShipmentDetailsBinding.inflate(getLayoutInflater());
        errMessage= findViewById(R.id.addShippingError);
        binding.adddetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadShipping();
            }
        });


        FirebaseUser currentUser =mAuth.getCurrentUser();
        if(currentUser!=null) {
            uid = currentUser.getUid();
        }

        if(getIntent().getExtras()!=null){
            isProceedCheck = getIntent().getExtras().getBoolean("isBackToProceed");
            button.setText("Back to checkout");
            totalAmtIntent = getIntent().getExtras().getString("totalAmount");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadShipping();
            }
        });

//        BottomNavigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        DatabaseReference isAdminRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("isAdminRef");
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
                        mAuth.signOut();
                        startActivity(new Intent(ShipmentDetails.this, Signin.class));
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

    private void uploadShipping(){
        DatabaseReference realtimeDB = FirebaseDatabase.getInstance().getReference("cart");
        String name, streetAddress, city, postCode;

        name = ((EditText) findViewById(R.id.addname)).getText().toString();
        streetAddress = ((EditText) findViewById(R.id.addhousenumber)).getText().toString();
        city = ((EditText) findViewById(R.id.addSCity)).getText().toString();
        postCode = ((EditText) findViewById(R.id.addpostcode)).getText().toString();

        if(TextUtils.isEmpty(name)|| TextUtils.isEmpty(streetAddress)|| TextUtils.isEmpty(city)|| TextUtils.isEmpty(postCode)){
            binding.adddetails.setError("true");
            errMessage.setText("Please complete the form!");
        }else if (!validateUKPostCode(postCode)){
            binding.adddetails.setError("true");
            errMessage.setText("Please enter valid Post code!");
        } else {
            Map<String, Object> shippingInfo = new HashMap<>();
            shippingInfo.put("name", name);
            shippingInfo.put("streetAddress", streetAddress);
            shippingInfo.put("city", city);
            shippingInfo.put("postCode",postCode);
            realtimeDB.child(uid).child("shipping").setValue(shippingInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Intent i;
                    if(isProceedCheck){
                        i= new Intent(getApplicationContext(), Proceed.class);

                    }
                    else {
                        i = new Intent(getApplicationContext(), Payment.class);
                    }
                    i.putExtra("totalAmount", totalAmtIntent);

                    startActivity(i);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ShipmentDetails.this, "Failed to add shipping details. Try again.", Toast.LENGTH_SHORT);
                }
            });


        }

    }


    private boolean validateUKPostCode(String postCode){
        String regex = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(postCode);

        return matcher.matches();
    }


}