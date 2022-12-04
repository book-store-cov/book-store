package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.DatabaseId;

import java.util.ArrayList;
import java.util.HashMap;

public class BookListMain extends AppCompatActivity {

    DatabaseReference database;
    ArrayList<BookListData> bookListTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance().getReference().child("books");
        bookListTemp = new ArrayList<BookListData>();

        Log.d("debug2", "in book main");

        BookListAdapter bookListAdapter = new BookListAdapter(bookListTemp, BookListMain.this);
        recyclerView.setAdapter(bookListAdapter);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HashMap<String, Object> data = (HashMap<String, Object>) dataSnapshot.getValue();


                    BookListData bookList = new BookListData(data.get("title"), data.get("author"), data.get("price"), data.get("imageURL"));

                    bookListTemp.add(bookList);

                }
                bookListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final Button button = (Button) this.findViewById(R.id.addbook_btn);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        });

    }
}


