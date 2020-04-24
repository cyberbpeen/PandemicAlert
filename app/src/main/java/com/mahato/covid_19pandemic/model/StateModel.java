package com.mahato.covid_19pandemic.model;

public class StateModel {

    private long active;
    private long confirmed;
    private long deaths;
    private long recovered;
    private String state;
    private String statecode;

    public StateModel() {
    }

    public StateModel(long active, long confirmed, long deaths, long recovered, String state, String statecode) {
        this.active = active;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.state = state;
        this.statecode = statecode;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(long confirmed) {
        this.confirmed = confirmed;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public void setRecovered(long recovered) {
        this.recovered = recovered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    @Override
    public String toString() {
        return "StateModel{" +
                "active=" + active +
                ", confirmed=" + confirmed +
                ", deaths=" + deaths +
                ", recovered=" + recovered +
                ", state='" + state + '\'' +
                ", statecode='" + statecode + '\'' +
                '}';
    }
}
