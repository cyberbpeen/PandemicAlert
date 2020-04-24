package com.mahato.covid_19pandemic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.mahato.covid_19pandemic.fragment.NepalFragment;
import com.mahato.covid_19pandemic.util.ConnectionDetector;
import com.mahato.covid_19pandemic.util.CustomDialog;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Handler mHandlerInternet = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_menu_btn)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }
                });

        setFragment(new NepalFragment());

        mInternetRunnable.run();
    }

    private Runnable mInternetRunnable = new Runnable() {
        @Override
        public void run() {
            ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
            if (!cd.isConnectingToInternet()) {
                new CustomDialog(MainActivity.this).internetError();
            }
            mHandlerInternet.postDelayed(this, 50000);
        }
    };

    private void setFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame_container, fragment);
        transaction.commit();
    }

    Boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}
