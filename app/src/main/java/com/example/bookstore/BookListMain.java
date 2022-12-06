package com.example.bookstore;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookstore.databinding.ActivityBookListMainBinding;
import com.example.bookstore.orders.OrderBook;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.bookstore.bookList.OnClickListener;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookListMain extends AppCompatActivity implements OnClickListener {

    ActivityBookListMainBinding binding;
    DatabaseReference dbRef;
    ArrayList<BookListData> bookListTemp;
    Button addBookButton;
    int availableBooks = 0, soldBooks=0;
    LinearLayoutCompat tableLayout;
    String uid = "";
    boolean isAdmin;
    TextView soldBookText, availableBookText;

    BottomNavigationView bottomNav;





    @Override
        protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookListMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_book_list_main);

        soldBookText= findViewById(R.id.sold_books_value);
        availableBookText= findViewById(R.id.available_books_value);
        addBookButton = findViewById(R.id.addbook_btn);
        tableLayout = findViewById(R.id.lineartable);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            uid = currentUser.getUid();
        }


        dbRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference bookRef = dbRef.child("books");

        bookListTemp = new ArrayList<BookListData>();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        BookListAdapter bookListAdapter = new BookListAdapter(bookListTemp, this);

        recyclerView.setAdapter(bookListAdapter);

        bookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HashMap<String, Object> data = (HashMap<String, Object>) dataSnapshot.getValue();
                    availableBooks += ((Long)data.get("count")).intValue();
                    BookListData bookList = new BookListData(data.get("title"), data.get("author"), data.get("price"), data.get("imageURL"), data.get("ISBN"));
                    bookListTemp.add(bookList);
                }
                availableBookText.setText(String.valueOf(availableBooks));
                bookListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference orderRef = dbRef.child("orders");
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot userSnap: snapshot.getChildren()){
                    for(DataSnapshot orderSnap: userSnap.getChildren()){
                        HashMap<String, Object> orderData = (HashMap<String ,Object>) orderSnap.getValue();
                        HashMap<String, Object> orderedBooks = (HashMap<String, Object>) orderData.get("books");
                        for(Map.Entry<String, Object> set : orderedBooks.entrySet()){
                            OrderBook bookObject = new OrderBook((HashMap<String, Object>) set.getValue());
                            soldBooks += bookObject.getCount();

                        }

                    }
                }
                soldBookText.setText(String.valueOf(soldBooks));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        addBookButton.setOnClickListener(new View.OnClickListener() {
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
                    mAuth.signOut();
                    startActivity(new Intent(BookListMain.this, Signin.class));
                    break;
                case R.id.navbar_home:
                    Intent intent2 = new Intent(BookListMain.this, BookListMain.class);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
                    break;

            }
            return true;
        });


        //    setup admin view
        DatabaseReference isAdminRef = dbRef.child("users").child(uid).child("isAdmin");
        isAdminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isAdmin = (boolean) snapshot.getValue();

                if(!isAdmin) {
                    tableLayout.setVisibility(View.GONE);
                }else {
                    bottomNav.getMenu().removeItem(R.id.navbar_cart);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
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


