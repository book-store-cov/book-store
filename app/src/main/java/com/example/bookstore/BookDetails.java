package com.example.bookstore;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.databinding.ActivityBookDetailsBinding;
import com.example.bookstore.databinding.ActivityCartBinding;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class BookDetails extends AppCompatActivity {

    ActivityBookDetailsBinding binding;

    ImageView bookImage;
    TextView titleText, ISBNText, priceText, authorText, publicationDateText, descriptionText;
    Button button;
    String title, imgURL;
    Long price;

    FirebaseAuth mAuth;
    String uid;


    DatabaseReference fDatabse;
    DatabaseReference dRef;

    boolean isAdmin= false;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = ActivityBookDetailsBinding.inflate(getLayoutInflater());

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            uid = currentUser.getUid();


        }

        setContentView(R.layout.activity_book_details);

        bookImage=findViewById(R.id.bookImage);
        button=findViewById(R.id.add_to_cart);
        titleText = findViewById(R.id.book_title);
        priceText = findViewById(R.id.bookPrice);
        ISBNText = findViewById(R.id.ISBN_value);
        authorText = findViewById(R.id.author_name);
        publicationDateText = findViewById(R.id.publication_date);
        descriptionText = findViewById(R.id.book_description);

        String ISBN = getIntent().getExtras().getString("ISBN");


        fDatabse = FirebaseDatabase.getInstance().getReference();
        dRef=fDatabse.child("books").child(ISBN);
        DatabaseReference cartDBRef = fDatabse.child("cart");

//        add to cart functionality
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> book = new HashMap<>();
                book.put("title", title);
                book.put("ISBN", ISBN);
                book.put("imageURL", imgURL);
                book.put("price", price);
                book.put("count", 1);

                cartDBRef.child(uid).child("books").child(ISBN).setValue(book).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Intent intent = new Intent(BookDetails.this, BookListMain.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Toast.makeText(BookDetails.this, "Failed to add book!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        //Setting up admin view
        DatabaseReference userRef = fDatabse.child("users");
        userRef.child(uid).child("isAdmin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getValue(boolean.class)){
//                    button.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        dRef.child("ISBN").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ISBNText.setText(ISBN);
                }else{
                    ISBNText.setText(ISBN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dRef.child("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    title=dataSnapshot.getValue(String.class);
                    titleText.setText(title);
                }else{
                    titleText.setText("Not Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dRef.child("author").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    authorText.setText(dataSnapshot.getValue(String.class));
                }else{
                    authorText.setText("Not Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dRef.child("publicationDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    publicationDateText.setText(dataSnapshot.getValue(String.class));
                }else{
                    publicationDateText.setText("Not Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        dRef.child("description").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    descriptionText.setText(dataSnapshot.getValue(String.class));
                }else{
                    descriptionText.setText("Not Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dRef.child("price").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                price = (Long) dataSnapshot.getValue();
                priceText.setText("£"+String.valueOf(price));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dRef.child("imageURL").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               imgURL=dataSnapshot.getValue(String.class);
                Picasso.get().load(imgURL).into(bookImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        Bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.navbar_home);
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.navbar_orders:
                        Intent intent = new Intent(BookDetails.this, OrderList.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_cart:
                        Intent intent1 = new Intent(BookDetails.this, Cart.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbar_logout:
//                        Logout;
                        return true;
                    case R.id.navbar_home:
                        Intent intent2 = new Intent(BookDetails.this, BookListMain.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });




    }

}


