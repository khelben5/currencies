package com.eduardodev.currencies.presentation.factory

import com.eduardodev.currencies.presentation.model.Conversion
import com.eduardodev.currencies.presentation.model.Rate


class ConversionFactory {

    fun createFromRates(rates: List<Rate>) = rates.map(::createFromRate)

    private fun createFromRate(rate: Rate) = Conversion(rate.code)
}