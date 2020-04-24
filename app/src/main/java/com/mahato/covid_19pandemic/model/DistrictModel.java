package com.mahato.covid_19pandemic.model;

public class DistrictModel {

    private String district;
    private long confirmed;

    public DistrictModel() {
    }

    public DistrictModel(String district, long confirmed) {
        this.district = district;
        this.confirmed = confirmed;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(long confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public String toString() {
        return "DistrictModel{" +
                "district='" + district + '\'' +
                ", confirmed=" + confirmed +
                '}';
    }
}
