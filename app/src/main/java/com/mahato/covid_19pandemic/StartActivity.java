package com.mahato.covid_19pandemic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (restorePrefData()) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(StartActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }

    private boolean restorePrefData() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Splash", MODE_PRIVATE);
        boolean activityOpen;
        activityOpen = preferences.getBoolean("Opened", false);
        return activityOpen;
    }
}
