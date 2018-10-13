package com.eduardodev.currencies.presentation.repository

import android.arch.lifecycle.LiveData
import com.eduardodev.currencies.presentation.model.Rate


interface RatesRepository {
    fun getRates(): LiveData<List<Rate>>
}