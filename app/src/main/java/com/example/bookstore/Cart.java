package com.example.bookstore;

import static com.example.bookstore.R.id.increment_btn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Cart extends AppCompatActivity {
    Button increment,decrement;
    TextView qtyText;

    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final Button button = (Button) this.findViewById(R.id.proceedToCheckout);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),ShipmentDetails.class);
                startActivity(i);

            }
        });



//            increment = findViewById(R.id.increment_btn);
//            decrement = findViewById(R.id.decrement_btn);
//            qtyText = findViewById(R.id.qtyText);
//
//
//        increment.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    count++;
//                    qtyText.setText("" + count);
//                }
//            });
//            decrement.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (count <= 0) count = 0;
//                    else
//                        count--;
//                    qtyText.setText("" + count);
//                }
//            });
//

    }

}