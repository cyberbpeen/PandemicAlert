package com.mahato.covid_19pandemic.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahato.covid_19pandemic.R;
import com.mahato.covid_19pandemic.model.Global;
import com.mahato.covid_19pandemic.requests.CovidApi;
import com.mahato.covid_19pandemic.requests.GlobalServiceGenerator;
import com.mahato.covid_19pandemic.util.CustomDialog;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GlobalFragment extends Fragment {

    private static final String TAG = "GlobalFragment";

    private TextView confirmed;
    private TextView todayConfirmed;
    private TextView active;
    private TextView recovered;
    private TextView critical;
    private TextView affectedCountry;
    private TextView deaths;
    private TextView todayDeaths;
    private Button nepalUpdateBtn;

    private CardView contentView;
    private ProgressBar progress;


    public GlobalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        confirmed = view.findViewById(R.id.frag_global_confirmed);
        todayConfirmed = view.findViewById(R.id.frag_global_today_confirm);
        active = view.findViewById(R.id.frag_global_active);
        recovered = view.findViewById(R.id.frag_global_recovered);
        critical = view.findViewById(R.id.frag_global_critical);
        affectedCountry = view.findViewById(R.id.frag_global_affected_country);
        deaths = view.findViewById(R.id.frag_global_deaths);
        todayDeaths = view.findViewById(R.id.frag_global_today_deaths);


        contentView = view.findViewById(R.id.frag_global_content);
        progress = view.findViewById(R.id.frag_global_progress);

        isDataLoaded(false);
        loadData();


        nepalUpdateBtn = view.findViewById(R.id.frag_global_nepal_update);
        nepalUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame_container, new NepalFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }


    private void loadData() {
        Log.d(TAG, "loadData: Start");
        CovidApi covidApi = GlobalServiceGenerator.getCovidApi();
        Call<Global> globalCall = covidApi.getGlobalData();

        globalCall.enqueue(new Callback<Global>() {
            @Override
            public void onResponse(Call<Global> call, Response<Global> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());

                if (response.code() == 200) {
                    setDataToViews(response.body());
                    isDataLoaded(true);
                }else {
                    new CustomDialog(getActivity()).showErrorDialog();
                }
            }

            @Override
            public void onFailure(Call<Global> call, Throwable t) {
                new CustomDialog(getActivity()).showErrorDialog();
            }
        });
    }

    private void setDataToViews(Global global) {
        confirmed.setText(String.valueOf(global.getCases()));
        todayConfirmed.setText(String.valueOf(global.getTodayCases()));
        active.setText(String.valueOf(global.getActive()));
        recovered.setText(String.valueOf(global.getRecovered()));
        deaths.setText(String.valueOf(global.getDeaths()));
        todayDeaths.setText(String.valueOf(global.getTodayDeaths()));
        critical.setText(String.valueOf(global.getCritical()));
        affectedCountry.setText(String.valueOf(global.getAffectedCountries()));
    }

    private void isDataLoaded(boolean isLoaded) {
        if (isLoaded) {
            contentView.setVisibility(View.VISIBLE);
            progress.setVisibility(View.INVISIBLE);
        }else {
            contentView.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);
        }
    }

}
