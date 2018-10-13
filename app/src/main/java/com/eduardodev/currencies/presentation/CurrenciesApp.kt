package com.eduardodev.currencies.presentation

import android.app.Application


class CurrenciesApp : Application() {

    companion object {
        lateinit var instance: CurrenciesApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}