package com.example.bookstore;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookstore.databinding.ActivityBookListMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.HashMap;

public class BookListMain extends AppCompatActivity {

    ActivityBookListMainBinding binding;
    DatabaseReference database;
    ArrayList<BookListData> bookListTemp;

//    private FirebaseRecyclerAdapter<BookListData,  BookListAdapter.ViewHolder> firebaseRecyclerAdapter;


    @Override
        protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookListMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_book_list_main);


        database = FirebaseDatabase.getInstance().getReference().child("books");
        bookListTemp = new ArrayList<BookListData>();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

//        FirebaseRecyclerOptions<BookListData> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<BookListData>()
//                .setQuery(database, BookListData.class)
//                .build();
//
//        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BookListData, BookListAdapter.ViewHolder>(firebaseRecyclerOptions);
//        recyclerView.setAdapter(firebaseRecyclerAdapter);
//



//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    HashMap<String, Object> data = (HashMap<String, Object>) dataSnapshot.getValue();
//                    BookListData bookList = new BookListData(data.get("title"), data.get("author"), data.get("price"), data.get("imageURL"));
//                    bookListTemp.add(bookList);
//                }
//
//                bookListAdapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//




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


}


