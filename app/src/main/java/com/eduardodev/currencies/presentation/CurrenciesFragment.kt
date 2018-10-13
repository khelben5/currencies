package com.eduardodev.currencies.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eduardodev.currencies.R


class CurrenciesFragment : Fragment() {

    companion object {
        fun newInstance() = CurrenciesFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_currencies, container, false)
}