package com.mahato.covid_19pandemic.requests;

import com.mahato.covid_19pandemic.model.CountryModel;
import com.mahato.covid_19pandemic.model.Global;
import com.mahato.covid_19pandemic.model.InfectionModel;
import com.mahato.covid_19pandemic.requests.responses.DistrictResponse;
import com.mahato.covid_19pandemic.requests.responses.RawDataResponse;
import com.mahato.covid_19pandemic.requests.responses.StateWiseResponse;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface CovidApi {

    @GET("data.json")
    Call<StateWiseResponse> getNepalDataByState();

    @GET("v2/state_district_wise.json")
    Call<List<DistrictResponse>> getNepalDataByDistrict();

    @GET("raw_data.json")
    Call<RawDataResponse> getNepalRawData();

    @GET("v2/all")
    Call<Global> getGlobalData();

    @GET("v2/countries?sort=country")
    Call<List<CountryModel>> getByCountry();
}
