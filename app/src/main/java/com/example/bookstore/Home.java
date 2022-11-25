package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bookstore.databinding.ActivityHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;


public class Home extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private int tAllBooks, tSoldBooks, tAvailableBooks;
    List bookList;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
    String userId = user.getUid();

    CollectionReference booksRef = FirebaseFirestore.getInstance().collection("books");
    DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference();
    DatabaseReference adminRef = dbRef.child("admin").child(userId).child("overview");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getOverviewDetails();


        binding.addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, AddBook.class);
                startActivity(intent);
            }
        });
    }

    private void getOverviewDetails() {
        adminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Overview overview = dataSnapshot.getValue(Overview.class);
                tAllBooks = overview.getAllBooks();
                tSoldBooks = overview.getSoldBooks();
                tAvailableBooks = overview.getAvailableBooks();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home.this, "Failed to collect data!", Toast.LENGTH_SHORT);
            }
        });
    }


    private void getBooks() {
        booksRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot docs = task.getResult();
                    if (docs.isEmpty()) {

                    }else {
                        bookList = docs.getDocuments();
                    }
                }
            }
        });
    }


}