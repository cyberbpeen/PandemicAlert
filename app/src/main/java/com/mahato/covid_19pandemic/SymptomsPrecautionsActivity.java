package com.mahato.covid_19pandemic;

import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.mahato.covid_19pandemic.adapter.SymptomsPrecautionsAdapter;
import com.mahato.covid_19pandemic.fragment.PrecautionsFragment;
import com.mahato.covid_19pandemic.fragment.SymptomsFragment;

public class SymptomsPrecautionsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_precautions);

        tabLayout = findViewById(R.id.symptoms_tab_layout);
        viewPager = findViewById(R.id.symptoms_view_pager);
        closeBtn = findViewById(R.id.symptoms_close_btn);

        SymptomsPrecautionsAdapter adapter = new SymptomsPrecautionsAdapter(getSupportFragmentManager(), 2);
        adapter.addFragment(new SymptomsFragment(), "Symptoms");
        adapter.addFragment(new PrecautionsFragment(), "Precautions");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
