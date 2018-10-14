package com.eduardodev.currencies.presentation.conversions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.os.Handler
import com.eduardodev.currencies.data.repository.NetworkRatesRepository
import com.eduardodev.currencies.presentation.factory.ConversionFactory
import com.eduardodev.currencies.presentation.model.Rate
import com.eduardodev.currencies.presentation.repository.*

private const val DATA_FETCH_PERIOD_MS = 20000L

class ConversionsViewModel(private val repository: RatesRepository = NetworkRatesRepository()) : ViewModel() {

    private val handler = Handler()

    private val requestRates = MutableLiveData<Boolean>().apply { value = true }
    private val rates = Transformations.switchMap(requestRates) { repository.getRates() }
    val conversions: LiveData<DataResource> = Transformations.map(rates, ::onRatesReceived)

    private fun onRatesReceived(ratesResource: DataResource?) =
            when (ratesResource) {
            is Success<*> -> {
                val rates = (ratesResource.data as List<*>).mapNotNull { rate -> rate as? Rate }
                val conversions = ConversionFactory().createFromRates(rates)
                setupNextDataRequest()
                Success(conversions)
            }
                is Failure -> {
                    setupNextDataRequest()
                    Failure(ratesResource.error)
                }
            is Loading -> Loading
                else -> {
                    setupNextDataRequest()
                    Failure(Exception("resource is null"))
                }
            }

    private fun setupNextDataRequest() {
        handler.postDelayed({ requestRates.value = true }, DATA_FETCH_PERIOD_MS)
    }
}