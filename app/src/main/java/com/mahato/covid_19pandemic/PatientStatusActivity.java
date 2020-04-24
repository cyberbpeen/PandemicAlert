package com.mahato.covid_19pandemic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mahato.covid_19pandemic.adapter.PatientAdapter;
import com.mahato.covid_19pandemic.model.InfectionModel;
import com.mahato.covid_19pandemic.requests.CovidApi;
import com.mahato.covid_19pandemic.requests.NepalServiceGenerator;
import com.mahato.covid_19pandemic.requests.responses.RawDataResponse;
import com.mahato.covid_19pandemic.util.ConnectionDetector;
import com.mahato.covid_19pandemic.util.CustomDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientStatusActivity extends AppCompatActivity {

    private static final String TAG = "PatientStatusActivity";

    private TextView title;
    private Button btnNepalUpdates;
    private ProgressBar progressBar;
    private RecyclerView patientListView;

    private PatientAdapter adapter;

    private Handler mHandlerInternet = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_status);

        initViews();
        mInternetRunnable.run();
    }

    private Runnable mInternetRunnable = new Runnable() {
        @Override
        public void run() {
            ConnectionDetector cd = new ConnectionDetector(PatientStatusActivity.this);
            if (!cd.isConnectingToInternet()) {
                new CustomDialog(PatientStatusActivity.this).internetError();
            }
            mHandlerInternet.postDelayed(this, 8000);
        }
    };

    private void initViews() {
        title = findViewById(R.id.raw_title);
        title.setText("Infected Patient Status");
        btnNepalUpdates = findViewById(R.id.raw_nepal_update);
        btnNepalUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientStatusActivity.this, MainActivity.class));
                finish();
            }
        });
        progressBar = findViewById(R.id.raw_progress);

        patientListView = findViewById(R.id.raw_patient_list_view);
        patientListView.setLayoutManager(new LinearLayoutManager(this));
        patientListView.setHasFixedSize(true);

        isDataLoaded(false);

        loadPatientData();
    }

    private void loadPatientData() {
        CovidApi covidApi = NepalServiceGenerator.getCovidApi();
        Call<RawDataResponse> listCall = covidApi.getNepalRawData();

        listCall.enqueue(new Callback<RawDataResponse>() {
            @Override
            public void onResponse(Call<RawDataResponse> call, Response<RawDataResponse> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());

                if (response.code() == 200) {

                    adapter = new PatientAdapter(PatientStatusActivity.this, response.body().getInfectionModels());
                    patientListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    isDataLoaded(true);
                }else {
                    new CustomDialog(PatientStatusActivity.this).showErrorDialog();
                }
            }

            @Override
            public void onFailure(Call<RawDataResponse> call, Throwable t) {
                new CustomDialog(PatientStatusActivity.this).showErrorDialog();
            }
        });
    }


    private void isDataLoaded(boolean isLoaded) {
        if (isLoaded) {
            patientListView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else {
            patientListView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
