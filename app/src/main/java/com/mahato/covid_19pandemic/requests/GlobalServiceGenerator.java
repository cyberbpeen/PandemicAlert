package com.mahato.covid_19pandemic.requests;

import com.mahato.covid_19pandemic.util.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalServiceGenerator {
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.GLOBAL_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static CovidApi covidApi = retrofit.create(CovidApi.class);

    public static CovidApi getCovidApi() {
        return covidApi;
    }
}
