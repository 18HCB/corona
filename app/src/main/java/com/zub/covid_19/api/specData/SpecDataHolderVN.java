package com.zub.covid_19.api.specData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpecDataHolderVN {
    @GET("vnPatientCases")
    Call<SpecDataVN> getSpecData();
}
