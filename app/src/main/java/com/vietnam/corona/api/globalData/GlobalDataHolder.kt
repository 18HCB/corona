package com.vietnam.corona.api.globalData

import retrofit2.Call
import retrofit2.http.GET

interface GlobalDataHolder {

    @GET("/api")
    fun getGlobalData(): Call<GlobalData>

}