package com.eduardodev.currencies.presentation.model

import java.util.*


data class Rate(val currency: Currency, val value: Double) {
    val countryCode =
        if (currency.currencyCode == "EUR")
            "EU"
        else
            Locale.getAvailableLocales().find {
                try {
                    Currency.getInstance(it) == currency
                } catch (exception: IllegalArgumentException) {
                    false
                }
            }?.country
}