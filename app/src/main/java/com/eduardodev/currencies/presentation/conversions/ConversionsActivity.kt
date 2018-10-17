package com.eduardodev.currencies.presentation.conversions

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.eduardodev.currencies.R

private const val TAG_FRAGMENT_CONVERSIONS = "conversionsFragment"

class ConversionsActivity : AppCompatActivity() {

    private val conversionsFragment: Fragment?
        get() = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_CONVERSIONS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversions)
        addConversionsFragmentIfNeeded()
    }

    private fun addConversionsFragmentIfNeeded() {
        if (conversionsFragment == null)
            addConversionsFragment()
    }

    private fun addConversionsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.conversionsFragmentContainer,
                createConversionsFragment(),
                TAG_FRAGMENT_CONVERSIONS
            )
            .commit()
    }

    private fun createConversionsFragment() = ConversionsFragment.newInstance()
}
