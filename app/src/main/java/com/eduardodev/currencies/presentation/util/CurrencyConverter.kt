package com.eduardodev.currencies.presentation.util

import com.eduardodev.currencies.presentation.model.Rate
import java.util.*


class CurrencyConverter {

    private val rates = emptyList<Rate>().toMutableList()

    fun updateRates(newRates: List<Rate>) {
        rates.clear()
        rates.addAll(newRates)
    }

    fun convert(from: Currency, to: Currency, amount: Double): Double {
        val fromRate = rates.find { it.currency == from }
        val toRate = rates.find { it.currency == to }

        if (fromRate == null || toRate == null) throw CurrencyNotFoundException()

        return (amount / fromRate.value) * toRate.value
    }

    class CurrencyNotFoundException : Exception()
}