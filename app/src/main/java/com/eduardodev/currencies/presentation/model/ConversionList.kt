package com.eduardodev.currencies.presentation.model

import com.eduardodev.currencies.presentation.extension.moveItemToFirstPosition
import com.eduardodev.currencies.presentation.util.CurrencyConverter
import java.util.*


class ConversionList {

    private val conversions = emptyList<Conversion>().toMutableList()
    private val converter = CurrencyConverter()

    fun getList(): List<Conversion> = conversions

    fun getSelectedCurrency() = conversions.firstOrNull()?.rate?.currency

    fun updateRates(newRates: List<Rate>) {
        val selectedCurrency = getSelectedCurrency()
        val newConversions = newRates.map { Conversion(it, it.value) }

        converter.updateRates(newRates)
        updateConversions(newConversions)
        selectedCurrency?.let { selectCurrency(it) }
    }

    fun selectCurrency(currency: Currency) {
        conversions.moveItemToFirstPosition { it.rate.currency == currency }
    }

    fun setValueForSelectedCurrency(value: Double) {
        val selectedCurrency = getSelectedCurrency() ?: return
        conversions[0] = conversions.first().copy(value = value)
        updateConversions(conversions.map {
            it.copy(value = converter.convert(selectedCurrency, it.rate.currency, value))
        })
    }

    fun valueForCurrency(currency: Currency) = conversions.find { it.rate.currency == currency }?.value

    private fun updateConversions(newConversions: List<Conversion>) {
        conversions.clear()
        conversions.addAll(newConversions)
    }
}