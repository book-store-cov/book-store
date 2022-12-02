package com.example.bookstore;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookstore.databinding.ActivityBookListMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.HashMap;

public class BookListMain extends Fragment {

    ActivityBookListMainBinding binding;
    DatabaseReference database;
    ArrayList<BookListData> bookListTemp;



    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_book_list_main, container, false);


        binding = ActivityBookListMainBinding.inflate(getLayoutInflater());
        database = FirebaseDatabase.getInstance().getReference().child("books");
        bookListTemp = new ArrayList<BookListData>();

        RecyclerView recyclerView = binding.recyclerView;
        LinearLayoutManager llm = new LinearLayoutManager(this.requireActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);




//        FirebaseRecyclerOptions<BookListData> options
//                = new FirebaseRecyclerOptions.Builder<BookListData>()
//                .setQuery(database, BookListData.class)
//                .build();

        BookListAdapter bookListAdapter = new BookListAdapter(bookListTemp, this.requireActivity());



        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HashMap<String, Object> data = (HashMap<String, Object>) dataSnapshot.getValue();
                    BookListData bookList = new BookListData(data.get("title"), data.get("author"), data.get("price"), data.get("imageURL"));
                    bookListTemp.add(bookList);
                }
                Log.d("debug2", "here in data base call");
                bookListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;


    }
}


