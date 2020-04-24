package com.mahato.covid_19pandemic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class EmergencyContactActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CALL = 1;
    private static final String TAG = "EmergencyContactActivit";

    private ImageButton closeBtn;
    private LinearLayout ranjit;
    private LinearLayout rajesh;
    private LinearLayout dinesh;
    private LinearLayout naresh;
    private LinearLayout shrawan;

    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        TextView title = findViewById(R.id.contact_title);
        title.setText("Emergency Contact");

        initViews();
    }

    private void initViews() {
        closeBtn = findViewById(R.id.contact_close_btn);
        rajesh = findViewById(R.id.contact_rajesh);
        ranjit = findViewById(R.id.contact_ranjit);
        shrawan = findViewById(R.id.contact_shrawan);
        dinesh = findViewById(R.id.contact_dinesh);
        naresh = findViewById(R.id.contact_naresh);

        closeBtn.setOnClickListener(this);
        rajesh.setOnClickListener(this);
        ranjit.setOnClickListener(this);
        shrawan.setOnClickListener(this);
        dinesh.setOnClickListener(this);
        naresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == closeBtn) {
            onBackPressed();
        } else if (v == rajesh) {
            // Call Dr. Rajesh
            number = "9851239988";
            makeCall();
        } else if (v == ranjit) {
            // Call Dr. Ranjit
            number = "9872701465";
            makeCall();
        } else if (v == shrawan) {
            // Call Dr. Shrawan
            number = "9851168220";
            makeCall();
        } else if (v == dinesh) {
            // Call Dr. Dinesh
            number = "9823168540";
            makeCall();
        } else if (v == naresh) {
            // Call Dr. Naresh
            number = "9803152149";
            makeCall();
        }
    }

    private void makeCall() {
        if (ContextCompat.checkSelfPermission(EmergencyContactActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EmergencyContactActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
                Log.d(TAG, "onRequestPermissionsResult: " + number);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
