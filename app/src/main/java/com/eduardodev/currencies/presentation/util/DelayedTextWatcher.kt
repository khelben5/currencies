package com.eduardodev.currencies.presentation.util

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher


class DelayedTextWatcher(
        private val delayInMs: Long,
        private val onTextChanged: (newText: String) -> Unit,
        private val onWaiting: () -> Unit
) : TextWatcher {

    private val handler = Handler()
    private val runnable = Runnable { onTextChanged(newText) }

    private lateinit var newText: String

    override fun afterTextChanged(s: Editable) {
        newText = s.toString()
        onWaiting()
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, delayInMs)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // Nothing to do.
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        // Nothing to do.
    }
}