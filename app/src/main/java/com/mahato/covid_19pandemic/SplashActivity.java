package com.mahato.covid_19pandemic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.mahato.covid_19pandemic.adapter.SliderAdapter;
import com.mahato.covid_19pandemic.model.SlideItem;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SliderAdapter sliderAdapter;
    private TabLayout tab;
    private Button start;
    private TextView swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        initViews();
        //Items on page
        final List<SlideItem> content = new ArrayList<>();
        //COVID-19
        content.add(new SlideItem("Coronavirus disease (COVID-19)","Coronavirus disease (COVID-19) is an infectious disease caused by a newly discovered coronavirus.", R.drawable.covid_one));
        content.add(new SlideItem("Coronavirus disease (COVID-19)","Most people infected with the COVID-19 virus will experience mild to moderate respiratory illness and recover without requiring special treatment.  Older people, and those with underlying medical problems like cardiovascular disease, diabetes, chronic respiratory disease, and cancer are more likely to develop serious illness.", R.drawable.covid_two));
        content.add(new SlideItem("Coronavirus disease (COVID-19)","The best way to prevent and slow down transmission is be well informed about the COVID-19 virus, the disease it causes and how it spreads. Protect yourself and others from infection by washing your hands or using an alcohol based rub frequently and not touching your face. ", R.drawable.covid_three));
        content.add(new SlideItem("Coronavirus disease (COVID-19)","The COVID-19 virus spreads primarily through droplets of saliva or discharge from the nose when an infected person coughs or sneezes, so it’s important that you also practice respiratory etiquette (for example, by coughing into a flexed elbow).", R.drawable.covid_four));


        //set viewPager
        sliderAdapter = new SliderAdapter(this, content);
        viewPager.setAdapter(sliderAdapter);
        //setup the tab layout with viewPager
        tab.setupWithViewPager(viewPager);


        //tabLayout swipe
        tab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //If tab layout switch to last page the button will pop with animation
                if (tab.getPosition() == content.size() - 1){
                    animateViewIn();
                }else if (tab.getPosition() == content.size() - 2) {
                    animateViewOut();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //get start button click listener
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //it already checked the TabActivity use shared preference to know true or false
                savePrefData();
                //If yes open MainActivity
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });
    }

    private void initViews() {
        viewPager = findViewById(R.id.splash_view_pager);
        tab = findViewById(R.id.splash_tab);
        start = findViewById(R.id.splash_get_started_btn);
        swipe = findViewById(R.id.splash_swipe);
    }


    private void animateViewOut(){
        swipe.setVisibility(View.VISIBLE);
        start.setVisibility(View.GONE);
        tab.setVisibility(View.VISIBLE);
    }

    private void animateViewIn(){
        //Hiding swip right text, tabs, and set Start Button Visible
        swipe.setVisibility(View.INVISIBLE);
        start.setVisibility(View.VISIBLE);
        tab.setVisibility(View.INVISIBLE);

        ViewGroup root = findViewById(R.id.splash_btn_container);
        int count = root.getChildCount();
        float offSet = getResources().getDimensionPixelSize(R.dimen.offset);
        Interpolator interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        //duration + interpolator
        for (int i = 0; i < count; i++ ){
            View view = root.getChildAt(i);
            view.setVisibility(View.VISIBLE);
            view.setTranslationX(offSet);
            view.setAlpha(0.85f);
            view.animate()
                    .translationX(0f)
                    .translationY(0f)
                    .alpha(1f)
                    .setInterpolator(interpolator)
                    .setDuration(1000L)
                    .start();
            offSet *= 1.5f;
        }
    }

    private void savePrefData(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Splash",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Opened", true);
        editor.apply();
    }
}
