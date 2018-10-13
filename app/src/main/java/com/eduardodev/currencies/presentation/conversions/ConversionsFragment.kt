package com.eduardodev.currencies.presentation.conversions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eduardodev.currencies.R
import com.eduardodev.currencies.presentation.model.Conversion


class ConversionsFragment : Fragment() {

    companion object {
        fun newInstance() = ConversionsFragment()
    }

    private val model by lazy {
        ViewModelProviders.of(this)[ConversionsViewModel::class.java]
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_conversions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.conversions.observe(this, Observer {
            it?.let { conversions -> onConversionsUpdate(conversions) }
        })
    }

    private fun onConversionsUpdate(conversions: List<Conversion>) {
        // todo
    }
}