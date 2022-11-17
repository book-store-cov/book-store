package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Signin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);

        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    //correct
//                    Toast.makeText(Signin.this,"LOGIN SUCCESSFUL NEXT",Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(Signin.this, Home.class);
                    startActivity(homeIntent);
                    finish();
                }else
                    //incorrect
                    Toast.makeText(Signin.this,"LOGIN FAILED !!!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}