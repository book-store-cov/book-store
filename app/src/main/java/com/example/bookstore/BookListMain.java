package com.example.bookstore;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bookstore.databinding.ActivityBookListMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.bookstore.bookList.OnClickListener;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class BookListMain extends AppCompatActivity implements OnClickListener {

    ActivityBookListMainBinding binding;
    DatabaseReference database;
    ArrayList<BookListData> bookListTemp;
    Button buttonTraveller;


    @Override
        protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookListMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_book_list_main);


        database = FirebaseDatabase.getInstance().getReference().child("books");
        bookListTemp = new ArrayList<BookListData>();
        buttonTraveller = findViewById(R.id.buttonTraveller);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        BookListAdapter bookListAdapter = new BookListAdapter(bookListTemp, this);

        recyclerView.setAdapter(bookListAdapter);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HashMap<String, Object> data = (HashMap<String, Object>) dataSnapshot.getValue();
                    BookListData bookList = new BookListData(data.get("title"), data.get("author"), data.get("price"), data.get("imageURL"), data.get("ISBN"));
                    bookListTemp.add(bookList);
                }



                bookListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonTraveller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookListMain.this, AddBook.class));
            }
        });





//        Bottom navigation

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.navbar_home);

        bottomNav.setOnItemSelectedListener((NavigationBarView.OnItemSelectedListener) item -> {

            switch(item.getItemId()){
                case R.id.navbar_orders:
                    Intent intent = new Intent(BookListMain.this, OrderList.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
                case R.id.navbar_cart:
                    Intent intent1 = new Intent(BookListMain.this, Cart.class);
                    startActivity(intent1);
                    overridePendingTransition(0,0);
                    break;
                case R.id.navbar_logout:
//                        Logout;
                    break;
                case R.id.navbar_home:
                    Intent intent2 = new Intent(BookListMain.this, BookListMain.class);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
                    break;

            }
            return true;
        });



    }

    private String getBookId(int position){
        BookListData data = bookListTemp.get(position);
        return data.getISBN();
    }



    @Override
    public void onBookClick(View view, int position){

        Intent intent = new Intent(this, BookDetails.class);

        intent.putExtra("ISBN", getBookId(position));
        startActivity(intent);

    }



}


