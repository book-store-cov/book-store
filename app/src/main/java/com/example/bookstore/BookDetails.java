package com.example.bookstore;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.databinding.ActivityAddBookBinding;
import com.example.bookstore.databinding.ActivityBookDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.cert.PolicyNode;

public class BookDetails extends AppCompatActivity {
    TextView productDescription,authorDetailsDisplay,productDetails, productDetail,bookName,rate,authorDetails,authorName,authorNameDisplay,isbnNum,isbnNumDisplay,publicationNameDisplay,language,languageName,publicationName;
    ImageView bookImage,backIcon,favIcon;
    Button buyButton;
    private String isbn;


    FirebaseDatabase fDatabase;
    CollectionReference booksRef = FirebaseFirestore.getInstance().collection("books");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



//        productDescription=findViewById(R.id.productDescription);
        bookImage=findViewById(R.id.bookImage);
        authorDetailsDisplay=findViewById(R.id.authorDetailsDisplay);
        productDetail=findViewById(R.id.productDetail);
        productDetails=findViewById(R.id.productDetails);
          bookName=findViewById(R.id.bookName);
//        backIcon=findViewById(R.id.backIcon);
//        favIcon = findViewById(R.id.favIcon);
//        rate=findViewById(R.id.rate);
//        buyButton=findViewById(R.id.buyButton);
        authorDetails=findViewById(R.id.authorDetails);
//        authorName=findViewById(R.id.authorName);
//        authorNameDisplay=findViewById(R.id.authorNameDisplay);
        isbnNum=findViewById(R.id.isbnNum);
        isbnNumDisplay=findViewById(R.id.isbnNumDisplay);
//        publicationNameDisplay=findViewById(R.id.publicationNameDisplay);
//        language=findViewById(R.id.language);
//        languageName=findViewById(R.id.languageName);
//        publicationName=findViewById(R.id.publicationName);

        fDatabase=FirebaseDatabase.getInstance();
        dref= fDatabase.getReference().child("BOOKS");



}
