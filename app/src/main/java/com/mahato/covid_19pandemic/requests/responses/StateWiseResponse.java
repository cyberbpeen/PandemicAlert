package com.mahato.covid_19pandemic.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mahato.covid_19pandemic.model.StateModel;
import com.mahato.covid_19pandemic.model.TestedModel;

import java.util.List;

public class StateWiseResponse {

    @SerializedName("statewise")
    @Expose
    private List<StateModel> states;

    @SerializedName("tested")
    @Expose
    private List<TestedModel> testedList;

    public List<StateModel> getStates() {
        return states;
    }

    public List<TestedModel> getTestedList() {
        return testedList;
    }

    @Override
    public String toString() {
        return "StateWiseResponse{" +
                "states=" + states +
                ", testedList=" + testedList +
                '}';
    }
}
