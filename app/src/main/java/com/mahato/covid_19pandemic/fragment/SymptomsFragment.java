package com.mahato.covid_19pandemic.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahato.covid_19pandemic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SymptomsFragment extends Fragment {

    public SymptomsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_symptoms, container, false);
    }
}
