package com.zub.covid_19.api.regulerData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RegulerDataHolder {
    @Headers({
        "Host: localhost",
        "Origin: http://localhost",
    })
    //@GET("public/api/update.json")
    //Call<RegulerData> getRegulerData();
    @GET("trendlineVn")
    Call<RegulerDataVN> getRegulerData();

}
