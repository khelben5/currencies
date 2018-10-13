package com.eduardodev.currencies.presentation.conversions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.eduardodev.currencies.data.RatesRepositoryImpl
import com.eduardodev.currencies.presentation.factory.ConversionFactory
import com.eduardodev.currencies.presentation.model.Conversion
import com.eduardodev.currencies.presentation.model.Rate
import com.eduardodev.currencies.presentation.repository.RatesRepository


class ConversionsViewModel(repository: RatesRepository = RatesRepositoryImpl()) : ViewModel() {

    private val rates: LiveData<List<Rate>> = repository.getRates()

    val conversions: LiveData<List<Conversion>> = Transformations.map(rates) {
        ConversionFactory().createFromRates(it)
    }

    fun selectCurrency() {
        // todo
    }

}