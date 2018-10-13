package com.eduardodev.currencies.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.eduardodev.currencies.presentation.model.Rate
import com.eduardodev.currencies.presentation.repository.RatesRepository


class RatesRepositoryImpl : RatesRepository {

    override fun getRates(): LiveData<List<Rate>> {
        // fixme
        return MutableLiveData<List<Rate>>().apply {
            value = listOf(Rate("GBP", 1.234), Rate("EUR", 2.34))
        }
    }
}