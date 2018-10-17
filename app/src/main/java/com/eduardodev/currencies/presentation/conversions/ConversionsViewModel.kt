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
import com.eduardodev.currencies.presentation.util.CurrencyConverter

private const val DATA_FETCH_PERIOD_MS = 1000L

class ConversionsViewModel(
        private val repository: RatesRepository = NetworkRatesRepository()
) : ViewModel() {

    private val handler = Handler()
    private val ratesObserver = Observer<DataResource>(::onRatesReceived)
    private val conversions = MutableLiveData<DataResource>()
    private val currencyConverter = CurrencyConverter()
    private val selectedConversion
        get() = extractConversionsFromResource(conversions.value).firstOrNull()

    private var rates: LiveData<DataResource>? = null

    init {
        loadRates()
    }

    override fun onCleared() {
        super.onCleared()
        rates?.removeObserver(ratesObserver)
    }

    fun getConversions(): LiveData<DataResource> = conversions

    fun selectConversion(conversion: Conversion) {
        conversions.value = Success(extractConversionsFromResource(conversions.value)
                .moveItemToFirstPosition { it.rate.currency == conversion.rate.currency })
    }

    fun updateConversionsWithValue(newValue: Double) {
        selectedConversion?.let { selectedConversion ->
            conversions.value = Success(extractConversionsFromResource(conversions.value).map {
                it.copy(value = currencyConverter.convert(
                        selectedConversion.rate.currency,
                        it.rate.currency,
                        newValue
                ))
            })
        }
    }

    private fun loadRates() {
        rates?.removeObserver(ratesObserver)
        rates = repository.getRates()
        rates!!.observeForever(ratesObserver)
    }

    private fun onRatesReceived(ratesResource: DataResource?) {
        when (ratesResource) {
            is Success<*> -> onSuccessResource(ratesResource)
            is Failure -> onFailureResource(ratesResource)
            is Loading -> onLoadingResource()
            null -> onNullResource()
        }
    }

    private fun onSuccessResource(ratesResource: Success<*>) {
        val rates = (ratesResource.data as List<*>).asTyped(Rate::class)
        val conversions = ConversionFactory().createFromRates(rates)
        currencyConverter.updateRates(rates)
        setupNextDataRequest()

        this.conversions.value = Success(
                selectedConversion?.let { selectedConversion ->
                    conversions.moveItemToFirstPosition {
                        selectedConversion.rate.currency == it.rate.currency
                    }
                } ?: conversions
        )
    }

    private fun onFailureResource(ratesResource: Failure) {
        setupNextDataRequest()
        this.conversions.value = Failure(ratesResource.error)
    }

    private fun onNullResource() {
        setupNextDataRequest()
        this.conversions.value = Failure(Exception("resource is null"))
    }

    private fun onLoadingResource() {
        this.conversions.value = Loading
    }

    private fun extractConversionsFromResource(resource: DataResource?) =
            ((resource as? Success<*>)?.data as? List<*>)?.asTyped(Conversion::class) ?: emptyList()

    private fun setupNextDataRequest() {
        handler.postDelayed(::loadRates, DATA_FETCH_PERIOD_MS)
    }
}