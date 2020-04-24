package com.mahato.covid_19pandemic.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mahato.covid_19pandemic.model.InfectionModel;

import java.util.List;

public class RawDataResponse {

    @SerializedName("raw_data")
    @Expose
    private List<InfectionModel> infectionModels;

    public List<InfectionModel> getInfectionModels() {
        return infectionModels;
    }
}
