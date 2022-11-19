package com.example.bookstore;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Reset extends AppCompatActivity {

    EditText inputEmail;
    Button btnReset;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        inputEmail = findViewById(R.id.inputEmail);
        btnReset = findViewById(R.id.btnReset);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforReset();
            }
        });
    }

    private void PerforReset() {
        String userEmail = inputEmail.getText().toString();
        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(Reset.this, "Please enter the email", Toast.LENGTH_SHORT).show();
            
        }else {
            mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getApplicationContext(), Signin.class));
                        Toast.makeText(Reset.this, "Reset in  Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Reset.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }



    }

}

