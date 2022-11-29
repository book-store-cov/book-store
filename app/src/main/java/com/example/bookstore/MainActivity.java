package com.example.bookstore;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.bookstore.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
    String userId = user.getUid();

    DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference();
//    DatabaseReference adminRef = dbRef.child("admin").child(userId).child("overview");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.mainDrawer.openDrawer(GravityCompat.START);
            }
        });


//        binding.addBookBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AddBook.class);
//                startActivity(intent);
//            }
//        });

        binding.mainNavigation.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.main_content);
        NavigationUI.setupWithNavController(binding.mainNavigation, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                binding.mainTitle.setText(navDestination.getLabel());
            }
        });

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