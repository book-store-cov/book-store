package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookstore.databinding.ActivityAddBookBinding;

public class AddBook extends AppCompatActivity {
    private ActivityAddBookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}