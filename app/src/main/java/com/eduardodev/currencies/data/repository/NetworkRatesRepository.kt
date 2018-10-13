package com.eduardodev.currencies.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.eduardodev.currencies.R
import com.eduardodev.currencies.data.extension.getRates
import com.eduardodev.currencies.data.network.NetworkAdapter
import com.eduardodev.currencies.data.network.RatesResponse
import com.eduardodev.currencies.presentation.CurrenciesApp
import com.eduardodev.currencies.presentation.repository.*
import com.eduardodev.currencies.data.network.Failure as NetworkFailure
import com.eduardodev.currencies.data.network.Loading as NetworkLoading
import com.eduardodev.currencies.data.network.Success as NetworkSuccess


class NetworkRatesRepository(
        baseUrl: String = CurrenciesApp.instance.getString(R.string.base_url)
) : RatesRepository {

    private val networkAdapter by lazy { NetworkAdapter(baseUrl) }

    override fun getRates(): LiveData<DataResource> = Transformations.map(networkAdapter.getRates()) {
        when (it) {
            is NetworkSuccess<*> -> Success((it.data as RatesResponse).rates.getRates())
            is NetworkFailure -> Failure(it.error)
            is NetworkLoading -> Loading
        }
    }
}