package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.model.DatabaseId;

public class BookListMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BookListData[] bookListData = new BookListData[]{
                new BookListData("Harry Potter and the Sorcerer's Stone","J.K. Rowling","£ 8.00",R.drawable.harry_potter_1),
                new BookListData("Harry Potter and Chamber of Secrets","J.K. Rowling","£ 8.00",R.drawable.hp2),
                new BookListData("Harry Potter and Prisoner of Azkaban","J.K. Rowling","£ 8.00",R.drawable.hp3),
                new BookListData("Harry Potter and Goblet of Fire","J.K. Rowling","£ 8.00",R.drawable.hp4),
                new BookListData("Harry Potter and Order of the Phoenix","J.K. Rowling","£ 8.00",R.drawable.hp5),
                new BookListData("Harry Potter and Half-Blood Prince","J.K. Rowling","£ 8.00",R.drawable.hp6),
                new BookListData("Harry Potter and Deathly Hallow","J.K. Rowling","£ 8.00",R.drawable.hp7),
        };

        Log.d("debug2", "in book main");

        BookListAdapter bookListAdapter = new BookListAdapter(bookListData, BookListMain.this);
        recyclerView.setAdapter(bookListAdapter);

    }
}


