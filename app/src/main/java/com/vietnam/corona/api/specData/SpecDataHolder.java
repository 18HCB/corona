package com.vietnam.corona.api.specData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpecDataHolder {

    @GET("public/api/data.json")
    Call<SpecData> getSpecData();

}
