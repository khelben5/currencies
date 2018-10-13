package com.eduardodev.currencies.data.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class NetworkAdapter(baseUrl: String) {

    private val api by lazy {
        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
                .create(API::class.java)
    }

    fun getRates(): LiveData<NetworkResource> = MutableLiveData<NetworkResource>().apply {
        value = Loading
        api.getRates(Currency.getInstance("EUR").currencyCode).enqueue(RetrofitCallback<RatesResponse>(
                { value = Success(it) },
                { value = Failure(it) }
        ))
    }

}

