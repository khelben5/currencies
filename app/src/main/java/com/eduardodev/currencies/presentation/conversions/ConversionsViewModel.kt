package com.eduardodev.currencies.presentation.conversions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.os.Handler
import com.eduardodev.currencies.data.repository.NetworkRatesRepository
import com.eduardodev.currencies.presentation.extension.asTyped
import com.eduardodev.currencies.presentation.extension.moveItemToFirstPosition
import com.eduardodev.currencies.presentation.factory.ConversionFactory
import com.eduardodev.currencies.presentation.model.Conversion
import com.eduardodev.currencies.presentation.model.Rate
import com.eduardodev.currencies.presentation.repository.*

private const val DATA_FETCH_PERIOD_MS = 20000L

class ConversionsViewModel(
        private val repository: RatesRepository = NetworkRatesRepository()
) : ViewModel() {

    private val handler = Handler()
    private val ratesObserver = Observer<DataResource>(::onRatesReceived)
    private val conversions = MutableLiveData<DataResource>()
    private var rates: LiveData<DataResource>? = null
    private var selectedConversion: Conversion? = null

    init {
        loadRates()
    }

    override fun onCleared() {
        super.onCleared()
        rates?.removeObserver(ratesObserver)
    }

    fun getConversions(): LiveData<DataResource> = conversions

    fun selectConversion(selectedConversion: Conversion) {
        this.selectedConversion = selectedConversion
        val modifiedConversions = extractConversionsFromResource(conversions.value)
                .updateWithSelected(selectedConversion)
        this.conversions.value = Success(modifiedConversions)
    }

    private fun loadRates() {
        rates?.removeObserver(ratesObserver)
        rates = repository.getRates()
        rates!!.observeForever(ratesObserver)
    }

    private fun onRatesReceived(ratesResource: DataResource?) {
        conversions.value = when (ratesResource) {
            is Success<*> -> {
                val rates = (ratesResource.data as List<*>).asTyped(Rate::class)
                val conversions = ConversionFactory().createFromRates(rates)
                setupNextDataRequest()
                Success(selectedConversion
                        ?.let { conversions.updateWithSelected(it) }
                        ?: conversions)
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
    }

    private fun extractConversionsFromResource(resource: DataResource?) =
            ((resource as? Success<*>)?.data as? List<*>)?.asTyped(Conversion::class) ?: emptyList()

    private fun List<Conversion>.updateWithSelected(selectedConversion: Conversion) =
            moveItemToFirstPosition {
                it.rate.currency.currencyCode == selectedConversion.rate.currency.currencyCode
            }

    private fun setupNextDataRequest() {
        handler.postDelayed(::loadRates, DATA_FETCH_PERIOD_MS)
    }
}