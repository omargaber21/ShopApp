package com.example.omar.pushnpull;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_TIME =2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent StartActivity =new Intent(SplashScreen.this,StartActivity.class);
                startActivity(StartActivity);
                finish();

            }
        },SPLASH_DISPLAY_TIME);
    }
}
