package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;


import com.example.bookstore.R;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

public class Signin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);

        MaterialButton login = (MaterialButton) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    //correct
                    Toast.makeText(Signin.this,"LOGIN SUCCESSFUL",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signin.this, Home.class);
                    startActivity(intent);
                    finish();
                }else
                    //incorrect
                    Toast.makeText(Signin.this,"LOGIN FAILED !!!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}

//package com.example.bookstoreapp;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//
//import com.example.bookstoreapp.databinding.SigninMainBinding;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.material.button.MaterialButton;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.view.View;
//
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class SigninActivity extends AppCompatActivity {
//
//SigninMainBinding binding;
//    FirebaseAuth firebaseAuth;
//    ProgressDialog progressDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding=SigninMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        firebaseAuth=FirebaseAuth.getInstance();
//        progressDialog=new ProgressDialog(this);
//
//
//
//        binding.login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email=binding.username.getText().toString().trim();
//                String password=binding.password.getText().toString();
//                progressDialog.show();
//
//                firebaseAuth.signInWithEmailAndPassword(email,password)
//                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                            @Override
//                            public void onSuccess(AuthResult authResult) {
//                                progressDialog.cancel();
//                                Toast.makeText(SigninActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                progressDialog.cancel();
//                                Toast.makeText(SigninActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//            }
//        });
//
//        binding.forgot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email=binding.username.getText().toString();
//                progressDialog.setTitle("Sending Mail");
//                progressDialog.show();
//
//                firebaseAuth.sendPasswordResetEmail(email)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                progressDialog.cancel();
//                                Toast.makeText(SigninActivity.this,"Email sent", Toast.LENGTH_SHORT).show();
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                progressDialog.cancel();
//                                Toast.makeText(SigninActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//            }
//        });
//
//
//
//
//    }
//}