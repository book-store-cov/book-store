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

import com.example.bookstore.databinding.ActivityPaymentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Payment extends AppCompatActivity{

    ActivityPaymentBinding binding;
    TextView errMessage;
    String totalAmount;

    FirebaseAuth mAuth;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mAuth = FirebaseAuth.getInstance();
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());

        errMessage = findViewById(R.id.addCardErrMsg);

        final Button button = (Button) this.findViewById(R.id.cardsubmit);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            uid = currentUser.getUid();
        }

        if(getIntent().getExtras()!=null){
            totalAmount = getIntent().getExtras().getString("totalAmount");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadCardInfo();

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
                        Intent intent = new Intent(Payment.this, OrderList.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_cart:
                        Intent intent1 = new Intent(Payment.this, Cart.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_logout:
//                        Logout;
                        return true;
                    case R.id.navbar_home:
                        Intent intent2 = new Intent(Payment.this, BookListMain.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });



    }

    private void uploadCardInfo()  {
        DatabaseReference realtimeDB = FirebaseDatabase.getInstance().getReference("cart");
        String cardNumber, cvv, expiryDate;


        cardNumber = ((EditText) findViewById(R.id.addcard)).getText().toString();
        cvv = ((EditText) findViewById(R.id.addcvv)).getText().toString();
        expiryDate = ((EditText) findViewById(R.id.addmy)).getText().toString();

        if(TextUtils.isEmpty(cardNumber)|| TextUtils.isEmpty(cvv)|| TextUtils.isEmpty(expiryDate)){
            binding.cardsubmit.setError("true");
            errMessage.setText("Please complete the form!");
        }else if (!validateExpiryDate(expiryDate)){
            binding.cardsubmit.setError("true");
            errMessage.setText("Please enter valid expiry date!");
        }else {
            Map<String,Object > card = new HashMap<>();
            card.put("cardNumber", cardNumber);
            card.put("cvv", cvv);
            card.put("expiryDate", expiryDate);

            realtimeDB.child(uid).child("payment").setValue(card).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Intent i = new Intent(getApplicationContext(),Proceed.class);
                    i.putExtra("totalAmount",totalAmount);
                    startActivity(i);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Payment.this, "Failed to add card. Try again", Toast.LENGTH_SHORT);
                }
            });


        }





    }

    private boolean validateExpiryDate(String expiryDate)  {
        boolean isValid =  expiryDate.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
        simpleDateFormat.setLenient(false);
        Date expiry = null;
        try {
            expiry = simpleDateFormat.parse(expiryDate);
            isValid = !expiry.before(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
            isValid=false;
        }
        return isValid;
    }


}