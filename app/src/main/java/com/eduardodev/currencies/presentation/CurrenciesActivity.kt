package com.eduardodev.currencies.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.eduardodev.currencies.R

private const val TAG_FRAGMENT_CURRENCIES = "currenciesFragment"

class CurrenciesActivity : AppCompatActivity() {

    private val currenciesFragment: Fragment?
        get() = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_CURRENCIES)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currencies)
        addCurrenciesFragmentIfNeeded()
    }

    private fun addCurrenciesFragmentIfNeeded() {
        if (currenciesFragment == null)
            addCurrenciesFragment()
    }

    private fun addCurrenciesFragment() {
        supportFragmentManager.beginTransaction()
                .replace(
                        R.id.currenciesFragmentContainer,
                        createCurrenciesFragment(),
                        TAG_FRAGMENT_CURRENCIES
                )
                .commit()
    }

    private fun createCurrenciesFragment() = CurrenciesFragment.newInstance()
}
