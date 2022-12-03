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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.databinding.ActivityBookDetailsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BookDetails extends AppCompatActivity {

    ActivityBookDetailsBinding binding;
    TextView isbnNumDisplay,bookName,authorNameDisplay,publicationNameDisplay,bookDescriptionDisplay,rate;
    ImageView bookImage;
    Button button;


    FirebaseDatabase fDatabse;
    DatabaseReference dRef;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_details);



        isbnNumDisplay=findViewById(R.id.isbnNumDisplay);
        bookName=findViewById(R.id.bookName);
        authorNameDisplay=findViewById(R.id.authorNameDisplay);
        publicationNameDisplay=findViewById(R.id.publicationNameDisplay);
        bookDescriptionDisplay=findViewById(R.id.bookDescriptionDisplay);
        rate=findViewById(R.id.rate);
        button=findViewById(R.id.button);
        bookImage=findViewById(R.id.bookImage);


        fDatabse = FirebaseDatabase.getInstance();
        dRef=fDatabse.getReference().child("books").child("1234567890123");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookDetails.this, AddBook.class));
            }
        });



        dRef.child("ISBN").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    isbnNumDisplay.setText(dataSnapshot.getValue(String.class));
                }else{
                    isbnNumDisplay.setText("Not Found");
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
                    bookName.setText(dataSnapshot.getValue(String.class));
                }else{
                    bookName.setText("Not Found");
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
                    authorNameDisplay.setText(dataSnapshot.getValue(String.class));
                }else{
                    authorNameDisplay.setText("Not Found");
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
                    publicationNameDisplay.setText(dataSnapshot.getValue(String.class));
                }else{
                    publicationNameDisplay.setText("Not Found");
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
                    bookDescriptionDisplay.setText(dataSnapshot.getValue(String.class));
                }else{
                    bookDescriptionDisplay.setText("Not Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dRef.child("price").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rate.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dRef.child("imageURL").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String link=dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(bookImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String ISBN = getIntent().getExtras().getString("ISBN");
        Log.d("debug2", "ISBN VALUE: "+ISBN);

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


