package com.eduardodev.currencies.presentation.repository

import android.arch.lifecycle.LiveData


interface RatesRepository {
    fun getRates(): LiveData<DataResource>
}