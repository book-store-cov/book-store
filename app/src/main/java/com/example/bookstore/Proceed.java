package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.cart.CartData;
import com.example.bookstore.databinding.ActivityPaymentBinding;
import com.example.bookstore.databinding.ActivityProceedBinding;
import com.example.bookstore.orders.OrderBook;
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
import java.util.Random;
import java.util.UUID;

public class Proceed extends AppCompatActivity {
    ActivityProceedBinding binding;
    TextView shippingName, shippingAddress, shippingCity, shippingPostCode;
    TextView cardNumber, cardCVV, cardExpiry, totalAmount;
    int totalAmtIntent = 0;
    DatabaseReference dbRef ;

    FirebaseAuth mAuth;
    String uid;

    HashMap<String, Object> orderObj = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed);
        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        shippingName = findViewById(R.id.name_value);
        shippingAddress = findViewById(R.id.address_value);
        shippingCity = findViewById(R.id.city_value);
        shippingPostCode = findViewById(R.id.postcode_value);
        cardNumber = findViewById(R.id.card_number_value);
        cardCVV = findViewById(R.id.cvv_value);
        cardExpiry = findViewById(R.id.year_value);

        totalAmount = findViewById(R.id.amount_proceed);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            uid = currentUser.getUid();
        }


        if(getIntent().getExtras()!=null) {
            totalAmtIntent = Integer.parseInt(getIntent().getExtras().getString("totalAmount") )  ;
        }
        totalAmount.setText("£"+totalAmtIntent);


        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart");
        cartRef.child(uid).child("shipping").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    HashMap<String, Object> shipping = (HashMap<String, Object>) snapshot.getValue();
                    shippingName.setText(shipping.get("name").toString());
                    shippingAddress.setText(shipping.get("streetAddress").toString());
                    shippingCity.setText(shipping.get("city").toString());
                    shippingPostCode.setText(shipping.get("postCode").toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        cartRef.child(uid).child("payment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    HashMap<String, Object> card = (HashMap<String, Object>) snapshot.getValue();
                    Log.d("debug2", "card value: " + card + " ");
                    cardNumber.setText(card.get("cardNumber").toString());
                    cardCVV.setText(card.get("cvv").toString());
                    cardExpiry.setText(card.get("expiryDate").toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//

        View imageView = findViewById(R.id.editShipment);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                Intent i = new Intent(getApplicationContext(),ShipmentDetails.class);
                i.putExtra("isBackToProceed", true);
                i.putExtra("totalAmount", totalAmtIntent);
                startActivity(i);
            }
        });
        View imageView1 = findViewById(R.id.edit_payment);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                Intent i = new Intent(getApplicationContext(),Payment.class);
                i.putExtra("totalAmount", totalAmtIntent);
                startActivity(i);
            }
        });


        Button btn = (Button)findViewById(R.id.proceedbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Proceed.this);
                builder.setTitle("Alert")
                        .setMessage("Are you sure, you want to continue ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                             addOrder();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getApplicationContext(),Proceed.class);
                                i.putExtra("totalAmount", totalAmtIntent);
                                startActivity(i);
                            }
                        });
                //Creating dialog box
                AlertDialog dialog  = builder.create();
                dialog.show();
            }
        });


    }
    private void addOrder(){

        DatabaseReference cartRef = dbRef.child("cart").child(uid);
        DatabaseReference orderRef = dbRef.child("orders").child(uid);
        String orderId = orderRef.push().getKey();


        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    orderObj = (HashMap<String, Object>) snapshot.getValue();
                    orderObj.put("orderID", orderId);
                    orderObj.put("totalPrice", (int) totalAmtIntent);
                    orderRef.child(orderId).setValue(orderObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            updateBookCount((HashMap<String, Object>) orderObj.get("books"));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Proceed.this, "Failed to place order! Try again.", Toast.LENGTH_SHORT);
                        }
                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Proceed.this, "Failed to place order! Try again.", Toast.LENGTH_SHORT);
            }
        });





    }

    private void updateBookCount(HashMap<String, Object> booksObj) {
        DatabaseReference booksRef = dbRef.child("books");
        final int[] allCount = new int[1];
        for (Map.Entry<String, Object> set : booksObj.entrySet()) {
            OrderBook bookObject = new OrderBook((HashMap<String, Object>) set.getValue());
            String ISBN = bookObject.getISBN();
            int count = bookObject.getCount();

            booksRef.child(ISBN).child("count").addListenerForSingleValueEvent (new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("debug2", "ALL COUNT: "+snapshot.getValue()+ " ORDERCOUNT"+ count);
                    int allCount = ((Long) snapshot.getValue()).intValue();
                    booksRef.child(ISBN).child("count").setValue(allCount-count).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            handleOrderSuccess();
                        }

                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





        }


    }
    private void handleOrderSuccess(){
        DatabaseReference cartRef = dbRef.child("cart");
        cartRef.child(uid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Intent i = new Intent(Proceed.this, SuccessOrder.class);
                startActivity(i);
            }
        });
    }


}