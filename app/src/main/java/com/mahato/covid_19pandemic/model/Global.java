package com.mahato.covid_19pandemic.model;

public class Global {

    private long cases;
    private long todayCases;
    private long deaths;
    private long todayDeaths;
    private long recovered;
    private long active;
    private long critical;
    private long tests;
    private long affectedCountries;

    public Global() {
    }

    public long getCases() {
        return cases;
    }

    public long getTodayCases() {
        return todayCases;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getTodayDeaths() {
        return todayDeaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public long getActive() {
        return active;
    }

    public long getCritical() {
        return critical;
    }

    public long getTests() {
        return tests;
    }

    public long getAffectedCountries() {
        return affectedCountries;
    }
}
