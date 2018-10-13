package com.eduardodev.currencies.presentation.conversions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.eduardodev.currencies.data.repository.NetworkRatesRepository
import com.eduardodev.currencies.presentation.factory.ConversionFactory
import com.eduardodev.currencies.presentation.model.Rate
import com.eduardodev.currencies.presentation.repository.*


class ConversionsViewModel(repository: RatesRepository = NetworkRatesRepository()) : ViewModel() {

    private val rates: LiveData<DataResource> = repository.getRates()

    val conversions: LiveData<DataResource> = Transformations.map(rates) {
        when (it) {
            is Success<*> -> {
                val rates = (it.data as List<*>).mapNotNull { rate -> rate as? Rate }
                val conversions = ConversionFactory().createFromRates(rates)
                Success(conversions)
            }
            is Failure -> Failure(it.error)
            is Loading -> Loading
        }
    }

}