package com.zub.covid_19.api.regulerData;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegulerDataFetch {


    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
