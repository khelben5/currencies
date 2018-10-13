package com.eduardodev.currencies.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface API {

    @GET("/latest")
    fun getRates(@Query("base") base: String): Call<RatesResponse>
}