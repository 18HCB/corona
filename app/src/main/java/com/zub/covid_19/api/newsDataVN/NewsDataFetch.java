package com.zub.covid_19.api.newsDataVN;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsDataFetch {

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("@string/URL_API")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
