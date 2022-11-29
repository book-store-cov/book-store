package com.example.bookstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Register extends AppCompatActivity {
    TextView alreadyHaveaaccount;
    EditText inputEmail,inputPassword,inputConfirmPassword;
    Button btnSignup;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    //FirebaseDatabase db = FirebaseDatabase.getInstance();
    //DatabaseReference root = db.getReference("users");
    FirebaseAuth mAuth;
    FirebaseUser mUser;
   @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_register);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

       alreadyHaveaaccount=findViewById(R.id.alreadyHaveaaccount);

       inputEmail=findViewById(R.id.inputEmail);
       inputPassword=findViewById(R.id.inputPassword);
       inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
       btnSignup=findViewById(R.id.btnSignup);
       progressDialog= new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

       alreadyHaveaaccount.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(Register.this,Signin.class));
           }
       });

       btnSignup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               PerforAuth();
           }
       });
   }

    /*void addUser(String email, String password) {
        HashMap<String, Object> user = new HashMap<>();

        user.put("Email", email);
        user.put("Password", password);

        root.setValue(user);
    }*/

   private void PerforAuth(){
       String email=inputEmail.getText().toString();
       String password=inputPassword.getText().toString();
       String confirmPassword=inputConfirmPassword.getText().toString();

       if (!email.matches(emailPattern))
       {
           inputEmail.setError("Enter valid Email");
       }else if (password.isEmpty() || password.length()<6)
       {
           inputPassword.setError("Enter valid Password");
       }else if (!password.equals(confirmPassword))
       {
           inputConfirmPassword.setError("Password Not Match");
       }else
       {
         progressDialog.setMessage("Please wait While Registration");
         progressDialog.setTitle("Registration");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful())
               {
                   progressDialog.dismiss();
                   sendUserToNextActivity();
                   Toast.makeText(Register.this, "Registration Successful",Toast.LENGTH_SHORT ).show();
               }else
               {
                   progressDialog.dismiss();
                   Toast.makeText(Register.this, ""+task.getException(),Toast.LENGTH_SHORT).show();
               }
            }
        });

       }

   }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(Register.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
