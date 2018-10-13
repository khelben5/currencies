package com.eduardodev.currencies.presentation.extension

import android.support.v4.app.Fragment
import org.jetbrains.anko.longToast


fun Fragment.longToast(message: String) {
    activity?.longToast(message)
}