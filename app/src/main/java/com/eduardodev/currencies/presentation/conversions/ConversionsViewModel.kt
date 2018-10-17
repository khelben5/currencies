package com.eduardodev.currencies.presentation.conversions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.os.Handler
import com.eduardodev.currencies.data.repository.NetworkRatesRepository
import com.eduardodev.currencies.presentation.extension.asTyped
import com.eduardodev.currencies.presentation.model.ConversionList
import com.eduardodev.currencies.presentation.model.Rate
import com.eduardodev.currencies.presentation.repository.*
import java.util.*

private const val DATA_FETCH_PERIOD_MS = 1000L

class ConversionsViewModel(
        private val repository: RatesRepository = NetworkRatesRepository()
) : ViewModel() {

    private val conversionList = ConversionList()

    private val handler = Handler()
    private val loadRatesRunnable = Runnable { loadRates() }
    private val ratesObserver = Observer<DataResource>(::onRatesReceived)
    private val conversions = MutableLiveData<DataResource>()

    private var rates: LiveData<DataResource>? = null

    init {
        loadRates()
    }

    override fun onCleared() {
        super.onCleared()
        rates?.removeObserver(ratesObserver)
    }

    fun onUserBeganToWrite() {
        handler.removeCallbacks(loadRatesRunnable)
    }

    fun getConversions(): LiveData<DataResource> = conversions

    fun selectCurrency(currency: Currency) {
        conversionList.selectCurrency(currency)
        conversions.value = Success(conversionList.getList())
    }

    fun setValueForSelectedCurrency(value: Double) {
        conversionList.setValueForSelectedCurrency(value)
        conversions.value = Success(conversionList.getList())
        loadRates()
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
        conversionList.updateRates(rates)
        setupNextDataRequest()
        conversions.value = Success(conversionList.getList())
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

    private fun setupNextDataRequest() {
        handler.postDelayed(loadRatesRunnable, DATA_FETCH_PERIOD_MS)
    }
}