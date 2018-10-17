package com.eduardodev.currencies.presentation.conversions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eduardodev.currencies.R
import com.eduardodev.currencies.presentation.extension.asTyped
import com.eduardodev.currencies.presentation.extension.longToast
import com.eduardodev.currencies.presentation.model.Conversion
import com.eduardodev.currencies.presentation.repository.DataResource
import com.eduardodev.currencies.presentation.repository.Failure
import com.eduardodev.currencies.presentation.repository.Loading
import com.eduardodev.currencies.presentation.repository.Success
import com.eduardodev.currencies.presentation.util.DelayedTextWatcher
import kotlinx.android.synthetic.main.fragment_conversions.*

private const val TEXT_DELAY_MS = 500L

class ConversionsFragment : Fragment() {

    companion object {
        fun newInstance() = ConversionsFragment()
    }

    private val model by lazy {
        ViewModelProviders.of(this)[ConversionsViewModel::class.java]
    }

    private val delayedTextWatcher = DelayedTextWatcher(TEXT_DELAY_MS) {
        val newValue = it.toDoubleOrNull()
        if (newValue == null)
            longToast(getString(R.string.error_wrong_format))
        else
            model.updateConversionsWithValue(newValue)
    }

    private val adapter get() = conversionsRecyclerView.adapter as ConversionsAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_conversions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        model.getConversions().observe(this, Observer {
            it?.let { resource -> onConversionsUpdate(resource) }
        })
    }

    private fun setupRecyclerView() {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        conversionsRecyclerView.setHasFixedSize(true)
        conversionsRecyclerView.addItemDecoration(decoration)
        conversionsRecyclerView.adapter = ConversionsAdapter(
                ::onConversionSelected,
                delayedTextWatcher
        )
    }

    private fun onConversionsUpdate(resource: DataResource) {
        when (resource) {
            is Success<*> -> {
                stopLoading()
                val conversions = (resource.data as List<*>).asTyped(Conversion::class)
                adapter.updateConversions(conversions)
            }
            is Failure -> {
                stopLoading()
                longToast(getString(R.string.error_message))
            }
            is Loading -> {
                // Nothing to do.
            }
        }
    }

    private fun stopLoading() {
        conversionsProgress.visibility = View.GONE
    }

    private fun onConversionSelected(conversion: Conversion) {
        conversionsRecyclerView.smoothScrollToPosition(0)
        model.selectConversion(conversion)
    }
}