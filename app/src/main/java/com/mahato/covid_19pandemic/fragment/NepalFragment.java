package com.mahato.covid_19pandemic.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mahato.covid_19pandemic.R;
import com.mahato.covid_19pandemic.adapter.NepalDataAdapter;
import com.mahato.covid_19pandemic.model.StateModel;
import com.mahato.covid_19pandemic.model.TestedModel;
import com.mahato.covid_19pandemic.requests.CovidApi;
import com.mahato.covid_19pandemic.requests.NepalServiceGenerator;
import com.mahato.covid_19pandemic.requests.responses.DistrictResponse;
import com.mahato.covid_19pandemic.requests.responses.StateWiseResponse;
import com.mahato.covid_19pandemic.util.CustomDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class NepalFragment extends Fragment {

    private TextView confirmed;
    private TextView active;
    private TextView recovered;
    private TextView deaths;
    private TextView totalTested;
    private TextView totalNegative;

    private Button globalUpdateBtn;

    private RecyclerView recyclerView;

    private LinearLayout content;
    private ProgressBar progress;

    private NepalDataAdapter adapter;
    private List<StateModel> stateList;


    public NepalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nepal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        globalUpdateBtn = view.findViewById(R.id.frag_nepal_global_updates);
        globalUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame_container, new GlobalFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        confirmed = view.findViewById(R.id.frag_nepal_confirmed);
        active = view.findViewById(R.id.frag_nepal_active);
        recovered = view.findViewById(R.id.frag_nepal_recovered);
        deaths = view.findViewById(R.id.frag_nepal_deaths);
        totalTested = view.findViewById(R.id.frag_nepal_total_tested);
        totalNegative = view.findViewById(R.id.frag_nepal_total_negative);

        content = view.findViewById(R.id.frag_nepal_content);
        progress = view.findViewById(R.id.frag_nepal_progress);

        recyclerView = view.findViewById(R.id.frag_nepal_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new NepalDataAdapter(getContext());
        recyclerView.setAdapter(adapter);

        stateList = new ArrayList<>();

        isDataLoaded(false);

        loadStateData();

        loadDistrictData();

    }


    private void loadDistrictData() {
        CovidApi covidApi = NepalServiceGenerator.getCovidApi();
        Call<List<DistrictResponse>> responseCall = covidApi.getNepalDataByDistrict();

        responseCall.enqueue(new Callback<List<DistrictResponse>>() {
            @Override
            public void onResponse(Call<List<DistrictResponse>> call, Response<List<DistrictResponse>> response) {
                if (response.code() == 200) {

                    List<DistrictResponse> responseList = response.body();
                    adapter.setDistrictResponses(responseList);
                } else {
                    new CustomDialog(getActivity()).showErrorDialog();
                    // TODO HANDLE EXCEPTION
                }
            }

            @Override
            public void onFailure(Call<List<DistrictResponse>> call, Throwable t) {
                new CustomDialog(getActivity()).showErrorDialog();
            }
        });
    }

    private void loadStateData() {
        CovidApi covidApi = NepalServiceGenerator.getCovidApi();
        Call<StateWiseResponse> responseCall = covidApi.getNepalDataByState();

        responseCall.enqueue(new Callback<StateWiseResponse>() {
            @Override
            public void onResponse(Call<StateWiseResponse> call, Response<StateWiseResponse> response) {

                if (response.code() == 200) {


                    List<TestedModel> total = response.body().getTestedList();
                    if (total != null && !total.isEmpty()){
                        TestedModel model = total.get(total.size() - 1);
                        Log.d("NTAG", "onResponse: Negative : " + model.getTotalnegativetested());
                        Log.d("NTAG", "onResponse: Total : " + model.getTotalsamplestested());
                        setTestedDataToViews(model);
                    }

                    for (StateModel state : response.body().getStates()) {

                        if (state.getState().equals("Total") || state.getStatecode().equals("TT")){
                            setDataToViews(state);
                        }else {
                            //set to recycler view
                           stateList.add(state);
                        }

                        adapter.setStateList(stateList);
                        adapter.notifyDataSetChanged();
                        isDataLoaded(true);
                    }

                } else {
                    // TODO HANDLE EXCEPTION
                    new CustomDialog(getActivity()).showErrorDialog();
                }
            }

            @Override
            public void onFailure(Call<StateWiseResponse> call, Throwable t) {
                new CustomDialog(getActivity()).showErrorDialog();
            }
        });
    }

    private void setTestedDataToViews(TestedModel model) {
        totalTested.setText(model.getTotalsamplestested());
        totalNegative.setText(model.getTotalnegativetested());
    }

    private void setDataToViews(StateModel state) {
        confirmed.setText(String.valueOf(state.getConfirmed()));
        active.setText(String.valueOf(state.getActive()));
        recovered.setText(String.valueOf(state.getRecovered()));
        deaths.setText(String.valueOf(state.getDeaths()));
    }

    private void isDataLoaded(boolean isLoaded) {
        if (isLoaded) {
            content.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        }else {
            content.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
        }
    }
}
