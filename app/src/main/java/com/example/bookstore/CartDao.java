package com.example.bookstore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartDao {
    private DatabaseReference databaseReference;

    public CartDao()
    {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(CartList.class.getSimpleName());

    }

    public Task<Void> addCartItems(CartList cartList)
    {
       return databaseReference.push().setValue(cartList);
    }
}
