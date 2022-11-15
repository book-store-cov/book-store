package com.example.bookstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity ;

public class SplashActivity extends AppCompatActivity{

    private static int SPLASH_SCREEN_TIMEOUT = 2000;
    @Override
     protected void onCreate (Bundle savedinstanceState){
        super.onCreate(savedinstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);

        Animation fadeout=new AlphaAnimation(1,0);
        fadeout.setInterpolator(new AccelerateInterpolator());
        fadeout.setStartOffset(500);
        fadeout.setDuration(1800);
        ImageView image=findViewById(R.id.imageView);
        image.setAnimation(fadeout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
        Intent intent = new Intent(SplashActivity.this, SigninActivity.class);
            startActivity(intent);
        finish();
            }
        }, SPLASH_SCREEN_TIMEOUT);

    }
}
