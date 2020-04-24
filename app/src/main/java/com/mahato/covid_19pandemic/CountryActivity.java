package com.mahato.covid_19pandemic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mahato.covid_19pandemic.adapter.CountryDataAdapter;
import com.mahato.covid_19pandemic.model.CountryModel;
import com.mahato.covid_19pandemic.requests.CovidApi;
import com.mahato.covid_19pandemic.requests.GlobalServiceGenerator;
import com.mahato.covid_19pandemic.util.ConnectionDetector;
import com.mahato.covid_19pandemic.util.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryActivity extends AppCompatActivity {

    private static final String TAG = "CountryActivity";

    private ProgressBar progressBar;
    private EditText searchBar;
    private RecyclerView countryListView;

    private CountryDataAdapter adapter;
    private List<CountryModel> mCountryList;

    private Handler mHandlerInternet = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        Button nepalUpdatesBtn = findViewById(R.id.country_nepal_update);
        nepalUpdatesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CountryActivity.this, MainActivity.class));
                finish();
            }
        });

        mInternetRunnable.run();

        progressBar = findViewById(R.id.country_progress);
        searchBar = findViewById(R.id.country_search_bar);
        countryListView = findViewById(R.id.country_list_view);
        countryListView.setLayoutManager(new LinearLayoutManager(this));
        countryListView.setHasFixedSize(true);


        isDataLoaded(false);

        loadCountryData();

        filterCountry();
    }

    private Runnable mInternetRunnable = new Runnable() {
        @Override
        public void run() {
            ConnectionDetector cd = new ConnectionDetector(CountryActivity.this);
            if (!cd.isConnectingToInternet()) {
                new CustomDialog(CountryActivity.this).internetError();
            }
            mHandlerInternet.postDelayed(this, 8000);
        }
    };

    private void loadCountryData() {
        CovidApi covidApi = GlobalServiceGenerator.getCovidApi();
        Call<List<CountryModel>> listCall = covidApi.getByCountry();

        listCall.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());

                if (response.code() == 200) {

                    mCountryList = response.body();
                    adapter = new CountryDataAdapter(CountryActivity.this, mCountryList);
                    countryListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    isDataLoaded(true);

                    for (CountryModel model : response.body()) {
                        Log.d(TAG, "onResponse: Flag " + model.getCountryInfo().getFlag());
                    }
                }else {
                    new CustomDialog(CountryActivity.this).showErrorDialog();
                }
            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {
                new CustomDialog(CountryActivity.this).showErrorDialog();
            }
        });
    }

    private void isDataLoaded(boolean isLoaded) {
        if (isLoaded) {
            countryListView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else {
            countryListView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void filterCountry() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: " + s);
                List<CountryModel> filteredCountry = new ArrayList<>();

                for (CountryModel country : mCountryList) {
                    if (country.getCountry().toLowerCase().contains(s)){
                        Log.d(TAG, "onTextChanged: Country Flag" + country.getCountry());
                        filteredCountry.add(country);
                    }
                }

                adapter.setFilteredCountry(filteredCountry);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
