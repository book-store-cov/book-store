package com.example.bookstore;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.example.bookstore.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
    String userId = user.getUid();

    DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNav.setOnItemSelectedListener(this);
        loadFragment(new BookListMain());
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch(item.getItemId()){
            case R.id.navbar_orders:
                fragment = new OrderList();
                break;
            case R.id.navbar_cart:
                fragment = new Cart();
                break;
            case R.id.navbar_logout:
//                        Logout;
                break;
            case R.id.navbar_home:
                fragment = new BookListMain();
                break;

        }
        if(fragment!=null){
            loadFragment(fragment);
        }
        return true;
    }
    void loadFragment(Fragment fragment) {
        //to attach fragment
        Log.d("debug2", "fragment load:"+fragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, fragment).commit();
    }




}


//    private void getOverviewDetails() {
//        adminRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Overview overview = dataSnapshot.getValue(Overview.class);
//                tAllBooks = overview.getAllBooks();
//                tSoldBooks = overview.getSoldBooks();
//                tAvailableBooks = overview.getAvailableBooks();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(MainActivity.this, "Failed to collect data!", Toast.LENGTH_SHORT);
//            }
//        });
//    }
//
//
//    private void getBooks() {
//        booksRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    QuerySnapshot docs = task.getResult();
//                    if (docs.isEmpty()) {
//
//                    }else {
//                        bookList = docs.getDocuments();
//                    }
//                }
//            }
//        });
//    }