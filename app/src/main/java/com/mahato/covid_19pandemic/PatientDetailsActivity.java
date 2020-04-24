package com.mahato.covid_19pandemic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mahato.covid_19pandemic.model.InfectionModel;

public class PatientDetailsActivity extends AppCompatActivity {

    private static final String TAG = "PatientDetailsActivity";

    private TextView mPatientNumber;
    private TextView mAge;
    private TextView mStatus;
    private TextView mDetectedDate;
    private TextView mDetectedCity;
    private TextView mDetectedState;
    private TextView mDetectedDistrict;
    private TextView mGender;
    private TextView mNationality;
    private TextView mStChangeDate;
    private TextView mType;
    private TextView mNote;
    private TextView mBackupNote;

    private Button nepalUpdates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        Intent intent = getIntent();
        InfectionModel infection = intent.getParcelableExtra("Patient");

        initViews();

        setDataToViews(infection);

        nepalUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDetailsActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private void initViews() {
        mPatientNumber = findViewById(R.id.pd_number);
        mAge = findViewById(R.id.pd_age);
        mStatus = findViewById(R.id.pd_current_status);
        mDetectedDate = findViewById(R.id.pd_detected_date);
        mDetectedCity = findViewById(R.id.pd_detected_city);
        mDetectedState = findViewById(R.id.pd_detected_state);
        mDetectedDistrict = findViewById(R.id.pd_detected_district);
        mGender = findViewById(R.id.pd_gender);
        mNationality = findViewById(R.id.pd_nationality);
        mStChangeDate = findViewById(R.id.pd_status_change_date);
        mType = findViewById(R.id.pd_transmission_type);
        mNote = findViewById(R.id.pd_notes);
        mBackupNote = findViewById(R.id.pd_backup_notes);

        nepalUpdates = findViewById(R.id.pd_nepal_update);
    }

    private void setDataToViews(InfectionModel infection) {
        String number = infection.getPatientnumber();
        String age = infection.getAgebracket();
        String status = infection.getCurrentstatus();
        String detectedDate = infection.getDateannounced();
        String detectedCity = infection.getDetectedcity();
        String detectedState = infection.getDetectedstate();
        String detectedDistrict = infection.getDetecteddistrict();
        String gender = infection.getGender();
        String nationality = infection.getNationality();
        String stChangeDate = infection.getStatuschangedate();
        String type = infection.getTypeoftransmission();
        String note = infection.getNotes();
        String backupNote = infection.getBackupnotes();

        if (number != null){
            mPatientNumber.setText( "Patient " + number);
        }

        if (status.equals("Recovered")){
            mPatientNumber.setTextColor(Color.parseColor("#228C22"));
        }else {
            mPatientNumber.setTextColor(Color.parseColor("#FF3B00"));
        }

        if (age != null){
            mAge.setText(age);
        }

        if (status != null){
            mStatus.setText(status);
        }

        if (detectedDate != null){
            mDetectedDate.setText(detectedDate);
        }

        if (detectedCity != null){
            mDetectedCity.setText(detectedCity);
        }

        if (detectedState != null){
            mDetectedState.setText(detectedState);
        }

        if (detectedDistrict != null){
            mDetectedDistrict.setText(detectedDistrict);
        }

        if (gender != null){
            mGender.setText(gender);
        }

        if (nationality != null){
            mNationality.setText(nationality);
        }

        if (stChangeDate != null){
            mStChangeDate.setText(stChangeDate);
        }

        if (type != null){
            mType.setText(type);
        }

        if (note != null){
            mNote.setText(note);
        }

        if (backupNote != null){
            mBackupNote.setText(backupNote);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
