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
import kotlinx.android.synthetic.main.fragment_conversions.*


class ConversionsFragment : Fragment() {

    companion object {
        fun newInstance() = ConversionsFragment()
    }

    private val model by lazy {
        ViewModelProviders.of(this)[ConversionsViewModel::class.java]
    }

    private val adapter get() = conversionsRecyclerView.adapter as ConversionsAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_conversions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        conversionsRecyclerView.adapter = ConversionsAdapter()
        model.conversions.observe(this, Observer {
            it?.let { conversions -> onConversionsUpdate(conversions) }
        })
    }

    private fun onConversionsUpdate(conversions: List<Conversion>) {
        adapter.updateConversions(conversions)
    }
}