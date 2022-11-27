package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookstore.databinding.ActivityAddBookBinding;
import com.example.bookstore.databinding.ActivityOnboardingBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Onboarding extends AppCompatActivity {
    //ActivityOnboardingBinding binding;
    EditText streetName;
    EditText city;
    EditText county;
    EditText postCode;
    Button continueButton;

    //FirebaseDatabase db = FirebaseDatabase.getInstance();
    //DatabaseReference root = db.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        //binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        streetName = findViewById(R.id.streetName);
        city = findViewById(R.id.city);
        county = findViewById(R.id.county);
        postCode = findViewById(R.id.postCode);
        continueButton = findViewById(R.id.continueButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetailsProvided();
            }
        });
    }

    boolean isValidPostCode(EditText t) {
        String postcode = t.getText().toString();
        String regex = "^[a-zA-Z]{1,2}[0-9Rr][0-9a-zA-Z]? ?[0-9][abd-hjlnp-uw-zABD-HJLNP-UW-Z]{2}$";
        Pattern pc = Pattern.compile(regex);
        Matcher matcher = pc.matcher(postcode);
        boolean result = matcher.find();
        return result;
    }

    void checkDetailsProvided() {
        if(TextUtils.isEmpty(streetName.getText().toString())) {
            streetName.setError("Street Name is required!");
        }
        if(TextUtils.isEmpty(city.getText().toString())) {
            city.setError("City/Town is required!");
        }
        if(TextUtils.isEmpty(county.getText().toString())) {
            county.setError("County is required!");
        }
        if(TextUtils.isEmpty(postCode.getText().toString())) {
            postCode.setError("Post code is required!");
        }
        if(!isValidPostCode(postCode)) {
            postCode.setError("Enter a valid postcode!");
        }
        /*Map<String, Object> libDet = new HashMap<>();
        libDet.put("Street", streetName.getText().toString());
        libDet.put("City", city.getText().toString());
        libDet.put("County", county.getText().toString());
        libDet.put("Post Code", postCode.getText().toString());

        root.setValue(libDet);*/
    }


}