package com.mahato.covid_19pandemic.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mahato.covid_19pandemic.model.DistrictModel;

import java.util.List;

public class DistrictResponse {

    @SerializedName("state")
    @Expose
    private String stateName;

    @SerializedName("districtData")
    @Expose
    private List<DistrictModel> districts;

    public String getStateName() {
        return stateName;
    }

    public List<DistrictModel> getDistricts() {
        return districts;
    }

    @Override
    public String toString() {
        return "DistrictResponse{" +
                "stateName='" + stateName + '\'' +
                ", districts=" + districts +
                '}';
    }
}
