package com.eduardodev.currencies.presentation.conversions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
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
import java.util.*

private const val TEXT_DELAY_MS = 1000L
private const val SCROLL_DELAY_MS = 1000L

class ConversionsFragment : Fragment() {

    companion object {
        fun newInstance() = ConversionsFragment()
    }

    private val model by lazy {
        ViewModelProviders.of(this)[ConversionsViewModel::class.java]
    }

    private val delayedTextWatcher = DelayedTextWatcher(
        TEXT_DELAY_MS,
        ::onValueFinallyChanged,
        ::onUserBeganToWrite
    )

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

    override fun onPause() {
        super.onPause()
        model.stopNextCall()
    }

    override fun onResume() {
        super.onResume()
        model.restartNextCall()
    }

    private fun setupRecyclerView() {
        conversionsRecyclerView.setHasFixedSize(true)
        conversionsRecyclerView.adapter = ConversionsAdapter(
            ::onCurrencySelected,
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

    private fun onCurrencySelected(currency: Currency) {
        model.selectCurrency(currency)
        conversionsRecyclerView.postDelayed({
            conversionsRecyclerView.smoothScrollToPosition(0)
        }, SCROLL_DELAY_MS)
    }

    private fun onUserBeganToWrite() {
        model.onUserBeganToWrite()
    }

    private fun onValueFinallyChanged(value: String) {
        val newValue = value.toDoubleOrNull()
        if (newValue == null)
            longToast(getString(R.string.error_wrong_format))
        else
            model.setValueForSelectedCurrency(newValue)
    }
}